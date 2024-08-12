package com.ssafy.api.service;

import com.ssafy.api.response.InstructorRes;
import com.ssafy.api.response.TagRes;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppliedService {

    private final CourseRepository courseRepository;
    private final CourseRepositoryImpl courseRepositoryImpl;
    private final CurriculumRepository curriculumRepository;
    private final com.ssafy.db.repository.FavorRepository favorRepository;
    private final InstructorRepository instructorRepository;
    private final MemberRepository memberRepository;
    private final RegisterRepository registerRepository;
    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;

    public List<TagRes> getAllTagres() {
        return tagRepository.getTags();
    }

    public Long getRegisterCount(Long courseId) {
        return registerRepository.countByCourseId(courseId);
    }

    public InstructorRes getInstructorByCourseId(Long courseId) {
        return instructorRepository.findInstructorByCourseId(courseId).orElseThrow(
                () -> new NotFoundException("Not Found Instructor of Course : Course_id is " + courseId)
        );
    }

    public Long getInstructorIdByCourseId(Long courseId) {
        return instructorRepository.findInstructorIdByCourseId(courseId).orElseThrow(
                () -> new NotFoundException("Not Found Instructor of Course : Course_id is " + courseId)
        );
    }

    // CourseRepository
    public Long checkRegisterStatus(Long memberId, Long courseId) {
        return courseRepository.checkRegisterStatus(memberId, courseId);
    }

    // ReviewRepository
    public boolean findReviewByMemberIdAndCourseId(Long memberId, Long courseId) {
        return reviewRepository.findByMemberIdAndCourseId(memberId, courseId).isPresent();
    }
}
