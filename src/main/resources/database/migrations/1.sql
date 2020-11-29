SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS carports;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS sheds;
DROP TABLE IF EXISTS materials;
DROP TABLE IF EXISTS material_types;
DROP TABLE IF EXISTS bill_of_materials;
SET FOREIGN_KEY_CHECKS = 1;

create table customers(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(250),
    email varchar(100),
    number int,
    address varchar(250),
    notes text
);

create table carports(
    id int PRIMARY KEY AUTO_INCREMENT,
    width int,
    `length` int,
    roof_type varchar(100)
);

create table material_types(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(250),
    description varchar(500)
);

create table orders(
    id int PRIMARY KEY AUTO_INCREMENT,
    customers_id int,
    carports_id int,
    FOREIGN KEY (customers_id) REFERENCES customers(id),
    FOREIGN KEY (carports_id) REFERENCES carports(id)
);

create table sheds(
    id int PRIMARY KEY AUTO_INCREMENT,
    width int,
    `length` int,
    carports_id int,
    FOREIGN KEY (carports_id) REFERENCES carports(id)
);

create table materials(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(250),
    stock int,
    material_types_id int,
    FOREIGN KEY (material_types_id) REFERENCES material_types(id)
);

create table bill_of_materials(
    id int PRIMARY KEY AUTO_INCREMENT,
    materials_id int,
    orders_id int,
    FOREIGN KEY (materials_id) REFERENCES materials(id),
    FOREIGN KEY (orders_id) REFERENCES orders(id)
);

UPDATE properties SET value = 1 WHERE name = 'version';