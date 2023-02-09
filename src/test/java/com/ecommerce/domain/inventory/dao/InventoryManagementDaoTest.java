package com.ecommerce.domain.inventory.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class InventoryManagementDaoTest {

    @Autowired
    InventoryManagementDao repository;

    private static String PRODUCT_ID = "QWE123";

    private static String PRODUCT_ID_2 = "TEST123";

    private static int QUANTITY = 2;

    @Test
    public void inventoryDao_save_success() {
        InventoryDetails inventoryDetails = repository.save(InventoryDetails.builder()
                .availableQuantity(QUANTITY)
                .productId(PRODUCT_ID)
                .build());

        assertEquals(PRODUCT_ID, inventoryDetails.getProductId());
        assertEquals(QUANTITY, inventoryDetails.getAvailableQuantity());
    }

    @Test
    public void inventoryDao_findByProductId_success() {

        Optional<InventoryDetails> inventoryDetails = repository.findByProductId(PRODUCT_ID_2);

        assertEquals(PRODUCT_ID_2, inventoryDetails.get().getProductId());
        assertEquals(QUANTITY, inventoryDetails.get().getAvailableQuantity());
    }
}
