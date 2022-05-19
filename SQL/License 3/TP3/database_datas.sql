-- INSERT INTO Departement VALUES ('1','ISIS');
-- INSERT INTO Departement VALUES ('2','INFO');
-- INSERT INTO Departement VALUES ('3','SHS');


INSERT INTO Enseignant VALUES('1','1','Lamine','Elyes','MCF','4185','4091','elyes.lamine@univ-jfc.fr');
INSERT INTO Enseignant VALUES('2','1','Pingaud','Herve','PROF',NULL,NULL,'herve.pingaud@univ-jfc.fr');
INSERT INTO Enseignant VALUES('3','1','Rigaud','Bernard','PROF',NULL,NULL,'Bernard.Rigaud@univ-jfc.fr');
INSERT INTO Enseignant VALUES('4','2','Montaut','Thierry','PRAG','1709','1700','thierry.montaut@univ-jfc.fr');
INSERT INTO Enseignant VALUES('5','2','Garric','Nicolas','PRAG','1709','1700','nicolas.garric@univ-jfc.fr');
INSERT INTO Enseignant VALUES('6','2','Ponzoli','David','MCF','2010','1700','david.panzoli@univ-jfc.fr');
INSERT INTO Enseignant VALUES('7','3','Hayawenau','Anne','PROF','6320','2512','Hayawenau@univ-jfc.fr');
INSERT INTO Enseignant VALUES('8','3','Elbhima','Sylvie','MCF','6654','3564','Elbhima.Sylvie@univ-jfc.fr');
INSERT INTO Enseignant VALUES('9','3','Wizzi','Aaed','MCF','2654','3564','wizzi.aaed@univ-jfc.fr');

INSERT INTO Salle VALUES('B','020','15');
INSERT INTO Salle VALUES('B','022','15');
INSERT INTO Salle VALUES('A','301','45');
INSERT INTO Salle VALUES('C','Amphi 8','500');
INSERT INTO Salle VALUES('C','Amphi 4','200');
INSERT INTO Salle VALUES('B','122','50');
INSERT INTO Salle VALUES('A','201','80');
INSERT INTO Salle VALUES('C','TPinfo 1','30');
INSERT INTO Salle VALUES('C','TPinfo 2','50');
INSERT INTO Salle VALUES('A','Amphi A','120');

INSERT INTO Enseignement VALUES ('1','1','Bases de Donnees Relationnelles','Niveau Licence (L3) : Modelisation E/A et UML, Modele relationnel, SQL,formes normales');
INSERT INTO Enseignement VALUES ('2','1','Langage C++','Niveau Master 1');
INSERT INTO Enseignement VALUES ('3','1','Mise a Niveau Bases de Donnees','Niveau Master 2 - Programme Licence et Master 1 en Bases de Donnees');

INSERT INTO Reservation VALUES ('1','B','022','1','1','1','2012/10/15','08:30:00','11:45:00','3');
INSERT INTO Reservation VALUES ('2','B','022','1','1','1','2012/11/04','08:30:00','10:30:00','2');
INSERT INTO Reservation VALUES ('3','B','022','1','1','1','2012/11/07','08:30:00','11:45:00','3');
INSERT INTO Reservation VALUES ('4','B','020','2','1','2','2012/10/15','09:30:00','11:30:00','2');
INSERT INTO Reservation VALUES ('5','B','122','3','1','1','2012/10/15','08:30:00','12:30:00','4');
