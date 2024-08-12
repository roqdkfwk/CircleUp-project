package com.ssafy.api.service;

import com.ssafy.api.request.ReviewCreatePostReq;
import com.ssafy.api.request.ReviewUpdatePatchReq;
import com.ssafy.api.response.ReviewGetRes;
import com.ssafy.common.custom.ConflictException;
import com.ssafy.common.custom.UnAuthorizedException;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final BasicService basicService;
    private final AppliedService appliedService;

    @Override
    public Long createReview(ReviewCreatePostReq reviewCreatePostReq, Long courseId, Long memberId) {
        Member member = basicService.findMemberByMemberId(memberId);
        Course course = basicService.findCourseByCourseId(courseId);

        if (appliedService.checkRegisterStatus(memberId, courseId) != 1) {
            throw new UnAuthorizedException("수강하지 않은 강의에 대해서는 리뷰를 작성할 수 없습니다.");
        }
        if (appliedService.findReviewByMemberIdAndCourseId(memberId, courseId)) {
            throw new ConflictException("이미 작성한 리뷰가 존재합니다.");
        }

        Review review = ReviewCreatePostReq.toEntity(reviewCreatePostReq, member, course);
        basicService.saveReview(review);
        calculateRating(course);

        return review.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewGetRes getReview(Long reviewId) {
        Review review = basicService.findReviewByReviewId(reviewId);
        return ReviewGetRes.fromEntity(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewGetRes> getReviewsByCourse(Long courseId) {
        List<Review> reviews = basicService.findAllReviewByCourseId(courseId);

        return reviews.stream()
                .map(ReviewGetRes::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void updateReview(Long reviewId, ReviewUpdatePatchReq reviewUpdatePatchReq, Long memberId) {
        Review review = basicService.findReviewByReviewId(reviewId);
        if (checkAuthority(memberId, review)) {
            throw new UnAuthorizedException("내가 작성하지 않은 리뷰는 수정할 수 없습니다.");
        }

        boolean isUpdated = false;
        if (reviewUpdatePatchReq.hasRatingChange()) {
            review.setRating(reviewUpdatePatchReq.getRating());
            isUpdated = true;
        }
        if (reviewUpdatePatchReq.hasContentChange()) {
            review.setContent(reviewUpdatePatchReq.getContent());
            isUpdated = true;
        }
        if (isUpdated) {
            review.setUpdatedAt(LocalDateTime.now());
            basicService.saveReview(review);
        }

        calculateRating(review.getCourse());
    }

    @Override
    public void deleteReview(Long reviewId, Long memberId) {
        Review review = basicService.findReviewByReviewId(reviewId);
        if (checkAuthority(memberId, review)) {
            throw new UnAuthorizedException("내가 작성하지 않은 리뷰는 삭제할 수 없습니다.");
        }

        basicService.deleteReview(review);
        calculateRating(review.getCourse());
    }

    public void calculateRating(Course course) {
        List<ReviewGetRes> reviews = getReviewsByCourse(course.getId());
        Double rating = reviews.stream()
                .mapToDouble(ReviewGetRes::getRating)
                .average()
                .orElse(0.0);
        course.setRating(rating);
        basicService.saveCourse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkAuthority(Long memberId, Review review) {
        return !review.getMember().getId().equals(memberId);
    }
}
