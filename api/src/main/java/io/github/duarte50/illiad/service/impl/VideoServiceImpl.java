package io.github.duarte50.illiad.service.impl;

import io.github.duarte50.illiad.dto.VideoDTO;
import io.github.duarte50.illiad.dto.VideoInfoDTO;
import io.github.duarte50.illiad.entity.VideoData;
import io.github.duarte50.illiad.entity.VideoInfo;
import io.github.duarte50.illiad.repository.UserRepository;
import io.github.duarte50.illiad.repository.VideoDataRepository;
import io.github.duarte50.illiad.repository.VideoInfoRepository;
import io.github.duarte50.illiad.service.JwtService;
import io.github.duarte50.illiad.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoDataRepository videoDataRepository;
    private final VideoInfoRepository videoInfoRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public Optional<VideoDTO> create(VideoDTO videoDTO, String token) throws IOException {
        var userEmail = jwtService.extractUsername(token.substring(7));
        var findUser = userRepository.findByEmail(userEmail);

        if (findUser.isEmpty()) {
            return Optional.empty();
        }

        var user = findUser.get();
        byte[] videoData = videoDTO.getVideoData();
        var videoDataEntity = videoDataRepository.save(VideoData.builder()
                .data(videoData)  // Store the video data as byte[]
                .build());
        var video = videoInfoRepository.save(VideoInfo.builder()
                .title(videoDTO.getTitle())
                .description(videoDTO.getDescription())
                .views(0)
                .likes(0)
                .dislikes(0)
                .createdAt(Timestamp.from(Instant.now()))
                .lastEditAt(Timestamp.from(Instant.now()))
                .videoData(videoDataEntity)
                .poster(user)
                .build());

        return Optional.of(VideoDTO.map(video));
    }

    @Override
    public Page<VideoInfoDTO> readAllRecent(int page) {
        var findVideos = videoInfoRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(Math.max(0, page - 1), 20));
        return findVideos.map(VideoInfoDTO::map);
    }

    @Override
    public Page<VideoInfoDTO> readAllByPoster(int page, String username) {
        var findUser = userRepository.findByUsername(username);

        if (findUser.isEmpty()) {
            return Page.empty(PageRequest.of(Math.max(0, page - 1), 20));
        }

        var user = findUser.get();

        var findVideos = videoInfoRepository.findAllByPoster(user, PageRequest.of(Math.max(0, page - 1), 20));

        return findVideos.map(VideoInfoDTO::map);
    }

    @Override
    public Page<VideoInfoDTO> readAllByTitle(int page, String title) {
        var findVideos = videoInfoRepository.findAllByTitleContainingIgnoreCaseOrderByCreatedAtDesc(
                title, PageRequest.of(Math.max(0, page - 1), 20)
        );

        return findVideos.map(VideoInfoDTO::map);
    }

    @Override
    public Optional<VideoDTO> readOneById(UUID id) {
        return videoInfoRepository.findById(id)
                .map(videoInfo -> {
                    videoInfo.setViews(videoInfo.getViews() + 1);
                    videoInfoRepository.save(videoInfo);

                    VideoDTO videoDTO = VideoDTO.map(videoInfo);
                    videoDTO.setVideoData(videoInfo.getVideoData().getData());
                    return videoDTO;
                });
    }

    @Override
    public void update(UUID id, VideoInfoDTO videoInfoDTO, String token) {
        var userEmail = jwtService.extractUsername(token.substring(7));
        var findUser = userRepository.findByEmail(userEmail);
        var findVideoInfo = videoInfoRepository.findById(id);

        if (findUser.isEmpty() || findVideoInfo.isEmpty() ||
            !findVideoInfo.get().getPoster().equals(findUser.get())) {
            return;
        }

        var video = findVideoInfo.get();

        video.setTitle(videoInfoDTO.getTitle());
        video.setDescription(videoInfoDTO.getDescription());
        video.setLastEditAt(Timestamp.from(Instant.now()));

        videoInfoRepository.save(video);
    }

    @Override
    public void delete(UUID id, String token) {
        var userEmail = jwtService.extractUsername(token.substring(7));
        var findUser = userRepository.findByEmail(userEmail);
        var findVideo = videoInfoRepository.findById(id);

        if (findUser.isEmpty() || findVideo.isEmpty() ||
                !findVideo.get().getPoster().equals(findUser.get())
        ) {
            return;
        }

        videoInfoRepository.deleteById(id);
    }
}
