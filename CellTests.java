/**
 * File: CellTests.java
 * Author: Pramithas Upreti
 * CS231
 * Section A
 * Project 5--> A Sudoku Solver
 * Date: Mar 16 2023
 * Purpose: To Test the Cell class
 */
public class CellTests {

	public static void main(String[] args) {
		Cell cell = new Cell(10, 5, 3, true);
		// Test get row
		assert cell.getRow() == 10 : "Error in row";
		System.out.println(cell.getRow() + " == 10");
		// Test get col
		assert cell.getCol() == 5 : "Error in col";
		System.out.println(cell.getCol() + " == 5");
		// Test get value
		assert cell.getValue() == 3 : "Error in value";
		System.out.println(cell.getValue() + " == 3");
		// Test is locked
		assert cell.isLocked() : "Error in locked";
		System.out.println(cell.isLocked() + " == true");

		// Test set locked
		cell.setLocked(false);
		assert !cell.isLocked() : "Error in set locked";
		System.out.println(!cell.isLocked() + " == true");

		// Test set value
		cell.setValue(8);
		assert cell.getValue() == 8 : "Error in set value";
		System.out.println(cell.getValue() + " == 8");

		// Test to string
		cell.setValue(5);
		System.out.println("String: " + cell.toString() + " == Cell(row=10, col=5, value=5, locked=false)");

		System.out.println("Done Testing Cell");
	}

}
