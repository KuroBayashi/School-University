 (* Bibliothèque sur les listes *)

let rec union = fun s1 -> fun s2 ->
  match s1 with
      [] -> s2
    | e::s1' ->
      if List.mem e s2
      then (union s1' s2)
      else e:: (union s1' s2) ;;

(* ------------------------------------------------------------ *)
(* Définition des termes *)

type const =
    BConst of bool
  | IConst of int
  | FConst of string  ;; (* Fonction *)

type term =
    Const of const
  | Var of string
  | Appl of term * term ;;

(* ----------------------------------------------------------- *)
(* Les exemples *)

(* Terme (p (m 3 x) 2) *)
(* à compléter *)
let unifex1 =
Appl (
  Appl (
    Const (FConst "p"),
    Appl (
      Appl (
	Const (FConst "m"),
	Const (IConst 3)
      ),
      Var "x"
    )
  ),
  Const (IConst 2)
)
;;


(* Terme (p (m 3 x) y) *)
(* à compléter *)
let unifex2 =
Appl (
  Appl (
    Const (FConst "p"),
    Appl (
      Appl (
	Const (FConst "m"),
	Const (IConst 3)
      ),
      Var "x"
    )
  ),
  Var "y"
)
;;


(* Terme (p x (m 3 x)) *)
let unifex3 =
Appl (
  Appl (
    Const (FConst "p"),
    Var "x"
  ),
  (
    Appl (
      Appl (
        Const (FConst "m"),
        Const (IConst 3)
      ),
      Var "x"
    )
  )
)
;;

(* Terme (p y (m 3 z)) *)
let unifex4 =
Appl (
  Appl (
    Const (FConst "p"),
    Var "y"
  ),
  (
    Appl (
      Appl (
        Const (FConst "m"),
        Const (IConst 3)
      ),
      Var "z"
    )
  )
)
;;

(* ******************************* *)
(* Partie 1 : Fonctions préalables *)
(* ******************************* *)

(* Question 2 *)
let rec afficher (expr: term) = match expr with
  Appl (l,r) -> "("^ (afficher l) ^" "^ (afficher r) ^")"
  |Var x -> x
  |Const c -> match c with
    BConst b -> string_of_bool b
    |IConst i -> string_of_int i
    |FConst f -> f
;;

# afficher unifex1;;
- : string = "((p ((m 3) x)) 2)"


(* Question 3 *)
let rec fv (expr: term) = match expr with
  Appl (l,r) -> union (fv l) (fv r)
  |Var x -> [x]
  |Const c -> []
;;

# fv unifex2;;
- : string list = ["x"; "y"]


(* ************************************* *)
(* Partie 2 : Unification, Substitutions *)
(* ************************************* *)

type 'a option =
  None
  |Some of 'a
;;

(* Question 1 *)
let rec cherche (element: 'a) (liste: ('a * 'b) list) = match liste with
  [] -> None
  |(key,value)::r -> if key = element then Some value
                     else cherche element r
;;

(* Question 2 *)
let rec applique_substitution (subs: (string * term) list) (expr: term) = match expr with
  Appl (l,r) -> Appl (applique_substitution subs l, applique_substitution subs r)
  |Const c -> Const c
  |Var x -> match (cherche x subs) with
			  None -> Var x
			  |Some v -> v
;;

# applique_substitution [("y", Const (IConst 2))] unifex2;;
- : term = Appl (Appl (Const (FConst "p"), Appl (Appl (Const (FConst "m"), Const (IConst 3)), Var "x")), Const (IConst 2))


(* Question 3 *)
let rec subst_dans_e (e: (term * term) list) (subs: (string * term) list) = match e with
  [] -> []
  |(a,b)::r -> (applique_substitution subs a, applique_substitution subs b)::subst_dans_e r subs
;;


(* Question 4 *)
(* A AMELIORER *)
let rec subst_dans_s (s: ('a * term) list) (subs: (string * term) list) = match s with
  [] -> []
  |(a,b)::r -> (a, applique_substitution subs b)::subst_dans_s r subs
;;


(* Question 5 *)
let rec unif (expr: (term * term) list * (string * term) list) = match expr with
  [],s -> s

  |(Const c, Const d)::e, s -> if c = d then unif (e,s) else failwith "Clash"
  |(Const c, Var x)  ::e, s -> let rep = [(x, Const c)] in unif (subst_dans_e e rep, (subst_dans_s s rep)@rep)
  |(Const _, Appl _) ::_, _ -> failwith "Fail"

  |(Var x, Const c)   ::e, s -> let rep = [(x, Const c)]  in unif (subst_dans_e e rep, (subst_dans_s s rep)@rep)
  |(Var x, Var y)     ::e, s -> let rep = [(x, Var y)]    in unif (subst_dans_e e rep, (subst_dans_s s rep)@rep)
  |(Var x, Appl (a,b))::e, s -> failwith "Check"

  |(Appl _, Const _)::_, _ -> failwith "Fail"
  |(Appl (a,b), Var x)  ::e, s -> failwith "Check"
  |(Appl (a,b), Appl (c,d)) ::e, s -> unif ((a,c)::(b,d)::e, s)
;;

# unif ([(unifex4, unifex3)], []) ;;
- : (string * term) list = [("y", Var "x"); ("z", Var "x")]

# unif ([(unifex1, unifex3)], []) ;;
Exception: Failure "Check".

# unif ([(unifex1, unifex2)], []) ;;
- : (string * term) list = [("x", Var "x"); ("y", Const (IConst 2))]
