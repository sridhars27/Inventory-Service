package com.ecommerce.domain.inventory.contoller;

import com.ecommerce.domain.inventory.dao.InventoryManagementDao;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventoryManagementControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    InventoryManagementDao dao;

    private static String PRODUCT_ID = "TEST123";

    @Test
    @Order(1)
    public void testAddAvailability() throws JSONException {

        String postRequestBody = new JSONObject()
                .put("product_id", PRODUCT_ID)
                .put("available_quantity", 2)
                .toString();

        webTestClient.post()
                .uri("/inventory/product")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(postRequestBody))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.product_id").isEqualTo(PRODUCT_ID)
                .jsonPath("$.available_quantity").isEqualTo(2);

    }

    @Test
    @Order(2)
    public void testCheckInventory() {
        webTestClient.get()
                .uri("/inventory/product/{productId}", PRODUCT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.product_id").isEqualTo(PRODUCT_ID)
                .jsonPath("$.available_quantity").isEqualTo(2);
    }

    @Test
    @Order(3)
    public void testCheckInventoryInvalidProductId() {
        webTestClient.get()
                .uri("/inventory/product/{productId}", "HHH134")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.error_code").isEqualTo(200)
                .jsonPath("$.error_details").isEqualTo("No Inventory details found for the given Id");
    }

    @AfterAll
    void afterAll() {
        dao.deleteAll();
    }
}
