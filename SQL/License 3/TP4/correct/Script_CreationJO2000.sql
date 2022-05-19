-- -----------------------------------------------------------------------------
--             Génération d'une base de données pour
--                           PostgreSQL
--                        (17/11/2015 16:35:50)
-- -----------------------------------------------------------------------------
--      Nom de la base : JO2000
--      Auteur : Elyes Lamine
--      Date de dernière modification : 17/10/2015 6:35:30
-- -----------------------------------------------------------------------------
-- Ce fichier contient le script destiné à créer la base de données "commandes".
-- Ce script doit être exécuté en tant qu'utilisateur "postgres".

\echo [INFO] Debut du script


\echo [INFO] Creation de la base de donnees
CREATE DATABASE jo200011 ENCODING 'UTF8';

\echo [INFO] Connexion a la nouvelle base de donnees
\c jo200011
SET DATESTYLE='DMY';

DROP TABLE IF EXISTS  GAGNER_INDIVIDUEL;

DROP TABLE IF EXISTS  APPARTENIR_EQUIPE;

DROP TABLE IF EXISTS  GAGNER_COLLECTIF;

DROP TABLE IF EXISTS  SPORTIF;

DROP TABLE IF EXISTS  EQUIPE;

DROP TABLE IF EXISTS  DISCIPLINE;

DROP TABLE IF EXISTS  SPORT;

-- -----------------------------------------------------------------------------
--       TABLE : SPORT \i C:/sql/Script_CreationJO2000.sql 
-- -----------------------------------------------------------------------------

CREATE TABLE SPORT
   (
    CODE_SPORT INT  PRIMARY KEY,
    INTITULE VARCHAR(40)  NOT NULL 
   ) ;


-- -----------------------------------------------------------------------------
--       TABLE : SPORTIFpsql 
-- -----------------------------------------------------------------------------

CREATE TABLE SPORTIF
   (
    NUM_LICENCE INT  PRIMARY KEY,
    NOM VARCHAR(20)  NOT NULL,
    PRENOM VARCHAR(20)  NULL,
    DATE_NAISS	DATE	NULL,
    PAYS  VARCHAR(20)  NULL,
    CODE_SPORT INT NOT NULL,
    CONSTRAINT FK_SPORTIF_SPORT FOREIGN KEY (CODE_SPORT) REFERENCES SPORT (CODE_SPORT)	
   ) ;


-- -----------------------------------------------------------------------------
--       TABLE : EQUIPE
-- -----------------------------------------------------------------------------

CREATE TABLE EQUIPE
   (
    NUM_EQUIPE INT  PRIMARY KEY,
    DENOMINATION VARCHAR(128)  NOT NULL  
   ) ;


-- -----------------------------------------------------------------------------
--       TABLE : DISCIPLINE
-- -----------------------------------------------------------------------------

CREATE TABLE DISCIPLINE
   (
    CODE_DISCIPLINE INT  PRIMARY KEY,
    INTITULE VARCHAR(60)  NOT NULL,
    CODE_SPORT INT  NOT NULL,
	CONSTRAINT FK_DISCIPLINE_SPORT FOREIGN KEY (CODE_SPORT) REFERENCES SPORT (CODE_SPORT)  	
   ) ;


-- -----------------------------------------------------------------------------
--       TABLE : GAGNER_INDIVIDUEL
-- -----------------------------------------------------------------------------

CREATE TABLE GAGNER_INDIVIDUEL
   (
    CODE_DISCIPLINE INT  NOT NULL,
    NUM_LICENCE INT  NOT NULL ,
    MEDAILLE VARCHAR(10)  NOT NULL,
    CONSTRAINT CLE_GAGNER_INDIVIDUEL PRIMARY KEY (CODE_DISCIPLINE, NUM_LICENCE),
	CONSTRAINT FK_GAGNER_INDIVIDUEL_DISCIPLIN FOREIGN KEY (CODE_DISCIPLINE) REFERENCES DISCIPLINE (CODE_DISCIPLINE)
	 
   ) ;


-- -----------------------------------------------------------------------------
--       TABLE : APPARTENIR
-- -----------------------------------------------------------------------------

CREATE TABLE APPARTENIR_EQUIPE
   (
    NUM_LICENCE INT  NOT NULL,
    NUM_EQUIPE INT  NOT NULL,
    CONSTRAINT CLE_APPARTENIR PRIMARY KEY (NUM_LICENCE, NUM_EQUIPE), 
    CONSTRAINT FK_APPARTENIR_SPORTIF FOREIGN KEY (NUM_LICENCE) REFERENCES SPORTIF (NUM_LICENCE),
	CONSTRAINT FK_APPARTENIR_EQUIPE FOREIGN KEY (NUM_EQUIPE) REFERENCES EQUIPE (NUM_EQUIPE) 
    	
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : GAGNER_PAR_EQUIPE
-- -----------------------------------------------------------------------------

CREATE TABLE GAGNER_COLLECTIF
   (
    CODE_DISCIPLINE INT  NOT NULL,
    NUM_EQUIPE INT  NOT NULL,
    MEDAILLE VARCHAR(10)  NOT NULL
    CONSTRAINT medaille2_ok CHECK (medaille IN ('Or','Argent','Bronze')),
    CONSTRAINT CLE_GAGNER_COLLECTIF PRIMARY KEY (CODE_DISCIPLINE, NUM_EQUIPE),
	CONSTRAINT FK_GAGNER_COLLECTIF_DISCIPLIN FOREIGN KEY (CODE_DISCIPLINE) REFERENCES DISCIPLINE (CODE_DISCIPLINE),
	CONSTRAINT FK_GAGNER_COLLECTIF FOREIGN KEY (NUM_EQUIPE) REFERENCES EQUIPE (NUM_EQUIPE)  
	   ) ;



-- -----------------------------------------------------------------------------
--      AJOUT de DONNES dans les TABLES
-- -----------------------------------------------------------------------------

INSERT INTO sport VALUES (1,'Athletisme');
INSERT INTO sport VALUES (2,'Aviron');
INSERT INTO sport VALUES (3,'Badminton');
INSERT INTO sport VALUES (4,'Baseball');
INSERT INTO sport VALUES (5,'Basket');
INSERT INTO sport VALUES (6,'Boxe');
INSERT INTO sport VALUES (7,'Canoe-Kayak');
INSERT INTO sport VALUES (8,'Cyclisme');
INSERT INTO sport VALUES (9,'Equitation');
INSERT INTO sport VALUES (10,'Escrime');
INSERT INTO sport VALUES (11,'Football');
INSERT INTO sport VALUES (12,'Gymnastique');
INSERT INTO sport VALUES (13,'Halterophilie');
INSERT INTO sport VALUES (14,'Handball');
INSERT INTO sport VALUES (15,'Hockey');
INSERT INTO sport VALUES (16,'Judo');
INSERT INTO sport VALUES (17,'Lutte');
INSERT INTO sport VALUES (18,'Natation');
INSERT INTO sport VALUES (19,'Pentathlon moderne');
INSERT INTO sport VALUES (20,'Softball');
INSERT INTO sport VALUES (21,'Taekwando');
INSERT INTO sport VALUES (22,'Tennis');
INSERT INTO sport VALUES (23,'Tennis de table');
INSERT INTO sport VALUES (24,'Tir');
INSERT INTO sport VALUES (25,'Tir a l''arc');
INSERT INTO sport VALUES (26,'Triathlon');
INSERT INTO sport VALUES (27,'Voile');
INSERT INTO sport VALUES (28,'Volleyball');





INSERT INTO discipline VALUES(1001, 'Epee individuel - messieurs', 10);
INSERT INTO discipline VALUES(1002, 'Epee individuel - dames', 10);
INSERT INTO discipline VALUES(1003, 'Fleuret individuel - messieurs', 10);
INSERT INTO discipline VALUES(1004, 'Fleuret individuel - dames', 10);
INSERT INTO discipline VALUES(1005, 'Sabre individuel - messieurs', 10);
INSERT INTO discipline VALUES(1006, 'Sabre individuel - dames', 10);
INSERT INTO discipline VALUES(1007, 'Epee par equipe - messieurs', 10);
INSERT INTO discipline VALUES(1008, 'Epee par equipe - dames', 10);
INSERT INTO discipline VALUES(1009, 'Fleuret par equipe - messieurs', 10);
INSERT INTO discipline VALUES(1010, 'Fleuret par equipe - dames', 10);
INSERT INTO discipline VALUES(1011, 'Sabre par equipe - messieurs', 10);
INSERT INTO discipline VALUES(1012, 'Sabre par equipe - dames', 10);
INSERT INTO discipline VALUES(1601, ' + 100kg', 16);
INSERT INTO discipline VALUES(801, 'Vtt', 8);
INSERT INTO discipline VALUES(803, '500 m - dames', 8);
INSERT INTO discipline VALUES(2401, 'Pistolet 10m - messieurs', 24);
INSERT INTO discipline VALUES(603, '48kg', 6);
INSERT INTO discipline VALUES(702, 'Canoe slalom', 7);
INSERT INTO discipline VALUES(1603, ' - 63 kg', 16);
INSERT INTO discipline VALUES(802, 'Piste vitesse - dames', 8);
INSERT INTO discipline VALUES(2403, 'Tir aux plateaux - dames', 24);
INSERT INTO discipline VALUES(1605, ' - 68 kg', 16);
INSERT INTO discipline VALUES(705, 'Kayak slalom - dames', 7);
INSERT INTO discipline VALUES(1204, 'Barre fixe', 12);
INSERT INTO discipline VALUES(605, 'Piste - dames', 8);
INSERT INTO discipline VALUES(1616, ' - 78 kg', 16);
INSERT INTO discipline VALUES(1801, '200m dos -dames', 18);
INSERT INTO discipline VALUES(810, 'Piste vitesse - messieurs', 8);
INSERT INTO discipline VALUES(2201, 'Simple - messieurs', 22);
INSERT INTO discipline VALUES(815, 'Contre la montre - dames', 8);
INSERT INTO discipline VALUES(601, 'Poids mouche', 6);
INSERT INTO discipline VALUES(2101, 'Taekwando', 21);
INSERT INTO discipline VALUES(1621, ' -100 kg', 16);
INSERT INTO discipline VALUES(1602, ' -90 kg', 16);
INSERT INTO discipline VALUES(820, 'Keirin', 8);
INSERT INTO discipline VALUES(821, 'Vitesse par equipe - messieurs', 8);
INSERT INTO discipline VALUES(701, 'Deux sans barreur - messieurs', 7);
INSERT INTO discipline VALUES(703, 'Quatre sans barreur', 7);
INSERT INTO discipline VALUES(501, 'Basket - messieurs', 5);
INSERT INTO discipline VALUES(1205, 'Cheval d''arcons', 12);
INSERT INTO discipline VALUES(2301, 'Double - messieurs', 23);
INSERT INTO discipline VALUES(710, 'Deux de couple', 7);
INSERT INTO discipline VALUES(1803, 'Synchronise - duo', 18);

INSERT INTO sportif VALUES(1, 'Barlois-leroux', 'Valerie', '28/05/1969', 'France', 10);
INSERT INTO sportif VALUES(2, 'Flessel-colovic', 'Laura', '06/11/1971', 'France', 10);
INSERT INTO sportif VALUES(3, 'Obry', 'Hugues', '19/05/1973', 'France', 10);
INSERT INTO sportif VALUES(4, 'Srecki', 'Eric', '02/07/1964', 'France', 10);
INSERT INTO sportif VALUES(5, 'Wuilleme', 'Adeline', '08/12/1975', 'France', 10);
INSERT INTO sportif VALUES(6, 'Di martino', 'Jean-francois', '02/03/1967', 'France', 10);
INSERT INTO sportif VALUES(7, 'Gourdain', 'Matthieu', '04/05/1974', 'France', 10);
INSERT INTO sportif VALUES(8, 'Pillet', 'Julien', '28/09/1977', 'France', 10);
INSERT INTO sportif VALUES(9, 'Touya', 'Damien', '23/04/1975', 'France', 10);
INSERT INTO sportif VALUES(10, 'Ferrari', 'Jean-Noel', '07/06/1974', 'France', 10);
INSERT INTO sportif VALUES(11, 'Guyart', 'Brice', '15/03/1981', 'France', 10);
INSERT INTO sportif VALUES(12, 'Plumenail', 'Lionel', '22/01/1967', 'France', 10);
INSERT INTO sportif VALUES(13, 'Tripathi', 'Sangita', '08/07/1968', 'France', 10);
INSERT INTO sportif VALUES(53, 'Douillet', 'David', NULL, 'France', 16);
INSERT INTO sportif VALUES(21, 'Martinez', 'Miguel', NULL, 'France', 8);
INSERT INTO sportif VALUES(22, 'Ballenger', 'Felicia', NULL, 'France', 8);
INSERT INTO sportif VALUES(31, 'Dumoulin', 'Franck', NULL, 'France', 24);
INSERT INTO sportif VALUES(41, 'Asloum', 'Brahim', NULL, 'France', 6);
INSERT INTO sportif VALUES(56, 'Estanguet', 'Tony', NULL, 'France', 7);
INSERT INTO sportif VALUES(77, 'Vandenhende', 'Severine', NULL, 'France', 16);
INSERT INTO sportif VALUES(87, 'Racinet', 'Delphine', NULL, 'France', 24);
INSERT INTO sportif VALUES(98, 'Benboudaoud', 'Larbi', NULL, 'France', 16);
INSERT INTO sportif VALUES(90, 'Guibal', 'Brigitte', NULL, 'France', 7);
INSERT INTO sportif VALUES(103, 'Clinet', 'Marion', NULL, 'France', 6);
INSERT INTO sportif VALUES(117, 'Varonian', 'Benjamin', NULL, 'France', 12);
INSERT INTO sportif VALUES(123, 'Lebrun', 'Celine', NULL, 'France', 16);
INSERT INTO sportif VALUES(145, 'Maracineanu', 'Roxana', NULL, 'France', 18);
INSERT INTO sportif VALUES(23, 'Rousseau', 'Florian', NULL, 'France', 8);
INSERT INTO sportif VALUES(201, 'Di Pasquale', 'Arnaud', NULL, 'France', 22);
INSERT INTO sportif VALUES(25, 'Longo', 'Jeannie', NULL, 'France', 8);
INSERT INTO sportif VALUES(202, 'Thomas', 'Jerome', NULL, 'France', 6);
INSERT INTO sportif VALUES(203, 'Gentil', 'Pascal', NULL, 'France', 21);
INSERT INTO sportif VALUES(204, 'Traineau', 'Stephane', NULL, 'France', 16);
INSERT INTO sportif VALUES(205, 'Demontfaucon', 'Frederic', NULL, 'France', 16);
INSERT INTO sportif VALUES(206, 'Bardet', 'Anne-Lise', NULL, 'France', 7);
INSERT INTO sportif VALUES(208, 'Gane', NULL, NULL, 'France', 8);
INSERT INTO sportif VALUES(209, 'Tournant', NULL, NULL, 'France', 8);
INSERT INTO sportif VALUES(210, 'Rolland', NULL, NULL, 'France', 7);
INSERT INTO sportif VALUES(211, 'Andrieux', NULL, NULL, 'France', 7);
INSERT INTO sportif VALUES(212, 'Porchier', NULL, NULL, 'France', 7);
INSERT INTO sportif VALUES(213, 'Bette', NULL, NULL, 'France', 7);
INSERT INTO sportif VALUES(214, 'Hocde', NULL, NULL, 'France', 7);
INSERT INTO sportif VALUES(215, 'Dorfman', NULL, NULL, 'France', 7);
INSERT INTO sportif VALUES(207, 'Poujade', 'Eric', NULL, 'France', 12);
INSERT INTO sportif VALUES(216, 'Gatien', NULL, NULL, 'France', 23);
INSERT INTO sportif VALUES(217, 'Chila', NULL, NULL, 'France', 23);
INSERT INTO sportif VALUES(218, 'Chapelle', NULL, NULL, 'France', 7);
INSERT INTO sportif VALUES(219, 'Touron', NULL, NULL, 'France', 7);
INSERT INTO sportif VALUES(220, 'Dedieu', NULL, NULL, 'France', 18);
INSERT INTO sportif VALUES(221, 'Lignot', NULL, NULL, 'France', 18);

INSERT INTO equipe VALUES(1001, 'Natation synchronise - duo');
INSERT INTO equipe VALUES(1002, 'Aviron - couple');
INSERT INTO equipe VALUES(1003, 'Tennis de table - double');
INSERT INTO equipe VALUES(1004, 'Sabre par equipe');
INSERT INTO equipe VALUES(1005, 'Epee par equipe');
INSERT INTO equipe VALUES(1006, 'Quatre sans barreur');
INSERT INTO equipe VALUES(1007, 'Fleuret par equipe');
INSERT INTO equipe VALUES(1008, 'Deux sans barreur');
INSERT INTO equipe VALUES(1009, 'Vitesse par equipe');
INSERT INTO equipe VALUES(1010, 'Basket messieurs');



INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1001,220);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1001,221);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1002,218);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1002,219);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1003,216);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1003,217);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1004,7);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1004,8);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1004,9);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1005,3);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1005,4);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1005,6);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1006,212);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1006,213);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1006,214);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1006,215);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1007,10);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1007,11);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1007,12);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1008,210);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1008,211);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1009,23);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1009,208);
INSERT INTO appartenir_equipe(num_equipe,num_licence) VALUES(1009,209);




INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(53, 1601, 'Or');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(21, 801, 'Or');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(22, 803, 'Or');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(31, 2401, 'Or');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(41, 603, 'Or');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(56, 702, 'Or');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(77, 1603, 'Or');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(22, 802, 'Or');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(23, 820, 'Or');
INSERT INTO gagner_collectif(num_equipe,code_discipline,medaille) VALUES(1009, 821, 'Or');
INSERT INTO gagner_collectif(num_equipe,code_discipline,medaille) VALUES(1008, 701, 'Or');
INSERT INTO gagner_collectif(num_equipe,code_discipline,medaille) VALUES(1006, 703, 'Or');
INSERT INTO gagner_collectif(num_equipe,code_discipline,medaille) VALUES(1007, 1009, 'Or');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(87, 2403, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(3, 1001, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(98, 1605, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(90, 705, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(103, 605, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(117, 1204, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(123, 1616, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(145, 1801, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(23, 810, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(7, 1005, 'Argent');
INSERT INTO gagner_collectif(num_equipe,code_discipline,medaille) VALUES(1005, 1007, 'Argent');
INSERT INTO gagner_collectif(num_equipe,code_discipline,medaille) VALUES(1010, 501, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(207, 1205, 'Argent');
INSERT INTO gagner_collectif(num_equipe,code_discipline,medaille) VALUES(1004, 1011, 'Argent');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(201, 2201, 'Bronze');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(25, 815, 'Bronze');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(202, 601, 'Bronze');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(203, 2101, 'Bronze');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(2, 1002, 'Bronze');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(204, 1621, 'Bronze');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(205, 1602, 'Bronze');
INSERT INTO gagner_individuel(num_licence,code_discipline,medaille) VALUES(206, 705, 'Bronze');
INSERT INTO gagner_collectif(num_equipe,code_discipline,medaille) VALUES(1003, 2301, 'Bronze');
INSERT INTO gagner_collectif(num_equipe,code_discipline,medaille) VALUES(1002, 710, 'Bronze');
INSERT INTO gagner_collectif(num_equipe,code_discipline,medaille) VALUES(1001, 1803, 'Bronze');




