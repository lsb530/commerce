package com.boki.commerce.api.item.dto;

import com.boki.commerce.api.item.domain.Item;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemAddRequest {

    @NotBlank(message = "name cannot be empty.")
    private String name;

    @PositiveOrZero(message = "price cannot be negative")
    private int price;

    public Item toEntity() {
        return Item.builder().name(name).price(price).build();
    }
}