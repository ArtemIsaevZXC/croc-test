package com.croc.bonjour.service;

import com.croc.bonjour.domain.MenuItem;
import com.croc.bonjour.domain.Order;
import com.croc.bonjour.domain.OrderItem;
import com.croc.bonjour.repository.MenuItemRepository;
import com.croc.bonjour.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    private MenuItemRepository menuItemRepository;

    public OrderService(OrderRepository orderRepository,
                        MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    /**
     * Создание заказа.
     *
     * @param order новый заказ.
     * @return новый заказ.
     */
    public Order createOrder(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            MenuItem menuItem = menuItemRepository.findByIdAndDeletedFalse(orderItem.getMenuItem().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Доступной позиции меню с таким id не существует"));
            orderItem.setMenuItem(menuItem);
        }
        order.setOrderItems(orderItems); // ?
        return orderRepository.save(order);
    }

    /**
     * Получение заказа по id.
     *
     * @param id id искомого заказа.
     * @return искомый заказ.
     * <p>
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказа с таким id нет."));
    }


    /**
     * Обновление статуса заказа.
     *
     * @param id        id искомого заказа.
     * @param newStatus новый статус.
     * @return обновленный заказ.
     */
    public Order updateOrderStatus(Long id, String newStatus) {
        Order order = getOrderById(id);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    /**
     * Добавить позицию в заказ.
     *
     * @param orderItem
     * @return
     */
    public Order addOrderItem(Long orderId, OrderItem orderItem) {
        MenuItem menuItem = menuItemRepository
                .findByIdAndDeletedFalse(orderItem.getMenuItem().getId())
                .orElseThrow(() -> new IllegalArgumentException("Доступной позиции меню с таким id не существует"));
        orderItem.setMenuItem(menuItem);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Заказа с таким id нет."));
        order.getOrderItems().add(orderItem);
        return orderRepository.save(order);
    }

}