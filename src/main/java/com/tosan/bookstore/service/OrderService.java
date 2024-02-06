package com.tosan.bookstore.service;

import com.tosan.bookstore.exception.BookDoesntExistsException;
import com.tosan.bookstore.exception.CustomerDoesntExistsException;
import com.tosan.bookstore.exception.InsufficientStockException;
import com.tosan.bookstore.exception.OrderNotFoundException;
import com.tosan.bookstore.model.dto.OrderDTO;
import com.tosan.bookstore.model.dto.OrderItemDTO;
import com.tosan.bookstore.model.dto.OrderStatus;
import com.tosan.bookstore.model.entity.BookEntity;
import com.tosan.bookstore.model.entity.CustomerEntity;
import com.tosan.bookstore.model.entity.OrderEntity;
import com.tosan.bookstore.model.entity.OrderItemEntity;
import com.tosan.bookstore.model.mapper.OrderMapper;
import com.tosan.bookstore.repository.BookRepository;
import com.tosan.bookstore.repository.CustomerRepository;
import com.tosan.bookstore.repository.OrderItemsRepository;
import com.tosan.bookstore.repository.OrderRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;
    private final OrderItemsRepository orderItemsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Page<OrderEntity> getAllOrdersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return orderRepository.findAll(pageable);
    }
    public List<OrderEntity> findOrderOfSpecificCustomer(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customerId);
        if (customerEntity.isPresent()) {
          return   entityManager
                    .createQuery("select o from OrderEntity o where o.customer.id = :id",OrderEntity.class)
                    .setParameter("id",customerId)
//                    .setMaxResults(size)
//                    .setFirstResult(size*page)
                    .getResultList();
//            return customerRepository.findById(customerId).get().getOrderEntityList();
        } else {
            throw new CustomerDoesntExistsException();
        }
    }
    public OrderEntity createOrder(OrderDTO orderDTO) {
        Optional<CustomerEntity> customer = customerRepository.findById(orderDTO.getCustomerId());
        if (customer.isEmpty()) {
            throw new CustomerDoesntExistsException();
        }

        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            Long bookId = orderItemDTO.getBookId();
            int requestedQuantity = orderItemDTO.getQuantity();

            Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);

            if (optionalBookEntity.isEmpty()) {
                throw new BookDoesntExistsException();
            }

            BookEntity bookEntity = optionalBookEntity.get();
            int availableQuantity = bookEntity.getQuantity();

            if (availableQuantity < requestedQuantity) {
                throw new InsufficientStockException();
            }
        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomer(customer.get());
        orderEntity.setOrderDate(new Timestamp(System.currentTimeMillis()));
        orderEntity.setTotalAmount(calculateTotalAmount(orderDTO.getOrderItems()));
        orderEntity.setStatus(OrderStatus.PROCESSING);


        List<OrderItemEntity>  orderItemEntities = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrder(orderEntity);
            Optional<BookEntity> optionalBookEntity = bookRepository.findById(orderItemDTO.getBookId());
            orderItemEntity.setBook(optionalBookEntity.get());
            orderItemEntity.setQuantity(orderItemDTO.getQuantity());
            orderItemEntities.add(orderItemEntity);
        }
        orderEntity.setOrderItems(orderItemEntities);
        orderRepository.save(orderEntity);
        return orderEntity;
    }


    private double calculateTotalAmount(List<OrderItemDTO> orderItems) {
        return orderItems.stream()
                .mapToDouble(item -> {
                    Optional<BookEntity> bookEntity = bookRepository.findById(item.getBookId());

                    if (bookEntity.isPresent()) {
                        return bookEntity.get().getPrice() * item.getQuantity();
                    } else {
                        throw new BookDoesntExistsException();
                    }
                })
                .sum();
    }

    public void deleteOrderById(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isPresent()) {
            orderRepository.delete(orderEntity.get());
        } else {
            throw new OrderNotFoundException();
        }
    }

    public void deleteOrderItemsByOrderId(Long orderId, List<OrderItemEntity> toDeleteItems) {
        Optional<OrderEntity> orderEntityOpt = orderRepository.findById(orderId);
        if (orderEntityOpt.isPresent()) {
            OrderEntity orderEntity = orderEntityOpt.get();
            List<OrderItemEntity> orderItems = orderEntity.getOrderItems();

            List<OrderItemEntity> itemsToRemove = new ArrayList<>();

            for (OrderItemEntity item : orderItems) {
                for (OrderItemEntity deleteItem : toDeleteItems) {
                    if (item.getId().equals(deleteItem.getId())) {
                        itemsToRemove.add(item);
                    }
                }
            }

            orderItems.removeAll(itemsToRemove);

            double totalAmount = calculateTotalAmount(OrderMapper.INSTANCE.orderItemEntitiesToOrderItemDTOs(orderItems));
            orderEntity.setTotalAmount(totalAmount);
            orderRepository.save(orderEntity);
        }
    }

}
