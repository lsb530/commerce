package com.boki.commerce.acceptance;

import static io.restassured.RestAssured.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.item.dto.ItemAddRequest;
import com.boki.commerce.api.item.dto.ItemResponse;
import com.boki.commerce.api.order.domain.Order;
import com.boki.commerce.api.order.dto.OrderResponse;
import com.boki.commerce.api.user.domain.User;
import com.boki.commerce.api.user.dto.UserRegisterRequest;
import com.boki.commerce.api.user.dto.UserSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AcceptanceTestEnv {

    @Autowired
    protected ObjectMapper objectMapper;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        RestAssured.port = port;
    }

    protected void createUser(User user) throws JsonProcessingException {
        UserRegisterRequest addRequest = UserRegisterRequest.builder().email(user.getEmail())
            .password(user.getPassword()).build();
        String body = objectMapper.writeValueAsString(addRequest);
        post("/api/users", body, HttpStatus.SC_OK, UserSession.class);
    }

    protected void createItem(Item item) throws JsonProcessingException {
        ItemAddRequest addRequest = ItemAddRequest.builder().name(item.getName()).price(item.getPrice())
            .build();
        String body = objectMapper.writeValueAsString(addRequest);
        put("/api/items", body, HttpStatus.SC_CREATED, ItemResponse.class);
    }

    protected void createOrder(Order order, String sessionId) throws JsonProcessingException {
        String uri = "/api/orders/" + 1;
        post(uri, "", HttpStatus.SC_OK, OrderResponse.class, sessionId);
    }

    protected <T> T post(String uri, String body, int status, Class<T> cls) {
        return
            given()
                .log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(body)
            .when()
                .post(uri)
            .then()
                .log().all()
                .statusCode(status)
                .contentType(APPLICATION_JSON_VALUE)
                .extract().as(cls);
    }

    protected <T> T post(String uri, String body, int status, Class<T> cls, String sessionId) {
        return
            given()
                .log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .sessionId(sessionId)
                .body(body)
                .when()
                .post(uri)
                .then()
                .log().all()
                .statusCode(status)
                .contentType(APPLICATION_JSON_VALUE)
                .extract().as(cls);
    }

    protected String post(String uri, String body, int status) {
            return given()
                .log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(body)
            .when()
                .post(uri)
            .then()
                .log().all()
                .statusCode(status)
                .extract().sessionId();
    }

    protected <T> T post(String uri, String body, int status, String paramName, Object paramValue, Class<T> cls) {
        return
            given()
                .log().all()
                .params(paramName, paramValue)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(body)
            .when()
                .post(uri)
            .then()
                .log().all()
                .statusCode(status)
                .contentType(APPLICATION_JSON_VALUE)
                .extract().as(cls);
    }

    protected <T> T get(String uri, int status, Class<T> cls) {
        return
            given()
                .log().all()
            .when()
                .get(uri)
            .then()
                .log().all()
                .statusCode(status)
                .contentType(APPLICATION_JSON_VALUE)
                .extract().as(cls);
    }

    protected <T> T get(String uri, int status, Class<T> cls, String sessionId) {
        return
            given()
                .log().all()
                .sessionId(sessionId)
                .when()
                .get(uri)
                .then()
                .log().all()
                .statusCode(status)
                .contentType(APPLICATION_JSON_VALUE)
                .extract().as(cls);
    }

    protected <T> T put(String uri, String body, int status, Class<T> cls) {
        return
            given()
                .log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(body)
            .when()
                .put(uri)
            .then()
                .log().all()
                .statusCode(status)
                .contentType(APPLICATION_JSON_VALUE)
                .extract().as(cls);
    }

    protected void delete(String uri) {
        given()
            .sessionId("")
            .log().all()
        .when()
            .delete(uri)
        .then()
            .log().all()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    protected void delete(String uri, String sessionId) {
        given()
            .sessionId(sessionId)
            .log().all()
            .when()
            .delete(uri)
            .then()
            .log().all()
            .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    protected <T> T delete(String uri, int status, Class<T> cls) {
        return
            given()
                .log().all()
                .when()
                .delete(uri)
                .then()
                .log().all()
                .statusCode(status)
                .contentType(APPLICATION_JSON_VALUE)
                .extract().as(cls);
    }
}