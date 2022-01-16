package com.boki.commerce.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.boki.commerce.api.item.domain.Item;
import com.boki.commerce.api.item.dto.ItemResponse;
import com.boki.commerce.api.item.dto.MultipleItemResponse;
import java.util.stream.Stream;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ItemAcceptanceTest extends AcceptanceTestEnv {

    private final static Item banana = Item.builder().name("banana").price(2000).build();
    private final static Item apple = Item.builder().name("apple").price(1500).build();

    @DisplayName("상품 관련 기능")
    @TestFactory
    Stream<DynamicTest> manage_item() {
        return Stream.of(
            dynamicTest("상품 2개 생성", () -> {
                createItem(banana);
                createItem(apple);
            }),
            dynamicTest("상품 조회", () -> {
                String uri = "/api/items/name/" + banana.getName();
                ItemResponse response = get(uri, HttpStatus.SC_OK, ItemResponse.class);
                assertEquals(response.getPrice(), banana.getPrice());
            }),
            dynamicTest("상품 전체검색",() -> {
                MultipleItemResponse response = get("/api/items", HttpStatus.SC_OK,
                    MultipleItemResponse.class);
                assertEquals(response.getItemCount(),2);
            })
        );
    }
}