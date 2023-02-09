DROP TABLE IF EXISTS inventory_details;

CREATE TABLE inventory_details (
  id VARCHAR(250) PRIMARY KEY,
  product_id VARCHAR(250) NOT NULL,
  available_quantity INT NOT NULL
);