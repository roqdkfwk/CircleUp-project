package com.ssafy.api.service;

import com.ssafy.api.response.CourseRes;
import com.ssafy.common.custom.BadRequestException;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.enums.Role;
import com.ssafy.db.entity.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final BasicService basicSerivce;

    public CourseRes rejectCourse(Long courseId, Long memberId) {
        Member admin = basicSerivce.findMemberByMemberId(memberId);
        if (!admin.getRole().equals(Role.Admin)) throw new BadRequestException("Not Admin");

        Course course = basicSerivce.findCourseByCourseId(courseId);
        if (course.getStatus().equals(Status.Pending)) throw new BadRequestException("Status is not Pending");

        course.setStatus(Status.Rejected);
        basicSerivce.saveCourse(course);

        return CourseRes.fromEntity(course);
    } // 승인 거절 for 관리자

    public CourseRes approveCourse(Long courseId, Long memberId) {
        Member admin = basicSerivce.findMemberByMemberId(memberId);
        if (!admin.getRole().equals(Role.Admin)) throw new BadRequestException("Not Admin");

        Course course = basicSerivce.findCourseByCourseId(courseId);
        if (course.getStatus().equals(Status.Pending)) throw new BadRequestException("Status is not Pending");

        course.setStatus(Status.Approved);
        basicSerivce.saveCourse(course);

        return CourseRes.fromEntity(course);
    } // 승인 for 관리자
}
