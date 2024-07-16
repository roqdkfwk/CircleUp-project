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

}
