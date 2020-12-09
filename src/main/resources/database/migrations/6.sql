SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE carports
    ADD COLUMN roof_material int NOT NULL DEFAULT 1,
    ADD COLUMN angle int NOT NULL DEFAULT 25;


SET FOREIGN_KEY_CHECKS = 1;

UPDATE properties SET value = 6 WHERE name = 'version';