package com.tosan.bookstore.controller;

import com.tosan.bookstore.exception.ApiResponse;
import com.tosan.bookstore.exception.ResponseStatus;
import com.tosan.bookstore.model.dto.CustomerDTO;
import com.tosan.bookstore.model.entity.CustomerEntity;
import com.tosan.bookstore.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CustomerEntity>>> getAllCustomer(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        List<CustomerEntity> result = customerService.findAllCustomersByPagination(page, size).getContent();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS, result));
    }

    @GetMapping("/search/{customerName}")
    public ResponseEntity<ApiResponse<CustomerEntity>> searchCustomerByName(
            @PathVariable("customerName") String customerName) {

        CustomerEntity result = customerService.filterCustomerByUserName(customerName);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS, result));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Void>> addNewCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        customerService.createNewCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,null));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,null));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Void>> editCustomer(@PathVariable("customerId") Long customerId,
                                                          @RequestBody CustomerDTO customerDTO) {
        customerService.editCustomerById(customerId, customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(ResponseStatus.SUCCESS,null));
    }

}
