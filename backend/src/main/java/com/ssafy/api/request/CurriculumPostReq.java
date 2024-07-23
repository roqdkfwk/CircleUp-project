package com.ssafy.api.request;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Curriculum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CurriculumPostReq {
    MultipartFile img;
    String name;
    String description;

    public Curriculum toEntity(Course course, Long indexNo, String imgUrl) {
        return Curriculum.builder()
                .course(course)
                .name(this.name)
                .index_no(indexNo)
                .description(this.description)
                .img_url(imgUrl)
                .time(0L).build();
    }
}
