ALTER TABLE offers
    ADD COLUMN timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE properties SET value = 11 WHERE name = 'version';
