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
            @RequestParam(required = false, value="tag") List<Long> tags)
    {
        List<CoursesRes> courseList ;

        // TODO 페이징
        if(keyword != null){
            // 강의 제목으로 검색
            return ResponseEntity.ok().body(courseService.getCoursesByTitle(keyword));
        }else if(tags != null){
            // 태그 목록으로 검색
            System.out.println("================태그 목록으로 검색================");
            System.out.println(tags.get(0));
            System.out.println(tags.get(0).getClass());
            return ResponseEntity.ok().body(courseService.getCoursesByTags(tags));
        }else if(type != null){
            if(type.equals("register")){ // 수강중
                // TODO 인증필터를 이용한 memberId 추가
                return ResponseEntity.ok().body(courseService.getRegisteredCourses(3L));
            }else if(type.equals("offer")){ // 추천순
                return ResponseEntity.ok().body(courseService.getOfferingCourses());
            }else if(type.equals("hot")){ // 인기순
                return ResponseEntity.ok().body(courseService.getCoursesByView());
            }else if(type.equals("free")){ // 무료
                return ResponseEntity.ok().body(courseService.getFreeCourses());
            }
        }
        return ResponseEntity.badRequest().build();
    }


}
