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
    int ratingNum;
    String ratingStr;
    Double progress;

    public static CoursesRes of(Course course) {
        return new CoursesRes(course.getId(),
                course.getImgUrl(),
                course.getName(),
                course.getSummary(),
                course.getPrice(),
                course.getView(),
                (int) Math.round(course.getRating()),
                String.format("%.1f", course.getRating()),
                (course.getTotalCourse()!=0?
                        course.getCompletedCourse() * 100 / course.getTotalCourse()
                        : 0) / 100d
        );
    }
}
