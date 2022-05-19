#!/usr/bin/env python
# -*- coding: utf-8 -*-

import lib.vector as Vector


def profRec(G, i, Visite, ordreVisite):
    """
    Fonction auxiliaire récursive qui provoque un parcours en profondeur du graphe à partir du sommet i
    Cette fonction ne retourne aucune résultat et se contente de mettre à jour les paramètres Visite et ordreVisite
    """
    Visite[i] = 1
    # print("Début du parcours de", i)

    ordreVisite.append(i)  # Première visite

    for y in G[i]:
        if Visite[y] == 0:
            profRec(G, y, Visite, ordreVisite)
        else:
            # print("Revisite de", y)
            pass

    # ordreVisite.append(i)  # Dernière visite

    # print("Fin du parcours de", i)


def profond(G, i):
    """
    Effectuant le parcours en profondeur du graphe G à partir du sommet i
    Fonction d'appel, initialise les variables puis appel récursif
    """
    # print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -")
    # print("Parcours en profondeur de", G, "à partir du sommet", i, ":")

    Visite = Vector.initVect(len(G), 0)
    ordreVisite = []

    profRec(G, i, Visite, ordreVisite)

    return ordreVisite


def profondG(G):
    """
    Effectue le parcours en profondeur généralisé à tout le graphe G
    """
    # print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -")
    # print("Parcours en profondeur généralisé de", G, ":")

    Visite = Vector.initVect(len(G), 0)
    ordreVisite = []

    for i in range(1, len(G)):
        if Visite[i] == 0:
            ordreSousVisite = []
            profRec(G, i, Visite, ordreSousVisite)
            ordreVisite.append(ordreSousVisite)

    return ordreVisite


if __name__ == "__main__":
    # 3 graphes orientés :
    G1 = [[], [5], [1, 4], [2], [3], [2, 4]]
    G2 = [[], [5], [1, 4, 5], [2, 4], [], [4]]
    G3 = [[], [3, 5, 6], [1], [2, 4], [], [], [4]]

    # et le graphe de Petersen
    Petersen = [[], [2, 5, 6], [1, 3, 7], [2, 4, 8], [3, 5, 9], [1, 4, 10], [1, 8, 9], [2, 9, 10], [3, 6, 10],
                [4, 6, 7], [5, 7, 8]]

    graphe = G1
    sommet = 1

    print("Profondeur : Ordre de visite :", profond(graphe, sommet))
    print("Profondeur généralisé : Ordre de visite :", profondG(graphe))
