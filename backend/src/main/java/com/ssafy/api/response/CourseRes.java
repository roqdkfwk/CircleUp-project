package com.ssafy.api.response;

import com.ssafy.db.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class CourseRes {

    Long id;
    String courseName;
    String imgUrl;
    Long price;
    Long view;

    String instructorName;
    String description;

    List<String> tags = new ArrayList<>();

    public static CourseRes of(Course course) {
        return CourseRes.builder()
                .id(course.getId())
                .courseName(course.getName())
                .imgUrl(course.getImgUrl())
                .price(course.getPrice())
                .view(course.getView())
                .instructorName(course.getInstructor().getMember().getName())
                .description(course.getDescription())
                .tags(course.getCourseTagList().stream().map(ct -> ct.getTag().getName()).collect(Collectors.toList())).build();
    }
}
