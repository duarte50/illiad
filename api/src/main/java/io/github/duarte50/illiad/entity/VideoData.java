package io.github.duarte50.illiad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class VideoData {
    @Id @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private byte[] data;
}
