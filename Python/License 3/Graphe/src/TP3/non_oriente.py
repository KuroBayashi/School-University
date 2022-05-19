#!/usr/bin/env python
# -*- coding: utf-8 -*-

import lib.vector as Vector

from src.TP2.parcours_profondeur import profRec


def isConnexe(G):
    """
    Un graphe non orienté est connexe si et seulement si le parcours en profondeur
    à partir du sommet 1 visite tous les sommets
    """
    Visite = Vector.initVect(len(G), 0)
    ordreVisite = []

    profRec(G, 1, Visite, ordreVisite)

    return len(ordreVisite) == len(G) -1


def cyclicRec(G, i, pere, Visite, cycle):
    """
    Effectue un parcours en profondeur du sommet i (dont le père est pere) en
    mettant à jour les variables Visite et cycle. Dès qu’un cycle est détecté,
    il faut arrêter tous les parcours en cours.
    """
    Visite[i] = 1
    # print("Début du parcours de", i)

    for y in G[i]:
        if Visite[y] == 0:
            if cyclicRec(G, y, i, Visite, cycle):
                return True
        else:
            # print("Revisite de", y)
            if y != pere:
                return True

    return False
    # print("Fin du parcours de", i)


def isCyclic(G):
    """
    Initialise les variables et gère le parcours en profondeur généralisé à tout le graphe.
    """
    Visite = Vector.initVect(len(G), 0)

    for i in range(1, len(G)):
        if Visite[i] == 0:
            if cyclicRec(G, i, i, Visite, False):
                return True

    return False


def isArbre(G):
    return isConnexe(G) and not isCyclic(G)


def plusCourtChemin(G, i):
    """
    on utilise un vecteur Dist tel que Dist[y] est la longueur du plus court chemin de x vers y (ou la valeur − 1
    si y n’est pas accessible à partir de x ). Afin de retrouver le chemin, on utilise également un tableau des
    pères tel que Pere[y] est le prédécesseur de y dans le plus court chemin de x vers y.
    """

    Visite = Vector.initVect(len(G), 0)
    Dist = Vector.initVect(len(G), -1)
    Pere = Vector.initVect(len(G), -1)
    File = []

    File.append(i)
    Visite[i] = 1
    Dist[i] = 0
    Pere[i] = i

    while File:
        y = File.pop(0)

        for z in G[y]:
            if Visite[z] == 0:
                Visite[z] = 1
                File.append(z)
                Pere[z] = y
                Dist[z] = Dist[y] +1

    return Dist, Pere


def bipartiRec(G, i, pere, Visite, n):
    """
    Effectue un parcours en profondeur du sommet i (dont le père est pere) en
    mettant à jour les variables Visite et cycle. Dès qu’un cycle est détecté,
    il faut arrêter tous les parcours en cours.
    """
    Visite[i] = n
    # print("Début du parcours de", i)

    for y in G[i]:
        if Visite[y] == 0:
            if bipartiRec(G, y, i, Visite, 1 if n == 2 else 2):
                return True
        else:
            # print("Revisite de", y)
            if y != pere and Visite[y] == Visite[i]:
                return True

    return False
    # print("Fin du parcours de", i)


def biparti(G):
    """
    Un graphe est biparti si et seulement si il n’admet pas de cycle de longueur impaire. L’algorithme de
    recherche de cycle peut donc être adapté en test de bipartisme. Il suffit pour cela de numéroter
    alternativement à l’aide de 1 et 2 les sommets visités. Un cycle de longueur impaire est détecté
    lorsque x revisite un sommet y de même numéro que lui. On utilise pour cela le vecteur de visite qui
    vaudra 0 pour un sommet non visité, et alternativement 1 ou 2 pour les sommets visités. L’ensemble X
    est alors l’ensemble des sommets à 1 et Y l’ensemble des sommets à 2.
    """
    Visite = Vector.initVect(len(G), 0)

    for i in range(1, len(G)):
        if Visite[i] == 0:
            if bipartiRec(G, i, i, Visite, 1):
                return False

    return True, [k for k, v in enumerate(Visite) if v == 1], [k for k, v in enumerate(Visite) if v == 2]


if __name__ == "__main__":
    G1 = [[],[2],[1,3,7],[2],[7],[7],[7],[2,4,5,6]]     # arbre
    G2 = [[],[2,4],[1,3,7],[2],[1,7],[7],[7],[2,4,5,6]] # connexe cyclique
    G3 = [[],[2],[1,3],[2],[7],[7],[7],[4,5,6]]         # pas connexe pas cyclique
    G4 = [[],[2],[1,3],[2],[5,7],[4,7],[7],[4,5,6]]     # pas connexe cyclique
    # Petersen
    Petersen = [[], [2, 5, 6], [1, 3, 7], [2, 4, 8], [3, 5, 9], [1, 4, 10], [1, 8, 9], [2, 9, 10], [3, 6, 10],
                [4, 6, 7], [5, 7, 8]]

    graphe = G3
    sommet = 1

    print("isConnexe : ", isConnexe(graphe))
    print("isCyclic : ", isCyclic(graphe))

    d, p = plusCourtChemin(graphe, sommet)
    print("Dist : ", d, "Pere : ", p)

    data = biparti(graphe)
    if data:
        _, X, Y = data
        print("Biparti : ", X, Y)
    else:
        print("Not Biparti")

