package com.boki.commerce.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.order.domain.Order;
import com.boki.commerce.api.order.dto.MultipleOrderResponse;
import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.domain.UserRepository;
import com.boki.commerce.api.user.dto.UserLoginRequest;
import com.boki.commerce.api.user.dto.UserRegisterRequest;
import com.boki.commerce.api.user.dto.UserSession;
import com.boki.commerce.api.user.exception.DuplicatedEmailException;
import com.boki.commerce.api.user.exception.UserNotFoundException;
import com.boki.commerce.api.user.exception.WrongPasswordException;
import com.boki.commerce.api.user.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserRegisterRequest registerRequest;

    private UserLoginRequest loginRequest;

    private User user;

    @BeforeEach
    void setUp() {
        String email = "commerce@commerce.com";
        String password = "password";
        userService = new UserService(userRepository, passwordEncoder);
        registerRequest = UserRegisterRequest.builder().email(email).password(password).build();
        loginRequest = UserLoginRequest.builder().email(email).password(password).build();
        user = User.builder().email(email).password(password).build();
    }

    @DisplayName("[회원가입] - 해당 이메일이 중복이라면 예외 발생")
    @Test
    void register_valid_email() {
        given(userRepository.existsUserByEmail(anyString())).willReturn(true);
        assertThrows(DuplicatedEmailException.class,
            () -> userService.register(registerRequest));
    }

    @DisplayName("[회원가입]")
    @Test
    void register() {
        given(userRepository.existsUserByEmail(anyString())).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn("password");
        given(userRepository.save(any(User.class))).willReturn(user);
        userService.register(registerRequest);
        BDDMockito.verifyNoMoreInteractions(userRepository);
    }

    @DisplayName("[로그인] - 해당 이메일이 존재하지 않는 경우 예외 발생")
    @Test
    void login_email_not_exist() {
        given(userRepository.findUserByEmail(anyString())).willReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
            () -> userService.login(loginRequest));
    }

    @DisplayName("[로그인] - 비밀번호가 맞지 않을 경우 예외 발생")
    @Test
    void login_wrong_password() {
        given(userRepository.findUserByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(any(), any())).willReturn(false);
        assertThrows(WrongPasswordException.class,
            () -> userService.login(loginRequest));
    }

    @DisplayName("[로그인]")
    @Test
    void login() {
        given(userRepository.findUserByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        UserSession response = userService.login(loginRequest);
        assertAll(
            () -> assertEquals(response.getId(), user.getId()),
            () -> assertEquals(response.getEmail(), user.getEmail())
        );
    }

    @DisplayName("[주문조회] - 유저가 주문한 상품들을 조회한다")
    @Test
    void user_orders() {
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        Item item = Item.builder().name("apple").price(1000).build();
        Order order = Order.builder().user(user).item(item).build();

        MultipleOrderResponse response = userService.findOrders(user.getId());

        assertEquals(response.getOrder().getItemCount(), 1);
    }

}