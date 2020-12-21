ALTER TABLE users ADD UNIQUE (email);

UPDATE properties SET value = 13 WHERE name = 'version';