package com.ssafy.api.controller;

import com.google.cloud.storage.Bucket;
import com.ssafy.api.service.CourseService;
import com.ssafy.common.custom.RequiredAuth;
import io.openvidu.java.client.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = {"Live"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final CourseService courseSerivce;
    private final Bucket bucket;

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;
    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;
    @Value("${OPENVIDU_RECORDINGPATH}")
    private String OPENVIDU_RECORDINGPATH;

    private OpenVidu openvidu;

    public SessionController(CourseService courseSerivce, Bucket bucket) {
        this.courseSerivce = courseSerivce;
        this.bucket = bucket;
    }

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    /**
     * Live 여부 확인
     */
    @GetMapping("/{course_id}")
    @ApiOperation(value = "Live 여부 확인")
    public ResponseEntity<?> existSession(
            @PathVariable(name = "course_id") String courseId
    ) {
        return new ResponseEntity<>(openvidu.getActiveSession(courseId) == null ? false : true, HttpStatus.OK);
    }

    /**
     * Live 방 개설
     */
    @PostMapping("/{course_id}")
    @ApiResponses({
            @ApiResponse(code = 401, message = "권한 없음(해당 강의의 강사가 아니거나 탈퇴한 회원임)"),
            @ApiResponse(code = 404, message = "존재하지 않는 강의")
    })
    @RequiredAuth
    @ApiOperation(value = "Live 방 개설", notes = "강사만 본인 강의의 Live를 개설할 수 있습니다")
    public ResponseEntity<String> createSession(
            @PathVariable(name = "course_id") String courseId,
            Authentication authentication
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        if (courseSerivce.instructorInCourse(Long.valueOf(courseId), memberId)) {
            makeSession(courseId);
            return new ResponseEntity<>(courseId, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Live 종료
     */
    @DeleteMapping("/{course_id}")
    @RequiredAuth
    @ApiResponses({
            @ApiResponse(code = 401, message = "권한 없음"),
    })
    @ApiOperation(value = "Live 종료", notes = "강사는 라이브를 종료하고 화면녹화파일을 저장한다")
    public ResponseEntity<ArrayList<String>> sessionClose(
            @PathVariable(name = "course_id") String course_id,
            @RequestParam(name = "curriculum_id") Long curriculumId,
            Authentication authentication
    ) {
        Long courseId = Long.valueOf(course_id);
        Long memberId = Long.valueOf(authentication.getName());
        if (courseSerivce.instructorInCourse(Long.valueOf(courseId), memberId) == false) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            openvidu.stopRecording(course_id);
            openvidu.getActiveSession(course_id).close();
        } catch (Exception e) {

        } finally {
            if (!saveVideo(courseId, curriculumId)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Live 방 접속
     */
    @PostMapping("/{course_id}/connections")
    @ApiOperation(value = "Live 방 접속", notes = "수강중인 라이브 강의로 접속할 수 있습니다")
    @ApiResponses({
            @ApiResponse(code = 401, message = "권한 없음(수강중인 회원이 아님)"),
            @ApiResponse(code = 404, message = "존재하지 않는 강의거나 라이브 강의를 찾을 수 없음"),
            @ApiResponse(code = 500, message = "세션 생성 에러")
    })
    @RequiredAuth
    public ResponseEntity<String> createConnection(
            @PathVariable("course_id") String courseId,
            Authentication authentication
    ) throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(courseId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long memberId = Long.valueOf(authentication.getName());
        if (courseSerivce.existRegister(memberId, Long.valueOf(courseId))
                || courseSerivce.instructorInCourse(Long.valueOf(courseId), memberId)) {
            ConnectionProperties properties = new ConnectionProperties.Builder().build();
            Connection connection = session.createConnection(properties);
            return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Live 방 퇴출
     */
    @DeleteMapping("/{course_id}/connections")
    @ApiOperation(value = "Live 방 퇴출")
    @ApiResponses({
            @ApiResponse(code = 500, message = "세션이나 커넥션을 찾지못하여 에러")
    })
    @RequiredAuth
    public ResponseEntity<String> deleteConnection(
            @PathVariable("course_id") String courseId,
            @RequestParam(name = "connection_id") String connectionId) {
        try {
            Session session = openvidu.getActiveSession(courseId);
            session.forceDisconnect(connectionId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /////////////////////////////////////////////////////

    private String makeSession(String courseId) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("customSessionId", courseId);
            RecordingProperties recordingProperties = new RecordingProperties.Builder()
                    .outputMode(Recording.OutputMode.COMPOSED)
                    .resolution("1280x960")
                    .frameRate(48)
                    .build();
            SessionProperties properties = new SessionProperties.Builder()
                    .customSessionId(courseId).recordingMode(RecordingMode.ALWAYS).defaultRecordingProperties(recordingProperties).build();
            Session session = openvidu.createSession(properties);
            openvidu.listRecordings();
            return session.getSessionId();
        } catch (OpenViduJavaClientException e) {
            throw new RuntimeException(e);
        } catch (OpenViduHttpException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean saveVideo(Long courseId, Long curriculumId) {

        String originalPath = OPENVIDU_RECORDINGPATH + "/" + courseId + "/" + courseId + ".mp4";
        String fileName = UUID.randomUUID().toString() + ".mp4";

        try (InputStream inputStream = new FileInputStream(originalPath)) {
            bucket.create(fileName, inputStream, "mp4");
            courseSerivce.saveVideoUrl(fileName, curriculumId);
            openvidu.deleteRecording(courseId.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

