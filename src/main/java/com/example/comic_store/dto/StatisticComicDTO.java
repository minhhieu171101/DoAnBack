package com.example.comic_store.dto;

import lombok.Data;

@Data
public class StatisticComicDTO {
    private String comicTypeName;
    private Long totalSold;
    private Long totalIncome;
    private Integer month;
    private Integer year;
}
