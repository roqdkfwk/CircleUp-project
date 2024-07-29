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


@Api(tags = {"강의 강사"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InstructionController {

    private final CourseSerivce courseService;

    @GetMapping("/courses/instructions")
    @ApiOperation(value = "내가 개설한 강의 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "존재하지 않는 회원")
    })
    public ResponseEntity<List<CoursesRes>> madeCourses(){
        // TODO
        Long memberId = 2L;
        return ResponseEntity.ok().body(courseService.getCoursesImade(memberId));
    }



}
