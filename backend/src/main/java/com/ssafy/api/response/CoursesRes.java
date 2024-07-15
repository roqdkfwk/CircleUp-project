package com.ssafy.api.response;

import com.ssafy.db.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoursesRes {

    Long id;
    String name;
    String img_url;
    Long price;
    Long view;

    public static CoursesRes of(Course course){
        return new CoursesRes(course.getId(), course.getName(),
                course.getImg_url(), course.getPrice(), course.getView());
    }
}
