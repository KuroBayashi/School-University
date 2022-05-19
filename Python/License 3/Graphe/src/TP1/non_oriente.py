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


def nbArete(G):
    """
    Retourne le nombre d'aretes du graphe.

    :param G: List - Representation du graphe par liste d'adjacence

    :return: Integer
    """
    size = len(G)
    n = 0

    for i in range(1, size):
        for x in G[i]:
            n += 2 if i == x else 1

    return n // 2


def ajoutArete(G, i, j):
    """
    Ajoute une arete au graphe.

    :param G: List - Representation du graphe par liste d'adjacence
    :param i: Integer - Premier sommet
    :param j: Integer - Second sommet
    """
    G[i].append(j)
    G[j].append(i)


def enleveArete(G, i, j):
    """
    Enleve une arete au graphe.

    :param G: List - Representation du graphe par liste d'adjacence
    :param i: Integer - Premier sommet
    :param j: Integer - Second sommet
    """
    if j in G[i] and i in G[j]:
        G[i].remove(j)
        G[j].remove(i)


def deg(G, i):
    """
    Calculte le degre d'un sommet.

    :param G: List - Representation du graphe par liste d'adjacence
    :param i: Integer - Sommet

    :return: Integer
    """
    return len(G[i])


def degre(G):
    """
    Calcule le degre de chacun des sommets.

    :param G: List - Representation du graphe par liste d'adjacence

    :return: List - Degre du sommet x à la position x
    """
    return [deg(G, i) for i in range(1, len(G))]


def nonOriente(M):
    """
    Verifie qu'une matrice est bien symetrique.

    :param M: List(List) - Representation du graphe par matrice d'adjacence

    :return: Boolean
    """
    size = len(M)
    for i in range(size):
        for j in range(i+1, size):
            if M[i][j] != M[j][i]:
                return False
    return True


def kuratowski(n):
    """
    Generation du vecteur des listes d'adjacences du n-ieme graphe de Kuratowski.

    :param n: Integer - Graphe voulu

    :return: List(List)
    """
    n += 1
    V = Vector.initVectList(n)
    for i in range(1, n):
        for j in range(1, n):
            if i != j:
                V[i].append(j)

    return V


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


def areteToListe(n, L):
    """
    Generation du vecteur des listes d'adjacences.

    :param n: Integer - Nombre de sommets
    :param L: List - Liste d'arete

    :return: List(List)
    """
    G = Vector.initVectList(n + 1)
    for arete in L:
        G[arete[0]].append(arete[1])
        if arete[0] != arete[1]:
            G[arete[1]].append(arete[0])

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
    L = [[1, 2], [1, 5], [1, 5], [2, 3], [2, 4], [2, 4], [3, 3], [3, 4], [4, 5]]
    G = [[], [2, 5, 5], [1, 3, 4, 4], [2, 3, 4], [2, 2, 3, 5], [1, 1, 4]]
    M = [[0, 1, 0, 0, 2],
         [1, 0, 1, 2, 0],
         [0, 1, 1, 1, 0],
         [0, 2, 1, 0, 1],
         [2, 0, 0, 1, 0]]

    print("Sommets :", nbSommets(G))
    print("Aretes :", nbArete(G))
    print()
    ajoutArete(G, 1, 3)
    print("Ajout arete (1,3) :", G)
    enleveArete(G, 1, 3)
    print("Enleve arete (1,3) :", G)
    print()
    print("Degre du sommet 5 :", deg(G, 5))
    print("Vecteur des degres :", degre(G))
    print()
    print("Est non oriente :", nonOriente(M))
    print("Vecteur des listes d'adjacences du 4eme graphe de Kuratowski :\n", kuratowski(4))
    print()
    print("Liste -> Matrice :", listeToMatrice(G))
    print("Arete -> Liste :", areteToListe(5, L))
    print("Matrice -> Liste :", matToListe(M))
