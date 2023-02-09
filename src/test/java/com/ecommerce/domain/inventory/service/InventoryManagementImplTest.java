package com.ecommerce.domain.inventory.service;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import com.ecommerce.domain.inventory.dao.InventoryManagementDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryManagementImplTest {

    private static final String INVENTORY_ID = UUID.randomUUID().toString();

    @InjectMocks
    private InventoryManagementImpl service;

    @Mock
    private InventoryManagementDao repository;

    private InventoryDetails inventoryDetails;

    private static String PRODUCT_ID= "Q123456";

    private static int QUANTITY= 2;

    @BeforeEach
    public void setup() {
        inventoryDetails = inventoryDetails.builder()
                .id(INVENTORY_ID)
                .productId(PRODUCT_ID)
                .availableQuantity(QUANTITY)
                .build();
    }

    @Test
    void inventoryImpl_addAvailability_success() {
        when(repository.save(any(InventoryDetails.class))).thenReturn(inventoryDetails);

        final InventoryDetails response = service.addAvailability(InventoryDetails.builder()
                .productId(PRODUCT_ID)
                .availableQuantity(QUANTITY)
                .build());
        verify(repository).save(any(InventoryDetails.class));
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(INVENTORY_ID, response.getId());
        assertEquals(PRODUCT_ID, response.getProductId());
        assertEquals(QUANTITY, response.getAvailableQuantity());
    }

    @Test
    void inventoryImpl_addAvailability_deltaValue_success() {
        when(repository.findByProductId(any(String.class))).thenReturn(Optional.of(inventoryDetails));
        when(repository.save(any(InventoryDetails.class))).thenReturn(inventoryDetails);

        final InventoryDetails response = service.addAvailability(InventoryDetails.builder()
                .productId(PRODUCT_ID)
                .availableQuantity(QUANTITY)
                .build());
        verify(repository).save(any(InventoryDetails.class));
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(INVENTORY_ID, response.getId());
        assertEquals(PRODUCT_ID, response.getProductId());
        assertEquals(4, response.getAvailableQuantity());
    }
}
