SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE carports
    MODIFY angle int DEFAULT 25;

SET FOREIGN_KEY_CHECKS = 1;

UPDATE properties SET value = 8 WHERE name = 'version';