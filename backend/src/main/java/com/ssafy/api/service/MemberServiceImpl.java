package com.ssafy.api.service;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.api.response.MemberModifyUpdateRes;
import com.ssafy.api.response.MemberReadGetRes;
import com.ssafy.api.response.MemberSignupPostRes;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.common.custom.UnAuthorizedException;
import com.ssafy.common.util.JwtUtil;
import com.ssafy.db.entity.Favor;
import com.ssafy.db.entity.Instructor;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Tag;
import com.ssafy.db.entity.enums.Role;
import com.ssafy.db.repository.FavorRepository;
import com.ssafy.db.repository.InstructorRepository;
import com.ssafy.db.repository.MemberRepository;
import com.ssafy.db.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final InstructorRepository instructorRepository;
    private final TagRepository tagRepository;
    private final FavorRepository favorRepository;
    private final JwtUtil jwtUtil;

    // 회원가입
    @Override
    public void signup(MemberSignupPostReq memberSignupPostReq) {
        Member member = MemberSignupPostRes.of(memberSignupPostReq);
        List<Long> tags = memberSignupPostReq.getTags();

        for (Long tagId : tags) {
            Tag tag = tagRepository.findById(tagId).orElseThrow(
                    () -> new NotFoundException("존재하지 않는 태그입니다")
            );

            saveFavor(member, tag);
        }
        if (member.getRole().equals(Role.Instructor)) {
            Instructor instructor = new Instructor();
            instructor.setId(member.getId());
            instructor.setMember(member);
            instructor.setDescription("");
            instructorRepository.save(instructor);
        }

        memberRepository.save(member);
    }

    // 회원탈퇴
    @Override
    public void withdrawMemberByToken(String token) {
        String accessToken = token.replace("Bearer ", "");
        // 만료된 토큰이거나 블랙리스트에 등록된 토큰이라면
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnAuthorizedException("Unauthorized access");
        }

        Long memberId = jwtUtil.extractId(accessToken);
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("Member not found with id: " + memberId));

        // 회원의 Favor 모두 찾아서 석제
        List<Favor> favors = favorRepository.findByMemberId(memberId);
        for (Favor favor : favors) {
            favorRepository.delete(favor);
        }

        // refresh token 제거
        member.setRefreshToken(null);

        // 회원 정보 삭제
        memberRepository.delete(member);
    }

    // 마이페이지
    @Override
    public MemberReadGetRes getMyInfo(Long memberId, String accessToken) {
        // 만료된 토큰이거나 블랙리스트에 등록된 토큰이라면
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnAuthorizedException("Unauthorized access");
        }

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("Not Found Member : Member_id is " + memberId)
        );

        // 해당 member가 선호하는 태그를 DB에서 찾음
        List<Favor> favors = favorRepository.findByMemberId(member.getId());

        // 선호하는 태그의 이름을 DB에서 찾음
        List<String> tagNameList = new ArrayList<>();
        for (Favor favor : favors) {
            String tagName = favor.getTag().getName();
            tagNameList.add(tagName);
        }
        return MemberReadGetRes.of(member, tagNameList);
    }

    // 회원정보수정
    @Override
    public MemberModifyUpdateRes modifyMember(Long memberId, MemberModifyUpdateReq memberModifyUpdateReq) {
        // DB에서 해당 회원 조회
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 회원입니다.")
        );

        // 회원의 Favor 모두 찾아서 석제
        List<Favor> favors = favorRepository.findByMemberId(member.getId());
        for (Favor favor : favors) {
            favorRepository.delete(favor);
        }

        // 수정된 Favor을 DB에 반영
        List<Long> tags = memberModifyUpdateReq.getTags();

        // 선호하는 태그의 이름을 DB에서 찾음
        List<String> tagNameList = new ArrayList<>();
        for (Long tagId : tags) {
            Tag tag = tagRepository.findById(tagId).orElseThrow(
                    () -> new NotFoundException("Not Found Tag : Tag is " + tagId)
            );

            saveFavor(member, tag);
            tagNameList.add(tag.getName());
        }

        // member의 정보를 수정
        MemberModifyUpdateRes.of(memberModifyUpdateReq, member);

        // 수정된 정보를 바탕으로 새로운 access / refresh 토큰 발급
        String newAccessToken = jwtUtil.generateAccessToken(member);
        String newRefreshToken = jwtUtil.generateRefreshToken(memberId);

        // 새로 발급 받은 refresh 토큰을 DB에 저장
        member.setRefreshToken(newRefreshToken);

        // 수정된 정보를 DB에 반영
        memberRepository.save(member);

        // 회원정보, 새로 발급받은 access 토큰, 선호하는 태그 이름을 반환하는 객체
        return MemberModifyUpdateRes.toEntity(member, newAccessToken, tagNameList);
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("존재하지 않는 회원입니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 회원입니다."));
    }

    @Override
    @Transactional
    public void saveFavor(Member member, Tag tag) {
        Favor favor = new Favor();
        favor.setMember(member);
        favor.setTag(tag);
        favorRepository.save(favor);
    }
}