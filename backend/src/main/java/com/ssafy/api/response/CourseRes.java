package com.ssafy.api.response;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Instructor;
import com.ssafy.db.entity.Member;
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
    String img_url;
    Long price;
    Long view;

    String instructorName;
    String curriculum;
    String description;

    List<String> tags = new ArrayList<>();

    public static CourseRes of(Course course){
        return CourseRes.builder()
                .id(course.getId())
                .courseName(course.getName())
                .img_url(course.getImg_url())
                .price(course.getPrice())
                .view(course.getView())
                .instructorName(course.getInstructor().getName())
                .curriculum(course.getCurriculum())
                .description(course.getDescription())
                .tags(course.getCourse_tag_list().stream().map(ct->ct.getTag().getName()).collect(Collectors.toList())).build();
    }
}
