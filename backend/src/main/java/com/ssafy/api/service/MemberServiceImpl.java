package com.ssafy.api.service;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.db.entity.Member;
import com.ssafy.db.repository.MemberRepository;
import com.ssafy.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    @Override
    @Transactional
    public void signup(MemberSignupPostReq memberSignupPostReq) {

        Member member = Member.builder()
                .email(memberSignupPostReq.getEmail())
                .pw(memberSignupPostReq.getPw())
                .name(memberSignupPostReq.getName())
                .token(memberSignupPostReq.getToken())
                .role(memberSignupPostReq.getRole())
                .tel(memberSignupPostReq.getTel())
                .contact(memberSignupPostReq.getContact())
                .build();

        memberRepository.save(member);
    }

    // 이메일 중복체크
    @Override
    public boolean checkEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }

    // 회원탈퇴
    @Override
    public void withdraw(Long memberId) {
        memberRepository.deleteById(memberId);
    }

//    // email, pw로 로그인
//    @Override
//    public Optional<Member> login(String email, String pw) {
//        Optional<Member> member = memberRepository.findByEmail(email);
//
//        if (member.isPresent() && passwordEncoder.matches(pw, member.get().getPw()))
//            return member;
//
//        return Optional.empty();
//    }

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
        if (memberModifyUpdateReq.getToken() != null) member.setToken(memberModifyUpdateReq.getToken());
        if (memberModifyUpdateReq.getRole() != null) member.setRole(memberModifyUpdateReq.getRole());
        if (memberModifyUpdateReq.getTel() != null) member.setTel(memberModifyUpdateReq.getTel());

        return memberRepository.save(member);
    }

    // 회원정보조회
    @Override
    public Optional<Member> readMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Member getMemberByEmail(String email) {

        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            return memberOptional.get();
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}