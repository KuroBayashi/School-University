(* ************************************ *)
(* TDP 2 : Arbres binaires de recherche *)
(* ************************************ *)

(* -------------------------------------- *)
(* Introduction, le type 'a arbre_binaire *)
(* -------------------------------------- *)

type 'a arbre_binaire = 
	Feuille
  | Noeud of 'a * 'a arbre_binaire * 'a arbre_binaire
;;


let abr1 = 
	Noeud(8, 
		Noeud(3, 
			Noeud(2, Feuille,Feuille),
			Noeud(6, Feuille,Feuille)
		),
		Noeud(19, Feuille,Feuille)
	)
;;

(* ----------------------------------- *)
(* Fonctions de base *)
(* ----------------------------------- *)

(* Question 1 *)
exception PasDeRacine;;

let racine (abr: 'a arbre_binaire) = match abr with
	Noeud(n,_,_) -> n
  | Feuille -> raise PasDeRacine
;;

(* Question 2 *)
let rec recherche (x: 'a) (abr: 'a arbre_binaire) = match abr with
	Noeud(n,_,_) when x = n -> true
  | Noeud(n,l,_) when x < n -> recherche x l
  | Noeud(n,_,r) -> recherche x r
  | Feuille -> false
;;

(* Question 3 *)
let lte (x: int) (y: int) = x <= y;;

let rec rec_trie (root: 'a) (left: 'a arbre_binaire) (right: 'a arbre_binaire) = match left, right with
    Noeud(na,la,ra), Noeud(nb,lb,rb) -> 
  | Noeud(n,l,r), Feuille -> 
  | Feuille, Noeud(n,l,r) -> 
  | Feuille, Feuille -> true
;;

let est_trie (abr: 'a arbre_binaire) = match abr with
	Noeud(n,l,r) -> 
  | Feuille -> true
;;