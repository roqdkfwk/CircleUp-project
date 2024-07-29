package com.ssafy.api.controller;

import com.ssafy.api.response.CoursesRes;
import com.ssafy.api.service.CourseSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"강의 수강"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegisterController {

    private final CourseSerivce courseService;

    @GetMapping("/courses/registers")
    @ApiOperation(value = "내가 수강한 강의 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 401, message = "인증 실패")
    })
    public ResponseEntity<List<CoursesRes>> registeredCourses(){
        // TODO
        Long memberId = 3L;
        return ResponseEntity.ok().body(courseService.getCoursesIregistered(memberId));
    }

    @GetMapping("/courses/registers/{course_id}")
    @ApiOperation(value = "수강 여부 조회")
    @ApiResponses({
            @ApiResponse(code = 401, message = "인증 실패")
    })
    public ResponseEntity<Boolean> isRegister(
            @PathVariable(name="course_id") Long courseId
    ){
        // TODO
        Long memberId = 3L;
        return ResponseEntity.ok().body(courseService.existRegister(memberId, courseId));
    }


    @PostMapping("/courses/registers/{course_id}")
    @ApiOperation(value = "수강 신청")
    @ApiResponses({
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원 혹은 강의"),
            @ApiResponse(code = 409, message = "이미 수강중")
    })
    public ResponseEntity<Void> doRegister(
            @PathVariable(name="course_id") Long courseId
    ){
        // TODO 인증필터를 이용한 memberId 추가
        Long memberId = 3L;
        courseService.doRegister(memberId, courseId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/courses/registers/{course_id}")
    @ApiOperation(value = "수강 취소")
    @ApiResponses({
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    public ResponseEntity<Void> cancelRegister(
            @PathVariable(name="course_id") Long courseId
    ){
        // TODO
        Long memberId = 3L;
        courseService.cancelRegister(memberId, courseId);
        return ResponseEntity.ok().build();
    }

}
