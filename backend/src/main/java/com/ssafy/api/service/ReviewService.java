package com.ssafy.api.service;

import com.ssafy.api.request.ReviewCreatePostReq;
import com.ssafy.api.request.ReviewUpdatePatchReq;
import com.ssafy.api.response.ReviewGetRes;
import com.ssafy.db.entity.Course;

import java.util.List;

public interface ReviewService {

    Long createReview(ReviewCreatePostReq reviewCreatePostReq, Long courseId, Long memberId);

    ReviewGetRes getReview(Long reviewId);

    List<ReviewGetRes> getReviewsByCourse(Long courseId);

    void updateReview(Long reviewId, ReviewUpdatePatchReq reviewUpdatePatchReq, Long memberid);

    void deleteReview(Long reviewId, Long memberid);

    void calculateRating(Course course);
}
