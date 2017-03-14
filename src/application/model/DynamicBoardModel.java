package application.model;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kristian and Aleksander
 *
 * StaticBoardModel is the model of a static Board that sets a default Board size which is rezisable for users.
 * The class makes it possible to resize the Board, cellsize, catch the cellposition and the state of each cell
 * on the board. The class also makes it possible for the board to iterate generations on the board.
 *
 * The class are also the model for the gamerules such as numNeigbours, and controls the creaton of gif files for
 * the game.
 */
public class DynamicBoardModel extends Board
{
    private double gifXLength = 0; // Factor that is used to calculate the x position to the gif images pixel value.
    private double gifYLength = 0; // Factor that is used to calculate the x position to the gif image pixel value.
    private DialogModel dialogModel; // DialogModel object that is used for the try/catch blockes.
    private int countGeneration;
    ArrayList<ArrayList<Boolean>> board; // The board that is used for the game.
    lieng.GIFWriter gWriter;

    /**
     * This method is the constructor, and sets a default size of the Board to 100x100 cells.
     */
    public DynamicBoardModel() {
        board = new ArrayList<>();
        dialogModel = new DialogModel();
    }

    /**
     * This method is called whenever the user throws new input related to boardsize.
     *
     * @param boardSize The specific size of the Board.
     */
    public void setBoard(int boardSize)
    {
        board.clear();
        for (int x = 0; x < boardSize ; x++)
        {
            board.add(new ArrayList<>());
            for (int y = 0; y < boardSize; y++)
            {
                board.get(x).add(y, false);
            }
        }
    }

    /**
     * This method is called whenever the current state of the board is needed.
     *
     * This method gets the current board and its current state and returns it where
     * the method is called.
     *
     * @return a board of type ArrayList.
     */
    public ArrayList getBoard() {

        return board;
    }

    /**
     * This method is called whenever the Y axis length of the Board is needed.
     *
     * @return the length of the Y axis of the Board.
     */
    public int getBoardYLength()
    {
        return board.get(0).size();
    }

    /**
     * This method is called whenever the X axis length of the Board is needed.
     *
     * @return the length of the X axis of the Board.
     */
    public int getBoardXLength()
    {
        return board.size();
    }

    /**
     * This method is called whenever the @see(# nextGeneration) method is called
     *
     * This method sets the countGeneration value
     *
     */
    public void CountGeneration()
    {
        countGeneration++;
    }

    /**
     * This method is called whenever a method need to set a new value for countGeneration
     *
     * This method set the countGeneration variable.
     *
     * @param newValue is the new value that is to be set to countGeneration.
     */
    public void setCountGeneration(int newValue)
    {
        countGeneration = newValue;
    }

    /**
     *This method is called whenever counting number of generations for the stat functionality.
     *
     * This method returns countGeneration
     * @return countGeneration
     */
    public int getCountGeneration()
    {
        return countGeneration;
    }

    /**
     * This method sets a state of a cell position to true.
     *
     * The method is used whenever the user wants to draw on Canvas.
     *
     * @param x Defines the X position on the Board to be true.
     * @param y Defines the Y position on the Board to be true.
     */
    public void setCellStateTrue(int x, int y)
    {
        if((x >= 0 && x < getBoardXLength()) && (y >= 0 && y < getBoardYLength())) {
            board.get(x).set(y, true);
        }
    }

    /**
     * This method sets a state of a cell position to false.
     *
     * The method is used whenever the user wants to erase a specific cell on Canvas.
     *
     * @param x Defines the X position on the Board to be false.
     * @param y Defines the Y position on the Board to be false.
     */
    public void setCellStateFalse(int x, int y)
    {
        if((x >= 0 && x < getBoardXLength()) && (y >= 0 && y < getBoardYLength())) {
            board.get(x).set(y,false);
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
    public void setCellStatesFalse(int x, int y)
    {
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
    public void setSquare(int x, int y)
    {
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
    public void setGlider(int x, int y)
    {
        setCellStateTrue(x,y);setCellStateTrue(x-1,y);setCellStateTrue(x+1,y);
        setCellStateTrue(x+1,y-1);setCellStateTrue(x,y-2);
    }

    /**
     * This method sets a state of several cell positions to true.
     *
     * The method is used whenever a user wants to draw a shape which will be formed as a pulsar.
     *
     * @param x Defines the X position on the Board to be set to true.
     * @param y Defines the Y position on the Board to be set to true.
     */
    public void setPulsar(int x, int y)
    {
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
    public void setSpaceship(int x, int y)
    {
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
    public void setGunGlider(int x,int y)
    {
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
     * This method is called whenever the need for the state of an specific position on the
     * board is needed.
     *
     * This method takes two parameters which is the position of a cell state that is needed.
     *
     * @param x is the x position on the board.
     * @param y is the y position on the board.
     * @return true or false depending on the position of the cells state.
     */
    public boolean getCellState(int x, int y)
    {
        if(x < 0 && x >= getBoardXLength() && y > 0 && y >= getBoardYLength()) {
            return false;
        }
        return board.get(x).get(y);
    }

    /**
     * This method is called whenever the user wants to draw a random Board on Canvas.
     *
     * The method loops threw the multidimensional array an sets cell states to true or false depending
     * on density value is higher or lower than the random value.
     *
     * @param density Defines the value to compare to the random value.
     */
    public void initRandomBoard(double density) {
        Random random = new Random();

        for (int x = 0; x < board.size(); x++) {
            for (int y = 0; y < getBoardYLength(); y++) {
                if (random.nextDouble() > density) {
                    board.get(x).set(y, true);
                } else {
                    board.get(x).set(y, false);
                }
            }
        }
    }

    /**
     * This method is called whenever the user wants to draw an new empty Board on Canvas.
     *
     * The method loops through the multidimensional array an sets all cell states to dead.
     */
    public void initNewBoard()
    {
        for (int x = 0; x < getBoardXLength(); x++)
        {
            for (int y = 0; y < getBoardYLength(); y++)
            {
                board.get(x).set(y, false);
            }
        }
    }

    /**
     * This method is called whenever a user want to draw a gif, and a gifstream is about to begin.
     *
     * This method create a gif writer object, this object is initialized with a default value of
     * time between frames in 200 milliseconds. The gif is initialized with a default size of 2000*2000 pixels.
     * The filename and the file destination is choosen by the user. This method is best used with the
     * {@see #gifStream(int counter, ArrayList gifBoard)} call after the call of this method.
     *
     * @param filePath is a string, this parameter have to be the path of the gif file.
     */
    public void initGif(String filePath)
    {
        try {
            int width = 2000;
            int height = 2000;

            gWriter = new lieng.GIFWriter(width, height, filePath, 200);
            gWriter.setBackgroundColor(Color.gray);
            gWriter.flush();
            gifXLength = (width / getBoardXLength())-1;
            gifYLength = (height / getBoardYLength())-1;

        }catch(IOException e)
        {

        }
    }

    /**
     * This method is called whenever a sequence of gif pictures are to be placed after each other in a gif file.
     *
     * This method is a recursive method, which starts with creating a temporarily board of
     * arraylists which is tempBoard. This temporarily board is initialized to be as big as the
     * gifBoard, which is the board that the method takes in as a parameter. TempBoard is then
     * placed with the value of gifBoards next generation. The method takes the new tempBoard
     * and use the width and height to get the position for the true (alive cell) values and
     * then placing them into the gif image, and saving the image to the gif sequence.
     *
     * @param counter is the counter for how many time the recursive method is going to call it self(the factor
     * that decides how many images the gif sequence are going to have).
     * @param gifBoard is the board that the method is taking next generation on,  to simulate the real boards
     * next generations so the method can catch the true cells position, and save it to the gifstream.
     */
    public void gifStream(int counter, ArrayList<ArrayList<Boolean>> gifBoard)
    {
        //The end of the recursive method
        if (counter == 0) {
            try {
                gWriter.close();
            }catch (IOException e)
            {
                dialogModel.setHeaderText("Gif stream fault");
                dialogModel.setContentText("Something went wrong while writing to the board to the gif file.");
                dialogModel.setInformationDialog();
            }
        } else {
            try {
                ArrayList<ArrayList<Boolean>> tempBoard = new ArrayList<>();
                // Initializing the temp board to be the same size as the gifBoard (incoming board).
                for (int x = 0; x < gifBoard.size(); x++) {
                    tempBoard.add(new ArrayList<>());
                    for (int y = 0; y < gifBoard.get(0).size(); y++) {
                        tempBoard.get(x).add(y, numNeighbors(x, y, gifBoard));
                    }
                }

                gifBoard = tempBoard;
                // Checking the tempboards width and height
                for (int x = 0; x < gifBoard.size(); x++) {
                    for (int y = 0; y < gifBoard.get(0).size(); y++) {
                        // Cheching every cell in tempboard, if any cell is true, that position is saved in the gif image.
                        if(gifBoard.get(x).get(y) == true)
                        {
                            // Converting cells to pixels for gif image. Making squares equals to cells size to the board, and finding their positions in the gif image,
                            // depending on where the true values are placed in the board.
                            gWriter.fillRect((int)(x * gifXLength),(int)((x+1) * gifXLength),(int)(y * gifYLength),(int)((y+1) * gifYLength) , Color.blue);
                        }
                    }
                }
                //placing the image in the gif sequence.
                gWriter.insertAndProceed();

                // subtracting the counter by one.
                counter--;

                //calling this method, with a counter which is subtracted with one, and the board that has evolved one generation in the future.
                gifStream(counter, gifBoard);
            }catch (IOException e)
            {
                dialogModel.setHeaderText("Gif stream fault");
                dialogModel.setContentText("Something went wrong while writing to the board to the gif file.");
                dialogModel.setInformationDialog();
            }
        }

    }

    /**
     * This method is called on each iteration of the game. It can be called on two different ways. The user can iterate
     * the game manually one by one by pressing the Generation button, or the Timeline object is set to call the method
     * from time to time based on the rate selected by the user.
     *
     * The method declares a temporary Board to store new values. What type of values (true/false) depends on the number
     * of neighbors returned by {@see #numNeighbors(int x, int y, ArrayList board)} method. The temporary Board will then transfer the new values into the
     * current Board.
     */
    public void nextGeneration()
    {
        CountGeneration();
        ArrayList<ArrayList<Boolean>> tempBoard = new ArrayList<>();

        for (int x = 0; x < board.size(); x++)
        {
            tempBoard.add(new ArrayList<>());
            for (int y = 0; y < getBoardYLength(); y++)
            {
                tempBoard.get(x).add(y, numNeighbors(x, y, board));
            }
        }

        board = tempBoard;
        expandBoard();
    }

    /**
     * This method is called whenever a board of type List List need to check the top column for true values, if the board
     * wants to expand.
     *
     * This method checks the first column in the real board for true values, if it finds a true value it adds one to a variable
     * called countTop. The method then checks if the countTop is bigger than 0, if so then the method needs to add a new column on
     * the top of the board.
     */
    public void checkTop()
    {
        int countTop = 0;

        for (int x= 0; x <getBoardXLength() ; x++)
        {
            if(board.get(x).get(0) == true)
            {
                countTop++;
            }
        }
        if (countTop > 0)
        {
            int rowSize = getBoardXLength();

            for (int x = 0; x <rowSize ; x++) {
                board.get(x).add(0, false);
            }
        }
    }

    /**
     * This method is called whenever a board of type List List need to check the right row for true values, if the board
     * wants to expand.
     *
     * This method checks the right row in the real board for true values, if it finds a true value it adds one to a variable
     * called countRight. The method then checks if the countRight is bigger than 0, if so then the method needs to add a new row on
     * the right side of the board.
     */
    public void checkRight()
    {
        int countRight = 0;

        for (int x= 0; x <getBoardYLength() ; x++)
        {
            if(board.get(getBoardXLength()-1).get(x) == true)
            {
                countRight++;
            }
        }
        if (countRight > 0)
        {
            int columnSize = getBoardYLength();
            board.add(getBoardXLength(), new ArrayList<>());

            for (int x = 0; x <columnSize ; x++) {
                board.get(getBoardXLength()-1).add(x, false);
            }
        }
    }

    /**
     * This method is called whenever a board of type List List need to check the bottom column for true values, if the board
     * wants to expand.
     *
     * This method checks the bottom column in the real board for true values, if it finds a true value it adds one to a variable
     * called countBottom. The method then checks if the countBottom is bigger than 0, if so then the method needs to add a new column on
     * the bottom of the board.
     */
    public void checkBottom()
    {
        int countBottom = 0;

        for (int x= 0; x <getBoardXLength() ; x++)
        {
            if(board.get(x).get(getBoardYLength()-1) == true)
            {
                countBottom++;
            }
        }
        if (countBottom > 0)
        {
            int columnSize = getBoardXLength();

            for (int x = 0; x <columnSize ; x++) {
                board.get(x).add(false);
            }
        }
    }

    /**
     * This method is called whenever a board of type List List need to check the left row for true values, if the board
     * wants to expand.
     *
     * This method checks the left row in the real board for true values, if it finds a true value it adds one to a variable
     * called countLeft. The method then checks if the countLeft bigger than 0, if so then the method needs to add a new row on
     * the left side of the board.
     */
    public void checkLeft()
    {
        int countLeft = 0;

        for (int x= 0; x <getBoardYLength() ; x++)
        {
            if(board.get(0).get(x) == true)
            {
                countLeft++;
            }
        }
        if (countLeft > 0)
        {
            int columnSize = getBoardYLength();
            board.add(0, new ArrayList<>());
            for (int x = 0; x <columnSize ; x++)
            {
                board.get(0).add(x, false);
            }
        }
    }

    /**
     * This method is called whenever a board wants to check and expand every side of the board.
     *
     * This method calls on every chech top, bottom, left and right sides of a bord to check for
     * true values and expand every side of the board.
     */
    public void expandBoard()
    {
        checkTop();
        checkLeft();
        checkBottom();
        checkRight();
    }

    /**
     * This method is called whenever the nextGeneration method is called.
     *
     * This method takes in a board and iterates through thet boards multidimensional array and sets
     * the states of cells to true or false depending on number of neighbors each cell have based
     * on the game rules.
     *
     * @param x Defines the X position on the Board.
     * @param y Defines the Y position on the Board.
     * @param board is an multidimensional array that the method is checking for neighbors.
     * @return state of cell (true or false)
     */
    public boolean numNeighbors(int x, int y, ArrayList<ArrayList<Boolean>> board)
        {
        int neighbors = 0;
        // Enter only if value of Y is higher than 0.
        if(y > 0)
        {
            if(board.get(x).get(y-1))
            {
                neighbors++;
            }
            // Enter only if value of X is higher than 0.
            if(x > 0)
            {
                if(board.get(x-1).get(y-1)) {
                    neighbors++;
                }
            }
            // Enter only if the value of X is less than the length
            // of Board minus 1(to avoid arrayIndexOutOfBoundsException)
            // and the value of Y is higher than 0.
            if(x < getBoardXLength() - 1)
            {
                if(board.get(x + 1).get(y - 1))
                {
                    neighbors++;
                }
            }
        }

        // Enter only if the value of Y is less than the length
        // of Board.
        if(y < getBoardYLength() - 1)
        {
            if(board.get(x).get(y + 1))
            {
                neighbors++;
            }
            // Enter only if the value of X is higher than 0 and
            // Y is less than the length of Board minus 1.
            if(x > 0)
            {
                if(board.get(x - 1).get(y + 1)) {
                    neighbors++;
                }
            }
            // Enter only if the value of X and Y is less than the length
            // of Board minus 1.
            if(x < getBoardXLength() - 1)
            {
                if(board.get(x + 1).get(y + 1))
                {
                    neighbors++;
                }
            }
        }

        // Enter only if the value of X is higher than 0.
        if(x > 0)
        {
            if(board.get(x - 1).get(y))
            {
                neighbors++;
            }
        }
        // Enter only if the value of X is less than the length
        // of the Board minus 1.
        if(x < getBoardXLength() - 1)
        {
            if(board.get(x + 1).get(y))
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
        if(board.get(x).get(y) && neighbors == 2)
        {
            return true;
        }
        // returns false otherwise.
        return false;
    }
}
