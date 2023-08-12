package com.croc.bonjour.controller;

import com.croc.bonjour.SpringBootApplicationTest;
import com.croc.bonjour.models.MenuItem;
import com.croc.bonjour.models.Order;
import com.croc.bonjour.models.OrderItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
public class OrderControllerTest extends SpringBootApplicationTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("Создание нового заказа")
    public void testCreateOrder() throws Exception {
        MenuItem menuItem1 = new MenuItem(1L, "BigMac", 200, 10.99, false);
        MenuItem menuItem2 = new MenuItem(2L, "Cheese-Burger", 200, 10.99, false);

        OrderItem orderItem1 = new OrderItem(5L, menuItem1, 2);
        OrderItem orderItem2 = new OrderItem(6L, menuItem2, 1);

        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);

        Order order = new Order();
        order.setOrderItems(orderItems);
        order.setCustomerName("Sponge Bob");
        order.setId(3L);
        order.setStatus("В работе");


        mockMvc.perform(post("/api/orders")
                        .content(new ObjectMapper().writeValueAsString(order))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.status").value("В работе"))
                .andExpect(jsonPath("$.orderItems[0].menuItem.name").value("BigMac"))
                .andExpect(jsonPath("$.customerName").value("Sponge Bob"));
    }

    @Test
    @DisplayName("Получение заказа по ID")
    public void testGetOrder() throws Exception {
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("В работе"))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    @DisplayName("Тест обновления статуса заказа")
    public void testUpdateOrderStatus() throws Exception {
        Long orderId = 2L;
        String newStatus = "Выполнен";
        Map<String, String> statusUpdate = new HashMap<>();
        statusUpdate.put("status", newStatus);

        mockMvc.perform(patch("/api/orders/{id}", orderId)
                        .content(new ObjectMapper().writeValueAsString(statusUpdate))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.customerName").value("Arthur Morgan"))
                .andExpect(jsonPath("$.status").value(newStatus));
    }
    @Test
    @DisplayName("Тест добавления позиции в заказ")
    public void testAddOrderItem() throws Exception {
        Long orderId = 2L;
        MenuItem menuItem = new MenuItem(2L, "Cheese-Burger", 200, 10.99, false);
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(1);


        mockMvc.perform(post("/api/orders/{id}/add", orderId)
                        .content(new ObjectMapper().writeValueAsString(orderItem))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.orderItems[2].menuItem.name").value("Cheese-Burger"))
                .andExpect(jsonPath("$.orderItems[2].quantity").value(1))
                .andExpect(jsonPath("$.customerName").value("Arthur Morgan"));
    }
}