package com.example.comic_store.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long userId;
    private Long comicId;
    private String fullName;
    private String comicName;
    private String content;
    private LocalDateTime datePost;
    private String imgComment;
    private int page;
    private int pageSize;
    private String searchKey;
}
