package com.example.comic_store.dto;

import lombok.Data;

@Data
public class ComicDTO {
    private Long id;
    private String comicName;
    private String authorName;
    private String contents;
    private Long price;
    private Long sale;
    private Long residualQuantity;
    private String imgComic;
    private int page;
    private int pageSize;
    private Long typeComicId;
    private String typeComicIds;
    private String searchKey;
}
