package com.boki.commerce.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.boki.commerce.api.user.dto.UserLoginRequest;
import com.boki.commerce.api.user.dto.UserRegisterRequest;
import com.boki.commerce.api.user.dto.UserSession;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UserAcceptanceTest extends AcceptanceTestEnv {

    @DisplayName("유저 관련 기능")
    @TestFactory
    Stream<DynamicTest> manage_user() {
        UserRegisterRequest registerRequest = UserRegisterRequest.builder().email("commerce@commerce.com")
            .password("password").build();
        UserLoginRequest loginRequest = UserLoginRequest.builder().email("commerce@commerce.com")
            .password("password").build();
        AtomicReference<String> sessionContainer = new AtomicReference<>("");
        return Stream.of(
            dynamicTest("회원가입", () -> {
                String body = objectMapper.writeValueAsString(registerRequest);
                UserSession response = post("/api/users", body, HttpStatus.SC_OK,
                    UserSession.class);
                assertEquals(response.getEmail(), registerRequest.getEmail());
            }),
            dynamicTest("로그인", () -> {
                String body = objectMapper.writeValueAsString(loginRequest);
                String sessionId = post("/api/users/login", body, HttpStatus.SC_OK);
                sessionContainer.set(sessionId);
            }),
            dynamicTest("로그아웃", () -> {
                String sessionId = sessionContainer.get();
                delete("/api/users/logout", sessionId);
            })
        );
    }
}