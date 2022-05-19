# -*- coding: utf-8 -*-

import lib.vector as Vector
import sys


def ajoutArete(G, i, j):
    G[i].append(j)
    G[j].append(i)


def enleveArete(G, i, j):
    if G[i].count(j) > 0:
        G[i].remove(j)
        G[j].remove(i)


def cyclicRec(G, i, pere, Visite, cycle):
    # print("Sommet",i)
    Visite[i] = 1
    k = 0
    while (k < len(G[i]) and not cycle):  # pour une fois c'est fait proprement...
        j = G[i][k]
        if Visite[j] == 0:
            cycle = cyclicRec(G, j, i, Visite, cycle)
            # print("aprĂ¨s exploration de ",j,cycle)
        else:
            if j != pere:
                # print("Cycle dĂŠtectĂŠ par l'arĂŞte en arriĂ¨re",i,j)
                return True
        # else:
        # print("Je suis ton pĂ¨re!")
        k = k + 1
    return cycle


def isCyclic(G):
    Visite = Vector.initVect(len(G), 0)
    cycle = False
    i = 1
    while (i < len(G) and not cycle):
        if Visite[i] == 0:
            cycle = cyclicRec(G, i, i, Visite, False)
        i = i + 1
    return cycle


def areteToListe(n, L):
    G = Vector.initVectList(n + 1)
    M = Vector.initMat(n + 1, 0)

    for i, j, p in L:
        ajoutArete(G, i, j)
        M[i][j] = p
        M[j][i] = p

    return (G, M)


def Kruskal(n, L):
    L = sorted(L, key=lambda x: x[2])

    T = Vector.initVectList(n + 1)
    poids = 0
    nbArete = 0

    for i, j, k in L:
        ajoutArete(T, i, j)

        if isCyclic(T):
            enleveArete(T, i, j)
        else:
            poids += k
            nbArete += 1

            if (nbArete == n - 1):
                break

    return (T, poids)


def Prim(n, L):
    G, M = areteToListe(n, L)

    x = 1
    Dist = Vector.initVect(n, sys.maxsize)
    Dist[0] = 0
    for i, j, p in L:
        if 1 == i:
            Dist[j - 1] = p

    Min = [1] * n
    nbArete = 0

    print(Dist)


if __name__ == "__main__":
    ListAretes = [[1, 2, 7], [1, 5, 6], [1, 6, 2], [2, 3, 4], [2, 5, 5], [3, 4, 1], [3, 5, 2], [4, 5, 3], [5, 6, 1]]

    print("AreteToListe : ")
    # (G,M)=areteToListe(6,ListAretes)
    # print(G)
    # affMat(M)

    print("Kruskal : ")
    # print(Kruskal(6,ListAretes))

    print("Prim :")
    Prim(6, ListAretes)
