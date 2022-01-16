package com.boki.commerce.api.user.controller;

import com.boki.commerce.api.user.dto.UserLoginRequest;
import com.boki.commerce.api.user.dto.UserRegisterRequest;
import com.boki.commerce.api.user.dto.UserSession;
import com.boki.commerce.api.user.service.UserService;
import com.boki.commerce.resolver.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "이메일, 비밀번호로 회원가입한다")
    @PostMapping
    public ResponseEntity<UserSession> register(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @ApiOperation(value = "로그인", notes = "이메일, 비밀번호로 로그인한다")
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserLoginRequest request,
        HttpServletRequest httpServletRequest) {
        UserSession userSession = userService.login(request);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("user", userSession);
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(),
            userSession, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃한다")
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        session.invalidate();
        return ResponseEntity.noContent().build();
    }

    @ApiIgnore
    @GetMapping("/me")
    public ResponseEntity<UserSession> me(@LoginUser UserSession userSession) {
        return ResponseEntity.ok(userSession);
    }
}