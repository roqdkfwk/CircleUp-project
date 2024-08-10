package com.ssafy.api.service;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.api.response.MemberModifyUpdateRes;
import com.ssafy.api.response.MemberRes;
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
        Member member = MemberSignupPostReq.toEntity(memberSignupPostReq);
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
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnAuthorizedException("Unauthorized access");
        }

        Long memberId = jwtUtil.extractId(accessToken);
        Member member = getMemberById(memberId);

//        // 회원의 Favor 모두 찾아서 석제
//        List<Favor> favors = favorRepository.findByMemberId(memberId);
//        for (Favor favor : favors) {
//            favorRepository.delete(favor);
//        }
//        member.setRefreshToken(null);

        memberRepository.delete(member);
    }

    // 마이페이지
    @Override
    public MemberRes getMyInfo(Long memberId, String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) {
            throw new UnAuthorizedException("Unauthorized access");
        }
        Member member = getMemberById(memberId);
        List<Favor> favors = favorRepository.findByMemberId(member.getId());
        List<String> tagNameList = new ArrayList<>();
        for (Favor favor : favors) {
            String tagName = favor.getTag().getName();
            tagNameList.add(tagName);
        }

        return MemberRes.fromEntity(member, tagNameList);
    }

    // 회원정보수정
    @Override
    public MemberModifyUpdateRes modifyMember(Long memberId, MemberModifyUpdateReq memberModifyUpdateReq) {
        Member member = getMemberById(memberId);
        List<Favor> favors = favorRepository.findByMemberId(member.getId());
        for (Favor favor : favors) {
            favorRepository.delete(favor);
        }

        List<Long> tags = memberModifyUpdateReq.getTags();;
        List<String> tagNameList = new ArrayList<>();
        for (Long tagId : tags) {
            Tag tag = tagRepository.findById(tagId).orElseThrow(
                    () -> new NotFoundException("Not Found Tag : Tag is " + tagId)
            );

            saveFavor(member, tag);
            tagNameList.add(tag.getName());
        }
        MemberModifyUpdateReq.toEntity(memberModifyUpdateReq, member);
        String newAccessToken = jwtUtil.generateAccessToken(member);
        String newRefreshToken = jwtUtil.generateRefreshToken(memberId);
        member.setRefreshToken(newRefreshToken);
        memberRepository.save(member);

        return MemberModifyUpdateRes.fromEntity(member, newAccessToken, tagNameList);
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