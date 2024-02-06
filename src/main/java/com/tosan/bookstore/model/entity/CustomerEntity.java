package com.tosan.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customer")
@Data
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Long id;

    @Column(name = "CUSTOMER_USER_NAME")
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orderEntityList;

}
