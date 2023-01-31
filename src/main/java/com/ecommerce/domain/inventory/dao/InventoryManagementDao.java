package com.ecommerce.domain.inventory.dao;

import com.ecommerce.domain.inventory.bo.InventoryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryManagementDao {

    @Autowired
    JdbcTemplate template;

    public InventoryDetails getItem(int productId){
        String query = "SELECT * FROM inventory_details WHERE product_id=?";
        InventoryDetails inventoryDetails = template.queryForObject(query,new Object[]{productId},new BeanPropertyRowMapper<>(InventoryDetails.class));

        return inventoryDetails;
    }
    /*Adding an item into database table*/
    public int addAvailability(int quantity, int productId){
        String query = "INSERT INTO inventory_details VALUES(?,?)";
        return template.update(query,productId,quantity);
    }
}
