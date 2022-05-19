
(* ---------------- 1. Listes d'associations ---------------------- *)

type 'a option =
	None
	|Some of 'a
;;


(*                  1.1 Par une liste de couples                    *)

let liste1 = [("Max",22);("Nicolas",24);("Lucie",29)] ;;

(*Question 1*)
let rec cherchel (element: 'a) (liste: ('a * 'b) list) = match liste with
	[] -> None
	|(key,value)::r -> if key = element then Some value
				 else cherchel element r
;;


(*Question 2*)
let ajoutel (key: 'a) (value: 'b) (liste: ('a * 'b) list) = (key,value)::liste;;


(*Question 3*)
let rec retirel (element: 'a) (liste: ('a * 'b) list) = match liste with
	[] -> []
	|(key,value)::r -> if key = element then r
					   else (key,value)::(retirel element r)
;;


(*                   1.2 Par une fonction                             *)

type ('a,'b) map = 'a -> 'b option ;;

let map1 = function
"Max" -> Some 22 
| "Nicolas" -> Some 24
| "Pierre" -> Some 29 
| _ -> None ;;


(*Question 1*)
let (emptyf: ('a,'b) map) = function 
	_ -> None
;;


(*Question 2*)
let cherchef (element: 'a) (m: ('a, 'b) map) = m element;;

let ajoutef (key: 'a) (value: 'b) (m: ('a, 'b) map) = 
	let (m2: ('a, 'b) map) = function
		x -> if x = key then Some value
			else m x
	in m2
;;

let retiref (element: 'a) (m: ('a, 'b) map) = 
	let (m2: ('a, 'b) map) = function
		x -> if x = element then None
			 else m x
	in m2
;;

(*Question 3*)
let rec liste_to_map (liste: ('a * 'b) list) = match liste with
	[] -> emptyf
	|(key,value)::r -> ajoutef key value (liste_to_map r)
;;


(* ---------------- 2. Vérification de types ---------------------- *)

(*                  2.1 Les expressions en Caml O'Caramel           *)

type expr = 
	ConstInt of int 
	| ConstBool of bool
	| Var of string
	| Add of expr * expr 
	| Equal of expr * expr 
	| And of expr * expr
;;
	
let expr1 = Add (ConstInt 2, ConstBool true);;   (*2 + true *)
let expr2 = Add (ConstInt 2, Var "x");;          (*2 + x *)
let expr3 =(And(Var "b", Equal(Var "y", Add(Var "x", ConstInt 2))));;   (*b && (y = x+2) *)

(* Question 1 *)
let rec affiche_expr (e: expr) = match e with
	 ConstInt  i -> string_of_int i
	|ConstBool b -> string_of_bool b
	|Var s -> s
	|Add   (x,y) -> "( "^ affiche_expr x ^" + "^ affiche_expr y ^" )"
	|Equal (x,y) -> "( "^ affiche_expr x ^" = "^ affiche_expr y ^" )"
	|And   (x,y) -> "( "^ affiche_expr x ^" && "^ affiche_expr y ^" )"
;;

(*                 2.2 Types et environnement de typage           *)

type tp = 
	Int 
	| Bool ;;
	
let env1 = [("x", Int); ("y", Int); ("b", Bool)] ;;

(*                2.3 La vérification de type (enfin !)           *)

(* Question 1 *)
exception UnknownVariableException;;
exception AddTypeException;;
exception EqualTypeException;;
exception AndTypeException;;

(* A finir *)
let rec verif_tp_expr (env: (string * tp) list) (e: expr) = match expr with
	 ConstInt  i -> Int
	|ConstBool b -> Bool
	|Var s -> let x = cherche s env in
				if x = None then raise UnknownVariableException
				else x
	|Add   (x,y) -> match (verif_tp_expr x, verif_tp_expr y) with
						Int, Int -> Int
						|_,_ -> raise AddTypeException
	|Equal (x,y) -> match (verif_tp_expr x, verif_tp_expr y) with
						Int, Int -> Bool
						Bool, Bool -> Bool
						|_,_ -> raise EqualTypeException
	|And   (x,y) -> match (verif_tp_expr x, verif_tp_expr y) with
						Bool, Bool -> Bool
						|_,_ -> raise EqualTypeException
;;



























	
