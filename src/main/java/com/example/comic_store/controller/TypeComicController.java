package com.example.comic_store.controller;

import com.example.comic_store.dto.TypeComicDTO;
import com.example.comic_store.service.TypeComicService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/type-comic")
public class TypeComicController {

    @Autowired
    private TypeComicService typeComicService;

    /**
     * Lấy danh sách các thể loại truyện
     *
     * @return ResponseEntity<List<TypeComicDTO>> Trả về danh sách các thể loại truyện tranh
     * @author
     * @modifiedBy
     * @modifiedDate
     * @vesion 1.0
     */
    @GetMapping(value = "/list-type-comic")
    public ResponseEntity<List<TypeComicDTO>> getListTypeComic() {
        return ResponseEntity.ok(typeComicService.getListTypeComic());
    }
}
