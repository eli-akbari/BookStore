package com.tosan.bookstore.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerDTO implements Serializable {

    @NotNull
    @NotBlank
    private String userName;

    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "09\\d{9}")
    private String phoneNumber;

}