package application.model;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by Aleksander
 *
 * This class controls every filechooser functionality for the game.
 */
public class FileChooserModel {
    private File file; // A file that is either saved or selected.
    private FileChooser fileChooser;
    private String filePath;

    /**
     * This is the constructor for this class, and only makes a filechooser object.
     */
    public FileChooserModel()
    {
        fileChooser = new FileChooser();
    }

    /**
     * This method is called whenever a user selects the "New RLE" option.
     * <p>
     * This method opens a file chooser, handle the users choice by putting it in the
     * variable "file".
     *
     * @param b is a button related to the scene. This is necessary
     * to determine where the standard platform file dialog should show.
     * @param title is the title of the filechooser window.
     * @param textFile is the definition of the type of tile that the filchooser is searching for.
     * @param extensionFilter is the definition of the extension of a file that the filechooser is searching for.
     */
    public void retrieveFileDialog(Button b, String title, String textFile, String extensionFilter)
    {
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(textFile, "*."+extensionFilter));
        file = fileChooser.showOpenDialog(b.getScene().getWindow());
        if(file == null) {
            return;
        }
    }

    /**
     * This method is called whenever a user wants to make a gif file and save it to a spcific location on the computer.
     *
     * this method open a fileChooser and handle the selected location for the file to be saved.
     *
     * @param b is a button related to the scene. This is necessary
     * to determine where the standard platform file dialog should show.
     * @param title is the title of the filechooser window.
     * @param textFile is the definition of the type of tile that the filchooser is searching for.
     * @param extensionFilter is the definition of the extension of a file that the filechooser is searching for.
     */
    public void retriveSaveDialog(Button b, String title, String textFile, String extensionFilter)
    {
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(textFile, "*."+extensionFilter));
        file = fileChooser.showSaveDialog(b.getScene().getWindow());
        if(file == null) {
            return;
        }
        setFilePath(file.getPath());
    }

    /**
     * This method is used whenever a filepath wants to be set to the object.
     *
     * This method sets the path of a selected file.
     * @param inFilePath is the path that is set to the filePath
     */
    public void setFilePath(String inFilePath)
    {
        filePath = inFilePath;
    }

    /**
     * This method is used to get the filepath.
     *
     * This method return the filepath.
     *
     * @return filePath.
     */
    public String getFilePath()
    {
        return filePath;
    }

}
