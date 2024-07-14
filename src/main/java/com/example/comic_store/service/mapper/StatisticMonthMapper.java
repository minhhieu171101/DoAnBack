package com.example.comic_store.service.mapper;

import com.example.comic_store.dto.StatisticComicDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StatisticMonthMapper {

    public StatisticComicDTO toStatisticComicDTO(Object[] object) {
        StatisticComicDTO statisticComicDTO = new StatisticComicDTO();
        if (object != null) {
            if (object.length == 1) {
                object = (Object[]) object[0];
            }
            statisticComicDTO.setComicTypeName((String) object[0]);
            statisticComicDTO.setTotalSold((Long) object[1]);
            statisticComicDTO.setTotalIncome((Long) object[2]);
        }
        return statisticComicDTO;
    }

    public List<StatisticComicDTO> toSatisticComicDTOList(List<Object[]> objects) {
        List<StatisticComicDTO> statisticComicDTOS = new ArrayList<>();
        for (Object[] object : objects) {
            statisticComicDTOS.add(toStatisticComicDTO(object));
        }
        return statisticComicDTOS;
    }
}
