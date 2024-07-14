package com.example.comic_store.service;

import com.example.comic_store.dto.ComicAdminDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.dto.WishlistDTO;
import org.springframework.data.domain.Page;

public interface WishlistService {
    Page<WishlistDTO> getWishListPage(WishlistDTO wishlistDTO);
    ServiceResult<String> addToWishlist(WishlistDTO wishlistDTO);
    ServiceResult<String> deleteComic(WishlistDTO wishlistDTO);
}
