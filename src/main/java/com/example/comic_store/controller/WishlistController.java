package com.example.comic_store.controller;

import com.example.comic_store.dto.ComicAdminDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.dto.WishlistDTO;
import com.example.comic_store.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/page-wishlist")
    public ResponseEntity<Page<WishlistDTO>> getPageWishlist(@RequestBody WishlistDTO wishlistDTO) {
        Page<WishlistDTO> userDTOPage = wishlistService.getWishListPage(wishlistDTO);
        return new ResponseEntity<>(userDTOPage, HttpStatus.OK);
    }

    @PostMapping("/add-wishlist")
    public ResponseEntity<ServiceResult<String>> updateWishlist(@RequestBody WishlistDTO wishlistDTO) {
        ServiceResult<String> result = wishlistService.addToWishlist(wishlistDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/delete-wishlist")
    public ResponseEntity<ServiceResult<String>> deleteWishlist(@RequestBody WishlistDTO wishlistDTO) {
        return new ResponseEntity<>(wishlistService.deleteComic(wishlistDTO), HttpStatus.OK);
    }
}
