package com.croc.bonjour.controller;

import com.croc.bonjour.models.MenuItem;
import com.croc.bonjour.service.MenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MenuController.class)
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @Test
    @DisplayName("Получение доступных позиций меню")
    public void testGetMenu() throws Exception {
        MenuItem menuItem1 = new MenuItem(1L, "Burger", 260, 500, false);
        MenuItem menuItem2 = new MenuItem(2L, "CheeseBurger", 600, 500, false);
        MenuItem menuItem3 = new MenuItem(3L, "BigMac", 260, 1500, false);
        List<MenuItem> menuItems = List.of(menuItem1, menuItem2, menuItem3);

        when(menuService.getMenu()).thenReturn(menuItems);

        mockMvc.perform(get("/api/menu"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name").value("Burger"))
                .andExpect(jsonPath("$[1].volume").value(600))
                .andExpect(jsonPath("$[2].price").value(1500));
    }

    @Test
    @DisplayName("Получение удаленных позиций меню")
    public void testGetDeletedMenuItems() throws Exception {
        MenuItem deletedMenuItem = new MenuItem(1L, "Carbonara", 500, 750, true);
        List<MenuItem> deletedMenuItems = List.of(deletedMenuItem);

        when(menuService.getDeletedMenuItems()).thenReturn(deletedMenuItems);

        mockMvc.perform(get("/api/menu/deleted"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name").value("Carbonara"));
    }

    @Test
    @DisplayName("Создание новой позиций меню")
    public void testCreateMenuItem() throws Exception {
        MenuItem createdPizza = new MenuItem(null, "Pizza", 350.0, 1000, false);
        MenuItem mockPizza = new MenuItem(1L, "Pizza", 350.0, 1000, false);

        when(menuService.createMenuItem(any(MenuItem.class))).thenReturn(mockPizza);

        mockMvc.perform(post("/api/menu/create")
                        .content(new ObjectMapper().writeValueAsString(createdPizza))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pizza"));
    }

    @Test
    @DisplayName("Удаление позиции из меню (пометить удаленной)")
    public void testDeleteMenuItemById() throws Exception {
        Long menuItemId = 1L;
        MenuItem deletedMenuItem = new MenuItem(menuItemId, "Deleted Item", 0.0, 0, true);

        when(menuService.deleteMenuItem(menuItemId)).thenReturn(deletedMenuItem);

        mockMvc.perform(post("/api/menu/delete/{id}", menuItemId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(menuItemId))
                .andExpect(jsonPath("$.name").value("Deleted Item"))
                .andExpect(jsonPath("$.deleted").value(true));
    }
}
