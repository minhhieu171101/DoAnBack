package com.example.comic_store.dto;

import lombok.Data;

@Data
public class ComicOrderDTO {
    private Long userId;
    private Long comicId;
    private Long comicOrderId;
    private String comicName;
    private String imgComic;
    private Long quantity;
    private Long totalPrice;
    private String typeName;
    private Long userOrderId;
}
