package com.croc.bonjour.controller;

import com.croc.bonjour.SpringBootApplicationTest;
import com.croc.bonjour.models.MenuItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
public class MenuControllerTest extends SpringBootApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение доступных позиций меню")
    public void testGetMenu() throws Exception {
        mockMvc.perform(get("/api/menu"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name").value("BigMac"))
                .andExpect(jsonPath("$[1].volume").value(200))
                .andExpect(jsonPath("$[1].price").value(10.99))
                .andExpect(jsonPath("$[*].deleted", everyItem(is(false))));
    }

    @Test
    @DisplayName("Получение удаленных позиций меню")
    public void testGetDeletedMenuItems() throws Exception {

        mockMvc.perform(get("/api/menu/deleted"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name").value("Deleted Burger"));
    }

    @Test
    @DisplayName("Создание новой позиций меню")
    public void testCreateMenuItem() throws Exception {
        MenuItem createdPizza = new MenuItem(null, "Pizza", 350, 15.45, false);

        mockMvc.perform(post("/api/menu/create")
                        .content(new ObjectMapper().writeValueAsString(createdPizza))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(4L))
                .andExpect(jsonPath("$.name").value("Pizza"));
    }

    @Test
    @DisplayName("Удаление позиции из меню (пометить удаленной)")
    public void testDeleteMenuItemById() throws Exception {
        Long menuItemId = 4L;

        mockMvc.perform(post("/api/menu/delete/{id}", menuItemId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(menuItemId))
                .andExpect(jsonPath("$.name").value("Pizza"))
                .andExpect(jsonPath("$.deleted").value(true));
    }

}
