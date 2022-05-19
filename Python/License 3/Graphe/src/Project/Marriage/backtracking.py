# -*- coding: utf-8 -*-

#
#   Algorithm Backtracking
#
def marryUs(M):
    ListeCouples = []   # Liste qui contiendra toutes les combinaisons possibles
                        # Une combinaison etant de la forme [0, 1, 3, 4, 5, 2], signifie que :
                        #   homme 1 est avec femme 1
                        #   homme 2 est avec femme 3
                        #   homme 3 est avec femme 4
                        #   ...

    def recursive(M, i, Couples, ListeCouples):
        if i == len(M):
            ListeCouples.append(Couples[:])
        else:
            for f in M[i]:
                if f not in Couples:
                    Couples[i] = f
                    recursive(M, i+1, Couples, ListeCouples)
                    Couples[i] = 0

    recursive(M, 1, [0] * len(M), ListeCouples)

    return ListeCouples


if __name__ == "__main__":
    # Liste des preferences (ici pour les hommes)
    #
    M = [
        [],
        [1, 2],     # Homme 1 (Femme 1 > Femme 2)
        [1, 3, 4],  # Homme 2 (Femme 1 > Femme 3 > Femme 4)
        [4, 5],     #   ...
        [3, 4],     #   ...
        [2, 5],     #   ...
    ]

    print("Marriage by backtracking :")
    ListeCouples = marryUs(M)
    for Couples in ListeCouples:
        print(Couples)