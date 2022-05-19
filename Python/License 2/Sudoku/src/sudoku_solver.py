from math import sqrt
from time import sleep


class SudokuSolver:

    def __init__(self, canvas):
        self.m_canvas = canvas
        self.m_size = 0

    def solve(self, grid):
        self.m_size = int(sqrt(len(grid)))
        self.placer_chiffre(0, grid)

    def ajout_possible(self, position, grid):
        c_x = position % self.m_size
        c_y = position // self.m_size

        for i in range(self.m_size):
            n_y = i * self.m_size + c_x
            n_x = position - c_x + i

            if (c_x != n_x and grid[n_x] == grid[position]) or \
                    (c_y != n_y and grid[n_y] == grid[position]) or \
                    (c_x // 3 == n_x // 3 and c_y // 3 == n_y // 3 and grid[self.m_size * n_y + n_x] == grid[position]):
                return False

        return True

    def placer_chiffre(self, position, grid):
        if position == len(grid):
            print("Solution : ", grid)
            return
        else:
            if grid[position] == "0":
                for i in range(1, self.m_size+1):
                    grid = grid[:position] + str(i) + grid[position + 1:]
                    if self.ajout_possible(position, grid):
                        self.placer_chiffre(position+1, grid)
                    grid = grid[:position] + "0" + grid[position + 1:]
            else:
                self.placer_chiffre(position+1, grid)