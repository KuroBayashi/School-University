#!/usr/bin/env python
# -*- coding: utf-8 -*-

import lib.vector as Vector


def nbSommets(G):
    """
    Retourne le nombre de sommets du graphe.

    :param G: List - Representation du graphe par liste d'adjacence

    :return: Integer
    """
    return len(G) - 1


def nbArcs(G):
    """
    Retourne le nombre d'arcs du graphe.

    :param G: List - Representation du graphe par liste d'adjacence

    :return: Integer
    """
    return len(list(sum(G, [])))

def ajoutArc(G, i, j):
    """
    Ajoute un arc au graphe.

    :param G: List - Representation du graphe par liste d'adjacence
    :param i: Integer - Premier sommet
    :param j: Integer - Second sommet
    """
    G[i].append(j)


def enleveArc(G, i, j):
    """
    Enleve une arete au graphe.

    :param G: List - Representation du graphe par liste d'adjacence
    :param i: Integer - Premier sommet
    :param j: Integer - Second sommet
    """
    if j in G[i]:
        G[i].remove(j)


def degS(G, i):
    """
    Calculte le degre sortant d'un sommet.

    :param G: List - Representation du graphe par liste d'adjacence
    :param i: Integer - Sommet

    :return: Integer
    """
    return len(G[i])


def degreS(G):
    """
    Calcule le degre sortant de chacun des sommets.

    :param G: List - Representation du graphe par liste d'adjacence

    :return: List - Degre sortant du sommet x à la position x
    """
    return [degS(G, i) for i in range(1, len(G))]


def degE(G, i):
    """
    Calculte le degre entrant d'un sommet.

    :param G: List - Representation du graphe par liste d'adjacence
    :param i: Integer - Sommet

    :return: Integer
    """
    return len(voisinageE(G, i))


def degreE(G):
    """
    Calcule le degre entrant de chacun des sommets.

    :param G: List - Representation du graphe par liste d'adjacence

    :return: List - Degre entrant du sommet x à la position x
    """
    return [degE(G, i) for i in range(1, len(G))]

def voisinageE(G, i):
    """
    Calcule le voisinage entrant d'un sommet.

    :param G: List - Representation du graphe par liste d'adjacence
    :param i: Integer - Sommet

    :return:
    """
    return [j for j in range(1, len(G)) if i in G[j]]

def listeToMatrice(G):
    """
    Generation de la matrice d'adjacence.

    :param G: List - Representation du graphe par liste d'adjacence

    :return: List(List)
    """
    size = len(G)

    M = Vector.initMat(size - 1, 0)
    for i in range(1, size):
        for j in G[i]:
            M[i - 1][j - 1] += 1

    return M


def arcsToListe(n, L):
    """
    Generation du vecteur des listes d'adjacences.

    :param n: Integer - Nombre de sommets
    :param L: List - Liste d'arcs

    :return: List(List)
    """
    G = Vector.initVectList(n + 1)
    for arc in L:
        G[arc[0]].append(arc[1])

    return G


def matToListe(M):
    """
    Generation du vecteur des listes d'adjacences.

    :param M: List(List) - Matrice d'adjacence

    :return: List(List)
    """
    size = len(M)

    G = Vector.initVectList(size + 1)
    for i in range(size):
        for j in range(size):
            for x in range(M[i][j]):
                G[i + 1].append(j + 1)

    return G


if __name__ == "__main__":
    L = [[1, 5], [2, 1], [2, 4], [3, 2], [4, 3], [5, 2], [5, 4]]
    G = [[], [5], [1, 4], [2], [3], [2, 4]]
    M = [[0, 0, 0, 0, 1],
         [1, 0, 0, 1, 0],
         [0, 1, 0, 0, 0],
         [0, 0, 1, 0, 0],
         [0, 1, 0, 1, 0]]

    print("Sommets :", nbSommets(G))
    print("Arcs :", nbArcs(G))
    print()
    ajoutArc(G, 1, 3)
    print("Ajout arc (1,3) :", G)
    enleveArc(G, 1, 3)
    print("Enleve arc (1,3) :", G)
    print()
    print("Degre sortant du sommet 5 :", degS(G, 5))
    print("Vecteur des degres sortant :", degreS(G))
    print()
    print("Degre entrant du sommet 5 :", degE(G, 5))
    print("Vecteur des degres entrant :", degreE(G))
    print()
    print("Voisinage entrant du sommet 3 :", voisinageE(G, 3))
    print()
    print("Liste -> Matrice :", listeToMatrice(G))
    print("Arcs -> Liste :", arcsToListe(5, L))
    print("Matrice -> Liste :", matToListe(M))
