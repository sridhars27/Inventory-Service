package com.ecommerce.domain.inventory.service;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import com.ecommerce.domain.inventory.dao.InventoryManagementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InventoryManagementImpl {

    @Autowired
    InventoryManagementDao inventoryManagementDao;

    @Transactional
    public InventoryDetails addAvailability(InventoryDetails inventoryDetails) {
        Optional<InventoryDetails> dbResponse = inventoryManagementDao.findByProductId(inventoryDetails.getProductId());
        if (dbResponse.isPresent()) {
            dbResponse.get().setAvailableQuantity(dbResponse.get().getAvailableQuantity()
                    + inventoryDetails.getAvailableQuantity());
            return inventoryManagementDao.save(dbResponse.get());
        }
        return inventoryManagementDao.save(inventoryDetails);
    }
}
