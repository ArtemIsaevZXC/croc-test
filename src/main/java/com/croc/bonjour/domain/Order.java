package com.croc.bonjour.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Заказ.
 */
@Entity(name = "Orders")
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Дата и время создания заказа.
     */
    private LocalDateTime orderDateTime;
    /**
     * Имя клиента.
     */
    private String customerName;
    /**
     * Статус заказа
     */
    private String status;
    /**
     * Список связанных с заказом позиций.
     * <p>
     * Заказ один, но позиций в нём много.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();



}