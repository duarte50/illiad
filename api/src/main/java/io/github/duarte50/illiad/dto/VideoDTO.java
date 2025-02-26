package io.github.duarte50.illiad.dto;

import io.github.duarte50.illiad.entity.VideoInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
    private UUID id;
    private String title;
    private String description;
    private String channel;
    private int views;
    private int likes;
    private int dislikes;
    private Timestamp createdAt;
    private Timestamp lastEditAt;
    private byte[] videoData;

    public static VideoDTO map(VideoInfo videoInfo) {
        return VideoDTO.builder()
                .id(videoInfo.getId())
                .title(videoInfo.getTitle())
                .description(videoInfo.getDescription())
                .channel(videoInfo.getPoster().getChannelName())
                .views(videoInfo.getViews())
                .likes(videoInfo.getLikes())
                .dislikes(videoInfo.getDislikes())
                .createdAt(videoInfo.getCreatedAt())
                .lastEditAt(videoInfo.getLastEditAt())
                .build();
    }
}
