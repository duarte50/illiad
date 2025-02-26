package io.github.duarte50.illiad.service;

import io.github.duarte50.illiad.dto.VideoDTO;
import io.github.duarte50.illiad.dto.VideoInfoDTO;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface VideoService {
    Optional<VideoDTO> create(VideoDTO videoDTO, String token) throws IOException;
    Page<VideoInfoDTO> readAllRecent(int page);
    Page<VideoInfoDTO> readAllByPoster(int page, String username);
    Page<VideoInfoDTO> readAllByTitle(int page, String title);
    Optional<VideoDTO> readOneById(UUID id);
    void update(UUID id, VideoInfoDTO videoInfoDTO, String token);
    void delete(UUID id, String token);
}
