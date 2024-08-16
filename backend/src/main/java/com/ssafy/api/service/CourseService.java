package com.ssafy.api.service;

import com.ssafy.api.response.*;
import com.ssafy.common.custom.BadRequestException;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.common.util.GCSUtil;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Curriculum;
import com.ssafy.db.entity.Instructor;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.enums.Role;
import com.ssafy.db.entity.enums.Status;
import com.ssafy.db.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final BasicService basicService;
    private final AppliedService appliedService;

    ////////////////////////////////////////
    public List<CoursesRes> getAdminPendingCourses(Long memberId) {
        Member admin = basicService.findMemberByMemberId(memberId);

        if (!admin.getRole().equals(Role.Admin)) throw new BadRequestException("Not Admin");

        return courseRepository.findAllByStatus(Status.Pending)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    } // 승인 대기 중인 강의 for 관리자

    // 상태 별 강의 조회 for 강사
    public List<CoursesRes> getMyCoursesByStatus(Long memberId, String status) {
        Member instructor = basicService.findMemberByMemberId(memberId);

        if (!instructor.getRole().equals(Role.Instructor)) throw new BadRequestException("Not Instructor");

        return courseRepository.findByStatusAndInstructorId(Status.get(status), memberId)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

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

        Long tagSize = basicService.getTagCount();
        LocalDate today = LocalDate.now();
        Long randomTagId = today.getDayOfYear() % tagSize + 1;

        return courseRepository.findByTagId(randomTagId, Status.Approved, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesByView(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return courseRepository
                .findAllByStatusOrderByViewDesc(Status.Approved, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getFreeCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return courseRepository.findByPriceAndStatus(0L, Status.Approved, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getLatestCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        return courseRepository.findAllByStatus(Status.Approved, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    ////////////////////////////

    public List<CoursesRes> getRegisteredCourses(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        return courseRepository.findByRegisteredMemberId(id, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesImade(Long memberId) {
        Instructor instructor = basicService.findInstructorByInstructorId(memberId);

        return courseRepository.findByInstructor(instructor).stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesIregistered(Long id) {
        return courseRepository.findByRegisteredMemberId(id)
                .stream().map(CoursesRes::of).collect(Collectors.toList());
    }

    public CourseRes getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found : Course_id is " + courseId)
        );

        course.upView();
        return CourseRes.fromEntity(course);
    }

    public Boolean existsCourse(Long courseId) {
        return (courseRepository.existsById(courseId)) ? true : false;
    }

    public Boolean instructorInCourse(Long courseId, Long memberId) {
        Long instructorId = appliedService.getInstructorIdByCourseId(courseId);
        return instructorId.equals(memberId);
    }

    public Boolean existRegister(Long memberId, Long courseId) {
        return basicService.existsRegisterByMemberIdAndCourseId(memberId, courseId);
    }

    @Transactional
    public Boolean saveVideoUrl(String fileName, Long curriculumId) {

        Curriculum curriculum = basicService.findCurriculumByCurriculumId(curriculumId);
        Long courseId = curriculum.getCourse().getId();
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found : Course_id is " + courseId)
        );
        curriculum.setRecUrl(GCSUtil.preUrl + fileName);
        basicService.saveCurriculum(curriculum);
        course.setCompletedCourse(curriculum.getIndexNo());
        basicService.saveCourse(course);
        return true;
    }

    public List<CurriculumRes> getCurriculumList(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found : Course_id is " + courseId)
        );

        return basicService.findCurriculumListByCourseId(courseId).stream()
                .map(curriculum -> CurriculumRes.fromEntity(curriculum, course.getCompletedCourse()))
                .collect(Collectors.toList());
    }

    public List<CurriculumUrlRes> getCurriculumUrlList(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course not found : Course_id is " + courseId)
        );

        long size = course.getCompletedCourse();
        List<CurriculumUrlRes> curriculumUrlResList = new ArrayList<>();

        List<Curriculum> curriculumList = course.getCurriculumList();

        for (int i = 0; i < size; i++) {
            Curriculum curriculum = curriculumList.get(i);
            curriculumUrlResList.add(CurriculumUrlRes.fromEntity(curriculum));
        }

        return curriculumUrlResList;
    }

}
