# -*- coding: utf-8 -*-
import sys

#
#   Helper
#
def initMat(size, value):
    return [ [ value for _ in range(size) ] for _ in range(size) ]

def initVect(size, value):
    return [ value for _ in range(size) ]

#
#   Algorithm : Edmonds - Karp
#
#   Doc (en) : https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm
#   Doc (fr) : https://fr.wikipedia.org/wiki/Algorithme_d%27Edmonds-Karp
#
def EdmondsKarp(CapacitiesMat, G, s, t):
    
    flowMax = 0
    FlowMat = initMat(len(CapacitiesMat), 0)
    
    while True:
        # Recherche d'un chemin de s vers t
        capacity, Pere = BreadthFirstSearch(CapacitiesMat, G, s, t, FlowMat)

        # Pas / Plus de chemin disponible, donc on arrete
        if 0 == capacity: break

        # Chemin trouvé, donc on ajoute sa capacite au "flow maximal"
        flowMax += capacity

        # On part du point d'arrive pour determiner le parcours du chemin
        #   en remontant grace au vecteur des peres
        x = t

        while s != x:
            p = Pere[x]
            # Mise a jour de la matrice du "flow"
            # Possibilite de mettre :
            #   - en positif pour avoir une vision des arcs "sortants"
            #   - en negatif pour avoir une vision des arcs "entrants"
            FlowMat[p][x] += capacity
            FlowMat[x][p] -= capacity
            x = p

    # Retourne le flow maximale du graphe, ainsi que la matrice associe afin d'en determiner sa representation
    return flowMax, FlowMat


#
#   Search
#
def BreadthFirstSearch(CapacitiesMat, G, s, t, FlowMat):
    
    Pere     = initVect(len(CapacitiesMat) + 1, 0)
    Capacity = initVect(len(CapacitiesMat) + 1, 0)
    Pile = []

    Pere[s] = -1
    Capacity[s] = sys.maxsize
    Pile.append(s)
    
    while Pile:
        x = Pile.pop()

        for v in G[x]:
            # Si la capacite maximale de l'arc (x,v) n'est pas atteinte
            #   et que le sommet 'v' n'a pas deja ete visite
            if 0 < CapacitiesMat[x][v] - FlowMat[x][v] and 0 == Pere[v]:
                Pere[v] = x
                Capacity[v] = min(Capacity[x], CapacitiesMat[x][v] - FlowMat[x][v])

                if t != v:
                    Pile.append(v)
                else:
                    return Capacity[t], Pere

    # Pas de chemin trouve, donc retourne une capacite de 0
    return 0, Pere
    
        

if __name__ == "__main__":
    
    # Graphe 1
    G_1 = [
        # Matrice des capacites
        #   num ligne   = sommet A
        #   num colonne = sommet B
        #
        # Exemple : Ligne 1, Colonne 4, Valeur 3 -> Arc de 1 vers 4 avec Capacite de 3
        [
            [0, 0, 0, 0, 0, 0, 0],
            [0, 0, 2, 0, 4, 0, 0],
            [0, 0, 0, 1, 2, 2, 0],
            [0, 0, 0, 0, 0, 0, 2],
            [0, 0, 0, 2, 0, 1, 0],
            [0, 0, 0, 0, 0, 0, 2],
            [0, 0, 0, 0, 0, 0, 0]
        ],
        # Liste des successeurs (deductible de la matrice), utilise pour faciliter les "calculs"
        [[], [2, 4], [3, 4, 5], [6], [3, 5], [6], []],
        # Source
        1,
        # Puit
        6
    ]
    
    # Graphe 2
    G_2 = [
        [
            [0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 3, 0, 3, 0, 0, 0],
            [0, 0, 0, 4, 0, 0, 0, 0],
            [0, 3, 0, 0, 1, 2, 0, 0],
            [0, 0, 0, 0, 0, 2, 6, 0],
            [0, 0, 1, 0, 0, 0, 0, 1],
            [0, 0, 0, 0, 0, 0, 0, 9],
            [0, 0, 0, 0, 0, 0, 0, 0]
        ],
        [[], [2, 4], [3], [1, 4, 5], [5, 6], [2, 7], [7], []],
        1,
        7
    ]

    # Run
    G = G_2

    print("Admonds-Karp :")
    f, F = EdmondsKarp(G[0], G[1], G[2], G[3])
    print("Flow max :", f)
    print("Flow matrice :")
    for line in F:
        print(line)
    