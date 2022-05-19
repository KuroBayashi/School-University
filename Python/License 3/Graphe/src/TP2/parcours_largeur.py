#!/usr/bin/env python
# -*- coding: utf-8 -*-

import lib.vector as Vector


def largeur(G, i):
    """
    Effectue le parcours en largeur du graphe G à partir du sommet i
    """
    # print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -")
    # print("Parcours en largeur de", G, "à partir du sommet", i, ":")

    Visite = Vector.initVect(len(G), 0)
    ordreVisite = []
    File = [i]

    Visite[i] = 1

    while File:
        y = File.pop(0)
        ordreVisite.append(y)
        # print("Visite de", y)

        for z in G[y]:
            if Visite[z] == 0:
                Visite[z] = 1
                File.append(z)
                # print("On ajoute", z, "à la file d'attente")
            else:
                # print("Revisite de", z)
                pass

    return ordreVisite


def largeurG(G):
    """
    Effectue le parcours en largeur généralisé à tout le graphe G
    """
    # print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -")
    # print("Parcours en largeur généralisé de", G, ":")

    Visite = Vector.initVect(len(G), 0)
    ordreVisite = []
    File = []

    for i in range(1, len(G)):
        if Visite[i] == 0:

            ordreSousVisite = []
            File.append(i)
            Visite[i] = 1
            # print("Parcours à partir de", i)

            while File:
                y = File.pop(0)
                ordreSousVisite.append(y)
                # print("Visite de", y)

                for z in G[y]:
                    if Visite[z] == 0:
                        Visite[z] = 1
                        File.append(z)
                        # print("On ajoute", z, "à la file d'attente")
                    else:
                        # print("Revisite de", z)
                        pass
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

    graphe = G2
    sommet = 1

    print("Largeur : Ordre de visite :", largeur(graphe, sommet))
    print("Largeur généralisé : Ordre de visite :", largeurG(graphe))