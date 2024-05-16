CREATE TABLE IF NOT EXISTS product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    balance_in_stock INT,
    purchase_price INT,
    minimum_quantity INT);



