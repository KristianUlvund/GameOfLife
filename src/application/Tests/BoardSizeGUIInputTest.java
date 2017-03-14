package application.Tests;

/**
 * Created by Aleksander
 *
 * This is a testclass for the method BoardSizeGUIInput() which exist in the ViewController.java.
 * This class contains a default constructor, a method setBoardSizeGUIINPUT() which is the method
 * that is called in the programTest.java class.
 */
public class BoardSizeGUIInputTest {

    private int testInputInt;
    private int [][] testBoard;
    private String testOutPut = "";

    /**
     * This method is called to test if the method works as it should be. This method is used
     * for Unit testing.
     * <p>
     * This method check if the testInput is higher than 3000. If it is, the "board" is too big.
     * <p>
     * Else, were creating the boardsize in {@see #setTestBoard(int testBoardInput)}.
     */
    public void setBoardSizeGUIInputInt() {
        try{
            if(testInputInt > 3000) {
                setTestOutPut("Ugyldig input. Tips: Maksimalt antall celler 3000*3000.");
            }
            else {
                setTestBoard(testInputInt);
            }
        }catch (NumberFormatException e) {
            setTestOutPut("Ugyldig tegn. Tips: Skriv inn et tall.");
        }
        catch (NegativeArraySizeException e) {
            setTestOutPut("Ugyldig tall. Tips: Skriv inn et positivt tall.");
        }
    }

    /**
     * This method sets the test input whenever a test want to create an input
     * <p> for a new test.
     *
     * @param testInput is an int that is used in the for creating the testboard.
     */
    public void setTestInput (int testInput)
    {
        this.testInputInt = testInput;
    }

    /**
     * This method return the test input.
     *
     * @return testInput is an int that us used for creating the testboard.
     */
    public int getTestInput ()
    {
        return testInputInt;
    }

    /**
     * This method is called whenever the setBoardSizeGUIInputInt() method want to produce
     * <p> an output for any test.
     *
     * @param testOutPut is a String that will either confirm or disapprove the test
     */
    public void setTestOutPut (String testOutPut)
    {
        this.testOutPut = testOutPut;
    }

    /**
     * This method return the test output.
     *
     * @return the test output that is a String used for confirm or disapprove a test.
     */
    public String getTestOutPut ()
    {
        return testOutPut;
    }

    /**
     * This method is called whenever a test method wants to set the value to
     * <p> the test board.
     *
     * @param testBoardInput is an int 2 dimensional array, that is used for testing
     * the setBoardSizeGUIInputInt() method.
     */
    public void setTestBoard (int testBoardInput)
    {
        int [][] newTestBoard = new int [testBoardInput][testBoardInput];
        testBoard = newTestBoard;
    }

    /**
     * This method return the testboard.
     *
     * @return testBoard that is used for testing the setBoardSizeGUIInputInt() method.
     */
    public int[][] getTestBoard ()
    {
        return testBoard;
    }
}
