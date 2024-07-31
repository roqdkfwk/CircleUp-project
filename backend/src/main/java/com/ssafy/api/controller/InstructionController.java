package com.ssafy.api.controller;

import com.ssafy.api.request.CourseCreatePostReq;
import com.ssafy.api.request.CourseModifyUpdateReq;
import com.ssafy.api.response.CoursesRes;
import com.ssafy.api.service.CourseSerivce;
import com.ssafy.common.custom.RequiredAuth;
import com.ssafy.db.entity.Course;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Api(tags = {"강의 : 강사(Instructor)"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@RequiredAuth
public class InstructionController {

    private final CourseSerivce courseService;

    @GetMapping("/courses/instructions")
    @ApiOperation(value = "내가 개설한 강의 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 회원이거나 강사가 아님")
    })
    public ResponseEntity<List<CoursesRes>> madeCourses(
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok().body(courseService.getCoursesImade(memberId));
    }

    @PostMapping(value = "/courses/instructions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "새로운 강의 만들기")
    public ResponseEntity<Long> createCourse(
            @ModelAttribute CourseCreatePostReq courseCreatePostReq,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        Course course = courseService.createCourse(courseCreatePostReq, memberId);
        return ResponseEntity.ok().body(course.getId()); // 개설한 강의 id를 반환
    }


    @PatchMapping(value = "/courses/instructions/{course_id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "기존 강의 수정")
    public ResponseEntity<Void> updateCourse(
            @PathVariable(name = "course_id") Long courseId,
            @ModelAttribute CourseModifyUpdateReq courseModifyUpdateReq,
            @RequestPart(name = "img", required = false) MultipartFile img,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        Course course = courseService.updateCourse(courseId, courseModifyUpdateReq, memberId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/courses/instructions/{course_id}")
    @ApiOperation(value = "강의 삭제", notes = "수강생이 아무도 없는 경우에만 삭제할 수 있습니다")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable(name = "course_id") Long courseId,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        courseService.deleteCourse(courseId, memberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
