(* Bibliothèque sur les listes et les chaînes de caractères en fin de page *)

(* 		Un type pour les AFN 	*)

type etatN = {
	acceptN: bool;
	tN: char -> int list
};;

type afn = {
	sigmaN: char list;		(* l'alphabet *)
	nN: int;				(* Q est l'ensemble {1..N} *)
	initN: int list;		(* les états initiaux *)
	eN : int -> etatN
};;


(*		Lecture d'un mot par un AFN			*)

let an1 = {sigmaN= ['a';'b'] ; nN = 3; initN = [1] ;
			eN = function
			    1 -> {acceptN = false ;
				      tN = function
					       'a'->[2] }
				|2 -> {acceptN = true ;
				      tN = function
					       'a'->[2]
						   |'b'-> [2;3] }
				|3 -> {acceptN = false ;
				      tN = function
					       'a'->[2]
						   |'b'->[3]   }
		};;

(*
	Recupere la liste des etats accessibles par un automate (atm)
	depuis une liste d'etats initiaux (starts)
	et par une transition d'un caractere (c)
*)
let rec get_accessible (atm: afn) (c: char) (starts: int list) = match starts with
	[] -> []
	|n::[] -> (
			try
				(atm.eN n).tN c
			with
				_ -> []
			)
	|n::r -> (
			try
				union ((atm.eN n).tN c) (get_accessible atm c r)
			with
				_ -> get_accessible atm c r
			)
;;

(*
	Applique la recurrence sur l'automate (atm)
	pour un parcours du mot (str) lettre par lettre,
	depuis une liste d'etats initiaux (starts)
	et retourne la liste des etats finaux possibles
*)
let rec accept_rec (atm: afn) (str: string) (starts: int list) =
	try
		if String.length str = 1 then
			get_accessible atm (tetec str) starts
		else
			accept_rec atm (reste str) (get_accessible atm (tetec str) starts)
	with
		_ -> [] ("Mot vide")
;;

(*
	Verfie si au moins un etat acceptant de l'automate (atm)
	est dans la liste (states)
*)
let rec has_valid (atm: afn) (states: int list) = match states with
	[] -> false
	|x::r -> (atm.eN x).acceptN || (has_valid atm r)
;;

(*
	Verifie qu'un mot (str)
	est reconnu par un automate (atm)
*)
let accept (atm: afn) (str: string) =
	has_valid atm (accept_rec atm str atm.initN)
;;


# accept an1 "b";;
- : bool = false
# accept an1 "bbaab";;
- : bool = false
# accept an1 "";;
- : bool = false
# accept an1 "a";;
- : bool = true
# accept an1 "aaaaa";;
- : bool = true
# accept an1 "bbba";;
- : bool = false

(* ******************************************************************* *)
 (* Bibliothèque sur les listes *)
let rec appartient = function
	(a,b::l)-> if a=b then true else appartient(a,l)
	|(_,[])-> false
;;
appartient : 'a * 'a list -> bool = <fun>

let rec union l = function
	(a::l2)-> if appartient(a,l) then union l l2 else a:: (union l l2)
	| []->l
;;
union : 'a list -> 'a list -> 'a list = <fun>

let rec long = function
	(_::l)->1+long(l)
	|[]-> 0
;;

(* Fin de la biliothèque sur les listes *)
(* ******************************************************************* *)
(*				Bibliothèque sur les chaînes de caractères 					 *)


let string_of_char = String.make 1 ;;

let tetec = function
	| "" -> failwith "Erreur : chaine vide"
	| s -> s.[0] ;;
(*val tetec : string -> char = <fun>*)

let tetes = fun s -> string_of_char (tetec(s));;

let reste = function
	| "" -> failwith "Erreur : chaine vide"
	| s -> String.sub s 1  ((String.length s) - 1 ) ;;
(*val reste : string -> string = <fun>*)
