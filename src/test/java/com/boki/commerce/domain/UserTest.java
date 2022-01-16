package com.boki.commerce.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.domain.UserRepository;
import com.boki.commerce.common.exception.IllegalParameterException;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    private final String email = "commerce@commerce.com";
    private final String password = "passwordpassword";

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().email(email).password(password).build();
    }

    @DisplayName("[유저 생성] - 이메일이 없으면 예외 발생")
    @Test
    void create_user_without_email() {
        assertThrows(IllegalParameterException.class,
            () -> User.builder().password(password).build());
    }

    @DisplayName("[유저 생성] - 비밀번호가 없으면 예외 발생")
    @Test
    void create_user_without_password() {
        assertThrows(IllegalParameterException.class,
            () -> User.builder().email(email).build());
    }

    @DisplayName("[유저 생성]")
    @Test
    void create_user() {
        User user = User.builder().email(email).password(password).build();
        assertAll(
            () -> assertEquals(email, user.getEmail()),
            () -> assertEquals(password, user.getPassword())
        );
    }

    @DisplayName("[Repository] - 유저 엔티티 저장")
    @Test
    void save_user() {
        entityManager.persist(user);
        List<User> users = userRepository.findAll();
        assertTrue(users.contains(user));
        assertEquals(users.size(), 1);
    }

    @DisplayName("[Repository] - 유저 id로 유저 찾기")
    @Test
    void find_user_by_id() {
        entityManager.persist(user);
        User findUser = userRepository.findById(user.getId()).get();
        assertEquals(user.getEmail(), findUser.getEmail());
    }

    @DisplayName("[Repository] - 유저 email로 찾은 유저와 같은지 확인")
    @Test
    void find_user_by_email() {
        entityManager.persist(user);
        User findUser = userRepository.findUserByEmail(user.getEmail()).get();
        assertEquals(user.getEmail(), findUser.getEmail());
    }

    @DisplayName("[Repository] - 조회하는 email이 존재하는지 확인")
    @Test
    void exists_user_by_email() {
        entityManager.persist(user);
        assertTrue(userRepository.existsUserByEmail(user.getEmail()));
    }
}