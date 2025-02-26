package io.github.duarte50.illiad.dto;

import io.github.duarte50.illiad.entity.VideoInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoInfoDTO {
    private UUID id;
    private String title;
    private String description;
    private String channel;
    private int views;
    private int likes;
    private int dislikes;
    private Timestamp createdAt;
    private Timestamp lastEditAt;

    public static VideoInfoDTO map(VideoInfo videoInfo) {
        return VideoInfoDTO.builder()
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
