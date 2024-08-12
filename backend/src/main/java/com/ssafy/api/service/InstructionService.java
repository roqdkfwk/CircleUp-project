package com.ssafy.api.service;

import com.google.cloud.storage.Bucket;
import com.ssafy.api.request.CourseCreatePostReq;
import com.ssafy.api.request.CourseModifyUpdateReq;
import com.ssafy.api.request.CurriculumPostReq;
import com.ssafy.api.request.CurriculumUpdateReq;
import com.ssafy.api.response.CourseRes;
import com.ssafy.common.custom.BadRequestException;
import com.ssafy.common.util.GCSUtil;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Curriculum;
import com.ssafy.db.entity.Instructor;
import com.ssafy.db.entity.Tag;
import com.ssafy.db.entity.enums.Status;
import com.ssafy.db.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructionService {

    private final CourseRepository courseRepository;
    private final BasicService basicService;
    private final AppliedService appliedService;
    private final Bucket bucket;

    // Course 관리
    public CourseRes createCourse(CourseCreatePostReq courseCreatePostReq, Long memberId) {
        Instructor instructor = basicService.findInstructorByInstructorId(memberId);

        if (courseCreatePostReq.getImg() == null) {
            throw new BadRequestException("Not File");
        }

        String contentType = courseCreatePostReq.getImg().getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BadRequestException("Not Image File");
        }

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        Course course = courseCreatePostReq.toEntity(instructor, timestamp, null);
        basicService.saveCourse(course);

        // todo :: partseTags 이대로? 다운이형이 프론트 코드 고칠 여력 없으면 그냥 이대로 두기...
        try {
            List<Tag> tagList = basicService.findTagListByTagIds(courseCreatePostReq.parseTags());
            course.addTag(tagList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create course", e);
        }

        GCSUtil.saveCourseImg(course, bucket, courseCreatePostReq.getImg());

        basicService.saveCourse(course);
        return CourseRes.fromEntity(course);
    }

    public CourseRes updateCourse(Long courseId, CourseModifyUpdateReq courseModifyUpdateReq, Long memberId) {
        try {
            // 1. 유효성 검증
            Instructor instructor = basicService.findInstructorByInstructorId(memberId);
            Course course = basicService.findCourseByCourseId(courseId);

            if (!course.getInstructor().equals(instructor)) {
                throw new BadRequestException("Instructor doesn't own the course");
            }

//            if(!course.getStatus().equals(Status.Draft) && !course.getStatus().equals(Status.Rejected)){
//                throw new BadRequestException("Status is "+course.getStatus());
//            }

            MultipartFile img = courseModifyUpdateReq.getImg();
            if (img != null) {
                String contentType = img.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) { // contentType 확인 >> img 아니면 예외처리
                    throw new BadRequestException("Not Image File");
                }
            }

            List<Tag> tagList = basicService.findTagListByTagIds(courseModifyUpdateReq.parseTags());
            course.update(courseModifyUpdateReq, tagList, bucket);

            basicService.saveCourse(course);
            return CourseRes.fromEntity(course);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update course", e);
        }
    }

    public void deleteCourse(Long courseId, Long memberId) {
        Course course = basicService.findCourseByCourseId(courseId);

//        if(course.getStatus().equals(Status.Pending) || course.getStatus().equals(Status.Completed)){
//            throw new BadRequestException("Status is "+ course.getStatus());
//        }
//
//        if (course.getStatus().equals(Status.Approved) && registerRepository.countByCourseId(courseId) > 0) {
//            throw new BadRequestException("Registrant exists");
//        }

        if (appliedService.getRegisterCount(courseId) > 0) {
            throw new BadRequestException("Registrant exists");
        }

        Instructor instructor = basicService.findInstructorByInstructorId(memberId);

        if (!course.getInstructor().equals(instructor)) {
            throw new BadRequestException("Instructor doesn't own the course");
        }

        System.out.println("###" + courseId);
        GCSUtil.deleteCourseImg(courseId, bucket);

        courseRepository.delete(course);
    }

    public CourseRes enqueueCourse(Long memberId, Long courseId) {
        Instructor instructor = basicService.findInstructorByInstructorId(memberId);

        Course course = basicService.findCourseByCourseId(courseId);

        if (!course.getInstructor().equals(instructor)) {
            throw new BadRequestException("Instructor doesn't own the course");
        }

        if (course.getStatus() != Status.Draft) throw new BadRequestException("Status is not Draft");

        course.setStatus(Status.Pending);
        basicService.saveCourse(course);

        return CourseRes.fromEntity(course);
    } // 강의 개설 신청 for 강사

    public CourseRes dequeueCourse(Long memberId, Long courseId) {
        Instructor instructor = basicService.findInstructorByInstructorId(memberId);

        Course course = basicService.findCourseByCourseId(courseId);

        if (!course.getInstructor().equals(instructor)) {
            throw new BadRequestException("Instructor doesn't own the course");
        }

        if (course.getStatus() != Status.Pending) throw new BadRequestException("Status is not Pending");

        course.setStatus(Status.Draft);
        basicService.saveCourse(course);

        return CourseRes.fromEntity(course);
    } // 강의 개설 신청 취소 for 강사

    // Curriculum 관리
    public CourseRes createCurriculum(CurriculumPostReq curriculumPostReq, Long courseId, Long memberId) {
        try {
            Instructor instructor = basicService.findInstructorByInstructorId(memberId);

            Course course = basicService.findCourseByCourseId(courseId);

//            if(!course.getStatus().equals(Status.Draft) && !course.getStatus().equals(Status.Rejected)){
//                throw new BadRequestException("Status is "+course.getStatus());
//            }

            if (!course.getInstructor().equals(instructor)) {
                throw new BadRequestException("Instructor doesn't own the course");
            }

            Curriculum curriculum = curriculumPostReq.toEntity(course);

            basicService.saveCurriculum(curriculum);

            return CourseRes.fromEntity(course);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create curriculum", e);
        }
    }

    public CourseRes updateCurriculum(CurriculumUpdateReq curriculumUpdateReq, Long courseId, Long curriculumId, Long memberId) {
        try {
            Instructor instructor = basicService.findInstructorByInstructorId(memberId);

            Course course = basicService.findCourseByCourseId(courseId);

//            if(!course.getStatus().equals(Status.Draft) && !course.getStatus().equals(Status.Rejected)){
//                throw new BadRequestException("Status is "+course.getStatus());
//            }

            if (!course.getInstructor().equals(instructor)) {
                throw new BadRequestException("Instructor doesn't own the course");
            }

            Curriculum curriculum = basicService.findCurriculumByCurriculumId(curriculumId);

            curriculum.update(curriculumUpdateReq, bucket);
            basicService.saveCurriculum(curriculum);

            return CourseRes.fromEntity(course);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update curriculum", e);
        }
    }

    public void deleteCurriculum(Long courseId, Long curriculumId, Long memberId) {
        Instructor instructor = basicService.findInstructorByInstructorId(memberId);
        Course course = basicService.findCourseByCourseId(courseId);

//        if(!course.getStatus().equals(Status.Draft) && !course.getStatus().equals(Status.Rejected)){
//            throw new BadRequestException("Status is "+course.getStatus());
//        }

        if (!course.getInstructor().equals(instructor)) {
            throw new BadRequestException("Instructor doesn't own the course");
        }

        Curriculum curriculum = basicService.findCurriculumByCurriculumId(curriculumId);

        if (curriculum.getTime() != null && curriculum.getTime() > 0) {
            throw new BadRequestException("Curriculum already done");
        }

        basicService.deleteCurriculum(curriculum);

        int idxDeleted = Math.toIntExact(curriculum.getIndexNo());
        List<Curriculum> curriculumList = course.getCurriculumList();
        for (int idx = idxDeleted - 1; idx < curriculumList.size(); idx++) {
            Curriculum curr = curriculumList.get(idx);
            curr.setIndexNo(idx + 1L);
            basicService.saveCurriculum(curr);
        }
    }

    //

    public void uploadDoc(Long courseId, Long curriculumId, Long memberId, MultipartFile doc) {
        Instructor instructor = basicService.findInstructorByInstructorId(memberId);

        Course course = basicService.findCourseByCourseId(courseId);

        if (!course.getInstructor().equals(instructor)) {
            throw new BadRequestException("Instructor doesn't own the course");
        }

        Curriculum curriculum = basicService.findCurriculumByCurriculumId(curriculumId);

        try {
            String blobName = "curriculum_" + curriculumId + "_doc";
            bucket.create(blobName, doc.getBytes(), doc.getContentType());
            curriculum.setDocUrl(GCSUtil.preUrl + blobName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        basicService.saveCurriculum(curriculum);
    }
}
