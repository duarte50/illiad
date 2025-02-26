package io.github.duarte50.illiad.controller;

import io.github.duarte50.illiad.dto.VideoDTO;
import io.github.duarte50.illiad.dto.VideoInfoDTO;
import io.github.duarte50.illiad.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class VideoController {
    // POST /videos
    // GET /videos/recent
    // GET /videos/search
    // GET /users/username/videos
    // PUT /videos/id
    // DELETE /videos/id

    private final VideoService videoService;

    @PostMapping("/videos")
    public ResponseEntity<Optional<VideoDTO>> create(
            @RequestPart("title") String title,  // Title part from the form
            @RequestPart("description") String description,  // Description part from the form
            @RequestPart("videoData") byte[] videoData,  // Video file part
            @RequestHeader("Authorization") String token

    ) throws IOException {
        var videoDTO = VideoDTO.builder()
                .title(title)
                .description(description)
                .videoData(videoData)  // Include video file
                .build();
        var video = videoService.create(videoDTO, token);

        if (video.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Optional.empty());
        }

        return ResponseEntity.created(URI.create("http://localhost:8080/videos/" + video.get().getId()))
                .body(video);
    }

    @GetMapping("/videos/recent")
    public ResponseEntity<Page<VideoInfoDTO>> getAllRecent(
            @RequestParam(defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(videoService.readAllRecent(page));
    }

    @GetMapping("/videos/search")
    public ResponseEntity<Page<VideoInfoDTO>> getAllByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(videoService.readAllByTitle(page, title));
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<Optional<VideoDTO>> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(videoService.readOneById(id));
    }

    @PutMapping("/videos/{id}")
    public ResponseEntity<Optional<VideoDTO>> update(
            @PathVariable UUID id,
            @RequestBody VideoInfoDTO videoInfoDTO,
            @RequestHeader("Authorization") String token
    ) {
        videoService.update(id, videoInfoDTO, token);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") UUID id,
            @RequestHeader("Authorization") String token
    ) {
        videoService.delete(id, token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{username}/videos")
    public ResponseEntity<Page<VideoInfoDTO>> getAllByPoster(
            @PathVariable String username,
            @RequestParam(defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(videoService.readAllByPoster(page, username));
    }
}
