package com.ssafy.api.response;

import com.ssafy.db.entity.Review;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ApiModel("ReviewGetResponse")
public class ReviewGetRes {
    Long id;
    Long memberId;
    String memberName;
    Long courseId;
    String courseName;
    Integer rating;
    String content;
    LocalDateTime createAt;
    LocalDateTime updateAt;

    public static ReviewGetRes fromEntity(Review review) {
        return ReviewGetRes.builder()
                .id(review.getId())
                .memberId(review.getMember().getId())
                .memberName(review.getMember().getName())
                .courseId(review.getCourse().getId())
                .courseName(review.getCourse().getName())
                .rating(review.getRating())
                .content(review.getContent())
                .createAt(review.getCreatedAt())
                .updateAt(review.getUpdatedAt())
                .build();
    }
}
