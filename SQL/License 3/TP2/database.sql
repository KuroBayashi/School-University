\echo [INFO] Connexion a `Postgres` en tant que Postgres
\c postgres

\echo [INFO] Creation des utilisateurs
CREATE USER Antony  WITH LOGIN ENCRYPTED PASSWORD 'antpass' CREATEDB CREATEUSER;
CREATE USER Marven  WITH LOGIN ENCRYPTED PASSWORD 'marword' CREATEDB NOCREATEUSER;
CREATE USER Fabrice WITH LOGIN ENCRYPTED PASSWORD 'fabword' NOCREATEDB NOCREATEUSER;

\echo [INFO] Connexion a `Postgres` en tant que Antony
\c - Antony

\echo [INFO] Creation de la base de donnees `Cirque`
CREATE DATABASE Cirque ENCODING 'UTF8';

\echo [INFO] Connexion a `Cirque` en tant que Antony
\c Cirque Antony

\echo [INFO] Creation des tables
CREATE TABLE personnel (
	nom 	VARCHAR(20),
	role	VARCHAR(20),

	CONSTRAINT pk_personnel PRIMARY KEY nom
);

CREATE TABLE numero (
	titre				VARCHAR(30),
	nature			VARCHAR(20),
	responsable	VARCHAR(20),

	CONSTRAINT pk_numero PRIMARY KEY titre,
	CONSTRAINT fk_numero_responsable FOREIGN KEY responsable REFERENCES personnel(nom)
);

CREATE TABLE accessoire (
	nom				VARCHAR(30),
	couleur		VARCHAR(10),
	volume		NUMERIC(5, 2),
	ratelier	NUMERIC(2),
	camion		NUMERIC(1),

	CONSTRAINT pk_accessoire PRIMARY KEY nom
);

CREATE TABLE utilisation (
	titre				VARCHAR(30),
	utilisateur	VARCHAR(20),
	accessoire	VARCHAR(30),

	CONSTRAINT pk_utilisation PRIMARY KEY (titre, utilisateur, accessoire),
	CONSTRAINT fk_utilisation_titre FOREIGN KEY titre REFERENCES numero(titre),
	CONSTRAINT fk_utilisation_utilisateur FOREIGN KEY utilisateur REFERENCES personnel(nom),
	CONSTRAINT fk_utilisation_accessoire FOREIGN KEY accessoire REFERENCES accessoire(nom)
);

\echo [INFO] Connexion a `Cirque` en tant que Marven
\c - Marven

\echo [INFO] Essai insertion
INSERT INTO personnel VALUES('Clovis', 'Jongleur');

\echo [INFO] Changement de proprietaire de la base `Cirque`
\c - postgres
ALTER DATABASE Cirque OWNER TO Marven;

\echo [INFO] Reconnexion a `Cirque` en tant que Marven
\c - Marven

\echo [INFO] Essai insertion
INSERT INTO personnel VALUES('Clovis', 'Jongleur');

\echo [INFO] Ajout du droit insertion a Marven
\c - postgres
GRANT INSERT ON Cirque TO Marven;

\echo [INFO] Remplissage des tables
\c - Marven

\echo [INFO] Table `personnel`
INSERT INTO personnel VALUES
	('Clovis', 'Jongleur'),
	('Reine de May', 'Ecuyer'),
	('Louche', 'Clown'),
	('Attention', 'Equilibriste'),
	('Partition', 'Musicien'),
	('Crinière', 'Dompteur'),
	('Jerry', 'Clown'),
	('Bal', 'Jongleur'),
	('Final', 'Musicien'),
	('Louis', 'Jongleur'),
	('Léo', 'Jongleur'),
	('Lulu', 'Ecuyer'),
	('Coloquinte', 'Equilibriste'),
	('Grostas', 'Jongleur'),
	('Sangtrèspur', 'Dompteur')
;

\echo [INFO] Table `numero`
INSERT INTO numero VALUES
	('Les Zoupalas', 'Jonglerie', 'Clovis'),
	('Le coche infernal', 'Equitation', 'Reine de May'),
	('Les fauves', 'Clownerie', 'Louche'),
	('Les Smilers', 'Equilibre', 'Attention'),
	('La passoire magique', 'Lion', 'Crinière'),
	('Les Zozos', 'Clownerie', 'Jerry'),
	('Les Tartarins', 'Jonglerie', 'Bal')
;

\echo [INFO] Table `accessoire`
INSERT INTO accessoire VALUES
	('Ballon', 'Rouge', 0.3, 15, 5),
	('Barre', 'Blanc', 0.6, 19, 5),
	('Fouet', 'Marron', 0.2, 11, 3),
	('Bicyclette à éléphant', 'Vert', 0.4, 27, 8),
	('Trompette', 'Rouge', 0.2, 2, 1),
	('Cercle magique', 'Orange', 0.2, 1, 1),
	('Boule', 'Cristal', 0.2, 88, 8),
	('Cage à lions', 'Noir', 10.0, 10, 2),
	('Chaise longue de lion', 'Bleu', 0.9, 11, 5),
	('Peigne de chimpanzé', 'Jaune', 0.2, 23, 3),
	('Etrier', NULL, NULL, NULL, NULL)
;

\echo [INFO] Table `utilisation`
INSERT INTO utilisation VALUES
	('Les Zoupalas', 'Louis', 'Ballon'),
	('Les Zoupalas', 'Léo', 'Ballon'),
	('Les Zoupalas', 'Louis', 'Barre'),
	('Le coche infernal', 'Grostas', 'Bicyclette à éléphant'),
	('Le coche infernal', 'Lulu', 'Fouet'),
	('Les fauves', 'Jerry', 'Trompette'),
	('Les smilers', 'Attention', 'Cercle magique'),
	('Les smilers', 'Attention', 'Boule'),
	('Les smilers', 'Coloquinte', 'Bicyclette à éléphant'),
	('La passoire magique', 'Crinière', 'Cage à lions'),
	('La passoire magique', 'Crinière', 'Chaise longue de lion'),
	('Les Zozos', 'Jerry', 'Bicyclette à éléphant'),
	('Les Zozos', 'Jerry', 'Peigne de chimpanzé'),
	('Les Tartarins', 'Grostas', 'Bicyclette à éléphant'),
	('Le coche infernal', 'Sangtrèspur', 'Etrier')
;

\echo [INFO] Connexion a `Cirque` en tant que Fabrice
\c - Fabrice

\echo [INFO]Essai creation de la vue `Res_Numero`
CREATE VIEW Res_Numero AS
	SELECT titre, responsable
	FROM numero
	WITH CHECK OPTION
;

\echo [INFO] Ajout des droits a Fabrice sur la table `numero`
\c - postgres

GRANT SELECT, UPDATE, DELETE ON numero TO Fabrice;

\echo [INFO] Reconnexion a `Cirque` en tant que Fabrice
\c - Fabrice

\echo [INFO] Creation vue
CREATE VIEW Res_Numero AS
	SELECT titre, responsable
	FROM numero
	WITH CHECK OPTION
;

\echo [INFO] Reconnexion en tant que Fabrice
CREATE OR REPLACE RULE "rule_res_numero_update" AS
	ON UPDATE TO Res_Numero
	DO INSTEAD
		UPDATE numero
		SET responsable = new.responsable
		WHERE titre = new.titre
;
CREATE OR REPLACE RULE "rule_res_numero_delete" AS
	ON DELETE TO Res_Numero
	DO INSTEAD
		DELETE FROM numero
		WHERE titre = new.titre AND responsable = new.responsable
;

\echo [INFO] Modification en utilisant la vue
UPDATE Res_Numero
	SET responsable = "leo"
	WHERE titre = "Zoupla"
;

\echo [INFO] Creation vue `ListeAccessoiresUtilisees`
CREATE VIEW Liste_Accessoires_Utilisees (nom_accessoire, numero, camion, ratelier) AS
	SELECT nom, titre, camion, ratelier
	FROM
;
