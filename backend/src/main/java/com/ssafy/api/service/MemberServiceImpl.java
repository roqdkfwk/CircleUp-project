package com.ssafy.api.service;

import com.ssafy.db.entity.Member;
import com.ssafy.db.repository.MemberRepository;
import com.ssafy.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void signup(MemberDto memberDto) {
        try {
            Member member = Member.builder()
                    .email(memberDto.getEmail())
                    .pw(memberDto.getPw())
                    .name(memberDto.getName())
                    .token(memberDto.getToken())
                    .type(memberDto.getType())
                    .tel(memberDto.getTel())
                    .build();

            memberRepository.save(member);
        } catch (Exception e) {
            System.out.println("회원가입에 실패했습니다: " + e.getMessage());
            throw new RuntimeException("회원가입 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void withdraw(Long memberId) {
        try {
            memberRepository.deleteById(memberId);
        } catch (Exception e) {
            System.out.println("회원 탈퇴 실패: " + e.getMessage());
            throw new RuntimeException("회원 탈퇴 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public Optional<Member> login(String email, String pw) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent() && member.get().getPw().equals(pw))
            return member;

        return Optional.empty();
    }

    @Override
    public Member updateMember(MemberDto memberDto) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberDto.getEmail());

        if (!optionalMember.isPresent()) {
            throw new IllegalArgumentException("해당 회원이 존재하지 않습니다. id=" + memberDto.getEmail());
        }

        Member member = optionalMember.get();

//        Email은 로그인 시 사용하는 아이디이므로 변경할 수 없음
//        if (memberDto.getEmail() != null) existingMember.setEmail(memberDto.getEmail());
        if (memberDto.getPw() != null) member.setPw(memberDto.getPw());
        if (memberDto.getName() != null) member.setName(memberDto.getName());
        if (memberDto.getToken() != null) member.setToken(memberDto.getToken());
        if (memberDto.getType() != null) member.setType(memberDto.getType());
        if (memberDto.getTel() != null) member.setTel(memberDto.getTel());

        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> readMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}