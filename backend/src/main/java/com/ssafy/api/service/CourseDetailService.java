package com.ssafy.api.service;

import com.ssafy.api.response.CurriculumRes;
import com.ssafy.api.response.CurriculumUrlRes;
import com.ssafy.api.response.InstructorRes;
import com.ssafy.api.response.TagRes;
import com.ssafy.db.entity.Curriculum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseDetailService {

    private final AppliedService appliedService;
    private final BasicService basicService;

    public List<CurriculumRes> getCurriculumById(List<Long> ids) {
        return basicService.findCurriculumListByCurriculumIds(ids)
                .stream()
                .map(CurriculumRes::of)
                .collect(Collectors.toList());
    }

    public List<TagRes> getTagList() {
        return appliedService.getAllTagres();
    }

    public InstructorRes getInstructorByCourseId(Long courseId) {
        return appliedService.getInstructorByCourseId(courseId);
    }

    public CurriculumUrlRes getCurriculumUrls(Long curriculumId) {
        Curriculum curriculum = basicService.findCurriculumByCurriculumId(curriculumId);

        return CurriculumUrlRes.fromEntity(curriculum);
    }

    public Long getCourseIdOfCurr(Long curriculumId) {
        Curriculum curriculum = basicService.findCurriculumByCurriculumId(curriculumId);

        return curriculum.getCourse().getId();
    }
}
