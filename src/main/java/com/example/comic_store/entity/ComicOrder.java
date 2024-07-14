package com.example.comic_store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "comic_order")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComicOrder extends BaseEntity{
    @Column(name = "comic_id")
    private Long comicId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_order_id")
    private Long userOrderId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "status")
    private Long status;
}
