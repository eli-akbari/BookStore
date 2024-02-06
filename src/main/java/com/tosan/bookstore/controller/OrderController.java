package com.tosan.bookstore.controller;

import com.tosan.bookstore.exception.ApiResponse;
import com.tosan.bookstore.exception.ResponseStatus;
import com.tosan.bookstore.model.dto.OrderDTO;
import com.tosan.bookstore.model.entity.OrderEntity;
import com.tosan.bookstore.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrderEntity>>> getAllOrders(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<OrderEntity> result = orderService.getAllOrdersWithPagination(page,size).getContent();
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS, result));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<List<OrderEntity>>> getOrderByCustomer(@PathVariable("customerId") Long id,
                                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "10") int size ) {
        List<OrderEntity> result = orderService.findOrderOfSpecificCustomer(id, page,size);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS, result));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<OrderEntity>> addNewOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderEntity registeredOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,registeredOrder));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Void>> deleteOrderById(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,null));
    }
}
