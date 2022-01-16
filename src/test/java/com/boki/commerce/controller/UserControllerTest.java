package com.boki.commerce.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.boki.commerce.api.user.controller.UserController;
import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.dto.UserLoginRequest;
import com.boki.commerce.api.user.dto.UserRegisterRequest;
import com.boki.commerce.api.user.dto.UserSession;
import com.boki.commerce.api.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

//    excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest extends ControllerTestEnv {

    @MockBean
    private UserService userService;

    private UserRegisterRequest userRegisterRequest;

    private UserLoginRequest userLoginRequest;

    private User user;

    @BeforeEach
    void setUp() {
        userRegisterRequest = UserRegisterRequest.builder().email(email).password(password).build();
        userLoginRequest = UserLoginRequest.builder().email(email).password(password).build();
        user = User.builder().email(email).password(password).build();
    }

    @DisplayName("회원가입")
    @Test
    void register() throws Exception {
        String request = objectMapper.writeValueAsString(userRegisterRequest);
        UserSession userSession = UserSession.of(user);

        given(userService.register(any())).willReturn(userSession);

        ResultActions result = mockMvc.perform(
            post("/api/users")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(request)
        );

        result.andExpect(status().isOk())
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
            .andExpect(jsonPath("$.length()", equalTo(2)))
            .andExpect(jsonPath("$.email", equalTo("commerce@commerce.com")));
    }

    @DisplayName("로그인")
    @Test
    void login() throws Exception {
        String request = objectMapper.writeValueAsString(userLoginRequest);
        UserSession response = UserSession.of(user);

        given(userService.login(any())).willReturn(response);

        ResultActions result = mockMvc.perform(
            post("/api/users/login")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(request)
        );

        result.andExpect(status().isOk());
    }

    @WithMockUser
    @DisplayName("로그아웃")
    @Test
    void logout() throws Exception {
        ResultActions result = mockMvc.perform(
            delete("/api/users/logout")
                .session(session)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
        );

        result.andExpect(status().isNoContent());
    }

    @WithMockUser
    @DisplayName("로그인한 유저 조회")
    @Test
    void me() throws Exception {
        ResultActions result = mockMvc.perform(
            get("/api/users/me")
                .session(session)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
        );
        result.andExpect(status().isOk())
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE));
    }
}