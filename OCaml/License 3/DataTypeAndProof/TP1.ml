
(*
	Liste : Elements (multiples) ordonnées (list)
	Ensemble : Elements uniques non-ordonnées (set)
	Multi-ensemble : Elements multiples non-ordonnées (multiset)
*)

(* ----------------------------- *)
(* PARTIE 1 *)
(* ----------------------------- *)

(* Question 1 *)
let rec multiplicite m x = match m with
    [] -> 0
  | a::r -> if a = x then 1 + multiplicite r x
            else multiplicite r x
;;

(* Question 2 *)
let rec enleve m x = match m with
    a::r -> if a = x then r
            else a::(enleve r x)
  | [] -> []
;;

(* Question 3 *)
let list_union m1 m2 =
    let rec union_rec l1 l2 = match l1 with
        a::r -> if multiplicite m1 a > multiplicite l2 a then union_rec r (a::l2)
    			else union_rec r l2
      | [] -> l2
	in
	    union_rec m1 m2
;;

list_union [1;2;3;3;4;5] [1;2;2;3;5]

let list_inter m1 m2 =
    let rec inter_rec l1 l2 = match (l1, l2) with
        (a::r, s) -> if multiplicite m1 a < multiplicite m2 a then a::(inter_rec r s)
                     else inter_rec r s
      | (r, b::s) -> if multiplicite m2 b <= multiplicite m1 b then b::(inter_rec r)
                     else inter_rec r s
      | _ -> []
    in
        inter_rec m1 m2
;;

list_inter [0;0;1;2] [0;2;2;2]

let list_diff m1 m2 =
    let rec diff_rec l1 l2 = match (l1, l2) with
        (a::r, s) -> if multiplicite l1 a > multiplicite m2 a then a::(diff_rec r s)
                     else diff_rec r s
      | _ -> []
    in
        diff_rec m1 m2
;;

list_diff [0;0;1;2] [0;2;2;2]

(* Question 4 *)
let rec list_insert m x = match m with
    a::r -> if a < x then a::(list_insert r x)
            else x::a::r
  | _ -> x::[]
;;

(* Question 5 *)
let rec list_sort m = match m with
    a::r -> list_insert (list_sort r) a
  | _ -> []
;;

(* Question 6*)
let list_equals m1 m2 = (list_sort m1) = (list_sort m2);;

(* ----------------------------- *)
(* PARTIE 2 *)
(* ----------------------------- *)
type 'a multiset = 'a -> int;;

(* Question 1 *)
let (ms_empty : 'a multiset) = function
    _ -> 0
;;

(* Question 2 *)
let ms_add (ms : 'a multiset) (x : 'a) =
	let (ms2 : 'a multiset) = function 
		y -> if y = x then ms(x) + 1 
			 else ms(y)
	in ms2
;;

let test = ms_add (ms_add ms_empty 2) 5;;

(* Question 3 *)
let rec multi_of_list (l : 'a list) = match l with
	[]   -> ms_empty
  | x::r -> ms_add (multi_of_list r) x 
;;

let test = multi_of_list [1;2;3;3;2;4];;

(* Question 4 *)
let ms_remove (ms : 'a multiset) (x : 'a) = 
	let (ms2 : 'a multiset) = function
		y -> if y = x then max (ms(x) - 1) 0
			 else ms(y)
	in ms2
;;

(* Question 5 *)
let ms_remove_all (ms : 'a multiset) (x : 'a) = 
	let (ms2 : 'a multiset) = function
		y -> if y = x then 0
			 else ms(y)
	in ms2
;;

(* Question 6 *)
let ms_sum (ms1 : 'a multiset) (ms2 : 'a multiset) = 
	let (ms3 : 'a multiset) = function
		y -> ms1(y) + ms2(y)
	in ms3
;;

let ms_union (ms1 : 'a multiset) (ms2 : 'a multiset) = 
	let (ms3 : 'a multiset) = function
		y -> max (ms1(y)) (ms2(y))
	in ms3
;;

let ms_inter (ms1 : 'a multiset) (ms2 : 'a multiset) = 
	let (ms3 : 'a multiset) = function
		y -> min (ms1(y)) (ms2(y))
	in ms3
;;

let ms_diff (ms1 : 'a multiset) (ms2 : 'a multiset) = 
	let (ms3 : 'a multiset) = function
		y -> max ((ms1(y)) - (ms2(y))) 0
	in ms3
;;



(* ----------------------------- *)
(* PARTIE 3 *)
(* ----------------------------- *)
type multiset_paire = {Max: int; Content: int multiset};;

(* Question 1 *)
let (mp_empty : multiset_paire) = {Max = 0; Content = ms_empty};;

let mp_add (mp : multiset_paire) (x : int) = match mp with
	{}
	
	in mp2
;;

(* Question 2 *)

(* Question 3 *)

(* Question 4 *)
