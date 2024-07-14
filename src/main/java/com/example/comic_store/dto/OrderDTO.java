package com.example.comic_store.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private String comicImage;
    private String comicName;
    private String typeComicName;
    private Long priceAfterSale;
}
