package com.boki.commerce.api.user.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    String password;

    @OneToMany(mappedBy = "user")
    Set<Order> orders = new HashSet<>();

    @Builder
    public User(Long id, String email, String password) {
        validateParams(email, password);
        this.email = email;
        this.password = password;
    }

    private void validateParams(String email, String password) {
        if(!StringUtils.hasText(email) || !StringUtils.hasText(password)) throw new IllegalParameterException();
    }


}