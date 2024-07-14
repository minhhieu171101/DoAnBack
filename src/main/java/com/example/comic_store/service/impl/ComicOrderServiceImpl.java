package com.example.comic_store.service.impl;

import com.example.comic_store.dto.ComicOrderDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.dto.StatisticComicDTO;
import com.example.comic_store.entity.Comic;
import com.example.comic_store.entity.ComicOrder;
import com.example.comic_store.repository.ComicOrderRepository;
import com.example.comic_store.repository.ComicRepository;
import com.example.comic_store.service.ComicOrderService;
import com.example.comic_store.service.mapper.ComicOrderMapper;
import com.example.comic_store.service.mapper.StatisticMonthMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ComicOrderServiceImpl implements ComicOrderService {

    @Autowired
    private ComicOrderRepository comicOrderRepository;

    @Autowired
    private ComicOrderMapper comicOrderMapper;

    @Autowired
    private StatisticMonthMapper statisticMonthMapper;

    @Autowired
    private ComicRepository comicRepository;

    @Override
    public List<ComicOrderDTO> getListComicOrder(String username) {
        List<Object[]> comicOrders = comicOrderRepository.getAllByUsername(username);
        return comicOrderMapper.comicOrderDTOList(comicOrders);
    }

    @Override
    public ServiceResult<String> createComicOrder(ComicOrderDTO comicOrderDTO) {
        ComicOrder comicOrder = comicOrderMapper.toComicOrder(comicOrderDTO);
        Comic comic = comicRepository.findById(comicOrderDTO.getComicId()).orElse(null);
        ServiceResult<String> result = new ServiceResult<>();
        ComicOrder comicOrderEx = comicOrderRepository.findByComicIdAndStatusAndUserId(comicOrderDTO.getComicId(), 0L, comicOrderDTO.getUserId() );
        if (comicOrderEx != null){
            if (comicOrderDTO.getQuantity()  <= comic.getResidualQuantity()) {
                comic.setResidualQuantity(comic.getResidualQuantity() - comicOrderDTO.getQuantity());
                comicOrderEx.setQuantity(comicOrderEx.getQuantity() + comicOrderDTO.getQuantity());
                comicOrderEx.setUpdatedAt(LocalDateTime.now());
                comicOrderEx.setUpdatedBy(comicOrderDTO.getUserId());
                comicRepository.save(comic);
                comicOrderRepository.save(comicOrderEx);
                result.setStatus(HttpStatus.OK);
                result.setData("Create successfully!");
                result.setMessage("Thêm vào giỏ hàng thành công!");
                return result;
            }
            else {
                result.setStatus(HttpStatus.BAD_REQUEST);
                result.setData("Create Failed!");
                result.setMessage("Thêm vào giỏ hàng thất bại!");
                return result;
            }
        }

        comicOrder.setCreatedAt(LocalDateTime.now());
        comicOrder.setUpdatedAt(LocalDateTime.now());
        comicOrder.setUpdatedBy(comicOrderDTO.getUserId());
        comicOrder.setStatus(0L);
        if (comic != null && comicOrderDTO.getQuantity() <= comic.getResidualQuantity()) {
            comic.setResidualQuantity(comic.getResidualQuantity() - comicOrderDTO.getQuantity());
            comicRepository.save(comic);
        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setData("Create Failed!");
            result.setMessage("Thêm vào giỏ hàng thất bại!");
            return result;
        }
        comicOrderRepository.save(comicOrder);
        result.setStatus(HttpStatus.OK);
        result.setData("Create successfully!");
        result.setMessage("Thêm vào giỏ hàng thành công!");
        return result;
    }

    @Override
    public ServiceResult<String> deleteComicOrder(Long comicOrderId) {
        ServiceResult<String> result = new ServiceResult<>();
        Optional<ComicOrder> comicOrder = comicOrderRepository.findById(comicOrderId);
        if (comicOrder.isPresent()) {
            Comic comic = comicRepository.findById(comicOrder.get().getComicId()).orElse(null);
            comic.setResidualQuantity(comicOrder.get().getQuantity() + comic.getResidualQuantity());
            comicRepository.save(comic);
            comicOrderRepository.delete(comicOrder.orElse(null));
            result.setMessage("Xóa sản phẩm thành công!");
            result.setData("Delete successfully!");
            result.setStatus(HttpStatus.OK);
            return result;
        }
        result.setMessage("Xóa sản phẩm thất bại!");
        result.setData("Delete Failed!");
        result.setStatus(HttpStatus.BAD_REQUEST);
        return result;
    }

    @Override
    public List<StatisticComicDTO> getStatisticComic(StatisticComicDTO statisticComicDTO) {
        LocalDateTime dateStartStatistic;
        LocalDateTime dateEndStatistic;
//         Trong trường hợp thang = null tức là chọn tất cả
        if (statisticComicDTO.getMonth() == null) {
            dateStartStatistic = LocalDateTime.of(statisticComicDTO.getYear(),1, 1, 0, 0);
            dateEndStatistic =  LocalDateTime.of(statisticComicDTO.getYear() + 1, 1, 1, 0 , 0);
//            Trường hợp chọn tháng
        } else {
            dateStartStatistic = LocalDateTime.of(statisticComicDTO.getYear(), statisticComicDTO.getMonth(), 1, 0 , 0);
            dateEndStatistic =  LocalDateTime.of(statisticComicDTO.getYear(), statisticComicDTO.getMonth() + 1, 1, 0 , 0);
            if (statisticComicDTO.getMonth() == 12) {
                LocalDate.of(statisticComicDTO.getYear() + 1, 1, 1);
            }
        }

        List<Object[]> statisticList = comicOrderRepository.getStatisticMonth(dateStartStatistic, dateEndStatistic);
        return statisticMonthMapper.toSatisticComicDTOList(statisticList);
    }

}
