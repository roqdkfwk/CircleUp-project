package com.ssafy.db.repository;

import com.ssafy.db.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    List<Curriculum> findAllByCourseId(Long courseId);
}
