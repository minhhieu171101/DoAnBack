package com.example.comic_store.controller;

import com.example.comic_store.dto.PurchaseOrderDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.dto.UserOrderDTO;
import com.example.comic_store.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-order")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @PostMapping("/create-order")
    public ResponseEntity<ServiceResult<String>> createOrder(@RequestBody UserOrderDTO userOrderDTO) {
        ServiceResult<String> result = userOrderService.saveUserOrder(userOrderDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/purchase-order")
    public ResponseEntity<Page<PurchaseOrderDTO>> getPagePurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        Page<PurchaseOrderDTO> result = userOrderService.getPagePurchaseOrder(purchaseOrderDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/update-order")
    public ResponseEntity<ServiceResult<String>> updateOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        ServiceResult<String> result = userOrderService.updateUserOrder(purchaseOrderDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/purchase-order-user")
    public ResponseEntity<Page<PurchaseOrderDTO>> getPurchaseOrderUser(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        Page<PurchaseOrderDTO> result = userOrderService.getPagePurchaseOrderUser(purchaseOrderDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
