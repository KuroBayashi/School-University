#!/usr/bin/env python
# -*- coding: utf-8 -*-

import lib.vector as Vector
from itertools import count, filterfalse
from copy import deepcopy


def mini(L):
    """
    Détermine le plus petit entier >= 1 qui n’appartient pas à la liste L. On seservira de cette fonction pour déterminer
    la plus petite couleur n’appartenant pas à laliste des couleurs interdites.
    """
    return next(filterfalse(set(L).__contains__, count(1)))


def colorNaive(G):
    """
    Détermine une coloration du graphe G par l’algorithme naïf
    """
    color = Vector.initVect(len(G), 0)  # le vecteur des couleurs initialisé à 0

    for x in range(1, len(G)):
        S = []
        for y in G[x]:
            if color[y] != 0:
                S.append(color[y])
        color[x] = mini(S)

    return color


def noyau(L, G):
    """
    Effectue le calcul du noyau d’un ensemble de sommets, c’est à dire une liste maximale de sommets ne contenant pas
    de sommets adjacents.
    """
    N = []
    LL = deepcopy(L)

    while LL:
        x = LL.pop(0)
        N.append(x)

        for y in G[x]:
            if y in LL:
                LL.remove(y)

    return N


def colorGlouton(G):
    """
    détermine une coloration du graphe G par l’algorithme glouton
    """
    Colors = Vector.initVect(len(G), 0)  # le vecteur des couleurs initialisé à 0
    S = list(range(1, len(G)))  # liste des sommets restant à colorier
    color = 1

    while S:
        N = noyau(S, G)

        for x in N:
            if x in S:
                S.remove(x)
            Colors[x] = color

        color += 1

    return Colors


def colorWP(G):
    """
    On a vu en TD qu’on diminue sensiblement le nombre de couleurs nécessaires en traitant les sommets dans
    l’ordre décroissant des degrés. On peut trier une liste d’objets à l’aide de la fonction Python sorted en donnant
    en paramètre la partie de l’objet sur laquelle porte le tri avec la syntaxe  :
        key = lambda x : "partie de x sur laquelle porte le tri"
    L’option reverse permet d’obtenir un tri dans l’ordre décroissant. Pour trier les sommets dans l’ordre décroissant
    des degrés, on peut donc créer la liste Deg de tous les couples (sommet, degré du sommet) et la trier à l’aide
    de la commande :
        sorted(Deg, key = lambda x : x[1], reverse = True)
    Compléter la fonction colorWP(G) , qui détermine une coloration du graphe G par l’algorithme de Welsh et Powell.
    """
    color = Vector.initVect(len(G), 0)  # le vecteur des couleurs
    color[1] = 1

    # Calcul des degrés
    Deg = []
    for i in range(1, len(G)):
        Deg.append([i, len(G[i])])
    # on trie par degré décroissant
    Deg = sorted(Deg, key=lambda x: x[1], reverse=True)
    # print(Deg)

    for d, _ in Deg:
        S = []
        for y in G[d]:
            if color[y] != 0:
                S.append(color[y])
        color[d] = mini(S)

    return color


if __name__ == "__main__":
    G = [[], [2, 3, 4], [1, 3, 4, 5], [1, 2, 4, 5, 6], [1, 2, 3, 6], [2, 3, 7], [3, 4, 7], [5, 6]]
    Petersen = [[], [2, 5, 6], [1, 3, 7], [2, 4, 8], [3, 5, 9], [1, 4, 10], [1, 8, 9], [2, 9, 10], [3, 6, 10],
                [4, 6, 7], [5, 7, 8]]

    print(mini([1, 4, 2, 6, 9]))

    print("Coloration naïve du graphe de Petersen")
    print(colorNaive(Petersen))
    print("Coloration naïve du graphe G")
    print(colorNaive(G))

    print("Coloration gloutonne du graphe de Petersen")
    print(colorGlouton(Petersen))
    print("Coloration gloutonne du graphe G")
    print(colorGlouton(G))

    print("Coloration de Welsh et Powell du graphe de Petersen")
    print(colorWP(Petersen))
    print("Coloration de Welsh et Powell du graphe G")
    print(colorWP(G))
