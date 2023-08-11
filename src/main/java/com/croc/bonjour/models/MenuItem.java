package com.croc.bonjour.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Позиция меню.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Название.
     */
    private String name;
    /**
     * Объем.
     */
    private double volume;
    /**
     * Цена.
     */
    private double price;
    /**
     * Флаг, указывающий на то, удалена ли позиция из меню.
     */
    private boolean deleted = false;

}