package com.boki.commerce.api.item.domain;

import static lombok.AccessLevel.PROTECTED;

import com.boki.commerce.api.order.domain.Order;
import com.boki.commerce.common.BaseTimeEntity;
import com.boki.commerce.common.exception.IllegalParameterException;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "items", indexes = @Index(name = "idx_name", columnList = "name"))
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    @OneToMany(mappedBy = "item")
    private Set<Order> orders = new HashSet<>();

    @Builder
    public Item(Long id, String name, Integer price) {
        validateParams(name, price);
        this.name = name;
        this.price = price;
    }

    public void update(int price) {
        if (price < 0) throw new IllegalParameterException();
        this.price = price;
    }

    private void validateParams(String name, Integer price) {
        if(!StringUtils.hasText(name) || ObjectUtils.isEmpty(price) || price < 0)
            throw new IllegalParameterException();
    }

}