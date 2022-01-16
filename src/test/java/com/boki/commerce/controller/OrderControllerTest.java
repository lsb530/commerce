package com.boki.commerce.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.order.controller.OrderController;
import com.boki.commerce.api.order.domain.Order;
import com.boki.commerce.api.order.dto.MultipleOrderResponse;
import com.boki.commerce.api.order.dto.OrderResponse;
import com.boki.commerce.api.order.service.OrderService;
import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest extends ControllerTestEnv {
    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    private User user;

    private Item banana;

    private Item apple;


    @BeforeEach
    void setUp() {
        user = User.builder().email(email).password(password).build();
        banana = Item.builder().name("banana").price(2000).build();
        apple = Item.builder().name("apple").price(1000).build();
    }

    @WithMockUser
    @DisplayName("주문내역 조회")
    @Test
    void get_orders() throws Exception {
        Order order1 = Order.builder().item(banana).user(user).build();
        Order order2 = Order.builder().item(apple).user(user).build();
        user.getOrders().add(order1);
        user.getOrders().add(order2);

        MultipleOrderResponse response = MultipleOrderResponse.of(user);

        given(userService.findOrders(any())).willReturn(response);

        ResultActions result = mockMvc.perform(
            get("/api/orders")
                .session(session)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
        );

        result.andExpect(status().isOk())
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
            .andExpect(jsonPath("$.order.items.length()", equalTo(2)));
    }

    @WithMockUser
    @DisplayName("상품 주문")
    @Test
    void order() throws Exception {
        Order order = Order.builder().item(banana).user(user).build();
        OrderResponse response = OrderResponse.of(order);

        given(orderService.order(any(), any())).willReturn(response);

        ResultActions result = mockMvc.perform(
            post("/api/orders/{itemId}", 1)
                .session(session)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
        );

        result.andExpect(status().isOk())
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
            .andExpect(jsonPath("$.itemName", equalTo("banana")))
            .andExpect(jsonPath("$.price", equalTo(2000)));
    }

}