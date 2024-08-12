package com.ssafy.api.response;

import com.ssafy.db.entity.Curriculum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CurriculumRes {
    Long id;
    Long indexNo;
    String curriculumName;
    String description;
    Boolean isCurrent;

    public static CurriculumRes fromEntity(Curriculum curriculum, Long nextClass) {
        return CurriculumRes.builder()
                .id(curriculum.getId())
                .indexNo(curriculum.getIndexNo())
                .curriculumName(curriculum.getName())
                .description(curriculum.getDescription())
                .isCurrent(curriculum.getIndexNo().equals(nextClass+1)? true : false).build();
    }
}
