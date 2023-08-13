package com.croc.bonjour.controller;

import com.croc.bonjour.dto.MenuDto;
import com.croc.bonjour.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public MenuDto getMenu() {
        MenuDto menuDto = new MenuDto();
        menuDto.setItems(menuService.getMenu());
        return menuDto;
    }

}