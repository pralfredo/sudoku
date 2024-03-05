/**
 * File: GuaranteedSudoku.java
 * Author: Pramithas Upreti
 * CS231
 * Section A
 * Project 5--> A Sudoku Solver
 * Date: Mar 16 2023
 * Extension
 * Purpose:This class represents a Guaranteed Sudoku game which generates a complete Sudoku board and 
 * then removes some cells to create a board with a certain number of locked cells.
 * This class also provides a method to solve the generated board.
 */
import java.util.Random;

public class GuaranteedSudoku {
    /**
     * The Sudoku board generated by the game.
     */
    private Board board;

    /**
     * The LandscapeDisplay used to display the Sudoku board.
     */
    private LandscapeDisplay ld;

    /**
     * Creates a GuaranteedSudoku game with a board that has one locked cell.
     */
    public GuaranteedSudoku() {
        board = new Board(1);
        ld = new LandscapeDisplay(board);
    }

    /**
     * Creates a GuaranteedSudoku game with a board that has a certain number of
     * locked cells.
     * 
     * @param lockedCell the number of locked cells in the generated board
     */
    public GuaranteedSudoku(int lockedCell) {
        this();
        solve();
        Random random = new Random();
        while (board.emptyCell() < (81 - lockedCell)) {
            int randRow = random.nextInt(9);
            int randCol = random.nextInt(9);
            if (!board.get(randRow, randCol).isLocked()) {
                board.set(randRow, randCol, 0);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.value(i, j) != 0) {
                    board.get(i, j).setLocked(true);
                }
            }
        }
        // System.out.println("Starting board: \n" + board);
        // System.out.println("Numlocked; " + board.numLocked());
    }

    /**
     * Finds a valid value for a given cell that hasn't been tried before.
     * 
     * @param row the row number of the cell
     * @param col the column number of the cell
     * @return a valid value for the cell or 0 if no valid value is found
     */
    public int findNextValue(int row, int col) {
        int value = 0;
        for (int i = board.get(row, col).getValue() + 1; i < 10; i++) {
            if (board.validValue(row, col, i)) {
                value = i;
                break;
            }
        }
        return value;
    }

    /**
     * Finds the next cell to visit and sets an appropriate value for it.
     * 
     * @return the next cell to visit or null if no solution is found
     */
    public Cell findNextCell() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell cell = board.get(i, j);
                if (cell.getValue() == 0) {
                    int value = findNextValue(i, j);
                    if (value != 0) {
                        cell.setValue(value);
                        return cell;
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    // solve
    public boolean solve() {
        // allocate a stack, initially empty
        Stack<Cell> stack = new LinkedList<>();
        // while the stack size is less than the number of unspecified cells
        while (stack.size() < (81 - board.numLocked())) {
            // create a cell called next by calling findNextCell
            Cell next = findNextCell();
            // while next is null and the stack is non-empty
            while (next == null && stack.size() > 0) {
                // pop a cell off the stack
                Cell popCell = stack.pop();
                // call findNextValue on this Cell
                popCell.setValue(findNextValue(popCell.getRow(), popCell.getCol()));
                // if the cell's value is no longer 0, set next to this popped cell
                if (popCell.getValue() > 0) {
                    next = popCell;
                }
            }
            // if next is still null
            if (next == null) {
                // System.out.println(stack.size());
                // no solution
                board.finished = true;
                return board.finished;
            }
            // else
            else {
                // push next onto the stack
                stack.push(next);
            }
        }
        // return true as the board contains the solution
        board.finished = true;
        return board.finished;
    }

    /**
     * Solves the Sudoku board using backtracking.
     * 
     * @return true if the board is solved, false otherwise
     */
    public boolean solve(int delay) throws InterruptedException {
        // allocate a stack, initially empty
        Stack<Cell> stack = new LinkedList<>();
        // while the stack size is less than the number of unspecified cells
        // int i = 0;
        while (stack.size() < (81 - board.numLocked())) {
            if (delay > 0)
                Thread.sleep(delay);
            if (ld != null)
                ld.repaint();
            // create a cell called next by calling findNextCell
            Cell next = findNextCell();
            // while next is null and the stack is non-empty
            while (next == null && stack.size() > 0) {
                // pop a cell off the stack
                Cell popCell = stack.pop();
                // call findNextValue on this Cell
                popCell.setValue(findNextValue(popCell.getRow(), popCell.getCol()));
                // if the cell's value is no longer 0, set next to this popped cell
                if (popCell.getValue() > 0) {
                    next = popCell;
                }
            }
            // if next is still null
            if (next == null) {
                // no solution
                board.finished = true;
                return board.finished;
            }
            // else
            else {
                // push next onto the stack
                stack.push(next);
            }
        }
        // return true as the board contains the solution
        board.finished = true;
        // System.out.println(stack);
        return board.finished;
    }

    /**
     * The main method
     * 
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        GuaranteedSudoku sudoku = new GuaranteedSudoku(80);
        sudoku.solve(10);
    }
}