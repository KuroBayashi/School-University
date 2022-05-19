# -------------------------------------
#	Init
# -------------------------------------
\c postgres

DROP TABLE IF EXISTS Commande;
DROP TABLE IF EXISTS Produit;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Fournisseur;

DROP DATABASE IF EXISTS commandes;
CREATE DATABASE commandes;

\c commandes

# -------------------------------------
#	Tables
# -------------------------------------
CREATE TABLE Client (
	numcli serial not null,
	nom varchar(20),
	prenom varchar(20),
	datenaiss date,
	rue varchar(30),
	cp int,
	ville varchar(30),

	constraint cle_client primary key (numcli)
);

CREATE TABLE Fournisseur (
	numfour serial not null,
	raisonsoc varchar(40),

	constraint cle_four primary key (numfour)
);

CREATE TABLE Produit (
	numprod serial not null,
	desi varchar(40),
	prixuni decimal(6,2),
	numfour int not null,

	constraint cle_prod primary key (numprod)
);

CREATE TABLE Commande (
	numcli int not null,
	numprod int not null,
	quantite int,
	datec date,

	constraint cle_commande primary key (numcli,numprod)
); 

# -------------------------------------
#	Constraints
# -------------------------------------
ALTER TABLE Produit 
ADD CONSTRAINT fk_numfour FOREIGN KEY (numfour) REFERENCES Fournisseur (numfour);

ALTER TABLE Commande 
ADD CONSTRAINT fk_ncli FOREIGN KEY (numcli) REFERENCES Client (numcli),
ADD CONSTRAINT fk_nprod FOREIGN KEY (numprod) REFERENCES Produit (numprod);

# -------------------------------------
#	Fixtures
# -------------------------------------
insert into Client values(1,'Dupont','Albert','1970-06-01','Rue de Crimee',69001,'Lyon');
insert into Client values(2,'West','james','1963-09-03','Studio',0,'Hollywood');
insert into Client values(3,'Martin','Marie','1978-06-05','Rue des Acacias',69130,'Ecully');
insert into Client values(4,'Durand','Gaston','1980-11-15','Rue de la Meuse',69008,'Lyon');
insert into Client values(5,'Titgoutte','Justine','1975-02-28','Chemin du Chateau',69630,'Chaponost');
insert into Client values(6,'Dupond','Noemie','1957-09-18','Rue de Dele',69007,'Lyon');

insert into Fournisseur values(11,'Top Jouet');
insert into Fournisseur values(12,'Mega fringue');
insert into Fournisseur values(13,'Madame Meuble');
insert into Fournisseur values(14,'Tout le Sport');

insert into Produit values(101,'Soldat qui tire',50.00,11);
insert into Produit values(102,'Cochon qui rit',50.00,11);
insert into Produit values(103,'poupee qui pleure',100,11);
insert into Produit values(104,'jean',250.00,12);
insert into Produit values(105,'Blouson',350.00,12);
insert into Produit values(106,'Chaussures',200.00,12);
insert into Produit values(107,'T-shirt',100.00,12);
insert into Produit values(108,'Table',500.00,13);
insert into Produit values(109,'Chaise',100.00,13);
insert into Produit values(110,'Armoire',1000,13);
insert into Produit values(111,'Lit',5000.00,13);
insert into Produit values(112,'Raquette de tennis',300.00,14);
insert into Produit values(113,'VTT',699.00,14);
insert into Produit values(114,'Ballon',75.00,14);

insert into Commande values(1,110,1,'1999-09-24');
insert into Commande values(1,108,1,'1999-09-24');
insert into Commande values(1,109,4,'1999-09-24');
insert into Commande values(3,101,2,'1999-09-24');
insert into Commande values(3,102,1,'1999-09-24');
insert into Commande values(4,104,3,'1999-09-24');
insert into Commande values(4,105,1,'1999-09-24');
insert into Commande values(4,106,2,'1999-09-24');
insert into Commande values(4,107,5,'1999-09-24');
insert into Commande values(5,114,10,'1999-09-24');
insert into Commande values(6,102,2,'1999-09-24');
insert into Commande values(6,103,5,'1999-09-24');
insert into Commande (numcli,numprod,datec) values(6,114,'1999-09-24');


