\echo [INFO] Creation des utilisateurs
CREATE USER Antony WITH PASSWORD 'antpass' CREATEDB CREATEUSER;
CREATE USER Fabrice WITH PASSWORD 'fabword' NOCREATEDB NOCREATEUSER;
CREATE USER Marven WITH PASSWORD 'marword' CREATEDB NOCREATEUSER;

\echo [INFO] Connexion a `Postgres` en tant que Antony
\c postgres antony

\echo [INFO] Creation de la base de donnees `Cirque`
CREATE DATABASE Cirque ENCODING 'UTF8';

\echo [INFO] Connexion a `Cirque` en tant que Antony
\c Cirque antony

\echo [INFO] Creation des tables
CREATE TABLE personnel (
	nom 	VARCHAR(20),
	role	VARCHAR(20),

	CONSTRAINT pk_personnel PRIMARY KEY nom
);

CREATE TABLE numero (
	titre		VARCHAR(30),
	nature		VARCHAR(20),
	responsable	VARCHAR(20),

	CONSTRAINT pk_numero PRIMARY KEY titre,
	CONSTRAINT fk_responsable FOREIGN KEY responsable REFERENCES personnel(nom)
);

CREATE TABLE accessoire (
	nom		VARCHAR(30) PRIMARY KEY,
	couleur		VARCHAR(10),
	volume		NUMERIC(5, 2),
	ratelier	NUMERIC(2),
	camion		NUMERIC(1),

	CONSTRAINT pk_accessoire PRIMARY KEY nom
);

CREATE TABLE utilisation (
	titre		VARCHAR(30),
	utilisateur	VARCHAR(20),
	accessoire	VARCHAR(30),
	CONSTRAINT fk_titre FOREIGN KEY
);


