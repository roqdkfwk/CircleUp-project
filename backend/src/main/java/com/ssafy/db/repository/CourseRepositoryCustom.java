package com.ssafy.db.repository;

import com.ssafy.db.entity.Instructor;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Tag;
import java.util.List;
import java.util.Optional;

public interface CourseRepositoryCustom {

    public List<Tag> getAllTag();
    public Long getTagSize();
    public Optional<Instructor> getInstructorById(Long id);
    public Optional<Member> getMemberById(Long id);

}
