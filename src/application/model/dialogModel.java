package application.model;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Created by Kristian.
 *
 * This class is used whenever a dialog box is needed.
 *
 * This class is a model for how a dialog box should be set up, and is used for
 * easy handling of try/catch blocks and output for user where an error has occurred.
 */
public class DialogModel {

    private Alert alert;
    private TextInputDialog dialog;
    private String contentText;
    private String headerText;

    /**
     * This method is called whenever a warning dialog is needed.
     *
     * This method sets a model for how an alert box should be used.
     */
    public void setWarningDialog() {

        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(getHeaderText());
        alert.setContentText(getContentText());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            alert.close();
        }
    }

    /**
     * This method is called whenever a warning information dialog is needed.
     *
     * This method sets a model for how an information dialog boz should be used.
     */
    public void setInformationDialog() {

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(getHeaderText());
        alert.setContentText(getContentText());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            alert.close();
        }
    }

    /**
     * This method is called whenever a exit information dialog is needed.
     *
     * This method sets a model for how an exit information dialog should be used.
     */
    public void setExitInformationDialog() {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert = new Alert(Alert.AlertType.INFORMATION,"Exit game of life" ,yes, no);

        alert.setContentText(getContentText());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            Platform.exit();
        }
        else if(result.get() == no) {
            alert.close();
        }
    }

    /**
     * This method is called whenever a exit url input dialog is needed.
     *
     * This method sets a model for how an url input dialog should be used.
     *
     * @return URL String.
     */
    public String setUrlInputDialog() {

        dialog = new TextInputDialog("http://example.com");
        dialog.setTitle("Pattern");
        dialog.setHeaderText("Enter web address");
        dialog.setContentText("Url");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
        {
            if(result == null) {
                return null;
            }
            return result.get();
        }
        return null;
    }

    /**
     * This method is called whenever a new content text is to be set.
     *
     * This method sets the content text
     *
     * @param newContextText is the content text that is to be set.
     */
    public void setContentText(String newContextText) {
        contentText = newContextText;
    }

    /**
     * This method is called whenever a new content text is to be reached.
     *
     * This method return the content text.
     * @return the content text.
     */
    public String getContentText() {
        return contentText;
    }

    /**
     * This method is called whenever a new header text is to be set.
     *
     * This method sets the header text.
     *
     * @param newHeaderText is the header text that is to be set.
     */
    public void setHeaderText(String newHeaderText) {
        headerText = newHeaderText;
    }

    /**
     * This method is called whenever a header text is to be reached.
     *
     * This method return the header text to be reached.
     * @return the header text.
     */
    public String getHeaderText() {
        return headerText;
    }
}
