package com.tosan.bookstore.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_items")
@Data
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEMS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private OrderEntity order;

    @Column(name = "QUANTITY")
    private int quantity;
}
