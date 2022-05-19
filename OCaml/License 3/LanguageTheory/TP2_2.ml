
(*		TP n°2 - Automate fini déterministe			*)
		
(* -------------------------------- *)
(* Types *)
(* -------------------------------- *)		
type etat = {
	accept : bool;
	t	: char -> int
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
(* Noire *)
let complete (automate: afd) = 
	let state = {accept = false; t = function _ -> 1 + automate.nQ} in 
		let (automate2: afd) = {
			sigma = automate.sigma;
			nQ = 1 + automate.nQ;
			init = automate.init;
			e = function
				x -> try 
						automate.e x
					 with 
						_ -> state.t x
		}
		in automate2
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

	



