package com.ssafy.api.service;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.api.response.MemberModifyUpdateRes;
import com.ssafy.api.response.MemberReadGetRes;
import com.ssafy.api.response.MemberSignupPostRes;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.common.util.JwtUtil;
import com.ssafy.db.entity.Favor;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Tag;
import com.ssafy.db.repository.FavorRepository;
import com.ssafy.db.repository.MemberRepository;
import com.ssafy.db.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final FavorRepository favorRepository;
    private final JwtUtil jwtUtil;

    // 회원가입
    @Override
    public void signup(MemberSignupPostReq memberSignupPostReq) {
        Member member = MemberSignupPostRes.of(memberSignupPostReq);
        Long memberId = member.getId();
        List<Long> tags = memberSignupPostReq.getTags();

        for (Long tagId : tags) {
            Tag tag = tagRepository.findById(tagId).orElseThrow(
                    () -> new NotFoundException("Not Found Tag : Tag is " + tagId)
            );

            Favor favor = new Favor();
            favor.setMember(member);
            favor.setTag(tag);
            favorRepository.save(favor);
        }
        memberRepository.save(member);
    }

//    // 이메일 중복체크
//    @Override
//    @Transactional(readOnly = true)
//    public boolean checkEmail(String email) {
//
//        Optional<Member> member = memberRepository.findByEmail(email);
//        return member.isPresent();
//    }

    // 회원탈퇴
    @Override
    public void withdrawMemberByToken(String token) {
        String accessToken = token.replace("Bearer ", "");
        if (!jwtUtil.validateToken(accessToken)) {
            throw new IllegalArgumentException("Invalid token");
        }

        Long memberId = jwtUtil.extractId(accessToken);
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + memberId));

        // refresh token 제거
        member.setRefreshToken(null);

        // 회원 정보 삭제
        memberRepository.delete(member);
    }

    // 회원정보수정
    @Override
    public Member modifyMember(Long memberId, MemberModifyUpdateReq memberModifyUpdateReq) {
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
        for (Long tagId : tags) {
            Tag tag = tagRepository.findById(tagId).orElseThrow(
                    () -> new NotFoundException("Not Found Tag : Tag is " + tagId)
            );
            Favor favor = new Favor();
            favor.setMember(member);
            favor.setTag(tag);
            favorRepository.save(favor);
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
        return member;
    }

    // 마이페이지
    @Override
    public MemberReadGetRes getMyInfo(Long memberId, String accessToken) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("Not Found Member : Member_id is " + memberId)
        );

//        // accessToken이 만료된 경우
//        if (jwtUtil.isTokenExpired(accessToken)) {
//            // refresh 토큰이 만료되지 않았다면
//            if (!jwtUtil.isTokenExpired(member.getRefreshToken())) {
//
//            }
//        }
        return MemberReadGetRes.of(member);
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
}