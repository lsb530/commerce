package com.boki.commerce.api.order.domain;

import static lombok.AccessLevel.PROTECTED;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.common.exception.IllegalParameterException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "orders")
public class Order {
    // 주문번호: 고유한 주문 id
    // 주문회원: 상품을 주문한 회원 id
    // 주문상품: 주문한 상품 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Order(Long id, Item item, User user) {
        validateParams(item, user);
        this.item = item;
        this.user = user;
        item.getOrders().add(this);
        user.getOrders().add(this);
    }

    private void validateParams(Item item, User user) {
        if(ObjectUtils.isEmpty(item) || ObjectUtils.isEmpty(user)) {
            throw new IllegalParameterException();
        }
    }

}