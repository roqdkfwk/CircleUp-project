package com.ssafy.api.service;

import com.ssafy.api.request.ReviewCreatePostReq;
import com.ssafy.api.request.ReviewUpdatePatchReq;
import com.ssafy.api.response.ReviewGetRes;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.common.custom.UnAuthorizedException;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Review;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.MemberRepository;
import com.ssafy.db.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;

    @Override
    public Long createReview(ReviewCreatePostReq reviewCreatePostReq, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 회원입니다.")
        );
        Course course = courseRepository.findById(reviewCreatePostReq.getCourseId()).orElseThrow(
                () -> new NotFoundException("존재하지 않는 강의입니다.")
        );


        Review review = ReviewCreatePostReq.toEntity(reviewCreatePostReq, member, course);
        return reviewRepository.save(review).getId();
    }

    @Override
    public ReviewGetRes getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException("리뷰를 찾을 수 없습니다.")
        );
        return ReviewGetRes.of(review);
    }

    @Override
    public List<ReviewGetRes> getReviewsByCourse(Long courseId) {
        List<Review> reviews = reviewRepository.findByCourseId(courseId);
        return reviews.stream()
                .map(ReviewGetRes::of)
                .collect(Collectors.toList());
    }

    @Override
    public void updateReview(Long reviewId, ReviewUpdatePatchReq reviewUpdatePatchReq, Long memberId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException("리뷰를 찾을 수 없습니다.")
        );

        if (!review.getMember().getId().equals(memberId)) {
            throw new UnAuthorizedException("리뷰를 수정할 권한이 없습니다.");
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
            reviewRepository.save(review);
        }
    }

    @Override
    public void deleteReview(Long reviewId, Long memberId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NotFoundException("리뷰를 찾을 수 없습니다.")
        );
        if (!review.getMember().getId().equals(memberId)) {
            throw new UnAuthorizedException("리뷰를 삭제할 권한이 없습니다.");
        }

        reviewRepository.delete(review);
    }
}
