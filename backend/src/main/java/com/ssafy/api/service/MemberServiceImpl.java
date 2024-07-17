package com.ssafy.api.service;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.api.response.MemberReadGetRes;
import com.ssafy.common.util.JwtUtil;
import com.ssafy.db.entity.Member;
import com.ssafy.db.repository.MemberRepository;
import com.ssafy.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public void withdraw(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    // 회원정보수정
    @Override
    public Member modifyMember(Long memberId, MemberModifyUpdateReq memberModifyUpdateReq) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if (!optionalMember.isPresent()) {
            throw new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + memberModifyUpdateReq.getName());
        }

        Member member = optionalMember.get();

        if (memberModifyUpdateReq.getContact() != null) member.setContact(memberModifyUpdateReq.getContact());
        if (memberModifyUpdateReq.getPw() != null) member.setPw(memberModifyUpdateReq.getPw());
        if (memberModifyUpdateReq.getName() != null) member.setName(memberModifyUpdateReq.getName());
        if (memberModifyUpdateReq.getRole() != null) member.setRole(memberModifyUpdateReq.getRole());
        if (memberModifyUpdateReq.getTel() != null) member.setTel(memberModifyUpdateReq.getTel());

        return memberRepository.save(member);
    }

    // 회원정보조회
    @Override
    @Transactional(readOnly = true)
    public MemberReadGetRes readMemberByToken(String token) {

        try {
            String memberToken = token.replace("Bearer ", "");
            Member member = authService.validateMember(memberToken);
            return convertToDto(member);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Invalid token or user not found");
        }
    }

    private MemberReadGetRes convertToDto(Member member) {

        return MemberReadGetRes.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .contact(member.getContact())
                .tel(member.getTel())
                .build();
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