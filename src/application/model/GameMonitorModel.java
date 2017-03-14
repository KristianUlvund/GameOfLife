package application.model;

import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

/**
 * Created by Aleksander
 *
 * This class is the model of the output for the game monitor textArea
 *
 */
public class GameMonitorModel {


    /**
     * This method is called when the new game button is pressed.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     */
    public void initNewGameOutPut(TextArea outPut)
    {
        outPut.clear();
        outPut.appendText("A new game has been initialized\n " +
                "Your option: \n" +
                "1. Press 'Draw option' to draw your own Game of Life\n" +
                "2. Press 'Random' button to initialize a new random game\n" +
                "3. Press 'Drag option' to drag the board around\n" +
                "4. Press 'Grid' to play without grid");
    }

    /**
     * This method is called when the random button is pressed.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     */
    public void initRandomOutPut(TextArea outPut)
    {
        outPut.clear();
        outPut.appendText("A Random game has been initialized\n " +
                "Your option: \n" +
                "1. Press 'Play' to play the game\n" +
                "2. Press 'Pause' to pause the game\n" +
                "3. Press 'Generation' to play each generation\n");
    }

    /**
     * This method is called when the draw option button is pressed.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     */
    public void initDrawOption(TextArea outPut)
    {
        outPut.clear();
        outPut.appendText("A draw option has been initialized\n " +
                "Your option: \n" +
                "1. Press left mouse button to draw on the board\n" +
                "2. Press right mouse button to erase cell on the board'\n" +
                "3. Press 'Generation' to play each generation\n");
    }

    /**
     * This method is called when the play button is pressed.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     */
    public void initPlay(TextArea outPut)
    {
        outPut.clear();
        outPut.appendText("A sequence of several generation is currently playing\n " +
                "Your option: \n" +
                "1. Press 'pause' button to pause the game\n" +
                "2. Press 'random' button to initialize a new random game\n" +
                "3. Press 'Create GIF' to create a new GIF file\n");
    }

    /**
     * This method is called when the drag option button is pressed.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     */
    public void initDragOption(TextArea outPut)
    {
        outPut.clear();
        outPut.appendText("A drag option has been initialized\n " +
                "Your option: \n" +
                "1. Press left mouse button to drag the board\n" +
                "2. use the scroll function to zoom'\n");
    }

    /**
     * This method is called when the grid option button is pressed.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     * @param toggleButton is a variable for checking if the grid button is pressed.
     */
    public void initGridOption(TextArea outPut, ToggleButton toggleButton)
    {
        if(toggleButton.isSelected()) {
            outPut.clear();
            outPut.appendText("A grid has been initialized\n " +
                    "Your option: \n" +
                    "1. Press the 'grid' button to hide the grid\n");
        }else
        {
            outPut.clear();
            outPut.appendText("The grid is hidden\n " +
                    "Your option: \n" +
                    "1. Press the 'grid' button to show the grid\n");
        }
    }

    /**
     * This method is called when the generation button is pressed.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     */
    public void initNextGeneration(TextArea outPut)
    {
        outPut.clear();
        outPut.appendText("A generation has been initialized\n " +
                "Your option: \n" +
                "1. Press the 'Generation' button to make the next generation\n" +
                "2. Press play to play several sequence of generations'\n" +
                "3. Press 'New Game' button to initialize a new game\n");
    }

    /**
     * This method is called when the fps slider is moved.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     * @param fps is the fps value.
     */
    public void initFPS(TextArea outPut, double fps)
    {
        outPut.clear();
        outPut.appendText("A new FPS rate has been initialized to the generation sequence\n " +
                "Your option: \n" +
                "1. Use the slider to either increase or decrease the fps\n" +
                "\n" +
                "The FPS rate; " + fps);
    }

    /**
     * This method is called when the dropshadow slider is moved.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     * @param dropShadowValue is the dropshadow value
     */
    public void initDropShadow(TextArea outPut, double dropShadowValue)
    {
        outPut.clear();
        outPut.appendText("A new dropShadow rate has been initialized\n " +
                "Your option: \n" +
                "1. Use the slider to either increase or decrease the dropShadow\n" +
                "\n" +
                "The DropShadow rate; " + dropShadowValue);
    }


    /**
     * This method is called when the create gif button is pressed.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     * @param isWorking is a boolean variable used to check if the gifstream is in process or finished.
     */
    public void initGif(TextArea outPut, int isWorking)
    {
        if(isWorking == 0)
        {
            outPut.clear();
            outPut.appendText("Creating a GIF image, wait while the game is processing\n");
        }
        if(isWorking == 1)
        {
            outPut.appendText("The Creation of GIF image was successful");
        }
    }

    /**
     * This method is called when the pause button is pressed.
     *
     * This method adds a text to the game monitor.
     *
     * @param outPut is the game monitor.
     */
    public void initPause(TextArea outPut)
    {
        outPut.clear();
        outPut.appendText("A pause has been initialized\n " +
                "Your option: \n" +
                "1. Press the 'Generation' button to make the next generation\n" +
                "2. Press play to play several sequence of generations'\n" +
                "3. Press 'New Game' button to initialize a new game\n");
    }

    /**
     * This method is called when the inputfield for board size is initialized.
     *
     * This method adds a text to the game monitor.
     *
     * @param boardSize is the board size on the gamecanvas.
     * @param outPut is the game monitor.
     */
    public void initBoardSize(TextArea outPut, int boardSize)
    {
        outPut.clear();
        outPut.appendText("The boardSize has been changed\n " +
                "Your option: \n" +
                "1. Press the 'Generation' button to make the next generation\n" +
                "2. Press play to play several sequence of generations'\n" +
                "3. Press 'New Game' button to initialize a new game\n" +
                " \n" +
                "The boardSize is now " + boardSize + "*" + boardSize);
    }

    public void initGifCancel (TextArea outPut)
    {
        outPut.clear();
        outPut.appendText("You canceled the gif process.");
    }



}
