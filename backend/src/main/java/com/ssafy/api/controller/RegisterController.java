package com.ssafy.api.controller;

import com.ssafy.api.response.CoursesRes;
import com.ssafy.api.service.CourseSerivce;
import com.ssafy.common.custom.RequiredAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"강의 : 수강생(User)"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@RequiredAuth
public class RegisterController {

    private final CourseSerivce courseService;

    @GetMapping("/courses/registers")
    @ApiOperation(value = "내가 수강한 강의 목록 조회")
    public ResponseEntity<List<CoursesRes>> registeredCourses(
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok().body(courseService.getCoursesIregistered(memberId));
    }

    @GetMapping("/courses/registers/{course_id}")
    @ApiOperation(value = "수강 여부 조회")
    public ResponseEntity<Boolean> isRegister(
            @PathVariable(name = "course_id") Long courseId,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok().body(courseService.existRegister(memberId, courseId));
    }


    @PostMapping("/courses/registers/{course_id}")
    @ApiOperation(value = "수강 신청")
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 회원 혹은 강의"),
            @ApiResponse(code = 409, message = "이미 수강중")
    })
    public ResponseEntity<Void> doRegister(
            @PathVariable(name = "course_id") Long courseId,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        courseService.doRegister(memberId, courseId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/courses/registers/{course_id}")
    @ApiOperation(value = "수강 취소")
    @ApiResponses({
            @ApiResponse(code = 400, message = "잘못된 요청")
    })
    public ResponseEntity<Void> cancelRegister(
            @PathVariable(name = "course_id") Long courseId,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        courseService.cancelRegister(memberId, courseId);
        return ResponseEntity.ok().build();
    }

}
