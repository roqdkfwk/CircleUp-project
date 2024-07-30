package com.ssafy.db.repository;

import com.ssafy.db.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select count(t) from Tag t")
    Long getTagSize();

}