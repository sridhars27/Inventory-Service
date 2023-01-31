package com.ecommerce.domain.inventory.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class InventoryManagementDaoTest {

    @Autowired
    InventoryManagementDao repository;

    @AfterEach
    public void cleanUpEach() {
        repository.deleteAll();
    }

    @Test
    public void addAvailability_success() {
        InventoryDetails inventoryDetails = repository.save(InventoryDetails.builder()
                .availableQuantity(5)
                .productId("TEST123")
                .build());

        assertEquals("TEST123", inventoryDetails.getProductId());
        assertEquals(5, inventoryDetails.getAvailableQuantity());


    }

    @Test
    public void find_inventoryDetails_by_product_id() {
        repository.save(InventoryDetails.builder()
                .availableQuantity(5)
                .productId("TEST123")
                .build());
        InventoryDetails inventoryDetails = repository.findByProductId("TEST123");

        assertEquals("TEST123", inventoryDetails.getProductId());
        assertEquals(5, inventoryDetails.getAvailableQuantity());
    }
}
