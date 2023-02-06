package com.ecommerce.domain.inventory.service;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import com.ecommerce.domain.inventory.dao.InventoryManagementDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryManagementImplTest {

    private static final UUID INVENTORY_ID = UUID.randomUUID();

    @InjectMocks
    private InventoryManagementImpl service;

    @Mock
    private InventoryManagementDao repository;

    private InventoryDetails inventoryDetails;

    @BeforeEach
    public void setup() {
        inventoryDetails = inventoryDetails.builder()
                .id(INVENTORY_ID)
                .productId("TEST123")
                .availableQuantity(2)
                .build();
    }

    @Test
    void should_save_inventory_details() {
        when(repository.save(any(InventoryDetails.class))).thenReturn(inventoryDetails);

        final InventoryDetails response = service.addAvailability(InventoryDetails.builder()
                .productId("TEST123")
                .availableQuantity(2)
                .build());
        verify(repository).save(any(InventoryDetails.class));
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(INVENTORY_ID, response.getId());
        assertEquals("TEST123", response.getProductId());
        assertEquals(2, response.getAvailableQuantity());
    }
}
