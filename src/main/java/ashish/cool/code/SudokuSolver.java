package ashish.cool.code;

import java.util.Arrays;

public class SudokuSolver {

    private final byte rows = 9;
    private final byte columns = 9;
    private final byte boxes_x = 3;
    private final byte boxes_y = 3;
    private final byte last_num = 9;

    private record NumberCords(byte x, byte y, byte box_x, byte box_y) {}

    private byte[][] sudoku_board = new byte[rows][columns];
    // 9x9 board

    public SudokuSolver(byte[][] sudoku_board) {
        board_checker(sudoku_board);
        this.sudoku_board = sudoku_board;
    }

    private void board_checker(byte[][] sudoku_board) {
        if (sudoku_board == null || sudoku_board.length != rows) {
            throw new IllegalArgumentException("Sudoku board must have exactly " + rows + " rows.");
        }

        for (byte i = 0; i < sudoku_board.length; i++) {
            if (sudoku_board[i] == null || sudoku_board[i].length != columns) {
                throw new IllegalArgumentException("Row " + i + " must have exactly " + columns + " columns.");
            }
        }
    }

    private NumberCords[] getNumberCords(int num) {
        NumberCords[] numCords = new NumberCords[rows];
        for (byte y = 0; y < sudoku_board.length; y++) {
            for (byte x = 0; x < sudoku_board[y].length; x++) {
                if (num == sudoku_board[y][x])
                    numCords[y] = new NumberCords(x, y, (byte) (x / boxes_x), (byte) (y / boxes_y));
            }
        }
        return numCords;
    }

    private void one_iteration_simple_elimination() {
        for (byte num = 1; num <= last_num; num++) {
            NumberCords[] numCords = getNumberCords(num);
            for (byte p = 0; p < numCords.length; p++) {
                if (numCords[p] == null) {
                    byte[] x = new byte[columns];
                    byte[] boxes_x_filled = new byte[boxes_x];
                    for (byte q = 0; q < numCords.length; q++) {
                        if (numCords[q] == null) {
                        } else if (q >= (p / boxes_y) * boxes_y && q < ((p / boxes_y) + 1) * boxes_y) {
                            boxes_x_filled[numCords[q].box_x] = (byte) (numCords[q].box_x + 1);
                        } else {
                            x[numCords[q].x] = (byte) (numCords[q].x + 1);
                        }
                    }
                    byte temp = 0;
                    byte count = 0;
                    for (byte q = 0; q < columns; q++) {
                        if (boxes_x_filled[q / 3] != 0 || x[q] != 0 || sudoku_board[p][q] != 0) continue;
                        else {
                            count++;
                            temp = q;
                        }
                        if (count > 1) break;
                    }
                    if (count == 1) {
                        sudoku_board[p][temp] = num;
                    }
                }
            }
        }
    }

    public void simple_elimination() {
        byte[][] sudoku_temp_board = new byte[rows][columns];
        do {
            for (byte i = 0; i < rows; i++) {
                sudoku_temp_board[i] = sudoku_board[i].clone();
            }
            one_iteration_simple_elimination();
        } while (!Arrays.deepEquals(sudoku_board, sudoku_temp_board));
    }

    public byte[][] getSudokuBoard() {
        return sudoku_board;
    }

    public static void main(String[] args) {
        byte[][] board = {
            {8, 0, 0, 0, 4, 0, 6, 0, 0},
            {0, 3, 0, 5, 0, 6, 9, 4, 0},
            {6, 1, 0, 0, 2, 0, 5, 0, 8},
            {9, 5, 7, 4, 0, 3, 0, 8, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 8, 0, 7, 0, 1, 4, 6, 9},
            {5, 0, 8, 0, 3, 0, 0, 1, 6},
            {0, 7, 6, 2, 0, 5, 0, 9, 0},
            {0, 0, 9, 0, 7, 0, 0, 0, 5},
        };
        SudokuSolver ss = new SudokuSolver(board);
        ss.simple_elimination();
        for (byte[] i : ss.getSudokuBoard()) {
            for (byte j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }
}
