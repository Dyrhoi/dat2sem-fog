SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE roof_materials
    ADD COLUMN type VARCHAR(25) DEFAULT "ANGLED";

INSERT INTO roof_materials(name, type) VALUES ("Plasttrapezplader", "FLAT");

SET FOREIGN_KEY_CHECKS = 1;

UPDATE properties SET value = 9 WHERE name = 'version';