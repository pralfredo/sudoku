/**
 * File: Cell.java
 * Author: Pramithas Upreti
 * CS231
 * Section A
 * Project 5--> A Sudoku Solver
 * Date: Mar 16 2023
 * Purpose: A representation of the Cell. 
 * It includes all the methods that help manage the cell.  
 */
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
public class Cell {

    private int row;
    private int col;
    private int value;
    private boolean locked;

    /**
     * Constructer for a cell
     */
    public Cell() {
        this.row = 0;
        this.col = 0;
        this.value = 0;
        this.locked = false;
    }

    /**
     * Constructs a cell with the specified row, col and value.
     * 
     * @param row
     * @param col
     * @param value
     */
    public Cell(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = false;
    }

    /**
     * Constructs a cell with the specified row, col and value.
     * 
     * @param row
     * @param col
     * @param value
     * @param locked
     */
    public Cell(int row, int col, int value, boolean locked) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = locked;
    }

    /**
     * Returns the row index of the cell.
     *
     * @return the row index of the cell
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column index of the cell.
     *
     * @return the column index of the cell
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the current value of the cell.
     *
     * @return the current value of the cell
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the cell to the specified value.
     *
     * @param newVal the new value of the cell
     */
    public void setValue(int newVal) {
        this.value = newVal;
    }

    /**
     * Returns true if the cell is locked, false otherwise.
     *
     * @return true if the cell is locked, false otherwise
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the locked state of the cell.
     *
     * @param lock true if the cell should be locked, false otherwise
     */
    public void setLocked(boolean lock) {
        this.locked = lock;
    }

    /**
     * Returns a string representation of the cell, including its row index, column
     * index, value, and locked state.
     *
     * @return a string representation of the cell
     */
    public String toString() {
        return "Cell(row=" + row + ", col=" + col + ", value=" + value + ", locked=" + locked + ")";
    }

    /**
     * Draws the cells number
     * @param g
     * @param x
     * @param y
     * @param scale
     */
    public void draw(Graphics g, int x, int y, int scale) {
		char toDraw = (char) ((int) '0' + getValue());
		g.setColor(isLocked() ? Color.RED : Color.WHITE);
		g.setFont(new Font("Arial", isLocked() ? Font.ITALIC : Font.BOLD, scale));
		g.drawChars(new char[] { toDraw }, 0, 1, x, y + 25);
	}

    public static void main(String[] args) {
        Cell a1 = new Cell();
        System.out.println(a1.toString());
    }
}