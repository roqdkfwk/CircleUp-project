package com.ssafy.db.repository;

import com.ssafy.db.entity.Tag;
import java.util.List;

public interface CourseRepositoryCustom {

    public List<Tag> getAllTag();
    public Long getTagSize();
}
