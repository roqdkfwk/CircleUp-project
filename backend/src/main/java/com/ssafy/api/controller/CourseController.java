package com.ssafy.api.controller;

import com.ssafy.api.response.CoursesRes;
import com.ssafy.api.response.TagRes;
import com.ssafy.api.service.CourseSerivce;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = {"강의"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {

    private final CourseSerivce courseService;

    @GetMapping("/tag")
    @Operation(summary="태그 목록 조회")
    public ResponseEntity<List<TagRes>> taglist(){
        return ResponseEntity.ok().body(courseService.getTagList());
    }

    @GetMapping("/courses")
    @Operation(summary = "강의 목록 조회")
    public ResponseEntity<List<CoursesRes>> courselist(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, value="tag") List<Long> tags,
            @RequestParam(required = true) int size)
    {
        List<CoursesRes> courseList ;

        System.out.println("size : " + size);

        if(keyword != null){
            // 강의 제목으로 검색
            return ResponseEntity.ok().body(courseService.getCoursesByTitle(keyword, size));
        }else if(tags != null){
            // 태그 목록으로 검색
            return ResponseEntity.ok().body(courseService.getCoursesByTags(tags, size));
        }else if(type != null){
            if(type.equals("register")){ // 수강중
                // TODO 인증필터를 이용한 memberId 추가
                return ResponseEntity.ok().body(courseService.getRegisteredCourses(3L, size));
            }else if(type.equals("offer")){ // 추천순
                return ResponseEntity.ok().body(courseService.getOfferingCourses(size));
            }else if(type.equals("hot")){ // 인기순
                return ResponseEntity.ok().body(courseService.getCoursesByView(size));
            }else if(type.equals("free")){ // 무료
                return ResponseEntity.ok().body(courseService.getFreeCourses(size));
            }
        }
        return ResponseEntity.badRequest().build();
    }


}
