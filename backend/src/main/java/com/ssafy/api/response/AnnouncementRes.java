package com.ssafy.api.response;

import com.ssafy.db.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AnnouncementRes {
    Long id;
    String author;
    String title;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static AnnouncementRes fromEntity(Announcement announcement) {
        return AnnouncementRes.builder()
                .id(announcement.getId())
                .author(announcement.getAuthor().getName())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .createdAt(announcement.getCreatedAt())
                .updatedAt(announcement.getUpdatedAt())
                .build();
    }
}
