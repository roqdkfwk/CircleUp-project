package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ReviewUpdatePatchRequest")
public class ReviewUpdatePatchReq {

    @ApiModelProperty(name = "별점", example = "5")
    private Integer rating;

    @ApiModelProperty(name = "리뷰 수정", example = "수정된 내용입니다.")
    private String content;

    // 리뷰 수정 시 별점은 수정하지 않는 경우 rating == null
    // null값을 받기 위해서 rating은 Integer 타입을 사용
    public boolean hasRatingChange() {
        return rating != null;
    }

    public boolean hasContentChange() {
        return content != null && !content.isEmpty();
    }
}
