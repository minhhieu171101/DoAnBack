package com.example.comic_store.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PurchaseOrderDTO {
    private Long userId;
    private Long userOrderId;
    private String fullName;
    private String address;
    private String phone;
    private Long totalPrice;
    private LocalDate dateOrder;
    private Integer status;
    private int page;
    private int pageSize;
    private String username;
    private String searchKey;
}
