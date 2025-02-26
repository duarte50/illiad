package io.github.duarte50.illiad.repository;

import io.github.duarte50.illiad.entity.VideoData;
import io.github.duarte50.illiad.entity.VideoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoDataRepository extends JpaRepository<VideoData, UUID> {

}
