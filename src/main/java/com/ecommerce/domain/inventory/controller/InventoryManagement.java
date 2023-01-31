package com.ecommerce.domain.inventory.controller;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import com.ecommerce.domain.inventory.dao.InventoryManagementDao;
import com.ecommerce.domain.inventory.model.InventoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryManagement {

    @Autowired
    InventoryManagementDao inventoryManagementDao;

    @PostMapping("/product")
    public ResponseEntity<InventoryDetails> addAvailability(@RequestBody final InventoryRequest inventoryRequest){
        InventoryDetails inventoryDetails = InventoryDetails.builder()
                .availableQuantity(inventoryRequest.getAvailableQuantity())
                .productId(inventoryRequest.getProductId())
                .build();
        InventoryDetails response = inventoryManagementDao.save(inventoryDetails);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryDetails> checkInventory(@PathVariable final String productId){

        InventoryDetails response = inventoryManagementDao.findByProductId(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
