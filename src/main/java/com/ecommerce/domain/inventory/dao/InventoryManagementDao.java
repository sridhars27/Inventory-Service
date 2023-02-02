package com.ecommerce.domain.inventory.dao;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryManagementDao extends JpaRepository<InventoryDetails, UUID> {


   Optional<InventoryDetails> findByProductId(final String productId);
}
