package com.boki.commerce.api.user.dto;

import com.boki.commerce.api.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSession {

    private Long id;

    private String email;

    public static UserSession of(User user) {
        return new UserSession(user.getId(), user.getEmail());
    }
}