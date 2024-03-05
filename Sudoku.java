/**
 * File: Sudoku.java
 * Author: Pramithas Upreti
 * CS231
 * Section A
 * Project 5--> A Sudoku Solver
 * Date: Mar 16 2023
 * Purpose: This class represents a Sudoku puzzle and provides methods to solve it.
 */
public class Sudoku {

    /**
     * The Sudoku board.
     */
    protected Board board;

    /**
     * The display of the Sudoku board.
     */
    private LandscapeDisplay ld;

    /**
     * Constructs a new Sudoku puzzle with a default board size of 20 and creates a
     * new LandscapeDisplay for it.
     */
    public Sudoku() {
        this(10);
    }

    /**
     * Constructs a new Sudoku puzzle with a set locked value as given by the user 
     * @param locked the locked value
     */
    public Sudoku(int locked){
        board = new Board(locked);
        ld = new LandscapeDisplay(board);
    }

    /**
     * Determines if there is a valid value for this cell that hasn't been tried.
     * 
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the valid value for this cell that hasn't been tried, or 0 if there
     *         is no such value
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
     * Finds the next cell to go to and finds an appropriate value for it.
     * 
     * @return the next cell to go to and its appropriate value, or null if there is
     *         no such cell
     */
    public Cell findNextCell() {
        // loop through all cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell cell = board.get(i, j);
                // if find a next cell to go to
                if (cell.getValue() == 0) {
                    // find a valid value to set to
                    int value = findNextValue(i, j);
                    // if a valid value is found
                    if (value != 0) {
                        // update the cell's value
                        cell.setValue(value);
                        // return the cell
                        return cell;
                    }
                    // if cannot find a valid value
                    else {
                        // no solution
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Solves the Sudoku puzzle.
     * 
     * @param delay the delay between updates to the display
     * @return true if the puzzle is solved, false otherwise
     * @throws InterruptedException if interrupted while sleeping
     */
    public boolean solve(int delay) throws InterruptedException {
        // allocate a stack, initially empty
        Stack<Cell> stack = new LinkedList<>();
        // while the stack size is less than the number of unspecified cells
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
        return board.finished;
    }

    /**
     * This is the main method which creates a new Sudoku object and solves the
     * puzzle.
     * 
     * @param args the command line arguments
     * @throws InterruptedException if an interrupt occurs while the thread is
     *                              sleeping
     */
    public static void main(String[] args) throws InterruptedException {
        Sudoku sudoku = new Sudoku(10);
        sudoku.solve(0);
    }
}