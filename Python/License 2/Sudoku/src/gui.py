from tkinter import Tk, Canvas


class Gui(Tk):

    WIDTH = 500
    HEIGHT = 500
    MARGIN = 10

    def __init__(self):
        super().__init__()
        self.setup()

        self.m_canvas = self.draw_grid()

    def setup(self):
        self.title("Python - Sudoku Solver")
        self.geometry("%dx%d+%d+%d" % (
            Gui.WIDTH,
            Gui.HEIGHT,
            (self.winfo_screenwidth() - Gui.WIDTH) // 2,
            (self.winfo_screenheight() - Gui.HEIGHT) // 2)
        )
        self.update()
        self.resizable(False, False)

    def show(self):
        self.mainloop()

    def draw_grid(self):
        canvas = Canvas(self)
        cell_w, cell_h = (Gui.WIDTH - 2 * Gui.MARGIN) // 9, (Gui.HEIGHT - 2 * Gui.MARGIN) // 9

        for i in range(10):
            canvas.create_line(0, i * cell_w, 9 * cell_w, i * cell_w)
            canvas.create_line(i * cell_h, 0,  i * cell_h, 9 * cell_h)

        canvas.configure(width=9*cell_w+1, height=9*cell_h+1)
        canvas.configure(background="#f1f1f1", highlightthickness=0)
        canvas.pack(expand=True)

        return canvas