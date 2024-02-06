package com.tosan.bookstore.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDTO implements Serializable {

    @NotNull
    private Long customerId;
    private List<OrderItemDTO> orderItems;
}