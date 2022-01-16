package com.boki.commerce.api.item.dto;

import com.boki.commerce.api.item.domain.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponse {

    private Long id;

    private String name;

    private int price;

    public static ItemResponse of(Item item) {
        return new ItemResponse(item.getId(), item.getName(), item.getPrice());
    }
}