
import src.TP1.oriente as Oriented

def triTopo(G):
    """
    Détermine un tri topologique du graphe G si celui-ci ne contient pas de cycle.
    Le tri s’arrête lorsqu'on ne trouve plus de sources.  On détecte donc que G contient un cycle si  le tri est
    terminé alors que les n sommets n'ont été triés.
    """
    T = []
    S = []
    Degre = [0] + Oriented.degreE(G)

    for x in range(1, len(G)):
        if Degre[x] == 0:
            S.append(x)

    while S:
        x = S.pop(0)
        T.append(x)

        for y in G[x]:
            Degre[y] -= 1
            if Degre[y] == 0:
                S.append(y)

    if len(T)==len(G)-1:
        print("Le graphe est sans cycle")
    else:
        print("Le graphe est cyclique")

    return T


def triNiveaux(G):
    """
    Détermine un tri par niveau du graphe G si celui-ci ne contient pas de cycle.
    Le tri s’arrête lorsqu'on ne trouve plus de sources.  On détecte donc que G contient un cycle si  le tri est
    terminé alors que les n sommets n'ont été triés.
    """
    T = []
    N1 = []
    Degre = [0] + Oriented.degreE(G)

    for x in range(1, len(G)):
        if Degre[x] == 0:
            N1.append(x)

    while N1:
        T.append(N1)

        N2 = []

        for x in N1:
            for y in G[x]:
                Degre[y] -= 1
                if Degre[y] == 0:
                    N2.append(y)
            N1 = N2[:]

    if len(list(sum(T, [])))==len(G)-1:
        print("Le graphe est sans cycle")
    else:
        print("Le graphe est cyclique")

    return T


if __name__ == "__main__":
    G = [[], [2, 6], [3, 5, 6], [4, 5], [], [4], [5], [2, 6]]

    print("Tri topologique : ", triTopo(G))
    print("Tri par niveaux : ", triNiveaux(G))

