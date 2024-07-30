package com.ssafy.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class CourseModifyUpdateReq {
    MultipartFile img;
    String name;
    String summary;
    Long price;
    String description;
    List<Long> tags;
}
