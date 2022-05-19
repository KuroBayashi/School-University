(* 
 *	Partie 1 : Evaluation stricte et paresseuse
 *)
(* 1.1 - Evaluation de termes *)

(* VOIR COURS *)

(* 1.2 - Comparaison des strategies en Caml *)
let rec take (n: int) (liste: 'a list) = match liste with
	[] -> []
	|a::r -> if n > 0 then a::(take (n-1) r)
			 else []
;;

let rec from (k: int) = 
	k::(from (k+1))
;;

(* 
 *	Partie 2 : Sequences infinies
 *)

(* 2.1 - Fonctions sur des sequences *)

type 'a seq = 
	Nil
	|Cons of 'a * (unit -> 'a seq)
;;

let rec fromq (n: int) = 
	Cons (n, fun() -> fromq(n+1))
;;

let rec takeq (n: int) (s: 'a seq) = match s with
	Nil -> []
	|Cons(k, next) -> if n = 0 then []
					  else k::(takeq (n-1) (next()))
;;

(* Question 1 *)
let rec mapq (f) (s: int seq) = match s with
	Nil -> Nil
	|Cons(k, next) -> Cons((f k), fun() -> mapq f (next()))
;; 

(* Question 2 *)
let rec filterq (f) (s: int seq) = match s with
	Nil -> Nil
	|Cons(k, next) -> if (f k) then Cons(k, fun() -> filterq f (next()))
					  else filterq f (next())
;;

(* Question 3 *)
let rec sieve (s: int seq) = match s with
	Nil -> Nil
	|Cons(k, next) -> Cons(k, fun() -> (filterq (fun x -> x mod k <> 0) (sieve (next()))))
;;

let rec primes = 
	sieve (fromq 2)
;;

(* 2.2 - Recherche de chemins dans un graphe *)

type ('a, 'b) graph = 
	('a * 'a * 'b) list
;;

type ('a, 'b) state = 
	('a * 'a list * 'b)
;;

let g = [("a", "b", 2); ("a", "c", 4); ("b", "c", 1);("d", "b", 3); ("b", "d", 3); ("c", "d", 1)];;

(* Question 1 *)
let sol_state (sol: ('a, 'b) state) = match sol with
	(target, last::_, _) -> target = last
;;

(* Question 2 *)
let get_neighbors (graphe: ('a, 'b) graph) ((target, path, cost): ('a, 'b) state) =
	List.filter (fun (a, b, weight) -> (a = (List.hd path) && not (List.mem b path))) graphe
;;

let rec next_states (graphe: ('a, 'b) graph) (etat: ('a, int) state) = 
	let add (target, path, cost) (a, b, weight) = (target, b::path, cost+weight)
		in List.map (add etat) (get_neighbors graphe etat)
;;

(* Question 3 *)
let init_state (start) (target) = (target, [start], 0);;

(* Question 4 *)
let depthfirst (next) (sol) (x) = 
	let rec dfs = function
		[] -> Nil
		|y::ys -> 
			if sol y then
				Cons(y, fun () -> dfs (next y @ ys))
			else dfs (next y @ ys)
	in dfs [x]
;;

let paths_add (graphe: ('a, 'b) graph) (start) (target) =
	depthfirst (next_states graphe) (sol_state) (init_state start target)
;;

(* Question 5 *)
let cf_max (a) (b) = max a b;;
let cf_sum (a) (b) = a + b;;

let rec next_states (graphe: ('a, 'b) graph) (cf) (etat: ('a, int) state) = 
	let add (target, path, cost) (a, b, weight) = (target, b::path, (cf cost weight))
		in List.map (add etat) (get_neighbors graphe etat)
;;

let paths_add (graphe: ('a, 'b) graph) (cf) (start) (target) =
	depthfirst (next_states graphe cf) (sol_state) (init_state start target)
;;

(* 2.3 - Probleme des n reines *)
(* Question 1 *)
let rec print_line (width: int) =
	if width = 0 then 
		print_string "---\n"
	else
		print_line "--" ^ print_line (width-1)
;;

let rec print_row (width: int) (n: int) (board: int list) = 
	if width = 0 then
		"|"
	else (
		print_string "|";
		
	)
;;

let print_board (n: int) (board: int list) = 
	print_string 
;;










