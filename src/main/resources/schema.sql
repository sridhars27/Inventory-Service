
CREATE TABLE inventory_details (
   id INT NOT NULL AUTO_INCREMENT,
   product_id VARCHAR(50) NOT NULL,
   available_quantity INT,
   PRIMARY KEY (`id`)
);