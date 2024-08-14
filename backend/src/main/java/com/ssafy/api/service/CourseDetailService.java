package com.ssafy.api.service;

import com.ssafy.api.response.*;
import com.ssafy.db.entity.Course;
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

    /////
    public List<CurriculumRes> getCurriculumById(List<Long> ids) {
        Course course = appliedService.getCourseIdByCurriculum(ids.get(0));
        return basicService.findCurriculumListByCurriculumIds(ids)
                .stream()
                .map(curriculum -> CurriculumRes.fromEntity(curriculum, course.getCompletedCourse()))
                .collect(Collectors.toList());
    }
}
