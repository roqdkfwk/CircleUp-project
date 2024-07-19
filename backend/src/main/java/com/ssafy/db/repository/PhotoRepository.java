package com.ssafy.db.repository;

import com.ssafy.db.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
