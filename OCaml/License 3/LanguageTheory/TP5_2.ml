(* *********************************** *)
(* Bibliothèque sur les listes         *)  
(* *********************************** *)
(* *********************************** *)
(* Piste Noire                         *)
(* *********************************** *)
let rec appartient = function 
	(a,b::l) -> if a=b then true else appartient(a,l)
	|(_,[])  -> false
;;
(*
	appartient : 'a * 'a list -> bool = <fun>
*)

let rec union l = function 
	(a::l2) -> if appartient(a,l) then union l l2 else a:: (union l l2)
	| []    -> l;;
(*
	union : 'a list -> 'a list -> 'a list = <fun>
*)

let rec enleve a = function
	x::q -> if x = a then q else x::(enleve a q)
	| [] -> []
;;

let rec intersection l1 = function
	| [] 	-> []
	| a::l2 -> if appartient(a,l1) then a::(intersection (enleve a l1) l2) else intersection l1 l2
;;

let rec long = function
	(_::l)->1+long(l)
	|[]-> 0
;;						

(* *********************************** *)
(* RAPPELS                             *)
(* *********************************** *)
(* Représentation des automates non-déterministes *)
type etatN = {
	acceptN : bool; 
	tN : char -> int list
};;
		
type afn = {
	sigmaN : char list;		(* l'alphabet *)
	nN : int;				(* Q est l'ensemble {1..N} *)
	initN : int list;		(* les états initiaux *)
	eN : int -> etatN
};;
			
(* Fonction transitN *)
exception  PasTransition ;;

let transitN = fun (aut, i, c) ->
	try 
		(aut.eN(i)).tN(c) 
	with 
		Match_failure _-> raise PasTransition
;;
	
(* Automate exemple *)
let an1  = {
	sigmaN= ['a';'b']; 
	nN = 6; 
	initN = [1]; 
	eN = function	
		1 -> {
			acceptN = false ;
			tN = function 
				'a'->[3]
		}
		|2 -> {
			acceptN = true ;
			tN = function 
				'a'->[2] 
				|'b'-> [1]
		}		   
		|3 -> {
			acceptN = true ;
			tN = function 
				'a'->[4]
				|'b'->[5]
		}	
		|4 -> {
			acceptN = true ;
			tN = function
				'a' -> [3]
		}
		|5 -> {
			acceptN = false ;
			tN = function 
				'a' -> [5]
				|'b' -> [6]
		}
		|6 -> {
			acceptN = false ;
			tN = function 
				'a' -> [5]
				|'b' -> [6]
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

(* Alphabet augmenté *)
let sigmaAug = ['a';'b';'_'];;

(* *********************************** *)
(* Déterminer les états accessibles    *)
(* *********************************** *)
let rec getTransit (atm: afn) (start: int) (sigma: char list) = match sigma with
	[] 	  -> []
	|a::r -> try
				union ((atm.eN start).tN a) (getTransit atm start r)
			with
				_ -> getTransit atm start r
;;

let rec recAccessibles (atm: afn) (neighbors: int list) (counter: int) = match neighbors with
	[]    -> []
	|a::r -> if counter = atm.nN then
				[]
			 else
			 	union (union [a] (recAccessibles atm (getTransit atm a atm.sigmaN) (counter+1))) (recAccessibles atm r counter)
;;

let rec recEtatsAccessibles (atm: afn) (init: int list) = match init with
	[]    -> []
	|a::r -> union (recAccessibles atm (getTransit atm a atm.sigmaN) 0) (recEtatsAccessibles atm r)
;; 

let etatsAccessibles (atm: afn) = 
	union (atm.initN) (recEtatsAccessibles atm atm.initN)
;;

(* *********************************** *)
(* Construction de l'automate inverse, 
  recherche des sommets co-accessibles *)
(* *********************************** *)

let rec recTNInverse (atm: afn) (s: int) (c: char) (i: int) = 
	try 
		(let target = (atm.eN i).tN c in 
			if List.mem s target then
				if i < atm.nN then
					i::(recTNInverse atm s c (i+1))
				else
					[i]
			else
				[]
		)
	with
		_ -> if i < atm.nN then
				recTNInverse atm s c (i+1)
			 else
				[]
;;

let tNInverse (atm: afn) (s: int) = {
	acceptN = (atm.eN s).acceptN;
	tN = function 
		c -> let transitions = (recTNInverse atm s c 1) in
				if transitions = [] then
					raise PasTransition
				else
					transitions
};;

let autoInverse (atm: afn) = {
	sigmaN = atm.sigmaN;
	nN = atm.nN;
	initN = atm.initN;
	eN = function
		s -> (tNInverse atm s)
};;










