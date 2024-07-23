package com.ssafy.api.service;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.api.response.MemberReadGetRes;
import com.ssafy.common.exception.handler.NotFoundException;
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
    public Member modifyMember(String token, MemberModifyUpdateReq memberModifyUpdateReq) {

        Long memberId = jwtUtil.extractId(token);
        Optional<Member> member = memberRepository.findById(memberId);

        if (!member.isPresent()) {
            throw new IllegalArgumentException("해당 회원이 존재하지 않습니다.");
        }

        member.get().setPw(memberModifyUpdateReq.getPw());
        member.get().setRole(memberModifyUpdateReq.getRole());
        member.get().setTel(memberModifyUpdateReq.getTel());
        member.get().setContact(memberModifyUpdateReq.getContact());

        Member updatedMember = memberRepository.save(member.get());

        String newAccessToken = jwtUtil.generateAccessToken(updatedMember);
        String newRefreshToken = jwtUtil.generateRefreshToken(memberId);

//        updatedMember.setAccessToken(newAccessToken);
        updatedMember.setRefreshToken(newRefreshToken);

        return updatedMember;
//        if (memberModifyUpdateReq.getContact() != null) member.get().setContact(memberModifyUpdateReq.getContact());
//        if (memberModifyUpdateReq.getPw() != null) member.get().setPw(memberModifyUpdateReq.getPw());
//        if (memberModifyUpdateReq.getName() != null) member.get().setName(memberModifyUpdateReq.getName());
//        if (memberModifyUpdateReq.getRole() != null) member.get().setRole(memberModifyUpdateReq.getRole());
//        if (memberModifyUpdateReq.getTel() != null) member.get().setTel(memberModifyUpdateReq.getTel());

//        return Optional.of(memberRepository.save(member));
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