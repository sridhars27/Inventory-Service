package com.ecommerce.domain.inventory.controller;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import com.ecommerce.domain.inventory.dao.InventoryManagementDao;
import com.ecommerce.domain.inventory.exception.ProductNotfoundException;
import com.ecommerce.domain.inventory.service.InventoryManagementImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryManagementController {

    @Autowired
    InventoryManagementImpl inventoryManagementImpl;

    @Autowired
    InventoryManagementDao inventoryManagementDao;

    @PostMapping("/product")
    public ResponseEntity<InventoryDetailResponse> addAvailability(@RequestBody InventoryDetailRequest inventoryDetailRequest) {
        InventoryDetails response = inventoryManagementImpl.addAvailability(inventoryDetailRequest.getInventoryDetails());
        return new ResponseEntity<>(new InventoryDetailResponse(response), HttpStatus.CREATED);

    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryDetailResponse> checkInventory(@PathVariable final String productId) {
        Optional<InventoryDetails> response = inventoryManagementDao.findByProductId(productId);
        if (!response.isPresent()) {
            throw new ProductNotfoundException("No Inventory details found for the given Id");
        }
        return new ResponseEntity<>(new InventoryDetailResponse(response.get()), HttpStatus.OK);
    }

    private static class InventoryDetailRequest {

        private InventoryDetails inventoryDetails = InventoryDetails.builder().build();

        public void setProduct_id(String product_id) {
            inventoryDetails.setProductId(product_id);
        }

        public void setAvailable_quantity(int available_quantity) {

            inventoryDetails.setAvailableQuantity(available_quantity);
        }

        public InventoryDetails getInventoryDetails() {
            return inventoryDetails;
        }
    }

    private class InventoryDetailResponse {

        private String productId;

        private int availableQuantity;

        public InventoryDetailResponse(InventoryDetails inventoryDetails) {
            this.productId = inventoryDetails.getProductId();
            this.availableQuantity = inventoryDetails.getAvailableQuantity();
        }

        public String getProduct_id() {
            return productId;
        }

        public int getAvailable_quantity() {
            return availableQuantity;
        }
    }

}
