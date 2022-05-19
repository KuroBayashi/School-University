
(*		TP n°1 bis - Type Automate			*)

(* -------------------------------- *)
(* Types *)
(* -------------------------------- *)
type etat = {
	accept : bool;
	t			 : char -> int
};;

type afd = {
	sigma : char list;
	nQ		: int;
	init	: int;
	e		: int -> etat
};;

(* -------------------------------- *)
(* Fonctions *)
(* -------------------------------- *)
let rec conversion s = 
	if String.length s = 0 then []
	else [s.[0]] @ (conversion (String.sub s 1 (String.length s -1)))
;;

(* Rouge *)
exception InvalidWord;;

let rec accepte_rec (automate: afd) (word: char list) (start: int) = match word with
	[] -> start
	| x::r -> try 
				accepte_rec automate r ((automate.e start).t x)
			  with
				_ -> raise InvalidWord
;;

let accepte (automate: afd) (word: string) =
  try 
	(automate.e (accepte_rec automate (conversion word) automate.init)).accept
  with
	InvalidWord -> false
;;


(* -------------------------------- *)
(* Test *)
(* -------------------------------- *)
(* Automate 1 *)
let a1 = {
	sigma = ['a';'b'];
	nQ = 3;
	init = 1;
	e = function
		1 -> {
			accept = false;
			t = function
				'a'-> 2
				|'b'-> 1
		}
		|2 -> {
			accept = false;
			t = function
				'a'-> 2
				|'b'-> 3
		}
		|3 -> {
			accept = true;
			t = function
				'a'-> 3
				|'b'-> 3
		}
};;

(* Automate 2 *)
let a2 = {
	sigma = ['a';'b'];
	nQ = 3;
	init = 1;
	e = function
		1 -> {
			accept = false;
			t = function
				'a'-> 2
		}
		|2 -> {
			accept = false;
			t = function
				'a'-> 2
				|'b'-> 3
		}
		|3 -> {
			accept = true;
			t = function
				'a'-> 3
				|'b'-> 3
		}
};;

List.map (accepte a1) ["abba"; "bbaaa"; "bbaaba"; "ba"; "ab"; ""];;
List.map (accepte a2) ["abba"; "bbaaa"; "bbaaba"; "ba"; "ab"; ""];;


(* Automate 3 *)
let a3 = {
	sigma = ['a';'b'];
	nQ = 3;
	init = 1;
	e = function
		1 -> {
			accept = false;
			t = function
				'a'-> 1
				|'b'-> 2
		}
		|2 -> {
			accept = true;
			t = function
				'a'-> 3
				|'b'-> 3
		}
		|3 -> {
			accept = false;
			t = function
				'a'-> 3
				|'b'-> 3
		}
};;

List.map (accepte a3) ["abba"; "bbaaa"; "bbaaba"; "ba"; "ab"; ""; "b"; "ab"; "aaab"; "aabb"];;




