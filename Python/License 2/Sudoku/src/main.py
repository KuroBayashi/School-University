from src.gui import Gui
from src.sudoku_solver import SudokuSolver


class Main:

    def __init__(self):
        self.m_gui = Gui()
        self.m_sudoku = SudokuSolver(self.m_gui.m_canvas)

    def run(self):
        grid = "000000000000000000000000000000000000000000000000000000000000000000000000000000000"

        self.m_gui.show()
        self.m_sudoku.solve(grid)



if __name__ == "__main__":
    main = Main()
    main.run()