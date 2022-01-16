package com.boki.commerce.api.item.dto;

import com.boki.commerce.api.item.domain.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MultipleItemResponse {

    private List<ItemResponse> items = new ArrayList<>();
    private int itemCount;

    public MultipleItemResponse(List<ItemResponse> items) {
        this.items = items;
        this.itemCount = items.size();
    }

    public static MultipleItemResponse of(List<Item> items) {
        List<ItemResponse> multipleItems = items.stream().map(ItemResponse::of)
            .collect(Collectors.toList());
        return new MultipleItemResponse(multipleItems);
    }
}