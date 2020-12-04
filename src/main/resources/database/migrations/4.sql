SET FOREIGN_KEY_CHECKS = 0;
ALTER TABLE bill_of_materials
    DROP FOREIGN KEY bill_of_materials_ibfk_2,
    CHANGE COLUMN orders_id orders_uuid VARCHAR(36) NOT NULL;
ALTER TABLE orders
CHANGE COLUMN id uuid VARCHAR(36) NOT NULL UNIQUE,
    ADD COLUMN token varchar(32) UNIQUE ;

ALTER TABLE bill_of_materials ADD FOREIGN KEY (orders_uuid) REFERENCES orders(uuid);


SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE customers
    CHANGE COLUMN number phone_number varchar(22) NOT NULL,
    ADD COLUMN postal_code int(6),
    ADD COLUMN city varchar(120);

UPDATE properties SET value = 4 WHERE name = 'version';