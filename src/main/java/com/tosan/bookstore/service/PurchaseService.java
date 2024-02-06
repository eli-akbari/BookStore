package com.tosan.bookstore.service;

import com.tosan.bookstore.exception.InsufficientStockException;
import com.tosan.bookstore.exception.OrderNotFoundException;
import com.tosan.bookstore.model.dto.OrderStatus;
import com.tosan.bookstore.model.dto.PurchaseOrderDTO;
import com.tosan.bookstore.model.entity.BookEntity;
import com.tosan.bookstore.model.entity.OrderEntity;
import com.tosan.bookstore.model.entity.OrderItemEntity;
import com.tosan.bookstore.repository.BookRepository;
import com.tosan.bookstore.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PurchaseService {

    private OrderRepository orderRepository;
    private BookRepository bookRepository;

    @Transactional
    public OrderEntity purchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {

        Long orderId = purchaseOrderDTO.getOrderId();
        Optional<OrderEntity> orderEntityOpt = orderRepository.findById(orderId);
        if (orderEntityOpt.isPresent()) {
            OrderEntity orderEntity = orderEntityOpt.get();
            List<OrderItemEntity> orderItems = orderEntity.getOrderItems();
            for (OrderItemEntity item : orderItems) {
                BookEntity book = item.getBook();
                int bookQuantity = book.getQuantity();
                if (bookQuantity < item.getQuantity() || bookQuantity == 0) {
                    throw new InsufficientStockException();
                } else {
                    int updatedQuantity = bookQuantity - item.getQuantity();
                    book.setQuantity(updatedQuantity);
                }
                bookRepository.save(book);
            }
            orderEntity.setStatus(OrderStatus.DELIVERED);
            return orderRepository.save(orderEntity);
        } else {
            throw new OrderNotFoundException();
        }
    }
}
