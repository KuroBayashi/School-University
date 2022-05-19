\echo [Q1] Liste de tous les clients
SELECT * FROM client;
\echo [Q2] Liste de tous les clients par ordre alphabetique inverse des noms
SELECT * FROM client ORDER BY nom DESC;
\echo [Q3] Designation et prix en Euro des produits
SELECT desi, round(prixuni/6,2) AS "PRIX EURO" FROM produit;
\echo [Q4] Nom et prenom des clients
SELECT nom,prenom FROM client;
\echo [Q5] Nom et prenom des clients qui habitent a Lyon
SELECT nom,prenom FROM client WHERE ville = 'Lyon';
\echo [Q6] Commandes en quantites au moins egale a 3
SELECT * FROM commande WHERE quantite >= 3;
\echo [Q7] Désignation des produits dont le prix est compris entre 50 et 100F
SELECT desi FROM produit WHERE prixuni >= 50 AND prixuni <= 100;
\echo [Q8] Commandes en quantite indeterminee
SELECT * FROM commande WHERE quantite IS NULL;
\echo [Q9] Nom et ville des clients dont la ville contient la chaine ll
SELECT nom, ville FROM client WHERE ville LIKE '%ll%';
\echo [Q10] Prenom des clients dont le nom est Dupont, Durand ou Martin
SELECT prenom FROM client WHERE nom='Dupont' or nom='Durand' or nom='Martin';
\echo [Q11] Moyenne des prix des produits
SELECT AVG(prixuni) FROM produit;
\echo [Q12] Nombre total de commandes
SELECT count(*) FROM commande;
\echo [Q13] Liste des commandes avec le nom des clients
SELECT client.nom,commande.datec,commande.quantite FROM commande INNER JOIN client USING(numcli);
\echo [Q14] Liste des commandes avec le numero et le nom des clients
SELECT client.numcli,client.nom,commande.datec,commande.quantite FROM commande INNER JOIN client USING(numcli);
\echo [Q15] Nom des clients qui ont commande une quantite de 1
SELECT DISTINCT client.nom FROM client INNER JOIN commande USING(numcli) WHERE commande.quantite='1';
\echo [Q16] Quantite totale commandée par chaque client
SELECT numcli, SUM(quantite) FROM commande GROUP BY numcli ORDER BY numcli;
\echo [Q17] Quantite moyenne commandee pour les produits faisant l objet de plus d une commande. 
SELECT numprod, AVG(quantite) FROM commande GROUP BY numprod HAVING COUNT(numprod)>1 ORDER BY numprod;
\echo [Q18] Donner le numero du produit le moins cher.
SELECT numprod FROM produit WHERE prixuni IN (SELECT MIN(prixuni) FROM produit);
\echo [Q19] Nom des clients avec le montant total depense
SELECT client.nom,SUM(commande.quantite*produit.prixuni) AS montant_total FROM client,commande,produit WHERE client.numcli=commande.numcli AND produit.numprod=commande.numprod GROUP BY montant_total;
\echo [Q20] Afficher le meilleur client
SELECT client.nom FROM client WHERE montant_total IN ();