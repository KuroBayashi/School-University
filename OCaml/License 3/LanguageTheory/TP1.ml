(* -------------------------------- *)
(* Partie 1 *)
(* -------------------------------- *)
let rec conversion s = 
	if String.length s = 0 then []
	else [s.[0]] @ (conversion (String.sub s 1 (String.length s -1)))
;;

(* -------------------------------- *)
(* Partie 2 *)
(* -------------------------------- *)

(* Question 1 *)
let rec teste_lettre lettre mot = match mot with
	[] -> false
  | a::r -> lettre = a || teste_lettre lettre r
;;

(* Question 2 *)
let rec egalite (mot1, mot2) = match (mot1, mot2) with
	[], [] -> true
  | a::r, b::s -> a = b && egalite (r, s)
  | _, _ -> false
;;

(* Question 3 *)
let rec concaten mot1 mot2 = match mot1 with
	[] -> mot2
  | a::r -> [a] @ (concaten r mot2)
;;

(* -------------------------------- *)
(* Partie 3 *)
(* -------------------------------- *)

(* Question 1 *)
exception InvalidPower of int;;

let rec puissance mot n =
	if n < 0 then raise (InvalidPower n)
	else
		if n = 0 then []
		else mot @ (puissance mot (n-1))
;;

puissance (conversion "abc") (-2);;

(* Question 2 *)
exception NotAPrefixe;;

let rec prefixe_reste ((prefixe: 'a list), (mot: 'a list)) = match prefixe, mot with
	[], _ -> mot
  | a::r, [] -> raise NotAPrefixe
  | a::r, b::s -> if a <> b then raise NotAPrefixe
				  else prefixe_reste (r, s)
;;

prefixe_reste (conversion "Hello", conversion "Hello World!");;

(* Question 3 *)
exception NotAPow of string;;
exception InvalidOrigin of string;;

let rec est_puiss ((origine: 'a list), (mot: 'a list)) = match origine, mot with
	_, [] -> true
  | _, _ -> try 
				let r = prefixe_reste (origine, mot) in
					if r <> mot then est_puiss (origine, r)
					else raise (InvalidOrigin "L'origine ne peut etre vide.")
		    with 
				NotAPrefixe -> raise (NotAPow "Ce n'est pas une puissance.")
;;

est_puiss (conversion "abc", conversion "abcabcabc");;

(* -------------------------------- *)
(* Partie 4 *)
(* -------------------------------- *)

(* Question 1 *)
let rec tranche ((n: int), (m: int), (mot: 'a list)) =
	
;;


