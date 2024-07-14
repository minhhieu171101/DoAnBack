package com.example.comic_store.dto;

import lombok.Data;

@Data
public class WishlistDTO {
    private Long id;
    private Long comicId;
    private Long createdBy;
    private String comicName;
    private String authorName;
    private String typeComicName;
    private String contents;
    private String comicImg;
    private String username;
    private int page;
    private int pageSize;
}
