package com.ssafy.api.service;

import com.ssafy.api.response.CourseRes;
import com.ssafy.api.response.CoursesRes;
import com.ssafy.api.response.SearchRes;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.common.util.GCSUtil;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Curriculum;
import com.ssafy.db.entity.Instructor;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.CurriculumRepository;
import com.ssafy.db.repository.InstructorRepository;
import com.ssafy.db.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseSerivce {

    private final CourseRepository courseRepository;
    private final TagRepository tagRepository;
    private final InstructorRepository instructorRepository;
    private final CurriculumRepository curriculumRepository;

    public List<SearchRes> getCoursesByTitle(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Map<Long, Long> countRegistersByCourseId = courseRepository.countRegistersByCourse().stream()
                .collect(Collectors.toMap(
                        arr -> (Long) arr[0],
                        arr -> (Long) arr[1]
                ));

        return courseRepository.findByKeyword(name, pageable)
                .stream()
                .map(course ->
                        SearchRes.of(course,
                                countRegistersByCourseId.get(course.getId()),
                                course.getCourseTagList().stream().map(ct -> ct.getTag().getName()).collect(Collectors.toList()))
                )
                .collect(Collectors.toList());
    }

    public List<SearchRes> getCoursesByTags(List<Long> tags, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Map<Long, Long> countRegistersByCourseId = courseRepository.countRegistersByCourse().stream()
                .collect(Collectors.toMap(
                        arr -> (Long) arr[0],
                        arr -> (Long) arr[1]
                ));

        return courseRepository.findByTagIds(tags, Long.valueOf(tags.size()), pageable)
                .stream()
                .map(course ->
                        SearchRes.of(course,
                                countRegistersByCourseId.get(course.getId()),
                                course.getCourseTagList().stream().map(ct -> ct.getTag().getName()).collect(Collectors.toList()))
                )
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getOfferingCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Long tagSize = tagRepository.getTagSize();
        LocalDate today = LocalDate.now();
        Long randomTagId = today.getDayOfYear() % tagSize + 1;

        return courseRepository.findByTagId(randomTagId, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesByView(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return courseRepository.findAllByOrderByViewDesc(pageable).stream().map(CoursesRes::of).collect(Collectors.toList());
    }

    public List<CoursesRes> getFreeCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return courseRepository.findByPrice(0L, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getLatestCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        return courseRepository.findAll(pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getRegisteredCourses(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        return courseRepository.findByRegisteredMemberId(id, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesImade(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not Found Instructor : Instructor_id is " + id)
        );

        return courseRepository.findByInstructor(instructor).stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesIregistered(Long id) {
        return courseRepository.findByRegisteredMemberId(id)
                .stream().map(CoursesRes::of).collect(Collectors.toList());
    }

    public CourseRes getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not Found Course : Course_id is " + id)
        );
        course.upView();
        return CourseRes.of(course);
    }


    /////////////////////////////////////////////////
    public Boolean existsCourse(Long courseId) {
        return (courseRepository.existsById(courseId)) ? true : false;
    }

    public Boolean instructorInCourse(Long courseId, Long memberId) {
        Long instructorId = instructorRepository.findInstructorIdByCourseId(courseId).orElseThrow(
                () -> new NotFoundException("Not Found Course : Course_id is " + courseId));
        ;
        return instructorId.equals(memberId);
    }

    public Boolean existRegister(Long memberId, Long courseId) {
        return courseRepository.existsRegisterByMemberIdAndCourseId(memberId, courseId) != null;
    }

    @Transactional
    public Boolean saveVideoUrl(String fileName, Long courseId, Long curriculumId) {
        Curriculum curriculum = curriculumRepository.findByIndexNoAndCourseId(curriculumId, courseId)
                .orElseThrow(() -> new NotFoundException("Not Found Curriculum")
                );
        curriculum.setRecUrl(GCSUtil.preUrl + fileName);
        curriculumRepository.save(curriculum);
        return true;
    }
}
