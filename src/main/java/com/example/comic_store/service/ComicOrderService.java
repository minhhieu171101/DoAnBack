package com.example.comic_store.service;

import com.example.comic_store.dto.ComicOrderDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.dto.StatisticComicDTO;
import java.util.List;

public interface ComicOrderService {
    List<ComicOrderDTO> getListComicOrder(String username);
    ServiceResult<String> createComicOrder(ComicOrderDTO comicOrderDTO);
    ServiceResult<String> deleteComicOrder(Long comicOrderId);
    List<StatisticComicDTO> getStatisticComic(StatisticComicDTO statisticComicDTO);
}
