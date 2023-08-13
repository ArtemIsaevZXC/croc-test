package com.croc.bonjour.service;

import com.croc.bonjour.domain.MenuItem;
import com.croc.bonjour.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class MenuServiceTest {

    @Autowired
    private MenuItemRepository menuItemRepository;

    private MenuService menuService;

    @BeforeEach
    public void setUp() {
        menuService = new MenuService(menuItemRepository);
    }

    @Test
    @DisplayName("Получение меню")
    public void testGetMenu() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1L, "BigMac", 200, 10.99, false));
        menuItems.add(new MenuItem(2L, "Cheese-Burger", 200, 10.99, false));

        List<MenuItem> result = menuService.getMenu();

        assertNotNull(result);
        assertEquals(menuItems.size(), result.size());
        assertEquals(menuItems.get(0), result.get(0)); //Бигмак
        assertEquals(menuItems.get(1), result.get(1)); //Чизбургер
    }

}