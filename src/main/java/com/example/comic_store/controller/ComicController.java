package com.example.comic_store.controller;

import com.example.comic_store.dto.ComicAdminDTO;
import com.example.comic_store.dto.ComicDTO;
import com.example.comic_store.dto.ComicDetailDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.service.ComicService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/comic")
public class ComicController {

    @Autowired
    private ComicService comicService;

    @PostMapping("/list-comic")
    public ResponseEntity<List<ComicDTO>> getListComicDTO(@RequestBody ComicDTO comicDTO) {
        try {
            List<ComicDTO> comicDTOList = comicService
                    .getListComicLanding(comicDTO.getPage(), comicDTO.getPageSize());
            return new ResponseEntity<>(comicDTOList, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ComicDetailDTO> getDetailComicDTO(@PathVariable("id") Long id) {
        try {
            ComicDetailDTO comicDetailDTO = comicService.getComicBy(id);
            return new ResponseEntity<>(comicDetailDTO, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping("/page-comic")
    public ResponseEntity<Page<ComicDTO>> getPageComicDTO(@RequestBody ComicDTO comicDTO) {
        try {
            Page<ComicDTO> comicDTOPage = comicService.getPageComic(comicDTO.getPage(), comicDTO.getPageSize(), comicDTO.getTypeComicId());
            return new ResponseEntity<>(comicDTOPage, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping("/comic-management-admin")
    public ResponseEntity<Page<ComicAdminDTO>> getPageComicAdmin(@RequestBody ComicDTO comicDTO) {
        Page<ComicAdminDTO> comicAdminDTOPage = comicService
                .getComicAdmin(comicDTO);
        return new ResponseEntity<>(comicAdminDTOPage, HttpStatus.OK);
    }

    @PostMapping("/update-comic")
    public ResponseEntity<ServiceResult<String>> updateComic(@RequestPart("comic") ComicAdminDTO comicAdminDTO,
                                                             @RequestPart(value = "file", required = false) MultipartFile file) {
        return new ResponseEntity<>(comicService.updateComic(comicAdminDTO, file), HttpStatus.OK);
    }

    @PostMapping("/delete-comic")
    public ResponseEntity<ServiceResult<String>> deleteComic(@RequestBody ComicAdminDTO comicAdminDTO) {
        return new ResponseEntity<>(comicService.deleteComic(comicAdminDTO), HttpStatus.OK);
    }

    @PostMapping("/comic-search")
    public ResponseEntity<Page<ComicDTO>> searchComic(@RequestBody ComicDTO comicDTO) {
        Page<ComicDTO> comicDTOPage = comicService.searchComic(comicDTO);
        return new ResponseEntity<>(comicDTOPage, HttpStatus.OK);
    }
}
