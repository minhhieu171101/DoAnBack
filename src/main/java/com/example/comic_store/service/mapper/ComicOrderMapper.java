package com.example.comic_store.service.mapper;

import com.example.comic_store.commons.Common;
import com.example.comic_store.dto.ComicOrderDTO;
import com.example.comic_store.entity.ComicOrder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ComicOrderMapper {

    public ComicOrderDTO toComicOrderDTO(Object[] object) {
        ComicOrderDTO comicOrderDTO = new ComicOrderDTO();
        if (object != null) {
            if (object.length == 1) {
                object = (Object[]) object[0];
            }
            comicOrderDTO.setUserId((Long) object[0]);
            comicOrderDTO.setComicId((Long) object[1]);
            comicOrderDTO.setComicOrderId((Long) object[2]);
            comicOrderDTO.setComicName((String) object[3]);
            comicOrderDTO.setImgComic((String) object[4]);
            comicOrderDTO.setQuantity((Long) object[5]);
            comicOrderDTO.setTotalPrice((Long) object[6]);
            comicOrderDTO.setTypeName(Common.convertTypeName((String) object[7]));
        }
        return comicOrderDTO;
    }

    public List<ComicOrderDTO> comicOrderDTOList(List<Object[]> objects) {
        List<ComicOrderDTO> comicOrderDTOList = new ArrayList<>();
        for (Object[] object : objects) {
            comicOrderDTOList.add(toComicOrderDTO(object));
        }
        return comicOrderDTOList;
    }

    public ComicOrder toComicOrder(ComicOrderDTO comicOrderDTO) {
        ComicOrder comicOrder = new ComicOrder();
        comicOrder.setComicId(comicOrderDTO.getComicId());
        comicOrder.setUserId(comicOrderDTO.getUserId());
        comicOrder.setQuantity(comicOrderDTO.getQuantity());
        comicOrder.setTotalPrice(comicOrderDTO.getTotalPrice());
        return comicOrder;
    }
}
