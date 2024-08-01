package com.ssafy.api.response;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Curriculum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class CurriculumRes {
    Long id;
    String curriculumName;
    String description;
    String imgUrl;

    public static CurriculumRes of(Curriculum curriculum) {
        return CurriculumRes.builder()
                .id(curriculum.getId())
                .curriculumName(curriculum.getName())
                .imgUrl(curriculum.getImgUrl())
                .description(curriculum.getDescription()).build();
    }
}
