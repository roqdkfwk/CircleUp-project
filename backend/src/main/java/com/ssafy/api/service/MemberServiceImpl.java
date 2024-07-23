package com.ssafy.api.service;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.api.response.MemberReadGetRes;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.common.util.JwtUtil;
import com.ssafy.db.entity.Member;
import com.ssafy.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    // 회원가입
    @Override
    public void signup(MemberSignupPostReq memberSignupPostReq) {

        Member member = Member.builder()
                .email(memberSignupPostReq.getEmail())
                .pw(memberSignupPostReq.getPw())
                .name(memberSignupPostReq.getName())
                .role(memberSignupPostReq.getRole())
                .tel(memberSignupPostReq.getTel())
                .contact(memberSignupPostReq.getContact())
                .build();

        memberRepository.save(member);
    }

    // 이메일 중복체크
    @Override
    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {

        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }

    // 회원탈퇴
    @Override
    public void withdrawMemberByToken(String token) {

        String accessToken = token.replace("Bearer ", "");

        if (!jwtUtil.validateToken(accessToken)) {
            throw new IllegalArgumentException("Invalid token");
        }

        Long memberId = jwtUtil.extractId(accessToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + memberId));

        // refresh token 제거
        member.setRefreshToken(null);

        // 회원 정보 삭제
        memberRepository.delete(member);
    }

    // 회원정보수정
    @Override
    public Member modifyMember(Long memberId, MemberModifyUpdateReq memberModifyUpdateReq) {

        // DB에서 해당 회원 조회
        Optional<Member> member = memberRepository.findById(memberId);

        // 존재하지 않는 회원인 경우
        if (!member.isPresent()) {
            throw new IllegalArgumentException("해당 회원이 존재하지 않습니다.");
        }

        String email = member.get().getEmail();

        // 수정된 정보를 받은 회원 modifiedMember
        Member modifiedMember = Member.modifiedMember(memberModifyUpdateReq, memberId, email);

        // 수정된 정보를 바탕으로 새로운 access / refresh 토큰 발급
        String newAccessToken = jwtUtil.generateAccessToken(modifiedMember);
        String newRefreshToken = jwtUtil.generateRefreshToken(memberId);

        // 새로 발급 받은 refresh 토큰을 DB에 저장
        modifiedMember.setRefreshToken(newRefreshToken);

        // 수정된 정보를 DB에 반영
        memberRepository.save(modifiedMember);
        return modifiedMember;
    }

    // 마이페이지
    @Override
    public MemberReadGetRes getMyInfo(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("Not Found Member : Member_id is " + memberId)
        );

        return MemberReadGetRes.of(member);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {

        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (memberOptional.isPresent()) {
            return memberOptional.get();
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId) {

        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isPresent()) {
            return memberOptional.get();
        } else {
            throw new EntityNotFoundException("User not found with id: " + memberId);
        }
    }
}