package com.ssafy.db.repository;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Instructor;
import org.kurento.client.internal.server.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {

    @Query("SELECT c FROM Course c WHERE c.name LIKE %:keyword% OR c.summary LIKE %:keyword%")
    List<Course> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    List<Course> findByPrice(Long price, Pageable pageable);

    List<Course> findAllByOrderByViewDesc(Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.id IN (SELECT r.course.id FROM Register r WHERE r.member.id = :memberId)")
    List<Course> findByRegisteredMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.id IN (SELECT ct.course.id FROM course_tag ct WHERE ct.tag.id = :tagId)")
    List<Course> findByTagId(@Param("tagId") Long tagId, Pageable pageable);

    @Query("SELECT c FROM Course c JOIN c.courseTagList ct WHERE ct.tag.id IN :tagIds GROUP BY c.id HAVING COUNT(ct.tag.id) = :size")
    List<Course> findByTagIds(@Param("tagIds") List<Long> tagIds, Long size, Pageable pageable);

    @Query("SELECT c FROM Course c JOIN FETCH c.instructor left Join c.courseTagList WHERE c.id = :id")
    Optional<Course> findById(@Param("id") Long id);

    List<Course> findByInstructor(Instructor instructor);

    @Query("SELECT c FROM Course c WHERE c.id IN " +
            "(SELECT r.id FROM Register r WHERE r.member.id = :memberId)")
    List<Course> findByRegisteredMemberId(@Param("memberId") Long memberId);


    @Query("SELECT 1 FROM Register r WHERE r.member.id = :memberId AND r.course.id = :courseId")
    Long existsRegisterByMemberIdAndCourseId(@Param("memberId") Long memberId, @Param("courseId") Long courseId);

}
