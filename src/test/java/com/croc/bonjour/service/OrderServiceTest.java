package com.croc.bonjour.service;

import com.croc.bonjour.models.MenuItem;
import com.croc.bonjour.models.Order;
import com.croc.bonjour.models.OrderItem;
import com.croc.bonjour.repository.MenuItemRepository;
import com.croc.bonjour.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService(orderRepository, menuItemRepository);
    }


    @Test
    @DisplayName("Создание заказа")
    public void testCreateOrder() {
        MenuItem menuItem1 = new MenuItem(1L, "Burger", 260, 500, false);
        MenuItem menuItem2 = new MenuItem(2L, "Pizza", 350, 700, false);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setMenuItem(menuItem1);
        orderItem1.setQuantity(2);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setMenuItem(menuItem2);
        orderItem2.setQuantity(1);

        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);

        Order order = new Order();
        order.setOrderItems(orderItems);

        when(menuItemRepository.findByIdAndDeletedFalse(menuItem1.getId())).thenReturn(Optional.of(menuItem1));
        when(menuItemRepository.findByIdAndDeletedFalse(menuItem2.getId())).thenReturn(Optional.of(menuItem2));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertNotNull(result);
        assertEquals(orderItems.size(), result.getOrderItems().size());
        assertEquals(orderItem1.getId(), result.getOrderItems().get(0).getId());
        assertEquals(orderItem2.getId(), result.getOrderItems().get(1).getId());
    }

    @Test
    @DisplayName("Обновление статуса заказа")
    public void testUpdateOrderStatus() {
        Long orderId = 1L;
        String newStatus = "Выполнен";

        Order order = new Order();
        order.setStatus("В работе");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.updateOrderStatus(orderId, newStatus);

        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
    }

    @Test
    @DisplayName("Добавление в заказ позиции")
    public void testAddOrderItem() {
        Long orderId = 1L;

        MenuItem menuItem = new MenuItem(1L, "Burger", 260, 500, false);
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(2);

        Order order = new Order();

        when(menuItemRepository.findByIdAndDeletedFalse(menuItem.getId())).thenReturn(Optional.of(menuItem));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.addOrderItem(orderId, orderItem);

        assertNotNull(result);
        assertEquals(1, result.getOrderItems().size());
        assertEquals(order.getOrderItems().get(0), result.getOrderItems().get(0));
    }
}