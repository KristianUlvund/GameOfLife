package application.Tests;

import application.model.DynamicBoardModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kristian
 */
public class DynamicBoardModelTest {

    DynamicBoardModel dbm;

    @Before
    public void setUp() {
        dbm = new DynamicBoardModel();
    }

    /**
     * This method tests whether the board expands if a living cell is at top of the board.
     */
    @Test
    public void testCheckTop(){
        dbm.setBoard(5);
        dbm.setCellStateTrue(2,2);
        dbm.setCellStateTrue(2,1);
        dbm.setCellStateTrue(2,0);
        dbm.setCellStateTrue(1,0);
        dbm.setCellStateTrue(0,1);

        for (int i = 0; i < 1; i++) {
            dbm.nextGeneration();
        }

        int expectedResult = 6;
        int actualResult = dbm.getBoardYLength();
        org.junit.Assert.assertEquals(expectedResult,actualResult);
    }

    /**
     * This method tests whether the board expands if a living cell is at right side of the board.
     */
    @Test
    public void testCheckRight(){
        dbm.setBoard(5);
        dbm.setCellStateTrue(2,2);
        dbm.setCellStateTrue(3,2);
        dbm.setCellStateTrue(4,2);
        dbm.setCellStateTrue(4,1);
        dbm.setCellStateTrue(3,0);

        for (int i = 0; i < 1; i++) {
            dbm.nextGeneration();
        }

        int expectedResult = 6;
        int actualResult = dbm.getBoardXLength();
        org.junit.Assert.assertEquals(expectedResult,actualResult);
    }

    /**
     * This method tests whether the board expands if a living cell is at bottom of the board.
     */
    @Test
    public void testCheckBottom(){
        dbm.setBoard(5);
        dbm.setCellStateTrue(1,3);
        dbm.setCellStateTrue(2,3);
        dbm.setCellStateTrue(3,3);
        dbm.setCellStateTrue(3,2);
        dbm.setCellStateTrue(2,1);

        for (int i = 0; i < 1; i++) {
            dbm.nextGeneration();
        }

        int expectedResult = 6;
        int actualResult = dbm.getBoardYLength();
        org.junit.Assert.assertEquals(expectedResult,actualResult);
    }

    /**
     * This method tests whether the board expands if a living cell is at left side of the board.
     */
    @Test
    public void testCheckLeft() {
        dbm.setBoard(5);
        dbm.setCellStateTrue(2,2);
        dbm.setCellStateTrue(1,2);
        dbm.setCellStateTrue(0,2);
        dbm.setCellStateTrue(0,1);
        dbm.setCellStateTrue(1,0);

        for (int i = 0; i < 1; i++) {
            dbm.nextGeneration();
        }

        int expectedResult = 6;
        int actualResult = dbm.getBoardXLength();
        org.junit.Assert.assertEquals(expectedResult,actualResult);
    }
}