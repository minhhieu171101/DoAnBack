package com.example.comic_store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Column(name = "date_post")
    private LocalDateTime datePost;

    @Column(name = "content")
    private String content;

    @Column(name = "img_comment")
    private String imgComment;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "comic_id", nullable = false)
    private Long comicId;
}

