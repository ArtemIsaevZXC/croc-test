package com.croc.bonjour.repository;

import com.croc.bonjour.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    /**
     * Поиск всех доступных позиций в меню.
     *
     * @return список доступных позиций в меню.
     */
    List<MenuItem> findByDeletedFalse();

    /**
     * Поиск доступных позиций из меню по id.
     *
     * @param id
     * @return
     */
    Optional<MenuItem> findByIdAndDeletedFalse(Long id);
}