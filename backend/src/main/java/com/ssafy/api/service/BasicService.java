package com.ssafy.api.service;

import com.ssafy.common.custom.NotFoundException;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BasicService {

    private final CourseRepository courseRepository;
    private final CurriculumRepository curriculumRepository;
    private final FavorRepository favorRepository;
    private final InstructorRepository instructorRepository;
    private final MemberRepository memberRepository;
    private final RegisterRepository registerRepository;
    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;

    // RegisterRepository
    public void saveRegister(Register register) {
        registerRepository.save(register);
    }

    public Boolean existsRegisterByMemberIdAndCourseId(Long memberId, Long courseId) {
        return registerRepository.existsRegisterByMemberIdAndCourseId(memberId, courseId) != null;
    }

    // CourseRepository
    @Transactional(readOnly = true)
    public Course findCourseByCourseId(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Not Found Course : CourseId is " + courseId)
        );
    }

    @Transactional
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    // CurriculumRepository
    @Transactional(readOnly = true)
    public Curriculum findCurriculumByCurriculumId(Long curriculumId) {
        return curriculumRepository.findById(curriculumId).orElseThrow(
                () -> new NotFoundException("Not Found Curriculum : CurriculumId is " + curriculumId)
        );
    }

    @Transactional
    public void saveCurriculum(Curriculum curriculum) {
        curriculumRepository.save(curriculum);
    }

    @Transactional
    public void updateTotalCourse(Course course){
        Long cnt = curriculumRepository.countByCourseId(course.getId());
        course.setTotalCourse(cnt);
        courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public List<Curriculum> findAllCurriculum() {
        return curriculumRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Curriculum> findCurriculumListByCurriculumIds(List<Long> curriculumIds) {
        return curriculumRepository.findAllById(curriculumIds);
    }

    @Transactional
    public void deleteCurriculum(Curriculum curriculum) {
        curriculumRepository.delete(curriculum);
    }

    public List<Curriculum> findCurriculumListByCourseId(Long courseId) {
        return curriculumRepository.findAllByCourseId(courseId);
    }

    // MemberRepository
    @Transactional(readOnly = true)
    public Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("Not Found Member : MemberId is " + memberId)
        );
    }

    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("존재하지 않는 회원입니다."));
    }

    public boolean existsMemberByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    // InstructorRepository
    @Transactional(readOnly = true)
    public Instructor findInstructorByInstructorId(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(
                () -> new NotFoundException("Not Found Instructor : InstructorId is " + instructorId)
        );
    }

    public void saveInstructor(Instructor instructor){
        instructorRepository.save(instructor);
    }

    // TagRepository
    @Transactional(readOnly = true)
    public Tag findTagByTagId(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(
                () -> new NotFoundException("Not Found Tag : TagId is " + tagId)
        );
    }

    @Transactional(readOnly = true)
    public List<Tag> findTagListByTagIds(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    @Transactional(readOnly = true)
    public Long getTagCount() {
        return tagRepository.count();
    }

    // FavorRepository
    public void saveFavor(Favor favor) {
        favorRepository.save(favor);
    }

    public void deleteFavor(Favor favor) {
        favorRepository.delete(favor);
    }

    @Transactional(readOnly = true)
    public List<Favor> findAllFavorByMemberId(Long memberId) {
        return favorRepository.findByMemberId(memberId);
    }
    //////////////////////////////////////////////////// Transaction


    // ReviewRepository
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }

    public List<Review> findAllReviewByCourseId(Long courseId) {
        return reviewRepository.findByCourseId(courseId);
    }

    public Review findReviewByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException("Not Found Review : ReviewId is " + reviewId)
        );
    }
}
