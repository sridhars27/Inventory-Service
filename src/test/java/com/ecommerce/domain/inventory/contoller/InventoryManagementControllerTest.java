package com.ecommerce.domain.inventory.contoller;

import com.ecommerce.domain.inventory.dao.InventoryManagementDao;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class InventoryManagementControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    InventoryManagementDao dao;

    private static String PRODUCT_ID = "QWE123";

    private static String PRODUCT_ID_2 = "TEST123";

    private static int QUANTITY = 2;

    @Test
    public void inventoryApi_addAvailability_success() throws JSONException {

        String postRequestBody = new JSONObject()
                .put("product_id", PRODUCT_ID)
                .put("available_quantity", QUANTITY)
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
                .jsonPath("$.available_quantity").isEqualTo(QUANTITY);

    }

    @Test
    public void inventoryApi_checkInventory_success() {
        webTestClient.get()
                .uri("/inventory/product/{productId}", PRODUCT_ID_2)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.product_id").isEqualTo(PRODUCT_ID_2)
                .jsonPath("$.available_quantity").isEqualTo(2);
    }

    @Test
    public void inventoryApi_checkInventory_failure() {
        webTestClient.get()
                .uri("/inventory/product/{productId}", "HHH134")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.error_code").isEqualTo(200)
                .jsonPath("$.error_details").isEqualTo("No Inventory details found for the given Id");
    }

}
