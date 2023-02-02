package com.ecommerce.domain.inventory.bo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;

import java.util.UUID;

@Entity
@Table(name = "inventory_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",updatable = false, nullable = false, columnDefinition="UUID")
    private UUID id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "available_quantity")
    private int availableQuantity;

}
