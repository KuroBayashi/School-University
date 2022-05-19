
# BACKTRACKING

def is_valid(G, i, solution):
    # Verifie si tous les sommets voisins sont d'une couleur differente
    for x in G[i]:
        if solution[x] == solution[i]:
            return False

    return True


def backtracking_rec(G, colors, i, solution, solutionList):
    if i == len(G):
        solutionList.append(solution[:])
    else:
        for color in colors:
            solution[i] = color
            if is_valid(G, i, solution):
                backtracking_rec(G, colors, i+1, solution, solutionList)
            solution[i] = 0


def backtracking(G, colors=None):
    solutionList = []

    # On a fourni une liste de couleur a tester
    if colors:
        backtracking_rec(G, colors, 1, [0] * len(G), solutionList)
    # On test avec le moins de couleur possible jusqu'a avoir une solution
    else:
        n = 2
        while not solutionList:
            backtracking_rec(G, list(range(1, n)), 1, [0] * len(G), solutionList)
            n +=1

    return solutionList


if __name__ == "__main__":
    G = [[], [2, 3, 4], [1, 3, 4, 5], [1, 2, 4, 5, 6], [1, 2, 3, 6], [2, 3, 7], [3, 4, 7], [5, 6]]
    Petersen = [[], [2, 5, 6], [1, 3, 7], [2, 4, 8], [3, 5, 9], [1, 4, 10], [1, 8, 9], [2, 9, 10], [3, 6, 10],
                [4, 6, 7], [5, 7, 8]]

    print("Coloration par backtracking du graphe de Petersen")
    solutions = backtracking(Petersen)
    if solutions:
        for solution in solutions:
            print(solution)
    else:
        print("Aucune solution")

    print("Coloration par backtracking du graphe G")
    solutions = backtracking(G)
    if solutions:
        for solution in solutions:
            print(solution)
    else:
        print("Aucune solution")