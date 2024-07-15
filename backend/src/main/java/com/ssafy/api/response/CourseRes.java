package com.ssafy.api.response;

import com.ssafy.db.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseRes {

    Long id;
    String name;
    String img_url;
    Long price;
    Long view;

    Long instructorId;

    public static CourseRes of(Course course){
        return null;
    }
}
