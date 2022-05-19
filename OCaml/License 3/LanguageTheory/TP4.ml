(* *********************************** *)
(* *********************************** *)
(*            PISTE ROUGE              *)
(* *********************************** *)
(* Remarque : Utilisation de '_' a la 
   place 'é' pour les epsilons 
   transitions. 					   *)
(* *********************************** *)


(* *********************************** *)
(* Un type pour les AFN                *)
(* *********************************** *)
type etatN = {
	acceptN: bool; 
	tN: char -> int list
};;

type afn = {
	sigmaN: char list;		(* l'alphabet *)
	nN: int;				(* Q est l'ensemble {1..N} *)
	initN: int list;		(* les états initiaux *)
	eN: int -> etatN
};;


(* Les exemples *)
let an1  = {
	sigmaN= ['a';'b']; 
	nN = 4; 
	initN = [1];
	eN = function
		1 -> {
			acceptN = false;
			tN = function
				'_'->[2] 
		}
		|2 -> {
			acceptN = false;
			tN = function
				'_'->[1;3] 
		}
		|3 -> {
			acceptN = true;
			tN = fun
				'_'->[1;4]
		}
		|4 -> {
			acceptN = false;
			tN = function
				'_'->[1]
		}
};;

let an2  = {
	sigmaN= ['a';'b']; 
	nN = 4; 
	initN = [1];
	eN = function
		1 -> {
			acceptN = false;
			tN = function
				'a' -> [2] 
		}
		|2 -> {
			acceptN = false;
			tN = function
				'_' -> [3]	
		}
		|3 -> {
			acceptN = true;
			tN = function
				'b' -> [2]
				| '_' -> [4]
		}
		|4 -> {
			acceptN = false;
			tN = function
				'b' -> [4]
				| 'a' -> [2]
		}
};;


(* PasTransition *)
exception PasTransition;;

let transitN = fun (aut, i, c) ->
	try 
		(aut.eN i).tN c
	with 
		Match_failure _ -> raise PasTransition
;;
(*
	# val transitN : afn * int * char -> int list = <fun>
*)

(* *********************************** *)
(* Calcul des clotures                 *)
(* *********************************** *)
let getTransit (atm: afn) (start: int) (c: char) =
	try
		transitN (atm, start, c)
	with
		_ -> []
;;
(*
	# val getTransit : afn -> int -> char -> int list = <fun>
*)

let rec recCloture (atm: afn) (neighbors: int list) (counter: int) = match neighbors with
	[] -> []
	|a::r -> if counter = atm.nN then
				[]
			 else
			 	union (union [a] (recCloture atm (getTransit atm a '_') (counter+1))) (recCloture atm r counter)
;;
(*
	# val recCloture : afn -> int list -> int -> int list = <fun>
*)

let cloture (atm: afn) (start: int) =
   union [start] (recCloture atm (getTransit atm start '_') 0)
;;
(*
	# val cloture : afn -> int -> int list = <fun>

	# cloture an1 2;;
	- : int list = [4; 3; 1; 2]
	# cloture an2 1;;
	- : int list = [1]
	# cloture an2 2;;
	- : int list = [4; 3; 2]
	# cloture an2 3;;
	- : int list = [4; 3]
*)

(* *********************************** *)
(* Transitions etendues                *)
(* *********************************** *)
let rec recEtend (atm: afn) (cloture: int list) (c: char) = match cloture with
	[] -> []
	|a::r -> union (getTransit atm a c) (recEtend atm r c)
;;
(*
	# val recEtend : afn -> int list -> char -> int list = <fun> 
*)

let etend (atm: afn) (start: int) (c: char) = 
	recEtend atm (cloture atm start) c
;;
(* 
	# val etend : afn -> int -> char -> int list = <fun>

	# etend an2 1 'b';;
	- : int list = []
	# etend an2 2 'b';;
	- : int list = [2; 4]
*)

(* *********************************** *)
(* Etats acceptants                    *)
(* *********************************** *)
let rec recEtat_acceptant (atm: afn) (cloture: int list) = match cloture with
	[] -> false
	|a::r ->  ((atm.eN a).acceptN) || (recEtat_acceptant atm r)
;;
(* 
	# val recEtat_acceptant : afn -> int list -> bool = <fun> 
*)

let etat_acceptant (atm: afn) (q: int) = 
	recEtat_acceptant atm (cloture atm q)
;;
(* 
	# val etat_acceptant : afn -> int -> bool = <fun>

	# etat_acceptant an2 1;;
	- : bool = false
	# etat_acceptant an2 2;;
	- : bool = true
*)

(* *********************************** *)
(* Construction de l'AFN equivalent    *)
(* *********************************** *)
(* Question 1 *)
let elimEpsAux (data: (int * afn)) = match data with 
	(q, atm) -> {
		acceptN = etat_acceptant atm q;
		tN = function
			a -> let accessible = etend atm q a in 
				     if a = '_' || accessible = [] then raise PasTransition
				     else accessible
	}
;;
(*
	# val elimEpsAux : int * afn -> etatN = <fun>
*)


(* Question 2 *)
let elimineEps (atm: afn) = {
	sigmaN = atm.sigmaN;
	nN = atm.nN;
	initN = atm.initN;
	eN = function 
		     q -> elimEpsAux (q, atm)
};;
(*
	# val elimineEps : afn -> afn = <fun>
	
	# let an3 = elimineEps an2;;
	val an3 : afn = {sigmaN = ['a'; 'b']; nN = 4; initN = [1]; eN = <fun>}
	
	# (an3.eN 1).tN 'a';;
	- : int list = [2]
	# (an3.eN 1).tN 'b';;
	Exception: PasTransition.
	# (an3.eN 1).tN '_';;
	Exception: PasTransition.
	
	# (an3.eN 3).tN 'a';;
	- : int list = [2]
	# (an3.eN 3).tN 'b';;
	- : int list = [2; 4]
	# (an3.eN 3).tN 'c';;
	Exception: PasTransition.
	# (an3.eN 3).tN '_';;
	Exception: PasTransition.
*)


(* ******************************************************************* *)
(* Bibliothèque sur les listes *)
(* ******************************************************************* *)
let rec appartient = function
	(a, b::l) -> a = b || appartient(a,l)
	|(_, []) -> false
;;
(* 
	# appartient : 'a * 'a list -> bool = <fun>
*)

let rec union l = function
	(a::l2) -> if appartient(a,l) then 
				   union l l2 
			   else a:: (union l l2)
	|[] -> l
;;
(* 
	# union : 'a list -> 'a list -> 'a list = <fun> 
*)

let rec long = function
	(_::l) -> 1+long(l)
	|[] -> 0
;;
(* 
	# long : 'a list -> int = <fun> 
*)

(* ******************************************************************* *)
(* Bibliothèque sur les chaînes de caractères *)
(* ******************************************************************* *)
let string_of_char = String.make 1 ;;

let tetec = function
	"" -> failwith "Erreur : chaine vide"
	| s -> s.[0]
;;
(* # tetec : string -> char = <fun>*)

let tetes = fun s -> string_of_char (tetec(s));;

let reste = function
	"" -> failwith "Erreur : chaine vide"
	| s -> String.sub s 1  ((String.length s) - 1)
;;
(*val reste : string -> string = <fun>*)
