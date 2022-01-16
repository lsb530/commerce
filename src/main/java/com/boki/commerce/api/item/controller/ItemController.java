package com.boki.commerce.api.item.controller;

import com.boki.commerce.api.item.dto.ItemAddRequest;
import com.boki.commerce.api.item.dto.ItemResponse;
import com.boki.commerce.api.item.dto.MultipleItemResponse;
import com.boki.commerce.api.item.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"Item"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    @ApiOperation(value = "상품 조회", notes = "상품 id로 상품 정보를 조회한다")
    @GetMapping("id/{itemId}") // Ambiguous
    public ResponseEntity<ItemResponse> findOneById(@PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(itemService.findOne(itemId));
    }

    @ApiOperation(value = "상품 조회", notes = "상품 name으로 상품 정보를 조회한다")
    @GetMapping("name/{name}")
    public ResponseEntity<ItemResponse> findOneByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(itemService.findOne(name));
    }

    @ApiOperation(value = "상품 전체 조회", notes = "상품을 전체 조회한다")
    @GetMapping
    public ResponseEntity<MultipleItemResponse> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @ApiIgnore
    @PutMapping
    public ResponseEntity<ItemResponse> addItem(@Valid @RequestBody ItemAddRequest request) {
        URI location = URI.create("/api/items/name/" + request.getName());
        return ResponseEntity.created(location).body(itemService.addItem(request));
    }

}