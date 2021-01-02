SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS order_status;

CREATE TABLE order_status(
    id int primary key auto_increment,
    name varchar(64) unique,
    color_rgb varchar(20)
);

INSERT INTO order_status (name, color_rgb) VALUES ('Under Inspektion', '255, 193, 7');
INSERT INTO order_status (name, color_rgb) VALUES ('Tilbud Afsendt', '0, 61, 118');
INSERT INTO order_status (name, color_rgb) VALUES ('Tilbud Accepteret', '55, 228, 49');
INSERT INTO order_status (name, color_rgb) VALUES ('Ordre Betalt', '55, 228, 49');

ALTER TABLE orders
    ADD COLUMN order_status_id int DEFAULT 1,
    ADD CONSTRAINT fk_order_status_id FOREIGN KEY orders(order_status_id) REFERENCES order_status(id);

SET FOREIGN_KEY_CHECKS = 1;

UPDATE properties SET value = 14 WHERE name = 'version';