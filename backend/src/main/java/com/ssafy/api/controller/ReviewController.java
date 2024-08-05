package com.ssafy.api.controller;

import com.ssafy.api.request.ReviewCreatePostReq;
import com.ssafy.api.request.ReviewUpdatePatchReq;
import com.ssafy.api.response.ReviewGetRes;
import com.ssafy.api.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "리뷰 API", tags = {"리뷰"})
@RestController
@RequestMapping("/api/courses/{courseId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ApiOperation(value = "리뷰 작성", notes = "새로운 리뷰를 작성합니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Long> addReview(
            @RequestBody ReviewCreatePostReq reviewCreatePostReq,
            @PathVariable Long courseId,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        Long reviewId = reviewService.createReview(reviewCreatePostReq, courseId, memberId);
        return ResponseEntity.status(201).body(reviewId);
    }

    @GetMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 조회", notes = "특정 리뷰를 조회합니다.")
    @ApiResponses(
            @ApiResponse(code = 404, message = "해당 리뷰 없음")
    )
    public ResponseEntity<ReviewGetRes> getReview(
//            @PathVariable(value = "courseId") Long courseId,
            @PathVariable(value = "reviewId") Long reviewId
    ) {
        ReviewGetRes reviewGetRes = reviewService.getReview(reviewId);
        return ResponseEntity.ok().body(reviewGetRes);
    }

    @GetMapping
    @ApiOperation(value = "강의별 리뷰 조회", notes = "특정 강의의 모든 리뷰를 조회합니다.")
    public ResponseEntity<List<ReviewGetRes>> getReviewByCourse(
            @PathVariable Long courseId
    ) {
        return ResponseEntity.ok().body(reviewService.getReviewsByCourse(courseId));
    }

    @PatchMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 수정", notes = "리뷰를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리뷰 수정 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 404, message = "해당 리뷰 없음")
    })
    public ResponseEntity<Void> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdatePatchReq reviewUpdatePatchReq,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        reviewService.updateReview(reviewId, reviewUpdatePatchReq, memberId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 삭제", notes = "리뷰를 삭제합니다.")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        reviewService.deleteReview(reviewId, memberId);
        return ResponseEntity.ok().build();
    }
}
