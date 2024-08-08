package com.ssafy.db.repository;

import com.ssafy.db.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    Optional<Curriculum> findByIndexNoAndCourseId(Long indexNo, Long courseId);
}
