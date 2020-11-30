INSERT INTO material_types(name, description) VALUES ('Træ', 'Træ og tagplader'),
                                                     ('Tag', 'Tagsten & Tagpap'),
                                                     ('Beslag', 'Beslag og Skruer'),
                                                     ('Skruer', 'Beslag og Skruer');

                                                            /*Træ materialer fra carport med hældning*/
INSERT INTO materials(name, stock, material_types_id) values ('25x150 mm. trykimprægneret bræt', 1000, 1),
                                                             ('25x150 mm. trykimprægneret bræt', 1000, 1),
                                                             ('25x150 mm. trykimprægneret bræt', 1000, 1),
                                                             ('færdigskåret (byg-selv spær)', 1000, 1),
                                                             ('97x97 mm. trykimprægneret stolpe', 1000, 1),
                                                             ('45x195 spærtræ ubehandlet', 1000, 1),
                                                             ('45x195 spærtræ ubehandlet', 1000, 1),
                                                             ('45x95 Reglar ubehandlet', 1000, 1),
                                                             ('45x95 Reglar ubehandlet', 1000, 1),
                                                             ('19x100 mm. trykimprægneret bræt', 1000, 1),
                                                             ('19x100 mm. trykimprægneret bræt', 1000, 1),
                                                             ('25x50 mm. trykimprægneret bræt', 1000, 1),
                                                             ('19x100 mm. trykimprægneret bræt', 1000, 1),
                                                             ('38x73 mm. taglægte T1', 1000, 1),
                                                             ('38x73 mm. taglægte T1', 1000, 1),
                                                             ('38x73 mm. taglæte T1', 1000, 1),

                                                            /*Tag materialer fra carport med hældning*/
                                                             ('B & C Dobbelt -s sort', 10000,2),
                                                             ('B & C Rygsten sort', 1000, 2),
                                                             ('B & C Toplægte holder', 1000, 2),
                                                             ('B & C Rygstensbeslag', 1000, 2),
                                                             ('B & C Tagstens bindere og nakkekroge', 1000, 2),

                                                            /*Beslag fra caport med hældning*/
                                                             ('Universal 190 mm. højre', 1000, 3),
                                                             ('Universal 190 mm. venstre', 1000, 3),
                                                             ('Stalddørsgreb 50x75', 1000, 3),
                                                             ('T-hængsel 390 mm.', 1000, 3),
                                                             ('Vinkelbeslag', 1000, 3),

                                                            /*Skruer fra caport med hældning*/
                                                             ('4,5 x 60 mm. Skruer, 200 stk', 1000, 4),
                                                             ('5,0 x 40 mm. Skruer, 250 stk', 1000, 4),
                                                             ('5,0 x 100 mm. Skruer, 100 stk', 1000, 4),
                                                             ('Bræddebolt 10x120 mm.', 1000, 4),
                                                             ('Firkantskiver 40x40x11 mm.', 1000, 4),
                                                             ('4,5 x 70 mm. Skruer, 200 stk', 1000, 4),
                                                             ('4,5 x 50 mm. Skruer, 350 stk', 1000, 4),

                                                            /*Træ materialer fra carport med fladt*/
                                                             ('25x200 mm. trykimprægneret brædt', 1000, 1),
                                                             ('25x200 mm. trykimprægneret brædt', 1000, 1),
                                                             ('25x125 mm. trykimprægneret brædt', 1000, 1),
                                                             ('25x125 mm. trykimprægneret brædt', 1000, 1),
                                                             ('38x73 mm. lægte ubehandlet', 1000, 1),
                                                             ('45x95 mm. reglar ubehandlet', 1000, 1),
                                                             ('45x95 mm. reglar ubehandlet', 1000, 1),
                                                             ('45x195 mm. spærtræ ubehandlet', 1000, 1),
                                                             ('45x195 mm. spærtræ ubehandlet', 1000, 1),
                                                             ('45x195 mm. spærtræ ubehandlet', 1000, 1),
                                                             ('97x97 mm. trykimprægneret brædt', 1000, 1),
                                                             ('19x100 mm. trykimprægneret brædt', 10000, 1),
                                                             ('19x100 mm. trykimprægneret brædt', 1000, 1),
                                                             ('19x100 mm. trykimprægneret brædt', 1000, 1),

                                                            /*Tag materialer fra carport med fladt*/
                                                             ('Plastmo Ecolite blåtonet', 1000, 2),
                                                             ('Plastmo Ecolite blåtonet', 1000, 2),

                                                            /*Beslag fra carport med fladt*/
                                                             ('Hulbånd 1x20 mm. 10 meter', 1000, 3),
                                                             ('Universal 190 mm. højre', 1000, 3),
                                                             ('Universal 190 mm. venstre', 1000, 3),
                                                             ('Stalddørsgreb 50x75', 1000, 3),
                                                             ('T-hængsel 390 mm.', 1000, 3),
                                                             ('Vinkelbeslag 35', 1000, 3),

                                                            /*Skruer fra carport med fladt*/
                                                             ('Plastmo bundskruer, 200 stk', 1000, 4),
                                                             ('4,5 x 60 mm. Skruer, 200 stk', 1000, 4),
                                                             ('4,0 x 50 mm. beslagskruer, 250 stk', 1000, 4),
                                                             ('Bræddebolt 10 x 120 mm.', 1000, 4),
                                                             ('Firkantskiver 40x40x11 mm.', 1000, 4),
                                                             ('4,5 x 70 mm. Skruer, 400 stk', 1000, 4),
                                                             ('4,5 x 50 mm. Skruer, 300 stk', 1000, 4);

UPDATE properties SET value = 2 WHERE name = 'version';