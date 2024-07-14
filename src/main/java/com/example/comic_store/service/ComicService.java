package com.example.comic_store.service;

import com.example.comic_store.dto.ComicAdminDTO;
import com.example.comic_store.dto.ComicDTO;
import com.example.comic_store.dto.ComicDetailDTO;
import com.example.comic_store.dto.ServiceResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ComicService {

    List<ComicDTO> getListComicLanding(int page, int pageSize);
    ComicDetailDTO getComicBy(Long id);
    Page<ComicDTO> getPageComic(int page, int pageSize, Long typeComicId);
    Page<ComicAdminDTO> getComicAdmin(ComicDTO comicDTO);
    ServiceResult<String> updateComic(ComicAdminDTO comicAdminDTO, MultipartFile file);
    ServiceResult<String> deleteComic(ComicAdminDTO comicAdminDTO);
    Page<ComicDTO> searchComic(ComicDTO comicDTO);
}
