SELECT * FROM client;
SELECT * FROM client ORDER BY nom DESC;
SELECT desi, ROUND(prixuni / 6, 4) AS prix_euro FROM produit;
SELECT nom, prenom FROM client;
SELECT nom, prenom FROM client WHERE ville='Lyon';

SELECT * FROM commande WHERE quantite > 3;
SELECT desi FROM produit WHERE prixuni BETWEEN 50 AND 100;
SELECT * FROM commande WHERE quantite IS NULL;
SELECT nom, ville FROM client WHERE ville LIKE '%ll%';
SELECT prenom FROM client WHERE nom IN ('Dupont', 'Durand', 'Martin');

SELECT AVG(prixuni) FROM produit;
SELECT COUNT(*) FROM commande;
SELECT client.nom, commande.datec, commande.quantite FROM commande INNER JOIN client ON commande.numcli = client.numcli;
SELECT client.numcli, client.nom, commande.datec, commande.quantite FROM commande INNER JOIN client ON commande.numcli = client.numcli;
SELECT DISTINCT client.nom FROM client INNER JOIN commande USING(numcli) WHERE commande.quantite = 1;

SELECT numcli, SUM(quantite) FROM commande GROUP BY numcli ORDER BY numcli;
SELECT numprod, AVG(quantite) FROM commande GROUP BY numprod HAVING COUNT(*) > 1;
