package application.model;

import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Created by Kristian
 *
 * This class is the model for the colors on the board.
 */
public class ColorModel
{
    final Random rand = new Random(); // Creates a random object.

    /**
     * This method is called whenever a color has been changed
     *
     * @return A random rgb value.
     */
    public Color randomColor()
    {
        final int r = rand.nextInt(255);
        final int g = rand.nextInt(255);
        final int b = rand.nextInt(255);
        final Color randColor = Color.rgb(r,g,b);
        return randColor;
    }
}



