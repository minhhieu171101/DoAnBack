package com.example.comic_store.dto;

import lombok.Data;

@Data
public class ComicOverviewDTO {
    private Long id;
    private String comicName;
    private Long price;
    private Long residualQuantity;
    private String avatarPath;
}
