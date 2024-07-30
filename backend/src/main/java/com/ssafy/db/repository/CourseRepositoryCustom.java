package com.ssafy.db.repository;

public interface CourseRepositoryCustom {
    
    public Boolean postRegister(Long memberId, Long courseId);

    public Boolean deleteRegister(Long memberId, Long courseId);

}
