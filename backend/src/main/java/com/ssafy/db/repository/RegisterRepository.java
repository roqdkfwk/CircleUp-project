package com.ssafy.db.repository;

import com.ssafy.db.entity.Register;
import org.kurento.client.internal.server.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Long> {
    @Query("SELECT count(*) FROM Register r WHERE r.course.id = :courseId")
    Long countByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT CASE " +
            "WHEN c.instructor.id = :memberId THEN 2 " +
            "WHEN EXISTS (SELECT 1 FROM Register r WHERE r.member.id = :memberId AND r.course.id = :courseId) THEN 1 " +
            "ELSE 0 " +
            "END " +
            "FROM Course c " +
            "WHERE c.id = :courseId")
    Long checkRegisterStatus(@Param("memberId") Long memberId, @Param("courseId") Long courseId);


    @Query("SELECT 1 FROM Register r WHERE r.member.id = :memberId AND r.course.id = :courseId")
    Long existsRegisterByMemberIdAndCourseId(@Param("memberId") Long memberId, @Param("courseId") Long courseId);

    @Query("select r from Register r where r.member.id=:memberId and r.course.id = :courseId")
    Optional<Register> findByMemberIdAndCourseId(@Param("memberId") Long memberId, @Param("courseId") Long courseId);
}
