package com.ssafy.db.repository;


import com.ssafy.db.entity.Instructor;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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


}
