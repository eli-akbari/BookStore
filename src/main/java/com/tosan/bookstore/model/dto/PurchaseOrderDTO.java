package com.tosan.bookstore.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class PurchaseOrderDTO implements Serializable {

    @NotNull
    @NotBlank
    private Long orderId;
}
