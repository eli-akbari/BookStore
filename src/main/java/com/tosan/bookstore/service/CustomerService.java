package com.tosan.bookstore.service;

import com.tosan.bookstore.exception.CustomerAlreadyExistsException;
import com.tosan.bookstore.exception.CustomerDoesntExistsException;
import com.tosan.bookstore.model.dto.CustomerDTO;
import com.tosan.bookstore.model.entity.CustomerEntity;
import com.tosan.bookstore.model.mapper.CustomerMapper;
import com.tosan.bookstore.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Page<CustomerEntity> findAllCustomersByPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findAll(pageable);
    }

    public CustomerEntity filterCustomerByUserName(String userName) {
        return customerRepository.findByUserName(userName)
                .orElseThrow(CustomerDoesntExistsException::new);
    }

    public void createNewCustomer(CustomerDTO customerDTO) {
        customerRepository.findByUserName(customerDTO.getUserName())
                .ifPresent(customer -> {
                    throw new CustomerAlreadyExistsException();
                });

        CustomerEntity newCustomer = CustomerMapper.INSTANCE.toCustomerEntity(customerDTO);
        customerRepository.save(newCustomer);
    }

    public void deleteCustomerById(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(CustomerDoesntExistsException::new);
        customerRepository.delete(customerEntity);
    }

    public void editCustomerById(Long customerId, CustomerDTO customerDTO) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(CustomerDoesntExistsException::new);

        String newUserName = customerDTO.getUserName();
        String newPhoneNumber = customerDTO.getPhoneNumber();

        if (newUserName.equals(customerEntity.getUserName()) && newPhoneNumber.equals(customerEntity.getPhoneNumber())) {
            throw new CustomerAlreadyExistsException();
        } else if (customerRepository.existsByUserName(newUserName)) {
            throw new CustomerAlreadyExistsException();
        } else {
            CustomerMapper.INSTANCE.updateCustomerEntity(customerDTO, customerEntity);
            customerRepository.save(customerEntity);
        }
    }
}
