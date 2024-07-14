package com.example.comic_store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "comic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    Tên truyện
    @Column(name = "comic_name")
    private String comicName;

//    Tên tác giả
    @Column(name = "author_name")
    private String authorName;

//    Ngày phát hành
    @Column(name = "release_date")
    private LocalDate releaseDate;

//    Thông tin giới thiệu về cuốn truyện
    @Column(name = "contents")
    private String contents;

//    Thể loại
    @Column(name = "type_comic_ids")
    private String typeComicIds;

//    Giá bán
    @Column(name = "price")
    private Long price;

//    Phần trăm giảm giá
    @Column(name = "sale")
    private Long sale;

//    Số lượng còn lại
    @Column(name = "residual_quantity")
    private Long residualQuantity;
//    Trạng thái của cuốn truyện: 1 còn hàng, 0 hết hàng
    @Column(name = "status")
    private Boolean status;

    @Column(name = "img_comic")
    private String imgComic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
