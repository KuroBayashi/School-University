(* Instructions of the CAM *)

open Miniml;;

type instr =
  PrimInstr of primop
| Cons
| Push
| Swap
| Return
| Quote of value
| Cur of code
| App
| Branch of code * code
(* new for recursive calls *)
| Call of var
| AddDefs of (var * code) list
| RmDefs of int
and value =
  NullV 
| VarV of Miniml.var
| IntV of int
| BoolV of bool
| PairV of value * value
| ClosureV of code * value
and code = instr list
  
type stackelem = Val of value | Cod of code

let execArith m n op = match op with  
  BAadd -> m + n
| BAsub -> m - n
| BAmul -> m * n
| BAdiv -> m / n
| BAmod -> m mod n
;; 

let execComp m n op = match op with  
  BCeq -> m = n
| BCge -> m >= n
| BCgt -> m > n
| BCle -> m <= n
| BClt -> m < n
| BCne -> m != n
;;

let rec exec = function
  (PairV(x, y), PrimInstr(UnOp(Fst))::c, st) -> exec (x, c, st)
| (PairV(x, y), PrimInstr(UnOp(Snd))::c, st) -> exec (y, c, st)
| (PairV(IntV(m), IntV(n)), PrimInstr(BinOp(BArith(op)))::c, st)  -> exec (IntV(execArith m n op), c, st)
| (PairV(IntV(m), IntV(n)), PrimInstr(BinOp(BCompar(op)))::c, st) -> exec (BoolV(execComp m n op), c, st)
| (t, Quote(v)::c, st)       -> exec (v, c, st)
| (x, Cons::c, Val(y)::st)   -> exec (PairV(y, x), c, st)
| (x, Push::c, st)           -> exec (x, c, Val(x)::st)
| (x, Swap::c, Val(y)::st)   -> exec (y, c, Val(x)::st)
| (x, Cur(d)::c, st)         -> exec (ClosureV(d, x), c, st)
| (x, Return::c, Cod(d)::st) -> exec (x, d, st)
| (PairV(ClosureV(cd, y), z), App::c, st)     -> exec (PairV(y, z), cd, Cod(c)::st)
| (BoolV(true),  Branch(t, e)::c, Val(x)::st) -> exec (x, t, Cod(c)::st)
| (BoolV(false), Branch(t, e)::c, Val(x)::st) -> exec (x, e, Cod(c)::st)
| cfg -> cfg
;;


let rec compile = function 
  (env, Bool(b)) -> [Quote(BoolV(b)]
| (env, Int(i))  -> [Quote(IntV(i)]
| (env, Var(v)) -> access v env
| (env, Pair(a, b)) ->  [Push] @ compile (env, a) @ [Swap] @ compile (env, b) @ [Cons]
| 
;;
