package com.boki.commerce.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.item.domain.ItemRepository;
import com.boki.commerce.common.exception.IllegalParameterException;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ItemTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager entityManager;

    private final String name = "apple";

    private final int price = 30_000;

    private Item item;

    @BeforeEach
    void setUp() {
        item = Item.builder().name(name).price(price).build();
    }

    @DisplayName("[아이템 생성] - 이름이 없으면 예외 발생")
    @Test
    void create_item_without_name() {
        assertThrows(IllegalParameterException.class,
            () -> Item.builder().price(price).build());
    }

    @DisplayName("[아이템 생성] - 가격이 없으면 예외 발생")
    @Test
    void create_item_without_price() {
        assertThrows(IllegalParameterException.class,
            () -> Item.builder().name(name).build());
    }

    @DisplayName("[아이템 생성]")
    @Test
    void create_item() {
        Item item = Item.builder().name(name).price(price).build();
        assertAll(
            () -> assertEquals(name, item.getName()),
            () -> assertEquals(price, item.getPrice())
        );
    }

    @DisplayName("[아이템 수정] - 가격이 음수면 예외 발생")
    @Test
    void update_item_negative_price() {
        assertThrows(IllegalParameterException.class, () -> item.update(-30));
    }

    @DisplayName("[아이템 수정]")
    @Test
    void update_item_price() {
        int originPrice = item.getPrice();
        item.update(50000);
        assertNotEquals(originPrice, item.getPrice());
    }

    @DisplayName("[Repository] - 아이템 엔티티 저장")
    @Test
    void save_item() {
        entityManager.persist(item);
        List<Item> items = itemRepository.findAll();
        assertTrue(items.contains(item));
        assertEquals(items.size(), 1);
    }

    @DisplayName("[Repository] - 아이템 id로 상품 찾기")
    @Test
    void find_item_by_id() {
        entityManager.persist(item);
        Item findItem = itemRepository.findById(item.getId()).get();
        assertEquals(item.getName(), findItem.getName());
    }

    @DisplayName("[Repository] - 조회하는 상품명이 존재하는지 확인")
    @Test
    void exists_item_by_name() {
        entityManager.persist(item);
        assertTrue(itemRepository.existsItemByName(item.getName()));
    }

}