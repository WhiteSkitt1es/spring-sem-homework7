INSERT INTO product (name, balance_in_stock, purchase_price, minimum_quantity)
VALUES
    ('Product1', FLOOR(RAND() * 100), FLOOR(RAND() * 1000), FLOOR(RAND() * 50)),
    ('Product2', FLOOR(RAND() * 100), FLOOR(RAND() * 1000), FLOOR(RAND() * 50)),
    ('Product3', FLOOR(RAND() * 100), FLOOR(RAND() * 1000), FLOOR(RAND() * 50)),
    ('Product4', FLOOR(RAND() * 100), FLOOR(RAND() * 1000), FLOOR(RAND() * 50)),
    ('Product5', FLOOR(RAND() * 100), FLOOR(RAND() * 1000), FLOOR(RAND() * 50));
