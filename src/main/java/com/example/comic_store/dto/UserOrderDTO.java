package com.example.comic_store.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class UserOrderDTO {
    private Date orderDate;
    private String note;
    private Long userId;
    private List<Long> comicOrders;
    private Integer status;
    private Long totalPrice;
}

