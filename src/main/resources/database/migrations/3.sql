CREATE TABLE roof_materials(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(250)
);

INSERT INTO roof_materials(name) VALUES ('Betontagsten - Rød'),
                                        ('Betontagsten - Teglrød'),
                                        ('Betontagsten - Brun'),
                                        ('Betontagsten - Sort'),
                                        ('Eternittag B6 - Grå'),
                                        ('Eternittag B6 - Sort'),
                                        ('Eternittag B6 - Mokka (brun)'),
                                        ('Eternittag B6 - Rødbrun'),
                                        ('Eternittag B6 - Teglrød'),
                                        ('Eternittag B7 - Grå'),
                                        ('Eternittag B7 - Mokka (brun)'),
                                        ('Eternittag B7 - Rødbrun'),
                                        ('Eternittag B7 - Teglrød'),
                                        ('Eternittag B7 - Rødflammet');

UPDATE properties SET value = 3 WHERE name = 'version';