/**
 * File: BoardTests.java
 * Author: Pramithas Upreti
 * CS231
 * Section A
 * Project 5--> A Sudoku Solver
 * Date: Mar 16 2023
 * Purpose: To Test the Board Class
 */
 public class BoardTests{

    public static void main(String args[]){

        // case 1: getCols(), getRows(), get()
        {
            // set up
            Board myBoard = new Board("board1.txt");

            // verify
            System.out.println(myBoard.getCols() + " == 9");
            System.out.println(myBoard.getRows() + " == 9");
            System.out.println(myBoard.get(0, 3) + " == 3");
            System.out.println(myBoard.get(2, 5) + " == 0");

            // assert
            assert myBoard.getCols() == 9 : "Error in Board::getCols()";
            assert myBoard.getRows() == 9 : "Error in Board::getRows()";
            assert myBoard.get(0, 3).getValue() == 3 : "Error in Board::get()";
            assert myBoard.get(2, 5).getValue() == 0 : "Error in Board::get()";
        }

        // case 2: set() methods
        {
            // set up
            Board myBoard = new Board();
            myBoard.set(3, 3, 5);
            myBoard.set(2, 5, true);
            myBoard.set(5, 5, 3, true);

            // verify
            System.out.println(myBoard.get(3,3).getValue() + " == 5");
            System.out.println(myBoard.get(2,5).isLocked() + " == true");
            System.out.println(myBoard.get(5,5).getValue() + " == 3");
            System.out.println(myBoard.get(5,5).isLocked() + " == true");

            // test
            assert myBoard.get(3, 3).getValue() == 5 : "Error in Board::set(r, c, value)";
            assert myBoard.get(2, 5).isLocked() == true : "Error in Board::get(r, c, locked)";
            assert myBoard.get(5, 5).getValue() == 3 : "Error in Board::get(r, c, value, locked)";
            assert myBoard.get(5, 5).isLocked() == true : "Error in Board::get(r, c, value, locked)";
        }

        // case 3: value, isLocked(), numLocked()
        {
            // set up
            Board myBoard = new Board();
            int firstTotalLocked = myBoard.numLocked();
            myBoard.set(1, 1, 4, true);
            myBoard.set(4, 2, 7, true);
            myBoard.set(7, 8, 2, true);
            myBoard.set(3, 6, 1, true);

            // verify
            System.out.println(firstTotalLocked + " == 0");
            System.out.println(myBoard.value(0,0) + " == 0");
            System.out.println(myBoard.value(1,1) + " == 4");
            System.out.println(myBoard.value(4,2) + " == 7");
            System.out.println(myBoard.value(7,8) + " == 2");
            System.out.println(myBoard.value(3,6) + " == 1");
            System.out.println(myBoard.numLocked() + " == 4");

            // test
            assert firstTotalLocked == 0 : "Error in Board::numLocked() or Board::Board()";
            assert myBoard.value(0,0) == 0 : "Error in Board::value()";
            assert myBoard.value(1,1) == 4 : "Error in Board::value()";
            assert myBoard.value(4,2) == 7 : "Error in Board::value()";
            assert myBoard.value(7,8) == 2 : "Error in Board::value()";
            assert myBoard.value(3,6) == 1 : "Error in Board::value()";
            assert myBoard.numLocked() == 4 : "Error in Board::numLocked() or Board::set(r, c, value, locked)";
        }

        // case 4: validValue()
        {
            // set up
            Board myBoard = new Board("board1.txt");
            boolean check1 = myBoard.validValue(0, 2, 9); // check block
            boolean check2 = myBoard.validValue(0, 2, 6); // check column
            boolean check3 = myBoard.validValue(0, 2, 3); // check row
            boolean check4 = myBoard.validValue(0, 2, 4); // valid value
            
            // verify
            System.out.println(check1 + " == false");
            System.out.println(check2 + " == false");
            System.out.println(check3 + " == false");
            System.out.println(check4 + " == true");

            // test
            assert check1 == false : "Error in Board::validValue()";
            assert check2 == false : "Error in Board::validValue()";
            assert check3 == false : "Error in Board::validValue()";
            assert check4 == true : "Error in Board::validValue()";
        }        
        System.out.println("*** Done testing Board! ***");
    }
}