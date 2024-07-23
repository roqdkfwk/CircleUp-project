package com.ssafy.api.controller;

import com.ssafy.api.response.CoursesRes;
import com.ssafy.api.service.CourseSerivce;
import com.ssafy.common.custom.RequiredAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiResponse(code = 404, message = "존재하지 않는 회원이거나 강사가 아님")
    public ResponseEntity<List<CoursesRes>> madeCourses(
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok().body(courseService.getCoursesImade(memberId));
    }


}
