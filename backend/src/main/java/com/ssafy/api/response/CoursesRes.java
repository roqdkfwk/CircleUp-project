package com.ssafy.api.response;

import com.ssafy.db.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoursesRes {
    Long id;
    String imgUrl;
    String name;
    String summary;
    Long price;
    Long view;

    public static CoursesRes of(Course course) {
        return new CoursesRes(course.getId(),
                course.getImgUrl(),
                course.getName(),
                course.getSummary(),
                course.getPrice(),
                course.getView());
    }
}
