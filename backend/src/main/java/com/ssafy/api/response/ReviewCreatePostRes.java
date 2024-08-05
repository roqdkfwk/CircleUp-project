//package com.ssafy.api.response;
//
//import com.ssafy.api.request.ReviewCreatePostReq;
//import com.ssafy.db.entity.Course;
//import com.ssafy.db.entity.Member;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Builder
//@ApiModel("ReviewCreatePostResponse")
//public class ReviewCreatePostRes {
//
//    @ApiModelProperty(name = "리뷰 ID")
//    private Long id;
//
//    @ApiModelProperty(name = "사용자 이름")
//    private String memberName;
//
//    @ApiModelProperty(name = "강의 이름")
//    private String courseName;
//
//    @ApiModelProperty(name = "별점")
//    private Integer rating;
//
//    @ApiModelProperty(name = "리뷰 내용")
//    private String content;
//
//    @ApiModelProperty(name = "작성 시간")
//    private LocalDateTime createdAt;
//
//    public static ReviewCreatePostRes of(
//            ReviewCreatePostReq reviewCreatePostReq,
//            Member member,
//            Course course
//    ) {
//        return ReviewCreatePostRes.builder()
//                .memberName(member.getName())
//                .courseName(course.getName())
//                .rating(reviewCreatePostReq.getRating())
//                .content(reviewCreatePostReq.getContent())
//                .createdAt(reviewCreatePostReq.getCreatedAt())
//                .build();
//    }
//}
