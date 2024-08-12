package com.ssafy.api.service;

import com.ssafy.api.response.CurriculumRes;
import com.ssafy.api.response.CurriculumUrlRes;
import com.ssafy.api.response.InstructorRes;
import com.ssafy.api.response.TagRes;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.db.entity.Curriculum;
import com.ssafy.db.repository.CurriculumRepository;
import com.ssafy.db.repository.InstructorRepository;
import com.ssafy.db.repository.RegisterRepository;
import com.ssafy.db.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseDetailService {

    private final InstructorRepository instructorRepository;
    private final TagRepository tagRepository;
    private final CurriculumRepository curriculumRepository;

    public List<CurriculumRes> getCurriculumById(List<Long> ids) {
        return curriculumRepository.findAllById(ids)
                .stream()
                .map(CurriculumRes::of)
                .collect(Collectors.toList());
    }

    public List<TagRes> getTagList() {
        return tagRepository.getTags();
    }

    public InstructorRes getInstructorByCourseId(Long id) {
        return instructorRepository.findInstructorByCourseId(id).orElseThrow(
                () -> new NotFoundException("Not Found Instructor of Course : Course_id is " + id)
        );
    }

    public CurriculumUrlRes getCurriculumUrls(Long curriculumId) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new NotFoundException("Not Found Curriculum : curriculum_Id is " + curriculumId));

        CurriculumUrlRes curriculumUrlRes = CurriculumUrlRes.fromEntity(curriculum);
        curriculumUrlRes.setDocUrl(curriculum.getDocUrl());
        curriculumUrlRes.setRecUrl(curriculum.getRecUrl());

        return curriculumUrlRes;
    }

    public Long getCourseIdOfCurr(Long curriculumId){
        Curriculum curriculum = curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new NotFoundException("Not Found Curriculum : curriculum_Id is" + curriculumId));

        return curriculum.getCourse().getId();
    }
}
