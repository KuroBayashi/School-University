#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sys


## VECT.PY
def initVect(n, a):
    """
    Initialisation d'un vecteur de taille n et de valeur numérique a
    """
    V = []
    for j in range(n):
        V.append(a)
    return V


def initVectList(n):
    """
    Initialisation d'un vecteur à n listes vides
    """
    V = []
    for j in range(n):
        V.append([])
    return V


def initMat(n, a):
    """
    création d'une matrice carrée de taille n initialisée à a
    """
    M = []
    for i in range(n):
        L = []
        for j in range(n):
            L.append(a)
        M.append(L)
    return M


def affMat(M):
    """
    Affichage d'une matrice
    """
    for i in range(len(M)):
        print(M[i])


## TP 7

def arcToListe(n, L):
    P = initVectList(n + 1)
    G = initVectList(n + 1)
    M = initMat(n + 1, 0)

    for i, j, p in L:
        P[j].append(i)
        G[i].append(j)
        M[i][j] = p

    return P, G, M


def Dijkstra(G, M, s):
    File = []
    Dist = initVect(len(G), sys.maxsize)
    Pere = initVect(len(G), 0)

    Dist[0] = 0
    Dist[s] = 0
    Pere[s] = s
    File.append(s)

    while File:
        x = File.pop(0)

        for y in G[x]:
            d = Dist[x] + M[x][y]

            if d < Dist[y]:
                Dist[y] = d
                Pere[y] = x
                File.append(y)

    return Pere, Dist


def Bellman(P, G, M, s):
    File = []
    Dist = initVect(len(G), sys.maxsize)
    Pere = initVect(len(G), 0)
    PS = initVect(len(G), 0)

    Dist[s] = 0
    Pere[s] = s

    for y in range(1, len(G)):
        PS[y] = -1 if y == s else 0

        for z in P[y]:
            if s != z:
                PS[y] += 1
            if PS[y] == 0:
                File.append(y)

    while File:
        y = File.pop(0)

        for x in P[y]:
            if Dist[x] + M[x][y] < Dist[y]:
                Dist[y] = Dist[x] + M[x][y]
                Pere[y] = x
        for z in G[y]:
            PS[z] -= 1

            if PS[z] == 0:
                File.append(z)

    return Pere, Dist


if __name__ == "__main__":
    G7 = [[1, 2, 10], [1, 3, 3], [1, 5, 6], [2, 1, 0], [3, 2, 4], [3, 5, 2], [4, 3, 1], [4, 5, 3], [5, 2, 0], [5, 6, 1],
          [6, 1, 2], [6, 2, 1]]
    G8 = [[1, 2, 1], [1, 3, -2], [2, 4, -2], [3, 2, 1], [3, 4, 5], [3, 5, 4], [5, 6, -1], [6, 4, -5]]

    s = 1

    _, G, M = arcToListe(6, G7)
    print("Dijkstra : ")
    P, D = Dijkstra(G, M, s)
    print("- Pere : ", P)
    print("- Dist : ", D)

    print()

    P, G, M = arcToListe(6, G8)
    print("Bellman : ")
    P, D = Bellman(P, G, M, s)
    print("- Pere : ", P)
    print("- Dist : ", D)

