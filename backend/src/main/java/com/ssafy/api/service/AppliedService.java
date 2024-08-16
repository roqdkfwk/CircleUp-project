package com.ssafy.api.service;

import com.ssafy.api.response.CourseRes;
import com.ssafy.api.response.InstructorRes;
import com.ssafy.api.response.TagRes;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Curriculum;
import com.ssafy.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppliedService {

    private final InstructorRepository instructorRepository;
    private final RegisterRepository registerRepository;
    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;
    private final CurriculumRepository curriculumRepository;

    @Transactional(readOnly = true)
    public List<TagRes> getAllTagres() {
        return tagRepository.getTags();
    }

    @Transactional(readOnly = true)
    public Long getRegisterCount(Long courseId) {
        return registerRepository.countByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public InstructorRes getInstructorByCourseId(Long courseId) {
        return instructorRepository.findInstructorByCourseId(courseId).orElseThrow(
                () -> new NotFoundException("Not Found Instructor of Course : Course_id is " + courseId)
        );
    }

    @Transactional(readOnly = true)
    public Long getInstructorIdByCourseId(Long courseId) {
        return instructorRepository.findInstructorIdByCourseId(courseId).orElseThrow(
                () -> new NotFoundException("Not Found Instructor of Course : Course_id is " + courseId)
        );
    }

    // CourseRepository
    @Transactional(readOnly = true)
    public Long checkRegisterStatus(Long memberId, Long courseId) {
        return registerRepository.checkRegisterStatus(memberId, courseId);
    }

    // ReviewRepository
    @Transactional(readOnly = true)
    public boolean findReviewByMemberIdAndCourseId(Long memberId, Long courseId) {
        return reviewRepository.findByMemberIdAndCourseId(memberId, courseId).isPresent();
    }

    public Course getCourseIdByCurriculum(Long curriculumId){
        Curriculum curriculum = curriculumRepository.findById(curriculumId).orElseThrow(
                () -> new NotFoundException("Not Found Course")
        );
        return curriculum.getCourse();
    }
}
