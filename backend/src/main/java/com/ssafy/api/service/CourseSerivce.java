package com.ssafy.api.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.ssafy.api.request.CourseCreatePostReq;
import com.ssafy.api.request.CourseModifyUpdateReq;
import com.ssafy.api.request.CurriculumPostReq;
import com.ssafy.api.response.CourseRes;
import com.ssafy.api.response.CoursesRes;
import com.ssafy.api.response.InstructorRes;
import com.ssafy.api.response.TagRes;
import com.ssafy.common.custom.BadRequestException;
import com.ssafy.common.custom.ConflictException;
import com.ssafy.common.custom.NotFoundException;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseSerivce {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CourseTagRepository courseTagRepository;
    private final TagRepository tagRepository;
    private final CurriculumRepository curriculumRepository;
    private final Bucket bucket;

    //////////////////////////////////////////////////////////////////////////
    public Course createCourse(CourseCreatePostReq courseCreatePostReq, Long memberId) {
        try {
            // 1. 유효성 검증
            // 요청자가 강사가 아닐때
            Instructor instructor = instructorRepository.findById(memberId).orElseThrow(
                    () -> new NotFoundException("Instructor not found")
            );
            // 빈 파일일때
            if (courseCreatePostReq.getImg() == null) {
                throw new BadRequestException("Not File");
            }
            // 이미지 파일이 아닐때
            String contentType = courseCreatePostReq.getImg().getContentType();
            if (!contentType.startsWith("image/")) { // contentType 확인 >> img 아니면 예외처리
                throw new BadRequestException("Not Image File");
            }

            // 2. 서비스 로직 실행
            // 현재 시간
            LocalDateTime now = LocalDateTime.now();
            // 타입 변경
            Timestamp timestamp = Timestamp.valueOf(now);
            // 일단 url null로 course 생성 + 등록 >> course id 생성!
            Course newCourse = courseCreatePostReq.toEntity(instructor, timestamp, null);
            newCourse = courseRepository.save(newCourse);

            // CourseTag에 등록
            List<Long> tags = courseCreatePostReq.getTags();
            if (tags != null) {
                for (Long tagId : tags) {
                    CourseTag newCoursTag = new CourseTag();
                    Tag tag = tagRepository.getOne(tagId);
                    newCoursTag.setCourse(newCourse);
                    newCoursTag.setTag(tag);
                    courseTagRepository.save(newCoursTag);
                }
            }

            // Curriculum 생성 및 추가
            List<CurriculumPostReq> curriculumRequests = courseCreatePostReq.getCurriculums();
            if (curriculumRequests != null) {
                Long idx = 0L;
                newCourse.initCurriculumList();
                for (CurriculumPostReq currReq : curriculumRequests) {
                    Curriculum newCurr = currReq.toEntity(newCourse, idx++, null);
                    newCurr = curriculumRepository.save(newCurr);

                    String blobName = "curr_" + newCurr.getId() + "_banner";
                    BlobInfo blobInfo = bucket.create(blobName, currReq.getImg().getBytes(), currReq.getImg().getContentType());
                    newCurr.setImgUrl(blobInfo.getMediaLink());
                    newCourse.addCurriculum(newCurr); // Course에 Curriculum 추가
                }
            }
            // 이미지 파일 네이밍
            String blobName = "course_" + newCourse.getId() + "_banner";
            BlobInfo blobInfo = bucket.create(blobName, courseCreatePostReq.getImg().getBytes(), courseCreatePostReq.getImg().getContentType());
            // img_url 넣어주기
            newCourse.setImgUrl(blobInfo.getMediaLink());
            // 3. 업데이트된 정보로 다시 저장
            return courseRepository.save(newCourse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create course due to image processing error", e);
        }
    }

    public Course updateCourse(Long courseId, CourseModifyUpdateReq courseModifyUpdateReq, Long memberId) {
        // 1. 유효성 검증
        Instructor instructor = instructorRepository.findById(memberId).orElseThrow(
                () -> new NotFoundException("Instructor not found")
        );

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new BadRequestException("Course not found");
        }
        MultipartFile img = courseModifyUpdateReq.getImg();
        if (img != null) {
            String contentType = img.getContentType();
            if (!contentType.startsWith("image/")) { // contentType 확인 >> img 아니면 예외처리
                throw new BadRequestException("Not Image File");
            }
        }
        try {
            // 2. 서비스 로직
            Course course = courseOptional.get();
            // 변경사항 확인 후 적용
            if (courseModifyUpdateReq.getName() != null) {
                course.setName(courseModifyUpdateReq.getName());
            }
            if (courseModifyUpdateReq.getSummary() != null) {
                course.setSummary(courseModifyUpdateReq.getSummary());
            }
            if (courseModifyUpdateReq.getPrice() != null) {
                course.setPrice(courseModifyUpdateReq.getPrice());
            }
            if (courseModifyUpdateReq.getDescription() != null) {
                course.setDescription(courseModifyUpdateReq.getDescription());
            }
            if (img != null) { // 이미지는 기존꺼 삭제 후 다시 저장..
                String blobName = "course_" + courseId + "_banner";

                Blob blob = bucket.get(blobName);
                blob.delete();

                BlobInfo blobInfo = bucket.create(blobName, img.getBytes(), img.getContentType());
                course.setImgUrl(blobInfo.getMediaLink());
            }

            List<CourseTag> oldTags = course.getCourseTagList();

            for(CourseTag ct: oldTags) System.out.println(ct.getTag().getId());
            courseTagRepository.deleteAll(oldTags);
            course.getCourseTagList().clear();

            if(courseModifyUpdateReq.getTags() != null){
                List<Tag> tagsToAdd = tagRepository.findAllById(courseModifyUpdateReq.getTags());
                List<CourseTag> newTags = new ArrayList<>();
                for(Tag tag : tagsToAdd){
                    tag.getId();
                    CourseTag courseTag = new CourseTag();
                    courseTag.setTag(tag);
                    courseTag.setCourse(course);

                    newTags.add(courseTag);
                    courseTagRepository.save(courseTag);
                }
                course.setCourseTagList(newTags);
            }

            // 3. 정보 업데이트
            return courseRepository.save(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////////////////////////////////////
    public List<TagRes> getTagList() {
        return tagRepository.findAll().stream().map(tag -> new TagRes(tag.getId(), tag.getName())).collect(Collectors.toList());
    }

    //////////////////////////////////////////////////////////////////////////
    public List<CoursesRes> getCoursesByTitle(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findByKeyword(name, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesByTags(List<Long> tags, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return courseRepository.findByTagIds(tags, Long.valueOf(tags.size()), pageable)
                .stream()
                .map(CoursesRes::of)
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

    public List<CoursesRes> getRegisteredCourses(Long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        return courseRepository.findByRegisteredMemberId(memberId, pageable)
                .stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    //////////////////////////////////////////////////////////////////////////

    @Transactional
    public CourseRes getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not Found Course : Course_id is " + id)
        );
        course.upView();
        return CourseRes.of(course);
    }


    public InstructorRes getInstructorByCourseId(Long id) {
        return instructorRepository.findInstructorByCourseId(id).orElseThrow(
                () -> new NotFoundException("Not Found Instructor of Course : Course_id is " + id)
        );
    }

    //////////////////////////////////////////////////////////////////////////

    public List<CoursesRes> getCoursesImade(Long member_id) {
        Instructor instructor = instructorRepository.findById(member_id).orElseThrow(
                () -> new NotFoundException("Not Found Instructor : Instructor_id is " + member_id)
        );

        return courseRepository.findByInstructor(instructor).stream()
                .map(CoursesRes::of)
                .collect(Collectors.toList());
    }

    public List<CoursesRes> getCoursesIregistered(Long member_id) {
        return courseRepository.findByRegisteredMemberId(member_id)
                .stream().map(CoursesRes::of).collect(Collectors.toList());
    }

    public Boolean existRegister(Long memberId, Long courseId) {
        if (courseRepository.existsRegisterByMemberIdAndCourseId(memberId, courseId) == null) {
            return false;
        }
        return true;
    }

    public void doRegister(Long memberId, Long courseId) {
        // 이미 수강중이면
        if (existRegister(memberId, courseId) == true) {
            throw new ConflictException("Already registered");
        }
        // TODO point 사용?

        // 수강등록 성공여부
        if (courseRepository.postRegister(memberId, courseId) == false) {
            throw new NotFoundException("Not Found Course or Member");
        }
        ;
    }

    public void cancelRegister(Long memberId, Long courseId) {
        // 수강취소
        if (courseRepository.deleteRegister(memberId, courseId) == false) {
            throw new BadRequestException("Not Found Course or Member or Register");
        }
    }
}
