package com.ssafy.api.request;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Instructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;

@Getter
@Setter
//@ToString
public class CourseCreatePostReq {
    MultipartFile img;
    String name;
    String summary;
    String description;
    String price;
    String tags;

    public Course toEntity(Instructor instructor, Timestamp timestamp, String imgUrl) {
        Long parsedPrice = Long.parseLong(price);

        return Course.builder()
                .instructor(instructor)
                .createdAt(timestamp)
                .name(this.name)
                .imgUrl(imgUrl)
                .view(0L)
                .price(parsedPrice)
                .description(this.description)
                .courseTagList(new ArrayList<>())
                .curriculumList(new ArrayList<>())
                .totalCourse(0L)
                .completedCourse(0L)
                .build();
    }
}
