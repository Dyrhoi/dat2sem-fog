SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS offers;
DROP TABLE IF EXISTS salesreps;

create TABLE offers(
    id int PRIMARY KEY AUTO_INCREMENT,
    order_uuid VARCHAR(36) NOT NULL,
    offer int,
    FOREIGN KEY (order_uuid) REFERENCES orders(uuid)
);

create table salesreps(
    id int PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email varchar(25) NOT NULL UNIQUE,
    salt BINARY(16) NOT NULL,
    secret BINARY(32) NOT NULL
);

AlTER TABLE orders
    ADD COLUMN salesrep_id int DEFAULT NULL,
    ADD FOREIGN KEY (salesrep_id) REFERENCES salesreps(id);



SET FOREIGN_KEY_CHECKS = 1;

UPDATE properties SET value = 7 WHERE name = 'version';