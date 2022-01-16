package com.boki.commerce.api.order.dto;

import com.boki.commerce.api.order.domain.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse {
    private Long userid;

    private String itemName;

    private int price;

    public static OrderResponse of(Order order) {
        return new OrderResponse(
            order.getUser().getId(),
            order.getItem().getName(),
            order.getItem().getPrice());
    }
}