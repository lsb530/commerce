package com.boki.commerce.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.order.domain.Order;
import com.boki.commerce.api.order.dto.MultipleOrderResponse;
import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.dto.UserLoginRequest;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class OrderAcceptanceTest extends AcceptanceTestEnv {

    private final static Item banana = Item.builder().name("banana").price(2000).build();

    private final static User user = User.builder().email("commerce@commerce.com").password("password")
        .build();

    private final static UserLoginRequest loginRequest = UserLoginRequest.builder()
        .email("commerce@commerce.com")
        .password("password").build();

    @WithMockUser
    @DisplayName("주문 관련 기능")
    @TestFactory
    Stream<DynamicTest> manage_order() {
        AtomicReference<String> sessionContainer = new AtomicReference<>("");
        return Stream.of(
            dynamicTest("주문 생성", () -> {
                createUser(user);
                createItem(banana);
                String body = objectMapper.writeValueAsString(loginRequest);
                String sessionId = post("/api/users/login", body, HttpStatus.SC_OK);
                sessionContainer.set(sessionId);
                Order order = Order.builder().item(banana).user(user).build();
                createOrder(order, sessionId);
            }),
            dynamicTest("주문 내역 조회", () -> {
                MultipleOrderResponse response = get("/api/orders", HttpStatus.SC_OK,
                    MultipleOrderResponse.class, sessionContainer.get());
                assertEquals(response.getOrder().getItems().get(0).getPrice(), banana.getPrice());
            })
        );
    }
}