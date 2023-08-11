package com.croc.bonjour.repository;

import com.croc.bonjour.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    /**
     * Поиск всех доступных позиций в меню.
     *
     * @return список доступных позиций в меню.
     */
    List<MenuItem> findByDeletedFalse();

    /**
     * Поиск всех удаленных позиций в меню.
     *
     * @return список удаленных позиций в меню.
     */
    List<MenuItem> findByDeletedTrue();

    /**
     * Поиск доступных позиций из меню по id.
     * @param id
     * @return
     */
    Optional<MenuItem> findByIdAndDeletedFalse(Long id);
}