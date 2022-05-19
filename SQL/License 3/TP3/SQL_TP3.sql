-- ----------------------- --
--
--  Base de donnees : TP3
--
-- ----------------------- --

-- Question 1
-- cf : c:/Users/pedago/Desktop/sql/database_structure.sql

-- Question 2
CREATE SEQUENCE seq_departement_id
  INCREMENT BY 1
  MINVALUE 1
  START WITH 1
  OWNED BY Departement.Departement_id
;

INSERT INTO Departement (Departement_ID, Nom_Departement) VALUES
  (NEXTVAL('seq_departement_id'), 'ISIS'),
  (NEXTVAL('seq_departement_id'), 'INFO'),
  (NEXTVAL('seq_departement_id'), 'SHS')
;

-- Question 3
-- cd : C:/Users/pedago/Desktop/sql/database_datas.sql

-- Question 4
CREATE VIEW view_reservation_count_by_enseignant (Nom, Prenom, Nombre_Reservation) AS
  SELECT e.Nom, e.Prenom, COUNT(r.Reservation_ID) AS Nombre_Reservation
  FROM Enseignant e
  LEFT JOIN Reservation r
    ON r.Enseignant_ID = e.Enseignant_ID
  GROUP BY e.Nom, e.Prenom
;

-- Question 5
SELECT Nom, Prenom, Nombre_Reservation
  FROM view_reservation_count_by_enseignant
  ORDER BY Nombre_Reservation DESC
;

-- Question 6
SELECT Nom, Prenom, Nombre_Reservation
  FROM view_reservation_count_by_enseignant
  WHERE Nombre_Reservation = 0
;

-- Question 7
CREATE VIEW view_info_enseignant (Matricule, Departement_ID, Nom, Prenom, Email) AS
  SELECT Enseignant_ID AS Matricule, Departement_ID, Nom, Prenom, Email
  FROM Enseignant
;

-- Question 8
CREATE OR REPLACE RULE "rule_info_enseignant_update" AS
  ON UPDATE TO view_info_enseignant
  DO INSTEAD
    UPDATE Enseignant SET Email = new.Email
    WHERE Nom = old.Nom
;

UPDATE view_info_enseignant
  SET Email = 'elyes.lamine@gmail.com'
  WHERE Nom = 'Lamine'
;

-- Question 9
CREATE SEQUENCE seq_enseignant_id
  INCREMENT BY 1
  MINVALUE 1
  START WITH 10
  OWNED BY Departement.Departement_id
;

CREATE OR REPLACE RULE "rule_info_enseignant_insert" AS
  ON INSERT TO view_info_enseignant
  DO INSTEAD
    INSERT INTO Enseignant (Enseignant_ID, Departement_ID, Nom, Prenom, Email)
    VALUES (NEXTVAL('seq_enseignant_id'), new.Departement_ID, new.Nom, new.Prenom, new.Email)
;

INSERT INTO view_info_enseignant (Departement_ID, Nom, Prenom, Email) VALUES
  (1, 'Torvald', 'Linus', 'linus.torvald@linux.com')
;

-- Question 10
CREATE OR REPLACE FUNCTION GetSalleCapaciteSuperieurA (capacite int)
  RETURNS SETOF Salle AS $$
    DECLARE
      salle Salle%ROWTYPE;
    BEGIN
      FOR salle IN SELECT Batiment, Numero_Salle, Capacite FROM Salle WHERE Capacite > capacite LOOP
        RETURN NEXT salle;
      END LOOP;
      RETURN;
  END $$ LANGUAGE 'plpgsql'
;

-- Question 10 (SQL)
CREATE OR REPLACE FUNCTION GetSalleCapaciteSuperieurA (INT)
  RETURNS SETOF Salle AS $$
    SELECT * FROM Salle WHERE Capacite > $1;
  END $$ LANGUAGE 'sql'
;

-- Question 11
CREATE OR REPLACE FUNCTION GetDepartement_ID (nom VARCHAR(25))
  RETURNS INT AS $$
    DECLARE
      id INT;
    BEGIN
      SELECT INTO id Departement_ID
        FROM Departement
        WHERE Nom_Departement = nom
      ;
      RETURN id;
  END $$ LANGUAGE 'plpgsql'
;

-- Question 11 (SQL)
CREATE OR REPLACE FUNCTION GetDepartement_ID (VARCHAR(25))
  RETURNS INT AS $$
    SELECT Departement_ID
      FROM Departement
      WHERE Nom_Departement = $1
    ;
  END $$ LANGUAGE 'sql'
;

-- Question 12
CREATE OR REPLACE FUNCTION SonDepartement (id INT)
  RETURNS VARCHAR(25) AS $$
    DECLARE
      nom VARCHAR(25);
    BEGIN
      SELECT INTO nom d.Nom_Departement
        FROM Departement d
        INNER JOIN Enseignant e
          ON e.Departement_ID = d.Departement_ID
      ;
      RETURN nom;
  END $$ LANGUAGE 'plpgsql'
;

-- Question 13
CREATE OR REPLACE FUNCTION MoyCapacite ()
  RETURNS NUMERIC AS $$
    DECLARE
      moyenne NUMERIC;
    BEGIN
      SELECT INTO moyenne AVG(Capacite) FROM Salle;
      RETURN moyenne;
  END $$ LANGUAGE 'plpgsql'
;

-- Question 14
SELECT Batiment, Numero_Salle, Capacite
  FROM Salle
  WHERE Capacite > MoyCapacite()
;

-- Question 15
SELECT Batiment, Numero_Salle, Capacite
  FROM Salle
  WHERE Capacite BETWEEN MoyCapacite()*0.85 AND MoyCapacite()*1.15
;

-- Question 16
CREATE TYPE t_collegue AS (nom VARCHAR(25), prenom VARCHAR(25));

CREATE OR REPLACE FUNCTION Collegues(id INT)
  RETURNS SETOF t_collegue AS $$
    DECLARE
      collegue t_collegue%ROWTYPE;
    BEGIN
      FOR collegue IN
        SELECT Nom, Prenom
        FROM Enseignant
        WHERE Enseignant_ID != id AND Departement_ID = (
          SELECT Departement_ID
          FROM Enseignant
          WHERE Enseignant_ID = id
        )
        LOOP
          RETURN NEXT collegue;
      END LOOP;
      RETURN;
  END $$ LANGUAGE 'plpgsql'
;

-- Question 17
CREATE OR REPLACE FUNCTION NumLignes (tbl VARCHAR(255))
  RETURNS INT AS $$
    DECLARE
      nbr INT;
    BEGIN
      EXECUTE 'SELECT COUNT(*) FROM ' || tbl
        INTO nbr
      ;
      RETURN nbr;
  END $$ LANGUAGE 'plpgsql'
;

-- Question 18
CREATE TRIGGER postgresql
  BEFORE INSERT OR UPDATE
  ON Enseignant

;
