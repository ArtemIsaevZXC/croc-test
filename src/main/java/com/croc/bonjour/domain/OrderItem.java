package com.croc.bonjour.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Позиция заказа.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Ссылка на позицию в меню.
     * <p>
     * Каждый orderItem (их может быть много) связан с одной конкретной позицией в меню
     */
    @ManyToOne
    private MenuItem menuItem;
    /**
     * Количество.
     */
    private int quantity;

}
