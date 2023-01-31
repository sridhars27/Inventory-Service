package com.ecommerce.domain.inventory.bo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDetails {

    private String id;
    private String product_id;
    private String available_quqntity;
}
