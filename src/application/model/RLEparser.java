package application.model;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kristian
 * <p>
 * This class is used whenever a user select a RLE file to parse.
 * <p>
 * This class contains a reader, filechooser and a RLE tekst file parser methods. These methods
 * work together to create a filechooser that select a RLE file, which will be read by a
 * reader, than a parser will parse the RLE file and convert it into a pattern
 * in a new board, which will be displayed in the game.
 */
public class RLEparser {

    // Setup for the class.
    private int boardSizeX;
    private int boardSizeY;
    final FileChooser fileChooser = new FileChooser();
    final DialogModel dialog = new DialogModel();
    File file;
    BufferedReader reader;
    String line;
    Pattern pattern;
    Matcher matcher;



    /**
     * This method is called whenever a file from {@see #readGameBoardFromDisk()} has contents.
     * <p>
     * This method primarily takes a file and parse it to a pattern into a fitting board.
     * It creates a reader which read every line in the file. The method will not read lines that start with "#".
     * The method will read the needed size of the pattern, and create a new board with the correspondingly size,
     * and create the pattern in the board depending on the RLE file.
     * <p>
     * @param r takes a Reader that is read line by line, and parsed into a pattern in a fitting board.
     * <p>
     * @return a board with the size of the figure, and with the correspondingly RLE file figure.
     * <p>
     * @throws IOException that needs to be handled
     */
    public ArrayList readGameBoard(Reader r) throws IOException {

        // Creates a reader that contains the file.
        reader = new BufferedReader(r);

        // Creates a variable that call on readers readLine() function, which read every line in the file.
        line = reader.readLine();

        // For loop that jump over every line that start with "#" which is comments in the RLE file.
        for(; line.charAt(0) == '#'; line = reader.readLine());

        // Using the pattern object to make a regular expression and make it match with the RLE file.
        pattern = Pattern.compile("^x = ([0-9]+), y = ([0-9]+), rule = (.+)$");
        matcher = pattern.matcher(line);

        if(!matcher.matches()) {
            return null;
        }

        // Matching the RLE files x axis.
        setBoardSizeX(Integer.parseInt(matcher.group(1)));
        // Matching the RLE files y axis.
        setBoardSizeY(Integer.parseInt(matcher.group(2)));

        ArrayList<ArrayList<Boolean>> importPattern = new ArrayList<>();

        importPattern.clear();
        for (int x = 0; x < getBoardSizeX() ; x++)
        {
            importPattern.add(new ArrayList<>());
            for (int y = 0; y < getBoardSizeY(); y++) {

                importPattern.get(x).add(y, false);
            }
        }

        int x = 0;
        int y = 0;
        int count = 0;

        // As long as next line in the RLE file is not null.
        while((line = reader.readLine()) != null)
        {
            // For loop that runs through every letter in a line in the RLE file.
            for(int i = 0; i < line.length(); i++)
            {
                // Sets the variable "c" to the next lether in the line
                char c = line.charAt(i);

                // If the variable "c" is a digit, then count how many cells is gonna be drawn after each other.
                if(Character.isDigit(c))
                {
                    count = 10*count + (c - '0');
                }

                // If the character "o" appear in the line. o = alive cell.
                else if(c == 'o')
                {
                    // If there exist more than 1 alive cell after each other.
                    if(count > 1)
                    {
                        // implements the alive cell and their position in the new board.
                        for(int j = 0; j < count; j++)
                        {
                            importPattern.get(x + j).set(y,true);
                        }
                    }
                    // If there are none alive cell after each other but only one alive cell.
                    else
                    {
                        importPattern.get(x).set(y,true);
                    }
                    x += Math.max(1, count);
                    count = 0;
                }

                // If the character "b" appear in the line. b = dead cell.
                else if(c == 'b')
                {
                    // Implement a dead cell.
                    importPattern.get(x).set(y,false);
                    x += Math.max(1, count);
                    count = 0;
                }

                // If the character "&" appears, it means a newline.
                else if(c == '$')
                {
                    y += Math.max(1, count);
                    x=0;
                    count = 0;
                }

                // If the character "!" appears, it mean the end of the pattern.
                else if(c == '!')
                {
                    return importPattern;
                }
                else
                {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * This method is called whenever a user have chosen a RLE file, and the {@see #readGameBoard(Reader r)} get called.
     * <p>
     * This method checks if the file which contains the expected RLE file contains anything, if so the {@see #readGameBoard(Reader r)}
     * is called. If the file contains null, a dialog box is showed.
     * <p>
     * @return a boolean 2 dimensional array, which is the new patterns board.
     * @throws IOException
     */
    public ArrayList readGameBoardFromDisk()
    {
        try
        {
            if(file != null)
            {
                return readGameBoard(new FileReader(file));
            }
        }
        catch (IOException e)
        {
            patternOutOfBoundsDialog();
        }
        return null;
    }

    /**
     * This method is called whenever a user have written an URL, and the {@see #readGameBoard(Reader r)} get called.
     * <p>
     * This method checks if the URL which contains the expected data contains anything, if so the {@see #readGameBoard(Reader r)}
     * is called. If the file contains null, a dialog box is showed.
     * <p>
     * @return a imported board from URL
     */
    public ArrayList readGameBoardFromUrl()
    {
        try
        {
            URL url = new URL(dialog.setUrlInputDialog());
            return readGameBoard(new InputStreamReader(url.openStream()));
        }
        catch (MalformedURLException me)
        {
            invalidUrlDialog();
        }
        catch (IOException ie)
        {
            invalidConnectionDialog();
        }
        return null;
    }

    /**
     * This method sets the boardsize width defined by the imported pattern
     *
     * @param x The number of cells horizontal.
     */
    private void setBoardSizeX(int x) {
        boardSizeX = x;
    }

    /**
     * This method sets the boardsize height defined by the imported pattern
     *
     * @param y The number of cells vertical.
     */
    private void setBoardSizeY(int y) {
        boardSizeY = y;
    }

    /**
     * This method gets the boardsize width defined by the imported pattern
     *
     * @return The board size width.
     */
    public int getBoardSizeX() {
        return boardSizeX;
    }

    /**
     * This method gets the boardsize height defined by the imported pattern
     *
     * @return The board size height.
     */
    public int getBoardSizeY() {
        return boardSizeY;
    }

    /**
     * This method is called whenever an connection error occur when {@see #readGameBoardFromUrl()} has been fired.
     */
    public void invalidConnectionDialog()
    {
        dialog.setHeaderText("Connection error");
        dialog.setContentText("Problems opening connection.");
        dialog.setWarningDialog();
    }

    /**
     * This method is called whenever an imported board is too big when {@see #readGameBoardFromDisk()} has been fired.
     */
    public void patternOutOfBoundsDialog()
    {
        dialog.setHeaderText("Something went wrong");
        dialog.setContentText("Please try again.");
        dialog.setWarningDialog();
    }

    /**
     * This method is called whenever an invalid URL has been written when {@see #readGameBoardFromUrl()} has been fired.
     */
    public void invalidUrlDialog()
    {
        dialog.setHeaderText("Invalid URL");
        dialog.setContentText("Please try again.");
        dialog.setInformationDialog();
    }


    /**
     * This method is called whenever the {@see #readGameBoardFromDisk()} has been pressed.
     * <p>
     * @param b A button from the scene to determine where the dialog should occurred.
     */
    public void retrieveFileDialog(Button b)
    {
        fileChooser.setTitle("Select Pattern");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.RLE"));
        file = fileChooser.showOpenDialog(b.getScene().getWindow());
    }
}
