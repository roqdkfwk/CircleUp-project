package com.ssafy.db.repository;

import com.ssafy.db.entity.*;

import java.util.List;
import java.util.Optional;

public interface CourseRepositoryCustom {

    public List<Tag> getAllTag();
    public Long getTagSize();
    public Optional<Instructor> getInstructorById(Long id);
    public Optional<Member> getMemberById(Long id);

    public Boolean postRegister(Long memberId, Long courseId);
    public Boolean deleteRegister(Long memberId, Long courseId);

}
