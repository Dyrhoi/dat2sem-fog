ALTER TABLE users ADD UNIQUE (email);

UPDATE properties SET value = 11 WHERE name = 'version';