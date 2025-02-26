package io.github.duarte50.illiad.repository;

import io.github.duarte50.illiad.entity.User;
import io.github.duarte50.illiad.entity.VideoInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoInfoRepository extends JpaRepository<VideoInfo, UUID> {
    Page<VideoInfo> findAllByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String title, Pageable pageable);
    Page<VideoInfo> findAllByPoster(User poster, Pageable pageable);
    Page<VideoInfo> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
