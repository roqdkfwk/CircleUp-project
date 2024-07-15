package com.ssafy.api.service;

import com.ssafy.api.response.CoursesRes;
import com.ssafy.api.response.TagRes;
import com.ssafy.db.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseSerivce {

    private final CourseRepository courseRepository;

    public List<TagRes> getTagList() {
        return courseRepository.getAllTag().stream().map(tag-> new TagRes(tag.getId(), tag.getName())).collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesByTitle(String name){
        return courseRepository.findByNameContaining(name)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesByView(){
        return courseRepository.findAllByOrderByViewDesc().stream().map(CoursesRes::of).collect(Collectors.toList());
    }

    public List<CoursesRes> getFreeCourses(){
        return courseRepository.findByPrice(0L)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getOfferingCourses(){
        Long tagSize = courseRepository.getTagSize();
        LocalDate today = LocalDate.now();
        Long randomTagId = today.getDayOfYear() % tagSize + 1;

        return courseRepository.findByTagId(randomTagId)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getRegisteredCourses(Long memberId){
        return courseRepository.findByRegisteredMemberId(memberId)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesByTags(List<Long> tags){
        return courseRepository.findByTagIds(tags, Long.valueOf(tags.size()))
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());

    }
}
