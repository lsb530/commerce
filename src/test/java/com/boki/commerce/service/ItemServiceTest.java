package com.boki.commerce.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.item.domain.ItemRepository;
import com.boki.commerce.api.item.dto.ItemAddRequest;
import com.boki.commerce.api.item.dto.ItemResponse;
import com.boki.commerce.api.item.dto.MultipleItemResponse;
import com.boki.commerce.api.item.exception.ItemNotFoundException;
import com.boki.commerce.api.item.service.ItemService;
import com.boki.commerce.api.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    private ItemAddRequest existAddRequest;
    private ItemAddRequest notExistAddRequest;

    private Item item;

    private User user;

    @BeforeEach
    void setUp() {
        String name = "banana";
        int price = 3000;
        itemService = new ItemService(itemRepository);
        existAddRequest = ItemAddRequest.builder().name(name).price(5000).build();
        notExistAddRequest = ItemAddRequest.builder().name("grape").price(8000).build();
        item = Item.builder().name(name).price(price).build();
    }

    @DisplayName("[상품 조회] - 찾는 상품 id가 존재하지 않을시 예외 발생")
    @Test
    void find_item_not_exist_id() {
        given(itemRepository.findById(any())).willReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class,
            () -> itemService.findOne(item.getId()));
    }

    @DisplayName("[상품 조회] - 찾는 상품 name이 존재하지 않을시 예외 발생")
    @Test
    void find_item_not_exist_name() {
        given(itemRepository.findItemByName(anyString())).willReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class,
            () -> itemService.findOne(item.getName()));
    }

    @DisplayName("[상품 조회]")
    @Test
    void find_item() {
        given(itemRepository.findById(any())).willReturn(Optional.of(item));
        given(itemRepository.findItemByName(anyString())).willReturn(Optional.of(item));
        ItemResponse response1 = itemService.findOne(item.getId());
        ItemResponse response2 = itemService.findOne(item.getName());
        assertAll(
            () -> assertEquals(response1.getName(), item.getName()),
            () -> assertEquals(response2.getPrice(), item.getPrice())
        );
    }

    @DisplayName("[상품 전체 조회]")
    @Test
    void find_all() {
        Item apple = Item.builder().name("apple").price(2000).build();
        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(apple);
        given(itemRepository.findAll()).willReturn(items);
        MultipleItemResponse response = itemService.findAll();
        assertEquals(response.getItemCount(), 2);
    }

    @DisplayName("[상품 추가] - 이미 상품이 존재할 경우 가격 수정")
    @Test
    void add_item_duplicate() {
        given(itemRepository.existsItemByName(anyString())).willReturn(true);
        given(itemRepository.findItemByName(anyString())).willReturn(Optional.of(item));
        int origin = item.getPrice();
        ItemResponse response = itemService.addItem(existAddRequest);
        assertNotEquals(response.getPrice(), origin);
    }

    @DisplayName("[상품 추가] - 상품이 존재하지 않을 경우 아이템 추가")
    @Test
    void add_new_item() {
        given(itemRepository.existsItemByName(anyString())).willReturn(false);
        given(itemRepository.save(any(Item.class))).willReturn(item);
        ItemResponse response = itemService.addItem(notExistAddRequest);
        assertEquals(response.getName(), item.getName());
    }
}