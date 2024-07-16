package com.ssafy.db.repository;


import com.ssafy.db.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class CourseRepositoryImpl implements CourseRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Tag> getAllTag() {
        return em.createQuery("select t from Tag t").getResultList();
    }

    @Override
    public Long getTagSize(){
        return (Long)em.createQuery("select count(t) from Tag t").getSingleResult();
    }

    @Override
    public Optional<Instructor> getInstructorById(Long id) {
        return Optional.of(em.find(Instructor.class, id));
//        return Optional.of((Instructor) em.createQuery("select i from Instructor i where i.member.id = :id").setParameter("id", id).getSingleResult());
    }

    @Override
    public Optional<Member> getMemberById(Long id) {
        return Optional.of(em.find(Member.class, id));
    }

    @Override
    @Transactional
    public Boolean postRegister(Long memberId, Long courseId) {
        Member member = em.find(Member.class, memberId);
        Course course = em.find(Course.class, courseId);
        if (member == null || course == null) {
            return false;
        }

        Register register = new Register();
        register.setMember(member);
        register.setCourse(course);
        register.setCreated_at(Timestamp.from(Instant.now()));
        em.merge(register);

        return true;
    }

    @Override
    @Transactional
    public Boolean deleteRegister(Long memberId, Long courseId) {
        try {
            Register register = (Register) em.createQuery("select r from Register r where r.member.id=:memberId and r.course.id = :courseId")
                .setParameter("memberId", memberId)
                .setParameter("courseId", courseId).getSingleResult();

            em.remove(register);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }


}
