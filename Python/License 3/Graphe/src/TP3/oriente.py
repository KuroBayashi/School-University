#!/usr/bin/env python
# -*- coding: utf-8 -*-

import lib.vector as Vector


def cyclicRec(G, i, Visite, cycle):
    Visite[i] = 1
    # print("Début du parcours de", i)

    for y in G[i]:
        if Visite[y] == 0:
            if cyclicRec(G, y, Visite, cycle):
                return True
        else:
            # print("Revisite de", y)
            return True

    Visite[i] = 0
    return False
    # print("Fin du parcours de", i)


def isCyclic(G):
    Visite = Vector.initVect(len(G), 0)

    for i in range(1, len(G)):
        if cyclicRec(G, i, Visite, False):
            return True

    return False


if __name__ == "__main__":
    G1 = [[], [3, 4, 5, 6], [1], [2, 4], [], [], [4]]
    G2 = [[], [4, 5, 6], [1], [2, 4], [], [], [4]]

    print("Le graphes admet-il un cycle ?")
    print(isCyclic(G1))
    print(isCyclic(G2))
