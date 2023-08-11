package com.croc.bonjour.service;

import com.croc.bonjour.models.MenuItem;
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

    /**
     * Получить список удаленных позиций меню.
     *
     * @return список удаленных позиций меню.
     */
    public List<MenuItem> getDeletedMenuItems() {
        return menuItemRepository.findByDeletedTrue();
    }

    /**
     * Добавить позицию в меню.
     *
     * @param menuItem новая позиция в меню.
     * @return новая позиция в меню.
     */
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    /**
     * Пометить позицию в меню как удаленную.
     *
     * @param id индетификатор позиции в меню.
     * @return удаленная позиция в меню.
     */
    public MenuItem deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Позиции в меню с таким id нет."));
        menuItem.setDeleted(true);
        return menuItemRepository.save(menuItem);
    }
}
