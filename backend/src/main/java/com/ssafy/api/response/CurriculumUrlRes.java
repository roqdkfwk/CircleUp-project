package com.ssafy.api.response;

import com.ssafy.db.entity.Curriculum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurriculumUrlRes {
    Long id;
    Long indexNo;
    String recUrl;
    String docUrl;

    public static CurriculumUrlRes fromEntity(Curriculum curriculum) {
        return new CurriculumUrlRes(curriculum.getId(),curriculum.getIndexNo(),curriculum.getRecUrl(),curriculum.getDocUrl());
    }
}
