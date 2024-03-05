/**
 * File: NewAlgorithmSudoku.java
 * Author: Pramithas Upreti
 * CS231
 * Section A
 * Project 5--> A Sudoku Solver
 * Date: Mar 16 2023
 * Extension
 * Purpose:  * This class implements a new algorithm for solving Sudoku puzzles.
 * It uses a stack to keep track of the cells that have been filled and
 * backtracks when no further progress can be made. The algorithm selects
 * the next cell to be filled based on the number of possible values and
 * uses the findNextValue method to determine the value for the cell.
 */
public class NewAlgorithmSudoku {

    private Board board;
    private LandscapeDisplay ld;

    /**
     * 
     * Constructor for the NewAlgorithmSudoku class.
     * Creates a new Board object with a size of 20 and a new LandscapeDisplay
     * object.
     */
    public NewAlgorithmSudoku() {
        board = new Board(30);
        ld = new LandscapeDisplay(board);
    }

    /**
     * 
     * Determines if there is a valid value for a given cell that hasn't been tried.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the valid value for the cell, or 0 if no such value exists
     */
    public int findNextValue(int row, int col) {
        int value = 0;
        // loop through values bigger than current value
        for (int i = board.get(row, col).getValue() + 1; i < 10; i++) {
            // if this value is valid
            if (board.validValue(row, col, i)) {
                // return this value
                value = i;
                break;
            }
            // if cannot find another valid value, return 0
        }
        return value;
    }

    /**
     * 
     * Finds the number of possible values for a given cell.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @return the number of possible values for the cell
     */
    public int getPossibleValues(int row, int col) {
        int possibleValues = 0;
        // loop through all values
        for (int i = 1; i < 10; i++) {
            if (board.validValue(row, col, i)) {
                possibleValues++;
            }
        }
        return possibleValues;
    }

    /**
     * 
     * Finds the next cell to go to and finds an appropriate value for it.
     * 
     * @return the next cell to be filled, or null if no such cell exists
     */
    public Cell findNextCell() {
        Cell chosenCell = null;
        int minPossibleValues = 10;
        // loop through all cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell cell = board.get(i, j);
                // if empty cell
                if (cell.getValue() == 0 && findNextValue(i, j) != 0
                        && getPossibleValues(i, j) < minPossibleValues && getPossibleValues(i, j) > 0) {
                    chosenCell = cell;
                    minPossibleValues = getPossibleValues(i, j);
                }
            }
        }
        // if a cell is not found
        if (chosenCell == null) {
            return null;
        }
        // if a cell is found
        else {
            chosenCell.setValue(findNextValue(chosenCell.getRow(), chosenCell.getCol()));
            return chosenCell;
        }
    }

    /**
     * 
     * Solves the Sudoku puzzle using a modified depth-first search algorithm.
     * The algorithm searches for the cell with the fewest possible values, and
     * tries each possible value until a solution is found.
     * 
     * @return true if the Sudoku puzzle is solved, false otherwise
     */
    public boolean solve() {
        // record start time
        long start = System.currentTimeMillis();
        // allocate a stack, initially empty
        Stack<Cell> stack = new LinkedList<>();
        // while the stack size is less than the number of unspecified cells
        while (stack.size() < (81 - board.numLocked())) {
            if (ld != null) {
                ld.repaint();
            }
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
            } else {
                // push next onto the stack
                stack.push(next);
            }
        }
        // return true as the board contains the solution
        board.finished = true;
        // record end time
        long end = System.currentTimeMillis();
        System.out.println("Solved in " + (end - start) + " milliseconds");
        return board.finished;
    }

    /**
     * 
     * The main method runs an instance of the NewAlgorithmSudoku class and attempts
     * to solve
     * a Sudoku puzzle.
     * 
     * @param args arguments passed to the program (not used)
     */
    public static void main(String[] args) {
        NewAlgorithmSudoku sudoku = new NewAlgorithmSudoku();
        sudoku.solve();
    }
}