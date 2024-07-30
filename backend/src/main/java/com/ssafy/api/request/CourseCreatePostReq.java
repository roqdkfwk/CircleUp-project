package com.ssafy.api.request;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Instructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CourseCreatePostReq {
    MultipartFile img;
    String name;
    String summary;
    String description;
    Long price;
    List<Long> tags = new ArrayList<>();
    List<CurriculumPostReq> curriculums = new ArrayList<>();

    public Course toEntity(Instructor instructor, Timestamp timestamp, String imgUrl) {
        return Course.builder()
                .instructor(instructor)
                .createdAt(timestamp)
                .name(this.name)
                .imgUrl(imgUrl)
                .view(0L)
                .price(this.price)
                .description(this.description)
                .totalCourse(0L)
                .completedCourse(0L)
                .build();
    }
}
