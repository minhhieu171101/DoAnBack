package com.example.comic_store.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ComicDetailDTO {
    private Long id;
    private String comicName;
    private String authorName;
    private String imgComic;
    private String typeName;
    private Long price;
    private Long sale;
    private String contents;
    private Long residualQuantity;
    private Integer status;
    private Long typeComicId;
    private String typeComicIds;
    private LocalDate releaseDate;
}
