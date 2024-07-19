package com.ssafy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.request.PhotoPostReq;
import com.ssafy.api.service.PhotoService;
import com.ssafy.db.entity.Photo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "Course Controller")
@RestController
@AllArgsConstructor
@RequestMapping("/api/photo")
public class PhotoController {
    private final PhotoService photoService;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Photo> uploadPhoto(
            @ModelAttribute PhotoPostReq photoPostReq){
        try{
            Photo photo = photoService.uploadPhoto(photoPostReq.getFile(), photoPostReq.getName());
            return ResponseEntity.ok(photo);
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
}
