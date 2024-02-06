package com.tosan.bookstore.repository;

import com.tosan.bookstore.model.entity.CustomerEntity;
import com.tosan.bookstore.model.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findByCustomer(CustomerEntity customer, Pageable pageable);
}
