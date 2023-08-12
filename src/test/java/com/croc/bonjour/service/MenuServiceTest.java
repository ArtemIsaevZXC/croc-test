package com.croc.bonjour.service;

import com.croc.bonjour.models.MenuItem;
import com.croc.bonjour.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MenuServiceTest {

    @Mock
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
        menuItems.add(new MenuItem(1L, "Burger", 260, 500, false));
        menuItems.add(new MenuItem(2L, "Pizza", 350, 700, false));

        when(menuItemRepository.findByDeletedFalse()).thenReturn(menuItems);

        List<MenuItem> result = menuService.getMenu();

        assertEquals(menuItems.size(), result.size());
    }

    @Test
    @DisplayName("Получение удаленных позиций из меню")
    public void testGetDeletedMenuItems() {
        List<MenuItem> deletedMenuItems = new ArrayList<>();
        deletedMenuItems.add(new MenuItem(3L, "Salad", 200, 300, true));

        when(menuItemRepository.findByDeletedTrue()).thenReturn(deletedMenuItems);

        List<MenuItem> result = menuService.getDeletedMenuItems();

        assertEquals(deletedMenuItems.size(), result.size());
    }

    @Test
    @DisplayName("Создание новой позиции в меню")
    public void testCreateMenuItem() {
        MenuItem menuItem = new MenuItem(4L, "Pasta", 400, 600, false);

        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem result = menuService.createMenuItem(menuItem);

        assertEquals(menuItem, result);
    }

    @Test
    @DisplayName("Удалиние позиции из меню (пометить удаленной)")
    public void testDeleteMenuItem() {
        Long menuItemId = 5L;
        MenuItem menuItem = new MenuItem(5L, "Sushi", 300, 800, false);

        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem result = menuService.deleteMenuItem(menuItemId);

        assertEquals(true, result.isDeleted());
    }
}