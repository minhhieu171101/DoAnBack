package com.example.comic_store.service.mapper;

import com.example.comic_store.dto.PurchaseOrderDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class UserOrderMapper {

    public PurchaseOrderDTO toPurchaseOrderDTO(Object[] object) {
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        if (object != null) {
            if (object.length == 1) {
                object = (Object[]) object[0];
            }
            purchaseOrderDTO.setUserId((Long) object[0]);
            purchaseOrderDTO.setUserOrderId((Long) object[1]);
            purchaseOrderDTO.setFullName((String) object[2]);
            purchaseOrderDTO.setAddress((String) object[3]);
            purchaseOrderDTO.setPhone((String) object[4]);
            purchaseOrderDTO.setTotalPrice((Long) object[5]);
            purchaseOrderDTO.setDateOrder((LocalDate) object[6]);
            purchaseOrderDTO.setStatus((Integer) object[7]);
        }
        return purchaseOrderDTO;
    }

    public Page<PurchaseOrderDTO> toPurchaseOrderDTOPage(Page<Object[]> objects) {
        List<PurchaseOrderDTO> purchaseOrderDTOS = new ArrayList<>();
        for (Object[] object : objects) {
            purchaseOrderDTOS.add(toPurchaseOrderDTO(object));
        }
        return new PageImpl<>(purchaseOrderDTOS, objects.getPageable(), objects.getTotalElements());
    }
}
