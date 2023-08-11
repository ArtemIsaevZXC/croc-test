package com.croc.bonjour.controller;

import com.croc.bonjour.models.MenuItem;
import com.croc.bonjour.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private MenuService menuService;
    public MenuController(MenuService menuService){
        this.menuService = menuService;
    }

    /**
     * Позиции в меню.
     * @return
     */
    @GetMapping
    public List<MenuItem> getMenu() {
        return menuService.getMenu();
    }

    /**
     * Удаленные позиции в меню.
     * @return
     */
    @GetMapping("/deleted")
    public List<MenuItem> getDeletedMenuItems() {
        return menuService.getDeletedMenuItems();
    }

    /**
     * Создать позицию в меню.
     * @param menuItem
     * @return
     */
    @PostMapping("/create")
    public MenuItem createMenuItem(@RequestBody MenuItem menuItem) {
        return menuService.createMenuItem(menuItem);
    }

    /**
     * Удалить позицию из меню
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public MenuItem deleteMenuItem(@PathVariable Long id) {
        return menuService.deleteMenuItem(id);
    }
}