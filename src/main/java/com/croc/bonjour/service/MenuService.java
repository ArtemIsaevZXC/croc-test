package com.croc.bonjour.service;

import com.croc.bonjour.domain.MenuItem;
import com.croc.bonjour.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис меню.
 */
@Service
public class MenuService {
    private MenuItemRepository menuItemRepository;

    public MenuService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    /**
     * Получить список доступных позиций меню.
     *
     * @return список доступных позиций меню.
     */
    public List<MenuItem> getMenu() {
        return menuItemRepository.findByDeletedFalse();
    }
}
