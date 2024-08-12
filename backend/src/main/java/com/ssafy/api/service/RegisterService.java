package com.ssafy.api.service;

import com.ssafy.common.custom.BadRequestException;
import com.ssafy.common.custom.ConflictException;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Register;
import com.ssafy.db.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {

    private final RegisterRepository registerRepository;
    private final BasicService basicService;

    public Long roleRegister(Long memberId, Long courseId) {
        return registerRepository.checkRegisterStatus(memberId, courseId);
    }


    public void doRegister(Long memberId, Long courseId) {
        // 이미 수강중이면
        if (registerRepository.existsRegisterByMemberIdAndCourseId(memberId, courseId) != null) {
            throw new ConflictException("Already registered");
        }

        Member member = basicService.findMemberByMemberId(memberId);
        Course course = basicService.findCourseByCourseId(courseId);

        Register register = new Register();
        register.setMember(member);
        register.setCourse(course);
        register.setCreatedAt(Timestamp.from(Instant.now()));

        basicService.saveRegister(register);
    }

    public void cancelRegister(Long memberId, Long courseId) {

        Register register = registerRepository.findByMemberIdAndCourseId(memberId, courseId).orElseThrow(
                () -> new NotFoundException("Register not found")
        );
        // 수강취소
        if (register == null) {
            throw new BadRequestException("Not Found Course or Member or Register");
        }
        registerRepository.delete(register);
    }
}
