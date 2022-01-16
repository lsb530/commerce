package com.boki.commerce.api.item.service;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.item.domain.ItemRepository;
import com.boki.commerce.api.item.dto.ItemAddRequest;
import com.boki.commerce.api.item.dto.ItemResponse;
import com.boki.commerce.api.item.dto.MultipleItemResponse;
import com.boki.commerce.api.item.exception.ItemNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemResponse findOne(Long itemId) {
        Item item = getItemById(itemId);
        return ItemResponse.of(item);
    }

    public ItemResponse findOne(String name) {
        Item item = getItemByName(name);
        return ItemResponse.of(item);
    }

    public MultipleItemResponse findAll() {
        List<Item> items = itemRepository.findAll();
        return MultipleItemResponse.of(items);
    }

    @Transactional
    public ItemResponse addItem(ItemAddRequest request) {
        if (itemRepository.existsItemByName(request.getName())) {
            Item item = getItemByName(request.getName());
            item.update(request.getPrice());
            return ItemResponse.of(item);
        } else {
            Item item = itemRepository.save(request.toEntity());
            return ItemResponse.of(item);
        }
    }

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
    }

    public Item getItemByName(String name) {
        return itemRepository.findItemByName(name).orElseThrow(ItemNotFoundException::new);
    }
}