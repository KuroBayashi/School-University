\echo [INFO] Suppression de la base de données
DROP DATABASE cirquexx;

DROP ROLE IF EXISTS Antonyxx;
DROP ROLE IF EXISTS Fabricexx;
DROP ROLE IF EXISTS Marvenxx;

-- 1. Créer trois utilisateurs.

-- a.	AntonyXX (mot de passe : antpass) avec droit de créer des bases de données et de créer des utilisateurs

CREATE USER Antonyxx WITH PASSWORD 'antpass' CREATEDB CREATEROLE;
--CREATE ROLE Antonyxx WITH LOGIN PASSWORD 'antpass' CREATEDB CREATEROLE;

-- b.	FabriceXX (mot de passe : fabword) sans autorisation de créer de BDs ni des utilisateurs

CREATE USER Fabricexx WITH PASSWORD 'fabword' NOCREATEDB NOCREATEROLE;
--CREATE ROLE Fabricexx WITH PASSWORD 'fabword' NOCREATEDB NOCREATEROLE;

-- c.	MarvenXX (mot de passe : marword) avec autorisation de créer de BDs et sans autorisation de créer des utilisateurs
CREATE USER Marvenxx WITH PASSWORD 'marword' NOCREATEDB NOCREATEROLE;
-- CREATE ROLE Marvenxx WITH PASSWORD 'marword' NOCREATEDB NOCREATEROLE;

-- 2.	Connectez-vous à la base postgres avec l’utilisateur AntonyXX

\c - antonyxx
-- psql -h localhost -U antonyxx -d postgres

-- 3.	Créer une base de données CirqueXX
\echo [INFO] Création de la base de données
CREATE DATABASE cirquexx ENCODING 'UTF8';


-- 4.	Connectez-vous à la base CirqueXX avec l’utilisateur AntonyXX

\c cirquexx

-- \c cirquexx antonyxx

-- 5.	Créer la structure des quatre tables de la base en intégrant les contraintes d’intégrité nécessaires (4 clés primaires, 4 clés étrangères).

-- il faut penser à se connecter à la base cirque avant de créer les tables

-- pensez ensuite à supprimer les tables si elles existent
DROP TABLE IF EXISTS Utilisation;
DROP TABLE IF EXISTS Numero;
DROP TABLE IF EXISTS Accessoire;
DROP TABLE IF EXISTS Personnel;


-- CREATION DES TABLES
\echo [INFO] Création de la table Personnel

CREATE TABLE Personnel (
	nom VARCHAR(20) NOT NULL,
	role VARCHAR(20),
	CONSTRAINT pk_personnel PRIMARY KEY (nom)
);

\echo [INFO] Création de la table Numero

CREATE TABLE Numero (
	titre VARCHAR(30) NOT NULL PRIMARY KEY,
	nature VARCHAR(20),
	responsable VARCHAR(20),
	CONSTRAINT fk_numero FOREIGN KEY (responsable) REFERENCES Personnel(nom)
);

\echo [INFO] Création de la table Accessoire

CREATE TABLE Accessoire (
	nom VARCHAR(30) NOT NULL,
	couleur VARCHAR(10),
	volume DECIMAL(5,2),
	ratelier INT,
	camion INT,
	CONSTRAINT pk_accessoire PRIMARY KEY (nom)
);

\echo [INFO] Création de la table Utilisation

CREATE TABLE Utilisation (
	titre VARCHAR(30) NOT NULL,
	utilisateur VARCHAR(20),
	accessoire VARCHAR(30),
	CONSTRAINT fk_1 FOREIGN KEY (accessoire) REFERENCES Accessoire(nom),
	CONSTRAINT fk_2 FOREIGN KEY (utilisateur) REFERENCES Personnel(nom),
	CONSTRAINT fk_3 FOREIGN KEY (titre) REFERENCES Numero(titre),
	CONSTRAINT pk_1 PRIMARY KEY (titre, utilisateur, accessoire)
);

6.	Connectez-vous en tant que MarvenXX pour peupler la base de données. Que constatez-vous ?
\c - marvenxx
-- INSERT INTO Personnel VALUES('Clovis', 'Jongleur'); commande rejetée car marvenxx n'a pas les privilèges requis pour manipuler la table Personnel

7.	Préciser que MarvenXX est le propriétaire de la base de données Cirques et vérifier le changement de propriétaire ( de Antonyxx à Marvenxx).
\c - postgres
-- Si vous êtes connecté en tant que superutilisateur ( comme postgres) vous pouvez executer directement cette commande
ALTER DATABASE cirquexx OWNER TO marvenxx;

-- sinon si vous êtes connecté en tant que un simple utilisateur ( antonyxx) vous devez ajouter le propriétaire au groupe du nouveau propriétaire
\c - antonyxx

GRANT marvenxx to antonyxx;
ALTER DATABASE cirquexx OWNER TO marvenxx;

-- 7. reconnectez-vous en tant que Marven pour peupler la base de données. Que constatez-vous ?


\echo [INFO] Connexion à la nouvelle base de données
\c cirquexx marvenxx
INSERT INTO Personnel VALUES('Clovis', 'Jongleur');
-- Pas la permission pour insert marvenxx n'a pas les privilèges requis pour manipuler la table Personnel

8.	Reconnectez-vous en tant que MarvenXX pour peupler la base de données. Que constatez-vous  ?
\c cirquexx marvenxx

\c - marvenxx

INSERT INTO Personnel VALUES('Clovis', 'Jongleur');
-- Pas la permission pour insert marvenxx n'a pas les privilèges requis pour manipuler la table Personnel

GRANT antonyxx to marvenxx ;
9.	Attribuer le droit d’insertion dans toutes les tables à MarvinXX et peupler la base cirque.

\c - antonyxx
-- GRANT Insert ON Personnel TO marvenxx;
-- GRANT Insert ON Numero TO marvenxx;
-- GRANT Insert ON Accessoire TO marvenxx;
-- GRANT Insert ON Utilisation TO marvenxx;
GRANT Insert ON Personnel, Numero, Accessoire , Utilisation TO marvenxx;\c - marven14

INSERT INTO personnel VALUES('Clovis', 'Jongleur'),
('Reine de May', 'Ecuyer'),
('Louche', 'Clown'),
('Attention', 'Equilibriste'),
('Partition', 'Musicien'),
('Criniere', 'Dompteur'),
('Jerry', 'Clown'),
('Bal', 'Jongleur'),
('Final', 'Musicien'),
('Louis', 'Jongleur'),
('Leo', 'Jongleur'),
('Lulu', 'Ecuyer'),
('Coloquinte', 'Equilibriste'),
('Grostas', 'Jongleur'),
('Sangtrespur', 'Dompteur');

INSERT INTO Numero VALUES
('Les Zoupalas', 'Jonglerie', 'Clovis'),
('Le coche infernal', 'Equitation', 'Reine de May'),
('Les fauves', 'Clownerie', 'Louche'),
('Les Smilers', 'Equilibre', 'Attention'),
('La passoire magique', 'Lion', 'Criniere'),
('Les Zozos', 'Clownerie', 'Jerry'),
('Les Tartarins', 'Jonglerie', 'Bal');

INSERT INTO Accessoire VALUES
('Ballon', 'Rouge', 0.3, 15, 5),
('Barre', 'Blanc', 0.6, 19, 5),
('Fouet', 'Marron', 0.2, 11, 3),
('Bicyclette a elephant', 'Vert', 0.4, 27, 8),
('Trompette', 'Rouge', 0.2, 2, 1),
('Cercle magique', 'Orange', 0.2, 1, 1),
('Boule', 'Cristal', 0.2, 88, 8),
('Cage a lions', 'Noir', 10.0, 0, 2),
('Chaise longue de lion', 'Bleu', 0.9, 11, 5),
('Peigne de chimpanze', 'Jaune', 0.2, 23, 3),
('Etrier', NULL, NULL, NULL, NULL);



INSERT INTO Utilisation VALUES
('Les Zoupalas', 'Louis', 'Ballon'),
('Les Zoupalas', 'Leo', 'Ballon'),
('Les Zoupalas', 'Louis', 'Barre'),
('Le coche infernal', 'Grostas', 'Bicyclette a elephant'),
('Le coche infernal', 'Lulu', 'Fouet'),
('Les fauves', 'Jerry', 'Trompette'),
('Les Smilers', 'Attention', 'Cercle magique'),
('Les Smilers', 'Attention', 'Boule'),
('Les Smilers', 'Coloquinte', 'Bicyclette a elephant'),
('La passoire magique', 'Criniere', 'Cage a lions'),
('La passoire magique', 'Criniere', 'Chaise longue de lion'),
('Les Zozos', 'Jerry', 'Bicyclette a elephant'),
('Les Zozos', 'Jerry', 'Peigne de chimpanze'),
('Les Tartarins', 'Grostas', 'Bicyclette a elephant'),
('Le coche infernal', 'Sangtrespur', 'Etrier');



-- 10.	Connectez-vous à la base cirque en tant que FabriceXX et créer la vue Res_Numero (Titre, Responsable)
à partir de la table Numero. Que constatez-vous


\c cirque fabrice

CREATE VIEW Res_Numero (Titre, Responsable) AS
SELECT Titre, Responsable
FROM NUMERO;


-- 11.	Attribuer le droit d’insertion, de modification et de suppression dans la table Numero à FabriceXX.


GRANT SELECT, UPDATE, DELETE ON NUMERO TO fabrice ;


-- 12.	À travers la vue Res_Numero, modifier le nom du responsable du numero Zoupla  en « leo».
-- Consulter le contenu de la vue Res_Numero et de la table Numero.

CREATE OR REPLACE RULE "rule1_modif_res_Numero"
AS ON UPDATE TO Res_Numero
DO INSTEAD
  UPDATE Numero SET responsable = new.responsable
	WHERE titre = new.titre;

UPDATE Res_Numero
SET responsable = 'Leo'
WHERE titre = 'Les Zoupalas';
-- vérification de la modification
SELECT * FROM Res_Numero;

SELECT * FROM Numero;


-- -- 12 Bis.	Insérez à partir de la vue Res_Numero un nouveau Numero.

CREATE OR REPLACE RULE "rule2_ins_res_Numero"
AS ON INSERT TO Res_Numero
DO INSTEAD
	INSERT INTO Numero (titre,responsable) VALUES
	(new.titre, new.responsable);

INSERT INTO Res_Numero VALUES
('Pierrot au chapeau pointu', 'Louche');

SELECT *
FROM Res_Numero;

-- 13.	Créez la vue ListeAccessoiresUtilisees (Nom_Accessoire,Numero,Camion, Ratelier),
-- Vérifiez son contenu. Quel est l’intérêt de définir cette vue ?

CREATE VIEW ListeAccessoiresUtilises ("Nom Accessoire", Numero, Camion, Ratelier, Responsable)
AS SELECT A.nom, U.titre, A.camion, A.ratelier, U.utilisateur
FROM Accessoire A, Utilisation U
WHERE U.accessoire = A.nom;

-- Penser à attribuer les droits d'utilisation des tables Accessoires et Utilisation avant de vérifier le résultat

\c - antonyxx
GRANT ALL PRIVILEGES ON Accessoire, Utilisation TO fabricexx;


\c - fabricexx
SELECT * FROM ListeAccessoiresUtilises;


-- 13 bis. A travers la vue ListeAccessoiresUtilises, modifier l'emplacement de l'accessoire fouet
-- en camion 5 et ratelier 12. Que se passe-t-il ?

CREATE OR REPLACE RULE "rule1_update_Liste"
AS ON UPDATE TO ListeAccessoiresUtilises
DO INSTEAD
	UPDATE Accessoire
	SET camion=new.camion, ratelier=new.ratelier
	WHERE nom = new."Nom Accessoire";

-- Verification de la regle

UPDATE ListeAccessoiresUtilises
SET camion=5, ratelier=12
WHERE "Nom Accessoire" = 'Fouet';

SELECT * FROM ListeAccessoiresUtilises;


-- 13 ter insérer un n-uplet quelconque dans la vue listeAccessoiresUtilises. Que se passe-t-il ?

CREATE OR REPLACE RULE "rule2_ins_Liste"
AS ON INSERT TO ListeAccessoiresUtilises
DO INSTEAD (
   INSERT INTO Accessoire (nom, camion, ratelier) VALUES (new."Nom Accessoire", new.camion, new.Ratelier);
   INSERT INTO Utilisation (titre,	accessoire, utilisateur) VALUES (new.numero, new."Nom Accessoire", new.responsable);
   );

INSERT INTO ListeAccessoiresUtilises VALUES ('Baguette','Pierrot au chapeau pointu', '8', '1','Jerry');
SELECT * FROM AccessoiresNonUtilises;
SELECT * FROM Accessoire;
SELECT * FROM ListeAccessoiresUtilises LIMIT 5;

-- 14.	Attribuer le droit de sélection et de mise à jour et d’insertion de la vue listeAccessoiresUtilises à Marvenxx.

-- il faut faire attention ici car bien que c'est Fabricexx qui est le propriétaire de la vue, il faudrait aussi que les personnes qui vont utiliser la vue ont les privilèges nécessaires pour utiliser les tables sous-jacentes
-- à la vue, dans notre cas ( Accessoire, Utilisation)
-- La commande \dp de psql permet d'obtenir des informations sur les droits existants pour les tables et colonnes
-- la commande \z aussi
-- Les entrées affichées par \dp sont interprétées ainsi :
--
--      rolename=xxxx -- privileges granted to a role
--              =xxxx -- privileges granted to PUBLIC
--
--
--                  r -- SELECT ("lecture")
--                  w -- UPDATE ("écriture")
--                  a -- INSERT ("ajout")
--                  d -- DELETE
--                  D -- TRUNCATE
--                  x -- REFERENCES
--                  t -- TRIGGER
--                  X -- EXECUTE
--                  U -- USAGE
--                  C -- CREATE
--                  c -- CONNECT
--                  T -- TEMPORARY
--            arwdDxt -- ALL PRIVILEGES (pour les tables, varie pour les autres objets)
--                  * -- option de transmission du privilège qui précède
--              /yyyy -- role qui a donné le droit


\c - antonyxx
GRANT ALL PRIVILEGES ON Numero, Accessoire, Utilisation TO fabricexx WITH GRANT OPTION;
\c - fabricexx
GRANT Select ON ListeAccessoiresUtilises TO marvenxx;
GRANT Select ON Accessoire , Utilisation TO marvenxx;

-- 15.	Attribuer le droit de sélection et de mise à jour de la vue Res_Numero à Antony.
GRANT Select, UPDATE ON Res_Numero TO antonyxx;

\c - marvenxx
SELECT * FROM ListeAccessoiresUtilises LIMIT 10;

-- 16.	Connectez-vous avec le login d’Antony et cherchez à travers la vue res_Numero les numeros dont le nom contient ‘zo’

\c - antonyxx
SELECT *
FROM Res_Numero
WHERE lower(titre) like '%zo%';

-- 17.	Créer un nouvel utilisateur «Carolxx » avec des droits de création des bases de données
-- et dont le mot de passe est "myworld" qui valide jusqu à la date du 01-01-2015.
DROP ROLE IF EXISTS Carolxx;
CREATE USER Carolxx WITH PASSWORD 'myword' VALID UNTIL '2015-01-01' CREATEDB;


-- 18.	Modifier le mot de passe de Carolxx en « yesword » avec une validité infinie.
ALTER USER Carolxx VALID UNTIL 'infinity';
ALTER USER Carolxx WITH PASSWORD 'yesword';


-- 19.	Connectez-vous à la base de données cirque avec le login « Carolxx »
-- et créez un schéma (CREATE SCHEMA AUTHORIZATION Carolxx;) qui contient une table « Personnel »
-- qui est une copie de la table « Personnel » d’Antony et une table "Mesnumeros" contenant les numéros de nature « Jonglerie » ou « Equilibre ». Vérifier leurs contenus.
--  a.	Rappel : L’accès aux tables d’un autre schéma se fait en préfixant le nom de la table par le nom du schéma (ex. carolxx.numero).
-- pensez à attribuer les droits de creation sur la base de données cirquexx ainsi que les droits de selection sur les tables d'antonyxx avant de vous connecter avec yesminexx
\c - postgres
GRANT CREATE ON DATABASE cirquexx TO Carolxx;
GRANT SELECT ON Numero, Accessoire, Utilisation, Personnel TO Carolxx;

\c - Carolxx

-- Creation de schema dont le nom par defaut est Carolxx
CREATE SCHEMA AUTHORIZATION Carolxx;


-- creation d'une table « Personnel » dans le schema Carolxx qui est une copie de la table « Personnel » d’Antony
CREATE TABLE carolxx.personnel AS
  TABLE personnel;

 --- vérification
Select * from Carolxx.personnel;
Select * from personnel; -- si on est connecté avec Carolxx il affichera le contenu de Carolxx.personnel
Select * from public.personnel; -- c'est le contenu de la table d'origine vu qu'elle n'était pas reliée un schema specifique -- select current_schema;

-- Creation d'une table "Mesnumeros" dans le schema yesminexx contenant les numeros de nature Jonglerie ou Equilibre
CREATE TABLE yesminexx.mesnumeros AS
SELECT * FROM numero WHERE nature ='Jonglerie' OR nature = 'Equilibre';

 --- vérification

Select * from mesnumeros;

-- Creation d'une table "Mesnumeros" dans le schema Carolxx contenant les numeros de nature Jonglerie
-- ou Equilibre
CREATE TABLE Carolxx.mesnumeros AS
SELECT * FROM numero WHERE nature ='Jonglerie' OR nature = 'Equilibre';

 --- vérification

Select * from mesnumeros;

-- 20.	Insérer un nouveau tuple dans la table personnel de Carolxx
INSERT INTO Carolxx.personnel values('Fanfan', 'Equilibriste');


-- 21.	Créer une table "utilisation" dans le schéma Carolxx contenant les titres des numéros et les accessoires
-- qui leur sont associés.

 CREATE TABLE Carolxx.utilisation AS
SELECT DISTINCT U.titre, U.accessoire FROM utilisation U WHERE U.titre IN (Select M.titre from  Carolxx.mesnumeros M);
  --- vérification

Select * from Carolxx.utilisation;

-- 22.	Insérer le tuple suivant ( Titre = 'Les Zoupalas', Accessoire = 'Ballon') dans la table CarolXX.utilisation,
--  ensuite insérer maintenant le tuple suivant ('Les Zoupalas', 'Leo', 'Ballon') dans la table public.utilisation. Que pouvez-vous conclure?

Insert INTO Carolxx.utilisation values ('Les Zoupalas', 'Ballon');

-- pensez d'abord à attribuer les droits d'insertion à Carolxx si vous êtes connectés avec son nom ou connectez-vous avec l'utilisateur antonyxx
-- avant d'executer la commande suivante
  Insert INTO public.utilisation values ('Les Zoupalas', 'Leo', 'Ballon');
-- Violation de la contrainte d'unicité de la valeur

-- 23.	Créer une table Lesaccessoires dans le schéma CarolXX qui permet de renseigner le nom (VARCHAR(30), la couleur (VARCHAR(10)) et le volume (DECIMAL(5,2)) de l'accessoire.
-- Le nom de l'accessoire sera la clé de cette nouvelle table

CREATE TABLE CarolXX.LesAccessoires (
	nom VARCHAR(30) NOT NULL,
	couleur VARCHAR(10),
	volume DECIMAL(5,2),
	CONSTRAINT pk_accessoire PRIMARY KEY (nom)
);


-- 24. peupler cette nouvelle table lesaccessoires en copiant les tuples contenus dans la table accessoire.
-- Veillez à copier uniquement les accessoires mentionnés dans la table mesnumeros.
INSERT  INTO LesAccessoires  (nom, couleur, volume ) VALUES
(SELECT A.nom, A.couleur, A.volume FROM public.accessoire A WHERE A.nom in (SELECT DISTINCT U.Accessoire From CarolXX.utilisation U)) ;
