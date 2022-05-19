from math import sqrt
from time import sleep
from tkinter import Tk, Canvas

import sys


def draw_text(p, sol):
    """
    Dessinne la valeur de la case "p" de la grille "sol"

    :param p: Position de la case a remplir
    :param sol: Grille du sudoku

    :type p: Integer
    :type sol: List
    """
    # global canvas, origin, TEXT_FONT, CELL_WIDTH, GRID_SIZE

    txt = "" if sol[p] == "0" else sol[p]
    x = CELL_WIDTH * (p % GRID_SIZE + 0.5)
    y = CELL_WIDTH * (p // GRID_SIZE + 0.5)
    color = "#282828" if sol[p] != origin[p] else "#255884"

    canvas.create_text(x, y, text=txt, font=TEXT_FONT, tags="text", fill=color)


def draw_solution(sol):
    """
    Dessinne les valeurs des cases de la grille "sol"

    :param sol: Grille du sudoku

    :type sol: List
    """
    # global root, canvas, SLEEP

    sleep(SLEEP)
    canvas.delete("text")
    for i in range(len(sol)):
        draw_text(i, sol)
    root.update()


def ajout_possible(p, sol):
    """
    Verifie que la valeur de la case "p" est valide dans la grille "sol"

    :param p: Position de la case a remplir
    :param sol: Grille du sudoku

    :type p: Integer
    :type sol: List

    :return: True si valide, sinon False
    """
    # global GRID_SIZE, GRID_SQRT

    c_x = p % GRID_SIZE
    c_y = p // GRID_SIZE

    for index in range(GRID_SIZE):
        n_x = c_y * GRID_SIZE + index  # ligne
        n_y = index * GRID_SIZE + c_x  # colonne

        b_y = GRID_SQRT * (c_y // GRID_SQRT) + index // GRID_SQRT
        b_x = GRID_SQRT * (c_x // GRID_SQRT) + index % GRID_SQRT
        b_p = GRID_SIZE * b_y + b_x  # bloc

        if (n_x != p and sol[n_x] == sol[p]) or \
                (n_y != p and sol[n_y] == sol[p]) or \
                (b_p != p and sol[b_p] == sol[p]):
            return False

    return True


def place_number(p, sol):
    """
    Essai de remplir la case "p" de la grille "sol"

    :param p: Position de la case a remplir
    :param sol: Grille du sudoku

    :type p: Integer
    :type sol: List
    """
    if p == len(sol):
        global cpt_sol

        cpt_sol += 1
        print("Solution ", cpt_sol, " : ", ",".join(sol))
    else:
        if sol[p] == "0":
            for num in range(1, GRID_SIZE+1):
                sol = sol[:p] + [str(num)] + sol[p + 1:]
                draw_solution(sol)
                if ajout_possible(p, sol):
                    place_number(p + 1, sol)
                sol = sol[:p] + ["0"] + sol[p + 1:]
                draw_solution(sol)
        else:
            place_number(p + 1, sol)


def sudoku_solve(sol):
    """"
    Lance la resolution du sudoku

    :param sol: Grille du sudoku

    :type sol: List
    """
    global origin  # , cpt_sol, GRID_SIZE

    sol = sol.split(",")
    origin = sol[:]

    if len(sol) == GRID_SIZE * GRID_SIZE:
        place_number(0, sol)
        print("Nombre de solution : ", cpt_sol)
    else:
        print("Grille invalide : GRID_SIZE ^ 2 = ", GRID_SIZE * GRID_SIZE, " et len(grid) = ", len(sol), ".")


def on_close():
    global root, canvas

    del canvas
    root.destroy()
    sys.exit()


if __name__ == "__main__":
    CELL_WIDTH = 40  # Dimension des cases
    TEXT_FONT = ("Open Sans", 18)

    GRID_SIZE = 9  # Taille de la grille : 4, 9, 16
    GRID_SQRT = int(sqrt(GRID_SIZE))

    SLEEP = 0.1  # Temps d'attente pour l'animation (en seconde)

    cpt_sol = 0  # Nombre de solution final
    origin = []  # Copie de la grille d'origine (pour la coloration des cases pre-rempli)

    root = Tk()
    root.title("Python - Sudoku Solver")
    root.wm_protocol("WM_DELETE_WINDOW", on_close)

    canvas = Canvas(root, highlightthickness=0)
    canvas.config(height=CELL_WIDTH * GRID_SIZE + 1, width=CELL_WIDTH * GRID_SIZE + 1, bg="#f1f1f1")
    for i in range(GRID_SIZE+1):
        l_w = 3 if i % GRID_SQRT == 0 else 1
        canvas.create_line(0, i * CELL_WIDTH, GRID_SIZE * CELL_WIDTH, i * CELL_WIDTH, width=l_w, fill="#282828")
        canvas.create_line(i * CELL_WIDTH, 0, i * CELL_WIDTH, GRID_SIZE * CELL_WIDTH, width=l_w, fill="#282828")
    canvas.pack(expand=True)

    #
    # /!\ Pensez a changer GRID_SIZE en fonction de la grille test  /!\ #
    #

    # Exemple de 4 x 4
    # sudoku_solve("1,0,0,0,"
    #              "0,2,0,0,"
    #              "0,0,3,0,"
    #              "0,0,0,4")

    # Exemple de 9 x 9
    sudoku_solve("8,0,0,0,0,3,5,0,6,"
                 "0,7,3,0,0,5,4,0,8,"
                 "9,6,0,4,8,0,0,0,0,"
                 "0,3,0,0,0,9,0,8,4,"
                 "1,0,0,0,0,0,0,0,7,"
                 "5,9,0,1,0,0,0,3,0,"
                 "0,0,0,0,3,1,0,6,5,"
                 "3,0,7,5,0,0,2,1,0,"
                 "6,0,1,8,0,0,0,0,3")

    # Exemple de 16 x 16
    # sudoku_solve("0,15,0,1,0,2,10,14,12,0,0,0,0,0,0,0,"
    #              "0,6,3,16,12,0,8,4,14,15,1,0,2,0,0,0,"
    #              "14,0,9,7,11,3,15,0,0,0,0,0,0,0,0,0,"
    #              "4,13,2,12,0,0,0,0,6,0,0,0,0,15,0,0,"
    #              "0,0,0,0,14,1,11,7,3,5,10,0,0,8,0,12,"
    #              "3,16,0,0,2,4,0,0,0,14,7,13,0,0,5,15,"
    #              "11,0,5,0,0,0,0,0,0,9,4,0,0,6,0,0,"
    #              "0,0,0,0,13,0,16,5,15,0,0,12,0,0,0,0,"
    #              "0,0,0,0,9,0,1,12,0,8,3,10,11,0,15,0,"
    #              "2,12,0,11,0,0,14,3,5,4,0,0,0,0,9,0,"
    #              "6,3,0,4,0,0,13,0,0,11,9,1,0,12,16,2,"
    #              "0,0,10,9,0,0,0,0,0,0,12,0,8,0,6,7,"
    #              "12,8,0,0,16,0,0,10,0,13,0,0,0,5,0,0,"
    #              "5,0,0,0,3,0,4,6,0,1,15,0,0,0,0,0,"
    #              "0,9,1,6,0,14,0,11,0,0,2,0,0,0,10,8,"
    #              "0,14,0,0,0,13,9,0,4,12,11,8,0,0,2,0")

    root.mainloop()
