package com.ssafy.api.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.ssafy.db.entity.Photo;
import com.ssafy.db.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class PhotoService {

    private final Bucket bucket;
    private final PhotoRepository photoRepository;

    public PhotoService(Bucket bucket, PhotoRepository photoRepository) {
        this.bucket = bucket;
        this.photoRepository = photoRepository;
    }

    public Photo uploadPhoto(MultipartFile file, String name) throws IOException {
        String blobName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobInfo blobInfo = bucket.create(blobName, file.getBytes(), file.getContentType());

        Photo photo = new Photo();
        photo.setName(name);
        photo.setUrl(blobInfo.getMediaLink());
        return photoRepository.save(photo);
    }
}