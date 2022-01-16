package com.boki.commerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.item.service.ItemService;
import com.boki.commerce.api.order.domain.Order;
import com.boki.commerce.api.order.domain.OrderRepository;
import com.boki.commerce.api.order.dto.OrderResponse;
import com.boki.commerce.api.order.service.OrderService;
import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        System.out.println("orderService = " + orderService);
    }

    @DisplayName("[주문] - 로그인한 유저와 아이템 id로 주문한다")
    @Test
    void order() {
        Item item = Item.builder().name("banana").price(2000).build();
        User user = User.builder().email("commerce@commerce.com").password("password").build();
        Order order = Order.builder().item(item).user(user).build();
        given(itemService.getItemById(any())).willReturn(item);
        given(userService.getUserById(any())).willReturn(user);
        given(orderRepository.save(any(Order.class))).willReturn(order);
        OrderResponse response = orderService.order(item.getId(), user.getId());
        assertEquals(response.getItemName(), item.getName());
        assertEquals(response.getUserid(), user.getId());
    }
}