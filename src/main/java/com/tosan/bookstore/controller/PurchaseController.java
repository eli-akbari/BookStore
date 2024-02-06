package com.tosan.bookstore.controller;

import com.tosan.bookstore.exception.ApiResponse;
import com.tosan.bookstore.exception.ResponseStatus;
import com.tosan.bookstore.model.dto.PurchaseOrderDTO;
import com.tosan.bookstore.model.entity.OrderEntity;
import com.tosan.bookstore.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
@AllArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping()
    public ResponseEntity<ApiResponse<OrderEntity>> purchaseBook(@Valid @RequestBody PurchaseOrderDTO purchaseOrderDTO) {

        OrderEntity purchasedOrder = purchaseService.purchaseOrder(purchaseOrderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,purchasedOrder));
    }
}
