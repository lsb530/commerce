package com.boki.commerce.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.boki.commerce.api.item.controller.ItemController;
import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.item.dto.ItemAddRequest;
import com.boki.commerce.api.item.dto.ItemResponse;
import com.boki.commerce.api.item.dto.MultipleItemResponse;
import com.boki.commerce.api.item.service.ItemService;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest extends ControllerTestEnv {

    @MockBean
    ItemService itemService;

    private ItemAddRequest itemAddRequest;

    private Item item;

    @BeforeEach
    void setUp() {
        itemAddRequest = ItemAddRequest.builder().name("banana").price(3000).build();
        item = Item.builder().name("grape").price(4000).build();
    }

    @DisplayName("상품 조회 - id")
    @Test
    void get_item_by_id() throws Exception {
        ItemResponse response = ItemResponse.of(item);

        given(itemService.findOne(anyLong())).willReturn(response);

        ResultActions result = mockMvc.perform(
            get("/api/items/id/{itemId}", 1)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
        );

        result.andExpect(status().isOk())
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
            .andExpect(jsonPath("$.name", equalTo("grape")))
            .andExpect(jsonPath("$.price", equalTo(4000)));
    }

    @DisplayName("상품 조회 - name")
    @Test
    void get_item_by_name() throws Exception {
        ItemResponse response = ItemResponse.of(item);

        given(itemService.findOne(anyString())).willReturn(response);

        ResultActions result = mockMvc.perform(
            get("/api/items/name/{name}", "grape")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
        );

        result.andExpect(status().isOk())
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
            .andExpect(jsonPath("$.name", equalTo("grape")))
            .andExpect(jsonPath("$.price", equalTo(4000)));
    }

    @DisplayName("상품 전체 조회")
    @Test
    void get_all_items() throws Exception {
        Item banana = Item.builder().name("banana").price(2000).build();
        Item apple = Item.builder().name("apple").price(1000).build();
        ArrayList<Item> items = new ArrayList<>();
        items.add(banana);
        items.add(apple);
        MultipleItemResponse response = MultipleItemResponse.of(items);

        given(itemService.findAll()).willReturn(response);

        ResultActions result = mockMvc.perform(
            get("/api/items")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
        );

        result.andExpect(status().isOk())
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
            .andExpect(jsonPath("$.itemCount", equalTo(2)));
    }

    @DisplayName("상품 추가")
    @Test
    void add_item() throws Exception {
        String request = objectMapper.writeValueAsString(itemAddRequest);
        ItemResponse response = ItemResponse.of(item);

        given(itemService.addItem(any())).willReturn(response);

        ResultActions result = mockMvc.perform(
            put("/api/items")
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .content(request)
        );

        result.andExpect(status().isCreated())
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
            .andExpect(jsonPath("$.name", equalTo("grape")))
            .andExpect(jsonPath("$.price", equalTo(4000)));
    }
}