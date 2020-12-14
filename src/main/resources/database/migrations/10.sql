SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS order_messages;
DROP TABLE IF EXISTS order_events;

CREATE TABLE order_messages (
    id int PRIMARY KEY AUTO_INCREMENT,
    author_id int,
    content text,
    order_token varchar(32),
    _date timestamp default(CURRENT_TIMESTAMP),
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (order_token) REFERENCES orders(token)
);

CREATE TABLE order_events (
    id int PRIMARY KEY auto_increment,
    author_id int,
    scope varchar(64),
    order_token varchar(32),
    _date timestamp default(CURRENT_TIMESTAMP),
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (order_token) REFERENCES orders(token)
);

SET FOREIGN_KEY_CHECKS = 1;

UPDATE properties SET value = 10 WHERE name = 'version';