package com.croc.bonjour.controller;

import com.croc.bonjour.domain.Order;
import com.croc.bonjour.domain.OrderItem;
import com.croc.bonjour.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PatchMapping("/{id}")
    public Order updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        String newStatus = statusUpdate.get("status");
        return orderService.updateOrderStatus(id, newStatus);
    }

    @PostMapping("/{id}/add")
    public Order addOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        return orderService.addOrderItem(id, orderItem);
    }
}
