package com.boki.commerce.api.order.service;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.item.service.ItemService;
import com.boki.commerce.api.order.domain.Order;
import com.boki.commerce.api.order.domain.OrderRepository;
import com.boki.commerce.api.order.dto.OrderResponse;
import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ItemService itemService;

    private final UserService userService;

    @Transactional
    public OrderResponse order(Long itemId, Long userId) {
        Item item = itemService.getItemById(itemId);
        User user = userService.getUserById(userId);
        Order order = Order.builder().item(item).user(user).build();
        return OrderResponse.of(orderRepository.save(order));
    }
}