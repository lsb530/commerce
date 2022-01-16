package com.boki.commerce.api.order.controller;

import com.boki.commerce.api.order.dto.MultipleOrderResponse;
import com.boki.commerce.api.order.dto.OrderResponse;
import com.boki.commerce.api.order.service.OrderService;
import com.boki.commerce.api.user.dto.UserSession;
import com.boki.commerce.api.user.service.UserService;
import com.boki.commerce.resolver.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Order"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final UserService userService;

    private final OrderService orderService;

    @ApiOperation(value = "주문내역 조회", notes = "유저가 주문한 상품들을 조회한다")
    @GetMapping
    public ResponseEntity<MultipleOrderResponse> findOrders(@LoginUser UserSession user) {
        return ResponseEntity.ok(userService.findOrders(user.getId()));
    }

    @ApiOperation(value = "상품 주문", notes = "상품 id로 주문한다")
    @PostMapping("/{itemId}")
    public ResponseEntity<OrderResponse> order(@PathVariable Long itemId,
        @LoginUser UserSession user) {
        return ResponseEntity.ok(orderService.order(itemId, user.getId()));
    }
}