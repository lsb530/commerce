package com.boki.commerce.api.user.service;

import com.boki.commerce.api.order.dto.MultipleOrderResponse;
import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.domain.UserRepository;
import com.boki.commerce.api.user.dto.UserLoginRequest;
import com.boki.commerce.api.user.dto.UserRegisterRequest;
import com.boki.commerce.api.user.dto.UserSession;
import com.boki.commerce.api.user.exception.DuplicatedEmailException;
import com.boki.commerce.api.user.exception.UserNotFoundException;
import com.boki.commerce.api.user.exception.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSession register(UserRegisterRequest request) {
        validateEmail(request.getEmail());
        User user = request.toEntity(passwordEncoder);
        return UserSession.of(userRepository.save(user));
    }

    public UserSession login(UserLoginRequest request) {
        User user = getUserByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }
        return UserSession.of(user);
    }

    public MultipleOrderResponse findOrders(Long userId) {
        User user = getUserById(userId);
        return MultipleOrderResponse.of(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    private void validateEmail(String email) {
        if (userRepository.existsUserByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

}