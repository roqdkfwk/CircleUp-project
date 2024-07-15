package com.ssafy.api.service;

import com.ssafy.api.response.CourseRes;
import com.ssafy.api.response.CoursesRes;
import com.ssafy.api.response.InstructorRes;
import com.ssafy.api.response.TagRes;
import com.ssafy.common.exception.handler.NotFoundException;
import com.ssafy.db.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseSerivce {

    private final CourseRepository courseRepository;

    //////////////////////////////////////////////////////////////////////////
    public List<TagRes> getTagList() {
        return courseRepository.getAllTag().stream().map(tag-> new TagRes(tag.getId(), tag.getName())).collect(Collectors.toList());
    }
    //////////////////////////////////////////////////////////////////////////
    public List<CoursesRes> getCoursesByTitle(String name, int size){
        Pageable pageable = PageRequest.of(0, size);
        return courseRepository.findByNameContaining(name, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesByView(int size){
        Pageable pageable = PageRequest.of(0, size);

        return courseRepository.findAllByOrderByViewDesc(pageable).stream().map(CoursesRes::of).collect(Collectors.toList());
    }

    public List<CoursesRes> getFreeCourses(int size){
        Pageable pageable = PageRequest.of(0, size);

        return courseRepository.findByPrice(0L, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getOfferingCourses(int size){
        Pageable pageable = PageRequest.of(0, size);

        Long tagSize = courseRepository.getTagSize();
        LocalDate today = LocalDate.now();
        Long randomTagId = today.getDayOfYear() % tagSize + 1;

        return courseRepository.findByTagId(randomTagId, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getRegisteredCourses(Long memberId, int size){
        Pageable pageable = PageRequest.of(0, size);

        return courseRepository.findByRegisteredMemberId(memberId, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesByTags(List<Long> tags, int size){
        Pageable pageable = PageRequest.of(0, size);

        return courseRepository.findByTagIds(tags, Long.valueOf(tags.size()), pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());

    }
    //////////////////////////////////////////////////////////////////////////


    public CourseRes getCourseById(Long id){
        return CourseRes.of(courseRepository.findAllById(id).orElseThrow(
                ()-> new NotFoundException("Not Found Course : " + id)
        ));
    }
}
