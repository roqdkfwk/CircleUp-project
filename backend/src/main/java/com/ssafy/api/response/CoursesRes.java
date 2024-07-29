package com.ssafy.api.response;

import com.ssafy.db.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoursesRes {

    Long id;
    String img_url;
    String name;
    String summary;
    Long price;
    Long view;

    public static CoursesRes of(Course course){
        return new CoursesRes(course.getId(), course.getImg_url(), course.getName(),
                course.getSummary(), course.getPrice(), course.getView());
    }
}
