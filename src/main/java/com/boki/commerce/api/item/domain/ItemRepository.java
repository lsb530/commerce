package com.boki.commerce.api.item.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findItemByName(String name);
    boolean existsItemByName(String name);
}