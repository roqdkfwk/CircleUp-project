package com.ssafy.db.repository;


import com.ssafy.db.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

}
