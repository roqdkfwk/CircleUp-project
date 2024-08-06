package com.ssafy.common.util;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.ssafy.api.request.CourseCreatePostReq;
import com.ssafy.api.request.CurriculumPostReq;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.Curriculum;
import org.springframework.web.multipart.MultipartFile;

public class GCSUtil {
    public static final String preUrl = "https://storage.googleapis.com/circleup-bucket/";

    public static void saveCourseImg(Course newCourse, Bucket bucket, CourseCreatePostReq courseCreatePostReq){
        try{
            String blobName = "course_" + newCourse.getId() + "_banner";
            BlobInfo blobInfo = bucket.create(blobName, courseCreatePostReq.getImg().getBytes(), courseCreatePostReq.getImg().getContentType());
            // img_url 넣어주기
            newCourse.setImgUrl(preUrl+blobName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveCurrImg(Curriculum newCurr, Bucket bucket, CurriculumPostReq curriculumPostReq){
        try{
            String blobName = "curriculum_" + newCurr.getId() + "_banner";
            BlobInfo blobInfo = bucket.create(blobName, curriculumPostReq.getImg().getBytes(), curriculumPostReq.getImg().getContentType());

            newCurr.setImgUrl(preUrl+blobName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteCourseImg(Long courseId, Bucket bucket){
        String blobName = "course_" + courseId + "_banner";
        Blob blob = bucket.get(blobName);
        blob.delete();
    }

    public static void updateCurrImg(Curriculum curriculum, Bucket bucket, MultipartFile img){
        try {
            String blobName = "curriculum_" + curriculum.getId() + "_banner";

            Blob blob = bucket.get(blobName);
            blob.delete();

            BlobInfo blobInfo = bucket.create(blobName, img.getBytes(), img.getContentType());
            curriculum.setImgUrl(preUrl + blobName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteCurrImg(Long curriculumId, Bucket bucket){
        String blobName = "curriculum_" + curriculumId + "_banner";
        Blob blob = bucket.get(blobName);
        blob.delete();
    }
}
