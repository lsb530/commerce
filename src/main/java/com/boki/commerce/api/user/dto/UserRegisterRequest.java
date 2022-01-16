package com.boki.commerce.api.user.dto;

import com.boki.commerce.api.user.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRegisterRequest {
    @NotBlank(message = "email cannot be empty.")
    @Email(message = "email address is not in a valid format.")
    private String email;

    @NotBlank(message = "password cannot be empty.")
    @Size(min = 6, max = 20, message = "password size should be 6-20")
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .build();
    }
}