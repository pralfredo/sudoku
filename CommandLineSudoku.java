/**
 * File: CommandLineSudoku.java
 * Author: Pramithas Upreti
 * CS231
 * Section A
 * Project 5--> A Sudoku Solver
 * Date: Mar 16 2023
 * Extension
 * Purpose: This extension allows the program to receive input from the command line to determine 
 * the number of initial values and measure the time required to solve the created Sudoku board. 
 * To accomplish this, a variable is created to store the command line argument and 
 * error handling is implemented to ensure that the entered value is a positive integer. 
 */
public class CommandLineSudoku {
    private Board board;
    private LandscapeDisplay ld;

    /**
     * Constructor for CommandLineSudoku class.
     * 
     * @param lockedCell the number of cells to be locked in the Sudoku puzzle
     */
    public CommandLineSudoku(int lockedCell) {
        board = new Board(lockedCell);
        ld = new LandscapeDisplay(board);
    }

    /**
     * Determines the next valid value for a given cell that has not been tried
     * before.
     * 
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the next valid value for the cell if found, or 0 if no valid value is
     *         found
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

    // find the next cell to go to and find an appropriate value for it
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
     * Finds the next cell to go to and finds an appropriate value for it.
     * 
     * @return the next cell to be assigned a value if found, or null if no next cell is found
     */
    public boolean solve() {
        // record start time
        long start = System.currentTimeMillis();
        // allocate a stack, initially empty
        Stack<Cell> stack = new LinkedList<>();
        // while the stack size is less than the number of unspecified cells
        while (stack.size() < (81 - board.numLocked())) {
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
        // record end time
        long end = System.currentTimeMillis();
        System.out.println("Solved in " + (end - start) + " milliseconds");
        return board.finished;
    }

    /**
     * The main method
     * @param args
     */
    public static void main(String[] args) {
        // error handling
        try {
            int lockedCell = Integer.parseInt(args[0]);
            if (lockedCell < 0) {
                throw new IllegalArgumentException("Number of locked cells must be a positive integer.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid command line argument. ");
            System.exit(1);
            // too few arguments
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Not enough command line arguments provided.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // errors related to invalid command line arguments
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        // command line argument
        int lockedCell = Integer.parseInt(args[0]);
        CommandLineSudoku sudoku = new CommandLineSudoku(lockedCell);
        sudoku.solve();
    }
}