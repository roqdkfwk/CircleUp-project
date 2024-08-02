package com.ssafy.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
public class CourseModifyUpdateReq {
    MultipartFile img;
    String name;
    String summary;
    String price;
    String description;
    String tags;
}
