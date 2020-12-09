SET FOREIGN_KEY_CHECKS = 0;

#Start over please
DROP TABLE customers;
DROP table salesreps;

CREATE TABLE users (
                       id int PRIMARY KEY AUTO_INCREMENT,
                       first_name varchar(250),
                       last_name varchar(250),
                       email varchar(100),
                       phone_number varchar(16),
                       address varchar(250),
                       postal_code int(6),
                       city varchar(120)
);

CREATE TABLE customers (
                           id int primary key auto_increment,
                           user_id int,
                           foreign key (user_id) REFERENCES users(id)
);

CREATE TABLE sales_representative (
                                      id int primary key auto_increment,
                                      salt BINARY(16) NOT NULL,
                                      secret BINARY(32) NOT NULL,
                                      user_id int,
                                      foreign key (user_id) REFERENCES users(id)
);

ALTER TABLE orders
    DROP FOREIGN KEY orders_ibfk_1,
    DROP FOREIGN KEY orders_ibfk_3,
    CHANGE COLUMN salesrep_id sales_representative_id int,
    ADD FOREIGN KEY (customers_id) REFERENCES customers(id),
    ADD FOREIGN KEY (sales_representative_id) references sales_representative(id)
;

SET FOREIGN_KEY_CHECKS = 1;

UPDATE properties SET value = 8 WHERE name = 'version';