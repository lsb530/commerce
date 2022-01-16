package com.boki.commerce.api.order.dto;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.item.dto.MultipleItemResponse;
import com.boki.commerce.api.order.domain.Order;
import com.boki.commerce.api.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MultipleOrderResponse {

    private Long userid;

    private MultipleItemResponse order;

    public static MultipleOrderResponse of(User user) {
        List<Item> items = user.getOrders().stream().map(Order::getItem)
            .collect(Collectors.toList());
        return new MultipleOrderResponse(user.getId(), MultipleItemResponse.of(items));
    }
}