package com.ssafy.api.service;

import com.ssafy.common.custom.NotFoundException;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicService {

    private final CourseRepository courseRepository;
    private final CourseRepositoryImpl courseRepositoryImpl;
    private final CurriculumRepository curriculumRepository;
    private final com.ssafy.db.repository.FavorRepository favorRepository;
    private final InstructorRepository instructorRepository;
    private final MemberRepository memberRepository;
    private final RegisterRepository registerRepository;
    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;

    // CourseBy~~~Id
    // CourseListBy~~~IdAnd~~~Id
    // CourseRepository
    public Course findCourseByCourseId(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Not Found Course : CourseId is " + courseId)
        );
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    // CurriculumRepository
    public void saveCurriculum(Curriculum curriculum) {
        curriculumRepository.save(curriculum);
    }

    public Curriculum findCurriculumByCurriculumId(Long curriculumId) {
        return curriculumRepository.findById(curriculumId).orElseThrow(
                () -> new NotFoundException("Not Found Curriculum : CurriculumId is " + curriculumId)
        );
    }

    public List<Curriculum> findAllCurriculum() {
        return curriculumRepository.findAll();
    }

    public List<Curriculum> findCurriculumListByCurriculumIds(List<Long> curriculumIds) {
        return curriculumRepository.findAllById(curriculumIds);
    }

    // MemberRepository
    public Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("Not Found Member : MemberId is " + memberId)
        );
    }

    // InstructorRepository
    public Instructor findInstructorByInstructorId(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(
                () -> new NotFoundException("Not Found Instructor : InstructorId is " + instructorId)
        );
    }

    // TagRepository
    public Tag findTagByTagId(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(
                () -> new NotFoundException("Not Found Tag : TagId is " + tagId)
        );
    }

    public List<Tag> findTagListByTagIds(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

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

    public List<Favor> findAllFavorByMemberId(Long memberId) {
        return favorRepository.findByMemberId(memberId);
    }
    //////////////////////////////////////////////////// Transaction

    public void deleteCurriculum(Curriculum curriculum) {
        curriculumRepository.delete(curriculum);
        curriculumRepository.flush();
    }

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
