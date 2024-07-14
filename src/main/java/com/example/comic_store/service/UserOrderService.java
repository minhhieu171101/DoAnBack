package com.example.comic_store.service;

import com.example.comic_store.dto.PurchaseOrderDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.dto.UserOrderDTO;
import org.springframework.data.domain.Page;

public interface UserOrderService {
    ServiceResult<String> saveUserOrder(UserOrderDTO userOrderDTO);
    Page<PurchaseOrderDTO> getPagePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);
    ServiceResult<String> updateUserOrder(PurchaseOrderDTO purchaseOrderDTO);
    Page<PurchaseOrderDTO> getPagePurchaseOrderUser(PurchaseOrderDTO purchaseOrderDTO);
}
