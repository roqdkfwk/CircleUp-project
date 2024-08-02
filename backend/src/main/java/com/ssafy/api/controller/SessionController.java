package com.ssafy.api.controller;

import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@ApiIgnore
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @PostMapping
    @RequestMapping("/{course_id}")
    public ResponseEntity<String> initializeSession(
            @PathVariable(name = "course_id") String courseId,
            @RequestHeader(required = false, value = "Memberid") String memberId
    ) throws OpenViduJavaClientException, OpenViduHttpException {

        Map<String, Object> map = new HashMap<>();
        map.put("customSessionId", courseId);
        SessionProperties properties = SessionProperties.fromJson(map).build();

        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    @PostMapping("/{course_id}/connections")
    public ResponseEntity<String> createConnection(
            @PathVariable("course_id") String courseId,
            @RequestBody(required = false) Map<String, Object> params
    ) throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(courseId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }
}

