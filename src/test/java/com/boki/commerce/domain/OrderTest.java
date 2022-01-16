package com.boki.commerce.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.item.domain.ItemRepository;
import com.boki.commerce.api.order.domain.Order;
import com.boki.commerce.api.order.domain.OrderRepository;
import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.domain.UserRepository;
import com.boki.commerce.common.exception.IllegalParameterException;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OrderTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    private User user;

    private Item item;

    private Order order;

    @BeforeEach
    void setUp() {
        user = User.builder().email("user@user.com").password("password!").build();
        item = Item.builder().name("banana").price(2000).build();
        order = Order.builder().item(item).user(user).build();
    }

    @DisplayName("[주문 생성] - 유저와 아이템이 있어야 주문 가능")
    @Test
    void create_order_without_essential_params() {
        assertThrows(
            IllegalParameterException.class,
            () -> Order.builder().item(item).build()
        );
        assertThrows(
            IllegalParameterException.class,
            () -> Order.builder().user(user).build()
        );
    }

    @DisplayName("[주문 생성]")
    @Test
    void create_order() {
        Order order = Order.builder().item(item).user(user).build();
        assertEquals(order.getItem().getPrice(), item.getPrice());
        assertEquals(order.getUser().getEmail(), user.getEmail());
    }

    @DisplayName("[Repository] - 주문 엔티티 저장")
    @Test
    void save_order() {
        entityManager.persist(order);
        List<Order> orders = orderRepository.findAll();
        assertEquals(orders.size(), 1);
    }

    @DisplayName("[Repository] - 주문 id로 주문 찾기")
    @Test
    void find_order_by_id() {
        entityManager.persist(order);
        Order findOrder = orderRepository.findById(this.order.getId()).get();
        assertEquals(order.getItem(), findOrder.getItem());
    }

}