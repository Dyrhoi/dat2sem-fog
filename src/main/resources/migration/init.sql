drop database if exists fog;
drop user if exists 'fog'@'localhost';
create database fog;

create user 'fog'@'localhost';
grant all privileges on fog.* to 'fog'@'localhost';

create user 'fog_test'@'localhost';
grant all privileges on fog_test.* to 'fog_test'@'localhost';

use fog;
DROP TABLE IF EXISTS properties;
CREATE TABLE properties (
    name VARCHAR(255) PRIMARY KEY,
    value VARCHAR(255) NOT NULL
);

INSERT INTO properties (name, value) VALUES ("version", "0");

SET GLOBAL time_zone = '+2:00';