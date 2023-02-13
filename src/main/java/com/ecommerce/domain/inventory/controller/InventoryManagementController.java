package com.ecommerce.domain.inventory.controller;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import com.ecommerce.domain.inventory.dao.InventoryManagementDao;
import com.ecommerce.domain.inventory.exception.ProductNotfoundException;
import com.ecommerce.domain.inventory.service.InventoryManagementImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryManagementController {

    @Autowired
    InventoryManagementImpl inventoryManagementImpl;

    @Autowired
    InventoryManagementDao inventoryManagementDao;

    @PostMapping("/product")
    @ApiOperation(value = "Save product in inventory", response = InventoryDetails.class)
    public ResponseEntity<InventoryDetailResponse> addAvailability(@Valid @RequestBody InventoryDetailRequest inventoryDetailRequest) {
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

    public static class InventoryDetailRequest {

        private InventoryDetails inventoryDetails = InventoryDetails.builder().build();

        @NotEmpty(message = "Product Id cannot be null or empty")
        @NotNull(message = "Product Id cannot be null or empty")
        private String productId;

        @NotNull(message = "Available Quantity cannot be null or empty")
        @Min(value = 1, message = "Available Quantity cannot be null or empty and must be greater than 0")
        private int availableQuantity;

        public void setProduct_id(String productId) {
            this.productId = productId.trim();
            inventoryDetails.setProductId(this.productId);
        }

        public void setAvailable_quantity(int availableQuantity) {
            this.availableQuantity = availableQuantity;
            inventoryDetails.setAvailableQuantity(this.availableQuantity);
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
