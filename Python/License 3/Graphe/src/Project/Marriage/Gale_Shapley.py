# -*- coding: utf-8 -*-

#
#   Algorithm Gale - Shapley
#
#   Doc (en) : https://en.wikipedia.org/wiki/Stable_marriage_problem
#   Doc (fr) : https://fr.wikipedia.org/wiki/Probl%C3%A8me_des_mariages_stables
#
def Gale_Shapley(M, W):
    Propose = [0] * len(M)          # Indice de la derniere proposition faite par chaque homme
    Engaged = [0] * len(M)          # Engagement actuel pour chaque homme Engaged[i] = Indice de la femme "actuelle"
    Free = list(range(1, len(M)))   # Liste des hommes libres

    while Free:
        m = Free.pop(0)         # On recupere un homme libre
        w = M[m][Propose[m]]    # On propose a la prochaine femme de la liste de l'homme m

        Propose[m] += 1         # Prochaine fois, on passera a la femme suivante de la liste

        if w not in Engaged:            # Si la femme n'est pas deja avec quelqu'un, ils forment un couple
            Engaged[m] = w
        else:
            m_2 = Engaged.index(w)      # Recuperation du pretendant actuel pour la femme

            if m_2 > W[w].index(m):     # Si la femme prefere le nouvel homme, elle echange
                Engaged[m] = w
                Engaged[m_2] = 0        # L'ancien homme redevient libre
                Free.append(m_2)
            else:
                Free.append(m)          # Sinon, l'homme retourne dans la liste des libres

    return Engaged


if __name__ == "__main__":
    # 2 Listes des preferences (Homme et Femme)
    # Toutes les listes de preferences doivent contenir chacuns des pretendants
    M = [
        [],
        [1, 2, 3, 4, 5],    # Homme 1 (Femme 1 > Femme 2 > Femme 3 > Femme 4 > Femme 5)
        [1, 3, 4, 2, 5],    # Homme 2 (Femme 1 > Femme 3 > Femme 4 > Femme 2 > Femme 5)
        [4, 5, 3, 1, 2],    # Homme 3   ...
        [3, 4, 5, 2, 1],    # Homme 4   ...
        [2, 5, 1, 3, 4]     # Homme 5   ...
    ]

    W = [
        [],
        [1, 2, 3, 4, 5],    # Femme 1 (Homme 1 > Homme 2 > Homme 3 > Homme 4 > Homme 5)
        [1, 2, 3, 4, 5],    # Femme 2   ...
        [1, 2, 3, 4, 5],    # Femme 3   ...
        [1, 2, 3, 4, 5],    # Femme 4   ...
        [1, 2, 3, 4, 5]     # Femme 5   ...
    ]

    print("Marriage by Gale-Shapley :")
    Actuel = Gale_Shapley(M, W)
    print(Actuel)