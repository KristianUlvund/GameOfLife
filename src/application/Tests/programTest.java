package application.Tests;

import application.model.StaticBoardModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kristian
 *
 * This class is a test class, which contains JUnit test methods.
 */
public class programTest {

    // Setup for the new references.
    StaticBoardModel sbm;
    BoardSizeGUIInputTest boardSizeInput;

    // Setup the references to the new objects.
    @Before
    public void setUp(){
        sbm = new StaticBoardModel();
        boardSizeInput = new BoardSizeGUIInputTest();
    }

    /**
     * This method test if the cell stays unchanged after next generation.
     */
    @Test
    public void testNextGeneration1() {

        sbm.setBoard(4);
        sbm.setCellStateTrue(1,1);
        sbm.setCellStateTrue(1,2);
        sbm.setCellStateTrue(2,1);
        sbm.setCellStateTrue(2,2);
        sbm.nextGeneration();

        String expectedResult = "0000011001100000";
        String actualResult = sbm.toString();
        org.junit.Assert.assertEquals(expectedResult,actualResult);
    }

    /**
     * This method test if the cell have the right position after 4 generations.
     */
    @Test
    public void testNextGeneration2() {

        sbm.setBoard(4);
        sbm.setCellStateTrue(0,2);
        sbm.setCellStateTrue(1,2);
        sbm.setCellStateTrue(2,2);
        sbm.setCellStateTrue(2,1);
        sbm.setCellStateTrue(1,0);

        for (int i = 0; i < 4; i++) {
            sbm.nextGeneration();
        }

        String expectedResult = "0000000101010011";
        String actualResult = sbm.toString();
        org.junit.Assert.assertEquals(expectedResult,actualResult);
    }

    /**
     * "Any live cell with fewer than two live neighbours dies, as if caused by under-population"
     *
     * This method test if the cells state will be false(dead) after next generation or if
     * the cell have less than 2 neighbours.
     *
     * Denne metoden tester om cellens tilstand vil være false(død)
     * etter neste generasjon om den har mindre enn 2 naboer.
     */
    @Test
    public void testNeighborsState1() {

        sbm.setBoard(3);
        sbm.setCellStateTrue(1,1);
        boolean expectedState = false;
        boolean actualState = sbm.numNeighbors(1,1);
        org.junit.Assert.assertEquals(expectedState,actualState);
    }

    /**
     * "Any live cell with two live neighbours lives on to the next generation"
     *
     * This method test if the cells state will be true(alive) after next generation, or
     * if the cells have exactly 2 neighbours.
     */
    @Test
    public void testNeighborsState2() {

        sbm.setBoard(3);
        sbm.setCellStateTrue(0,0);
        sbm.setCellStateTrue(1,0);
        sbm.setCellStateTrue(1,1);
        boolean expectedState = true;
        boolean actualState = sbm.numNeighbors(1,1);
        org.junit.Assert.assertEquals(expectedState,actualState);
    }

    /**
     * "Any live cell with three live neighbours lives on to the next generation"
     *
     * This method test if the cells state will be true(alive) after next generation,
     * or if they have exactly 3 neighbours.
     */
    @Test
    public void testNeighborsState3() {

        sbm.setBoard(3);
        sbm.setCellStateTrue(0,0);
        sbm.setCellStateTrue(1,1);
        sbm.setCellStateTrue(1,0);
        sbm.setCellStateTrue(2,0);
        boolean expectedState = true;
        boolean actualState = sbm.numNeighbors(1,1);
        org.junit.Assert.assertEquals(expectedState,actualState);
    }

    /**
     * "Any live cell with more than three live neighbours dies, as if by over-population"
     *
     * This method test if the cells state will be false(dead) after next generation, or if
     * it has three neighbours.
     */
    @Test
    public void testNeighborsState4() {

        sbm.setBoard(3);
        sbm.setCellStateTrue(0,0);
        sbm.setCellStateTrue(1,1);
        sbm.setCellStateTrue(1,0);
        sbm.setCellStateTrue(2,0);
        sbm.setCellStateTrue(2,1);
        boolean expectedState = false;
        boolean actualState = sbm.numNeighbors(1,1);
        org.junit.Assert.assertEquals(expectedState,actualState);
    }

    /**
     * "Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction"
     *
     * This method test if the cells state will be true(alive) after next generation,
     * if its false (dead) or have 3 neighbours.
     */
    @Test
    public void testNeighborsState5() {

        sbm.setBoard(3);
        sbm.setCellStateTrue(0,0);
        sbm.setCellStateTrue(1,0);
        sbm.setCellStateTrue(2,0);
        boolean expectedState = true;
        boolean actualState = sbm.numNeighbors(1,1);
        org.junit.Assert.assertEquals(expectedState,actualState);
    }

    /**
     * This method is a test method, and is used to test if the
     * {@see BoardSizeGUIInputTest#setBoardSizeGUIInputInt()} returns the right size of a board
     * depending on an input.
     *
     * This method create a test board called expectedState which is the
     * size of the board that should be returned from
     * the {@see BoardSizeGUIInputTest#setBoardSizeGUIInputInt()} method.
     */
    @Test
    public void testCorrectBoardSizeInput()
    {
        int testInput = 40;
        int [][] actualState;
        int [][] expectedState = new int[40][40];
        boardSizeInput.setTestInput(testInput);
        boardSizeInput.setBoardSizeGUIInputInt();

        actualState = boardSizeInput.getTestBoard();

        org.junit.Assert.assertArrayEquals(expectedState, actualState);
    }

    /**
     * This method is a test method, and is used to test if the
     * {@see BoardSizeGUIInputTest#setBoardSizeGUIInputInt()} returns a warning if the
     * boardsize input is too high.
     *
     * This method create a test board called expectedState which is the
     * size of the board that is expected return a warning from
     * the {@see BoardSizeGUIInputTest#setBoardSizeGUIInputInt()} method.
     */
    @Test
    public void testTooHighBoardSizeInput()
    {
        int testInput = 5000;
        String actualState = "";
        String expectedState = "Ugyldig input. Tips: Maksimalt antall celler 3000*3000.";
        boardSizeInput.setTestInput(testInput);
        boardSizeInput.setBoardSizeGUIInputInt();
        actualState = boardSizeInput.getTestOutPut();

        org.junit.Assert.assertEquals(expectedState, actualState);
    }


}
