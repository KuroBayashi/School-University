

(* ----------------------- Mise en place -------------------- *)

(* Expressions du langage *)
type expr = 
    Const of string
  | Var of string
  | Func of string * expr
  | App of expr * expr
;;

(* Exemples *)
(* à compléter *) 
let t1 = Func("f", Func("x", App(Var "f", Var "x")));;

(*à compléter *)
let t2 = Func("x", App(Var "f", App(Var "f", Var "x")));;

let t3 = Func("f", App(Var "f",  App(Var "f", Const "ic1")));;

let t4 = Func("f", App(Var "f",  App(Var "f", Const "bc1")));;

let t5 = Func("f", Func("g", Func("h",(Func ("a",  
    App(Var "h", (App(App (Var "f", App(Var "g", Var "h")), (App(Var "h", Var "a")))))))))) ;;

(* afficherExp *)
let rec afficheExp (e: expr) = match e with
	Const c -> c
	|Var x -> x
	|App (a, b) -> "("^ afficheExp a ^ " " ^ afficheExp b ^")"
	|Func (f, a) -> "fun "^ f ^" -> "^ afficheExp a
;;

# afficheExp t1;;
- : string = "fun f -> fun x -> (f x)"

(* Type pour les types *)
type tp = 
	ConstT of string
	| VarT of string  
	| FunT of tp * tp ;;
	
	
(* afficheType *)
let rec afficheType (t: tp) = match t with
	ConstT c -> c
	|VarT x -> x
	|FunT (a, b) -> "("^ afficheType a ^ " -> " ^ afficheType b ^")"
;;

# afficheType (FunT (VarT "f", VarT "x"));;
- : string = "(f -> x)"


(* Environnement *)
type envt = (string * tp) list ;;

(* Listes d'associations *)
type 'a option =
	None
	| Some of 'a ;;
	
let rec cherche x = function
	| [] -> None
	| (a,b) :: q -> if x = a then Some b else cherche x q ;;
	

(* Typer les variables et les constantes *)
let tpVariable (env: envt) (x: string) = match (cherche x env) with
	None -> failwith "Unknown Variable"
	|Some x -> x
;;

# tpVariable env1 "v";;
- : tp = ConstT "int"

let tpConstante (value: string) = match value with
	"ic1" -> ConstT "Int"
	|"ic2" -> ConstT "Int"
	|"bc1" -> ConstT "Bool"
	|"bc2" -> ConstT "Bool"
	|_ -> failwith "Unknown Constante Type"
;;

# tpConstante "ic1";;
- : tp = ConstT "Int"
# tpConstante "bc1";;
- : tp = ConstT "Bool"

(* Créer de nouvelles variables de types *)
let nouvelleVarTp (n: int) (opt: string option) = match opt with
	None -> ("a" ^ string_of_int n, n+1)
	|Some v -> (v ^ string_of_int n, n+1)
;;

# nouvelleVarTp 3 None;;
- : string * int = ("a3", 4)
# nouvelleVarTp 3 (Some "v");;
- : string * int = ("v3", 4)

(* ----------------- Inférence de types ---------------- *)

(* Génération des contraintes *)
let rec genereContraintes (counter: int) (env: envt) (e: expr) = match e with
	Const c -> (tpConstante c, [], counter)
  | Var x -> (tpVariable env x, [], counter)
  
  | Func (f, a) -> let (newVarTp, newVarCounter) = nouvelleVarTp counter None in 
						let (aTp, aContraintes, aCounter) = genereContraintes newVarCounter ((f, VarT newVarTp)::env) a in
							(FunT (VarT newVarTp, aTp), aContraintes, aCounter)
							
  | App (a, b) -> let (aTp, aContraintes, aCounter) = genereContraintes counter env a in
						let (bTp, bContraintes, bCounter) = genereContraintes aCounter env b in
							let (newVarTp, newVarCounter) = nouvelleVarTp bCounter None in
								(VarT newVarTp, ((aTp, FunT (bTp, VarT newVarTp))::(aContraintes @ bContraintes)), newVarCounter)
;;

# genereContraintes 0 [] t4;;
- : tp * (tp * tp) list * int =
(FunT (VarT "a0", VarT "a2"),
 [(VarT "a0", FunT (VarT "a1", VarT "a2"));
  (VarT "a0", FunT (ConstT "Bool", VarT "a1"))],
 3)
 
(* Unification *)
let rec union = fun s1 -> fun s2 ->
  match s1 with
      [] -> s2
    | e::s1' -> 
      if List.mem e s2 
      then (union s1' s2)
      else e:: (union s1' s2) ;;

let rec fv = function
  | ConstT c -> []
  | VarT v -> [v]
  | FunT (t1, t2) -> union (fv t1) (fv t2) ;;

let rec applique_substitution t li = match t with
    | ConstT c -> ConstT c
    | VarT v -> 
	(match cherche v li with
	     None -> VarT v
	   | Some trm' -> trm')
    | FunT (t1, t2) -> FunT (applique_substitution t1 li, applique_substitution t2 li) ;;

let comp_subst sbst' sbst = 
  (List.map (fun (v, t) -> (v, applique_substitution t sbst')) sbst) @ sbst' ;;

exception UnifError of string ;;

let rec unif = function
    ([], sigma) -> sigma
  | ((ConstT c1, ConstT c2) :: e, sigma) -> if c1 = c2 then unif (e, sigma) 
													  else raise (UnifError("clash"))
  | ((VarT x1, VarT x2) :: e, sigma) -> if x1 = x2 then unif (e, sigma) 
												   else let sigma' = [(x1, VarT x2)] in 
														let f = fun (s1,s2) -> (applique_substitution s1 sigma', applique_substitution s2 sigma') in
														let e' = List.map f e in 
													unif (e', comp_subst sigma' sigma)
													
  | ((FunT (s1, s2), FunT (t1, t2)):: e, sigma) ->  unif ((s1, t1) :: (s2, t2) :: e, sigma)
  
  | ((VarT x, t) :: e, sigma) ->  if List.mem x (fv t) then raise (UnifError("check"))
													   else let sigma' = [(x, t)] in 
															let f = fun (s1,s2) -> (applique_substitution s1 sigma', applique_substitution s2 sigma') in
															let e' = List.map f e in
													unif (e', comp_subst sigma' sigma)
  | ((t, VarT x) :: e, sigma) -> unif ((VarT x, t) :: e, sigma)
  | ((_, _) :: e, sigma) -> raise (UnifError("fail")) ;;

  
(* Inférence de types *)
let infereType (env: envt) (e: expr) = 
	let (aTp, aContraintes, aCounter) = genereContraintes 0 env e in
		applique_substitution aTp (unif(aContraintes, []))
;;

# afficheType(infereType [] t4) ;;
- : string = "((Bool -> Bool) -> Bool)"
# afficheType(infereType [] t5) ;;
- : string = "((a4 -> (a6 -> a3)) -> (((a3 -> a6) -> a4) -> ((a3 -> a6) -> (a3 -> a6))))"








	
