package com.croc.bonjour.service;

import com.croc.bonjour.domain.MenuItem;
import com.croc.bonjour.domain.Order;
import com.croc.bonjour.domain.OrderItem;
import com.croc.bonjour.repository.MenuItemRepository;
import com.croc.bonjour.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService(orderRepository, menuItemRepository);
    }

    @Test
    @DisplayName("Создание заказа")
    public void testCreateOrder() {
        MenuItem menuItem1 = new MenuItem(1L, "BigMac", 200, 10.99, false);
        MenuItem menuItem2 = new MenuItem(2L, "Cheese-Burger", 200, 10.99, false);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setMenuItem(menuItem1);
        orderItem1.setQuantity(4); // 4 Бигмаков

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setMenuItem(menuItem2);
        orderItem2.setQuantity(5); // 5 чизбургеров

        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);

        Order order = new Order(null, LocalDateTime.now(), "New Customer", "В работе", orderItems);
        order.setOrderItems(orderItems);

        Order result = orderService.createOrder(order);

        assertNotNull(result);
        assertEquals(3L, result.getId()); // потому что 3й заказ, 2 других при начальном заполнении
        assertEquals(order.getCustomerName(), result.getCustomerName());
        assertEquals(orderItems.size(), result.getOrderItems().size());
        assertEquals(orderItem1.getId(), result.getOrderItems().get(0).getId());
        assertEquals(orderItem2.getId(), result.getOrderItems().get(1).getId());
    }

    @Test
    @DisplayName("Обновление статуса заказа")
    public void testUpdateOrderStatus() {
        Long orderId = 1L;
        String newStatus = "Выполнен";

        Order result = orderService.updateOrderStatus(orderId, newStatus);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals(newStatus, result.getStatus());
    }

    @Test
    @DisplayName("Добавление в заказ позиции")
    public void testAddOrderItem() {
        Long orderId = 1L;

        MenuItem menuItem = new MenuItem(2L, "Cheese-Burger", 200, 10.99, false);

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(2); // добавляем еще 2 чизбургера в 1-й заказ

        Order result = orderService.addOrderItem(orderId, orderItem);

        assertNotNull(result);
        assertEquals(3, result.getOrderItems().size()); // 2 старые позиции + 1 новая
        assertEquals(orderItem.getQuantity(), result.getOrderItems().get(2).getQuantity()); // 2 новых чизбургера
        assertEquals(menuItem.getName(), result.getOrderItems().get(2).getMenuItem().getName()); // Это действительно чизбургер
    }

    @Test
    @DisplayName("Создание заказа с несуществующими блюдами")
    public void testCreateOrderWithNonExistentMenuItems() {
        MenuItem nonExistentMenuItem = new MenuItem(100L, "Non-Existent Item", 200, 10.99, false);

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(nonExistentMenuItem);
        orderItem.setQuantity(3);

        List<OrderItem> orderItems = List.of(orderItem);

        Order order = new Order(null, LocalDateTime.now(), "New Customer", "В работе", orderItems);

        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(order));
    }

    @Test
    @DisplayName("Обновление статуса несуществующего заказа")
    public void testUpdateNonExistentOrderStatus() {
        Long nonExistentOrderId = 100L;
        String newStatus = "Выполнен";

        assertThrows(IllegalArgumentException.class, () -> orderService.updateOrderStatus(nonExistentOrderId, newStatus));
    }

    @Test
    @DisplayName("Добавление несуществующего блюда в заказ")
    public void testAddNonExistentMenuItemToOrder() {
        Long orderId = 1L;
        MenuItem nonExistentMenuItem = new MenuItem(100L, "Non-Existent Item", 200, 10.99, false);

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(nonExistentMenuItem);
        orderItem.setQuantity(2);

        assertThrows(IllegalArgumentException.class, () -> orderService.addOrderItem(orderId, orderItem));
    }

    @Test
    @DisplayName("Добавление удаленного блюда в заказ")
    public void testAddDeletedMenuItemToOrder() {
        Long orderId = 1L;

        MenuItem deletedMenuItem = new MenuItem(4L, "Deleted-Item", 200, 10.99, true);

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(deletedMenuItem);
        orderItem.setQuantity(2);

        assertThrows(IllegalArgumentException.class, () -> orderService.addOrderItem(orderId, orderItem));
    }

}