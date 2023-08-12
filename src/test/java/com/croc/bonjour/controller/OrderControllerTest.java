package com.croc.bonjour.controller;


import com.croc.bonjour.models.MenuItem;
import com.croc.bonjour.models.Order;
import com.croc.bonjour.models.OrderItem;
import com.croc.bonjour.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("Создание нового заказа")
    public void testCreateOrder() throws Exception {
        MenuItem menuItem1 = new MenuItem(1L, "Burger", 260, 500, false);
        MenuItem menuItem2 = new MenuItem(2L, "Pizza", 350, 700, false);

        OrderItem orderItem1 = new OrderItem(1L, menuItem1, 2);
        OrderItem orderItem2 = new OrderItem(2L, menuItem2, 1);

        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);

        Order order = new Order();
        order.setOrderItems(orderItems);
        order.setId(1L);
        order.setStatus("В работе");

        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .content(new ObjectMapper().writeValueAsString(order))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("В работе"))
                .andExpect(jsonPath("$.orderItems[0].menuItem.name").value("Burger"));
    }

    @Test
    @DisplayName("Получение заказа по ID")
    public void testGetOrder() throws Exception {
        Order order = new Order(1L, LocalDateTime.now(), "John Doe", "В работе", Collections.emptyList());

        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("В работе"))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }
    @Test
    @DisplayName("Тест обновления статуса заказа")
    public void testUpdateOrderStatus() throws Exception {
        Long orderId = 1L;
        String newStatus = "Выполнен";

        Map<String, String> statusUpdate = new HashMap<>();
        statusUpdate.put("status", newStatus);

        Order order = new Order();
        order.setStatus("В работе");

        doAnswer(invocation -> {
            order.setStatus(newStatus);
            return order;
        }).when(orderService).updateOrderStatus(eq(orderId), eq(newStatus));

        mockMvc.perform(patch("/api/orders/{id}", orderId)
                        .content(new ObjectMapper().writeValueAsString(statusUpdate))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status").value(newStatus));
    }

    @Test
    @DisplayName("Тест добавления позиции в заказ")
    public void testAddOrderItem() throws Exception {
        Long orderId = 1L;

        MenuItem menuItem = new MenuItem(1L, "Burger", 260, 500, false);
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(2);

        Order order = new Order();

        doAnswer(invocation -> {
            order.getOrderItems().add(orderItem);
            return order;
        }).when(orderService).addOrderItem(eq(orderId), any(OrderItem.class));

        mockMvc.perform(post("/api/orders/{id}/add", orderId)
                        .content(new ObjectMapper().writeValueAsString(orderItem))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.orderItems[0].menuItem.name").value("Burger"))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(2));
    }

}