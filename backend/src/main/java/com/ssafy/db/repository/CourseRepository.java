package com.ssafy.db.repository;

import com.ssafy.db.entity.Course;
import org.kurento.client.internal.server.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom{

    List<Course> findByNameContaining(String name);
    List<Course> findByPrice(Long price);
    List<Course> findAllByOrderByViewDesc();

    @Query("SELECT c FROM Course c WHERE c.id IN (SELECT r.course.id FROM Register r WHERE r.member.id = :memberId)")
    List<Course> findByRegisteredMemberId(@Param("memberId") Long memberId);

    @Query("SELECT c FROM Course c WHERE c.id IN (SELECT ct.course.id FROM course_tag ct WHERE ct.tag.id = :tagId)")
    List<Course> findByTagId(@Param("tagId") Long tagId);

    @Query("SELECT c FROM Course c JOIN c.course_tag_list ct WHERE ct.tag.id IN :tagIds GROUP BY c.id HAVING COUNT(ct.tag.id) = :size")
    List<Course> findByTagIds(@Param("tagIds") List<Long> tagIds, Long size);
}
