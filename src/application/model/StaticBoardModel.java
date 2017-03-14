package application.model;

import java.util.Random;

/**
 * Created by Kristian and Aleksander
 *
 * StaticBoardModel is the model of a static Board that sets a default Board size.
 * The class makes it possible to resize the Board, cellsize, catch the cellposition for other methods.
 * The class are also the model for the gamerules such as numNeigbours.
 *
 */
@Deprecated
public class StaticBoardModel extends Board
{
    private boolean board[][];

    /**
     * This method is the constructor, and sets a default size of the Board to 100x100 cells.
     */
    public StaticBoardModel() {
        board = new boolean[100][100];
    }

    /**
     * This method is called whenever the user throws new input related to boardsize.
     *
     * @param boardSize The specific size of the Board.
     */
    @Override
    public void setBoard(int boardSize) {
        board = new boolean[boardSize][boardSize];
    }

    /**
     * This method is called whenever the Y axis length of the Board is needed.
     *
     * @return the length of the Y axis of the Board.
     */
    @Override
    public int getBoardYLength() {
        return board[0].length;
    }

    /**
     * This method is called whenever the X axis length of the Board is needed.
     *
     * @return the length of the X axis of the Board.
     */
    @Override
    public int getBoardXLength() {
        return board.length;
    }

    /**
     * This method sets a state of a cell position to true.
     *
     * The method is used whenever the user wants to draw on Canvas.
     *
     * @param x Defines the X position on the Board to be true.
     * @param y Defines the Y position on the Board to be true.
     */
    @Override
    public void setCellStateTrue(int x, int y) {
        if((x >= 0 && x < board.length) && (y >= 0 && y < board[0].length)) {
            board[x][y] = true;
        }
    }

    /**
     * This method sets a state of a cell position to false.
     *
     * The method is used whenever the user wants to erase a specific cell on Canvas.
     *
     * @param x Defines the X position on the Board to be set to false.
     * @param y Defines the Y position on the Board to be set to false.
     */
    @Override
    public void setCellStateFalse(int x, int y) {
        if((x >= 0 && x < board.length) && (y >= 0 && y < board[0].length)) {
            board[x][y] = false;
        }
    }

    /**
     * This method sets a state of several cell positions to false.
     *
     * The method is used whenever the user wants to erase several living cells on Canvas.
     *
     * @param x Defines the X position on the Board to be set to false.
     * @param y Defines the Y position on the Board to be set to false.
     */
    @Override
    public void setCellStatesFalse(int x, int y) {
        setCellStateFalse(x,y);setCellStateFalse(x-1,y-1);setCellStateFalse(x,y-1);
        setCellStateFalse(x+1,y-1);setCellStateFalse(x-1,y);setCellStateFalse(x+1,y);
        setCellStateFalse(x-1,y+1);setCellStateFalse(x,y+1);setCellStateFalse(x+1,y+1);
    }

    /**
     * This method sets a state of several cell positions to true.
     *
     * The method is used whenever a user wants to draw a shape which is rectangular.
     *
     * @param x Defines the X position on the Board to be set to true.
     * @param y Defines the Y position on the Board to be set to true.
     */
    @Override
    public void setSquare(int x, int y) {
        setCellStateTrue(x,y);setCellStateTrue(x+1,y);
        setCellStateTrue(x,y+1);setCellStateTrue(x+1,y+1);
    }

    /**
     * This method sets a state of several cell positions to true.
     *
     * The method is used whenever a user wants to draw a shape which is formed as a glider.
     *
     * @param x Defines the X position on the Board to be set to true.
     * @param y Defines the Y position on the Board to be set to true.
     */
    @Override
    public void setGlider(int x, int y) {
        setCellStateFalse(x,y);setCellStateFalse(x-1,y);setCellStateFalse(x+1,y);
        setCellStateFalse(x+1,y-1);setCellStateFalse(x,y-2);
    }

    /**
     * This method sets a state of several cell positions to true.
     *
     * The method is used whenever a user wants to draw a shape which will be formed as a pulsar.
     *
     * @param x Defines the X position on the Board to be set to true.
     * @param y Defines the Y position on the Board to be set to true.
     */
    @Override
    public void setPulsar(int x, int y) {
        setCellStateTrue(x,y);setCellStateTrue(x+3,y);setCellStateTrue(x+4,y+1);
        setCellStateTrue(x,y+2);setCellStateTrue(x+4,y+2);setCellStateTrue(x+1,y+3);
        setCellStateTrue(x+2,y+2);setCellStateTrue(x+3,y+3);setCellStateTrue(x+4,y+3);
    }

    /**
     * This method sets a state of several cell positions to true.
     *
     * The method is used whenever a user wants to draw a shape which will be formed as a spaceship.
     *
     * @param x Defines the X position on the Board to be set to true.
     * @param y Defines the Y position on the Board to be set to true.
     */
    @Override
    public void setSpaceship(int x, int y) {
        setCellStateTrue(x,y);setCellStateTrue(x+3,y);setCellStateTrue(x+4,y+1);
        setCellStateTrue(x,y+2);setCellStateTrue(x+4,y+2);setCellStateTrue(x+1,y+3);
        setCellStateTrue(x+2,y+3);setCellStateTrue(x+3,y+3);setCellStateTrue(x+4,y+3);
    }

    /**
     * This method sets a state of several cell positions to true.
     *
     * The method is used whenever a user wants to draw a shape which is formed as a gun glider.
     *
     * @param x Defines the X position on the Board to be set to true.
     * @param y Defines the Y position on the Board to be set to true.
     */
    @Override
    public void setGunGlider(int x, int y) {
        setCellStateTrue(x,y);setCellStateTrue(x,y+1);setCellStateTrue(x+1,y);
        setCellStateTrue(x+1,y+1);setCellStateTrue(x+10,y);setCellStateTrue(x+10,y+1);
        setCellStateTrue(x+10,y+2);setCellStateTrue(x+11,y-1);setCellStateTrue(x+11,y+3);
        setCellStateTrue(x+12,y-2);setCellStateTrue(x+12,y+4);setCellStateTrue(x+13,y-2);
        setCellStateTrue(x+13,y+4);setCellStateTrue(x+14,y+1);setCellStateTrue(x+15,y-1);
        setCellStateTrue(x+15,y+3);setCellStateTrue(x+16,y);setCellStateTrue(x+16,y+1);
        setCellStateTrue(x+16,y+2);setCellStateTrue(x+17,y+1);setCellStateTrue(x+20,y-2);
        setCellStateTrue(x+20,y-1);setCellStateTrue(x+20,y);setCellStateTrue(x+21,y-2);
        setCellStateTrue(x+21,y-1);setCellStateTrue(x+21,y);setCellStateTrue(x+22,y-3);
        setCellStateTrue(x+22,y+1);setCellStateTrue(x+24,y-4);setCellStateTrue(x+24,y-3);
        setCellStateTrue(x+24,y+1);setCellStateTrue(x+24,y+2);setCellStateTrue(x+34,y-2);
        setCellStateTrue(x+34,y-1);setCellStateTrue(x+35,y-2);setCellStateTrue(x+35,y-1);
        setCellStateTrue(x+34,y+17);setCellStateTrue(x+34,y+18);setCellStateTrue(x+35,y+17);
        setCellStateTrue(x+35,y+18);setCellStateTrue(x+35,y+20);setCellStateTrue(x+35,y+21);
        setCellStateTrue(x+35,y+22);setCellStateTrue(x+36,y+23);setCellStateTrue(x+37,y+17);
        setCellStateTrue(x+37,y+18);setCellStateTrue(x+37,y+20);setCellStateTrue(x+37,y+21);
        setCellStateTrue(x+37,y+22);setCellStateTrue(x+38,y+18);setCellStateTrue(x+37,y+22);
        setCellStateTrue(x+38,y+18);setCellStateTrue(x+38,y+20);setCellStateTrue(x+39,y+18);
        setCellStateTrue(x+39,y+20);setCellStateTrue(x+40,y+19);
    }

    /**
     * This method is called whenever a specific cell position is needed.
     *
     * @param x Defines the X position on the Board.
     * @param y Defines the Y position on the Board.
     * @return The cell position on the Board.
     */
    @Override
    public boolean getCellState(int x, int y) {
        return board[x][y];
    }

    /**
     * This method is called whenever the user wants to draw a random Board on Canvas.
     *
     * The method loops threw the multidimensional array an sets cell states to true or false depending
     * on density value is higher or lower than the random value.
     *
     * @param density Defines the value to compare to the random value.
     */
    @Override
    public void initRandomBoard(double density) {
        Random random = new Random();

        for (int x = 0; x < board.length; x++)
        {
            for (int y = 0; y < board[0].length; y++)
            {
                if(random.nextDouble() > density)
                {
                    board[x][y] = true;
                }
                else
                {
                    board[x][y] = false;
                }
            }
        }
    }

    /**
     * This method is called whenever the user wants to draw an new empty Board on Canvas.
     *
     * The method loops through the multidimensional array an sets all cell states to dead.
     */
    @Override
    public void initNewBoard() {
        for (int x = 0; x < board.length; x++)
        {
            for (int y = 0; y < board[0].length; y++)
            {
                board[x][y] = false;
            }
        }
    }

    /**
     * This method is called on each iteration of the game. It can be called on two different ways. The user can iterate
     * the game manually one by one by pressing the Generation button, or the Timeline object is set to call the method
     * from time to time based on the rate selected by the user.
     *
     * The method declares a temporary Board to store new values. What type of values (true/false) depends on the number
     * of neighbors you get by the method numNeighbors. The temporary Board will then transfer the new values into the
     * current Board.
     */
    @Override
    public void nextGeneration() {
        boolean tempBoard[][] = new boolean[board.length][board[0].length];

        for (int x = 0; x < board.length; x++)
        {
            for (int y = 0; y < board[0].length; y++)
            {
                tempBoard[x][y] = numNeighbors(x, y);
            }
        }
        for (int x = 0; x < board.length; x++)
        {
            for (int y = 0; y < board[0].length; y++)
            {
                board[x][y] = tempBoard[x][y];
            }
        }
    }

    /**
     * This method is called whenever the nextGeneration method is called.
     *
     * The method iterates through an multidimensional array and sets the states of cells to true or false
     * depending on number of neighbors each cell have based on the game rules.
     *
     * @param x Defines the X position on the Board.
     * @param y Defines the Y position on the Board.
     * @return state of cell (true or false)
     */
    public boolean numNeighbors(int x, int y)
    {
        int neighbors = 0;
        // Enter only if value of Y is higher than 0.
        if(y > 0)
        {
            if(board[x][y - 1])
            {
                neighbors++;
            }
            // Enter only if value of X is higher than 0.
            if(x > 0)
            {
                if(board[x - 1][y - 1]) {
                    neighbors++;
                }
            }
            // Enter only if the value of X is less than the length
            // of Board minus 1(to avoid arrayIndexOutOfBoundsException)
            // and the value of Y is higher than 0.
            if(x < board.length - 1)
            {
                if(board[x + 1][y - 1])
                {
                    neighbors++;
                }
            }
        }

        // Enter only if the value of Y is less than the length
        // of Board.
        if(y < board[0].length - 1)
        {
            if(board[x][y+1])
            {
                neighbors++;
            }
            // Enter only if the value of X is higher than 0 and
            // Y is less than the length of Board minus 1.
            if(x > 0)
            {
                if(board[x - 1][y + 1]) {
                    neighbors++;
                }
            }
            // Enter only if the value of X and Y is less than the length
            // of Board minus 1.
            if(x < board.length - 1)
            {
                if(board[x + 1][y + 1])
                {
                    neighbors++;
                }
            }
        }

        // Enter only if the value of X is higher than 0.
        if(x > 0)
        {
            if(board[x - 1 ][y])
            {
                neighbors++;
            }
        }
        // Enter only if the value of X is less than the length
        // of the Board minus 1.
        if(x < board.length - 1)
        {
            if(board[x + 1][y])
            {
                neighbors++;
            }
        }

        // returns true if number of neighbors is exactly 3.
        if(neighbors == 3)
        {
            return true;
        }
        // returns true if cell is alive and has exactly 2 neighbors.
        if(board[x][y] && neighbors == 2)
        {
            return true;
        }
        // returns false otherwise.
        return false;
    }

    /**
     * This method is called whenever a testMethod is triggered.
     *
     * The method loops through a multidimensional array. If a cell state is true the method appends the String binary
     * a value of 1, otherwise 0.
     *
     * @return a String of binary values.
     */
    @Override
    public String toString() {

        String binary = "";
        for(int x = 0; x < board.length; x++)
        {
            for(int y = 0; y < board[0].length; y++)
            {
                if(board[x][y])
                {
                    binary += "1";
                }
                else {
                    binary += "0";
                }
            }
        }
        return binary;
    }
}
