package com.ecommerce.domain.inventory.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class InventoryManagementDaoTest {

    @Autowired
    InventoryManagementDao repository;

    @AfterEach
    public void cleanUpEach() {
        repository.deleteAll();
    }

    @Test
    private void addAvailability_success() {
        InventoryDetails inventoryDetails = repository.save(InventoryDetails.builder()
                .availableQuantity(5)
                .productId("TEST123")
                .build());

        assertEquals("TEST123", inventoryDetails.getProductId());
        assertEquals(5, inventoryDetails.getAvailableQuantity());


    }

    @Test
    private void find_inventoryDetails_by_product_id() {
        repository.save(InventoryDetails.builder()
                .availableQuantity(5)
                .productId("TEST123")
                .build());
        Optional<InventoryDetails> inventoryDetails = repository.findByProductId("TEST123");

        assertEquals("TEST123", inventoryDetails.get().getProductId());
        assertEquals(5, inventoryDetails.get().getAvailableQuantity());
    }
}
