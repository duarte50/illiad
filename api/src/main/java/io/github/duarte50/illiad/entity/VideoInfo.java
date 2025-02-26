package io.github.duarte50.illiad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class VideoInfo {
    @Id @GeneratedValue
    private UUID id;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int views;
    @Column(nullable = false)
    private int likes;
    @Column(nullable = false)
    private int dislikes;
    @Column(nullable = false)
    private Timestamp createdAt;
    @Column(nullable = false)
    private Timestamp lastEditAt;

    @ManyToOne @JoinColumn
    private User poster;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn
    private VideoData videoData;
}
