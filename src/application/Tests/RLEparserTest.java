package application.Tests;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aleksander
 *
 * This is a testclass for the method readGameBoard(), which is in the RLEparser.Java class. In this
 * <p> testclass the method has a fabricated input that is exactly the same as the input in the RLE txt files.
 */

public class RLEparserTest
{
    /**
     * This method is a test method for the method.
     * <p> This method is called whenever a test for this method has been initialized.
     *
     * This method has a scanner instead of a reader, which read line by line in a String, that comes
     * <p> from {@see #RLEGliderExample()}, which is exactly the same as a RLE file. The method will then
     * <p> parse the String and create a board that will match the RLE patterns size, and the cell
     * <p> pattern itself inside the board.
     *
     * @return a String that is either 0 or 1 for false and true. This will confirm if the parser
     * <p> works or not.
     *
     * @throws IOException that needs to be handled
     */
    public String readGameBoardTest() throws IOException {

        String line = RLEGliderExample();
        Scanner scanner = new Scanner(RLEGliderExample());

        for(; line.charAt(0) == '#'; line = scanner.nextLine());

        Pattern pattern = Pattern.compile("^x = ([0-9]+), y = ([0-9]+), rule = (.+)$");
        Matcher matcher = pattern.matcher(line);

        if(!matcher.matches()) {
            return null;
        }

        int n = Integer.parseInt(matcher.group(1));
        int m = Integer.parseInt(matcher.group(2));
        boolean [][] importPattern = new boolean [n][m];

        int x = 0;
        int y = 0;
        int count = 0;

        while((line = scanner.nextLine()) != null)
        {
            for(int i = 0; i < line.length(); i++)
            {
                char c = line.charAt(i);

                if(Character.isDigit(c))
                {
                    count = 10*count + (c - '0');
                }
                else if(c == 'o')
                {
                    // Om det er FLERE enn 1 levende celle etter hverandre
                    if(count > 1)
                    {
                        for(int j = 0; j < count; j++)
                        {
                            // levende celler
                            importPattern[x + j][y] = true;
                        }
                    }
                    // Om det IKKE er flere enn 1 levende celle etter hverandre
                    else
                    {
                        importPattern[x][y] = true;
                    }
                    x += Math.max(1, count);
                    count = 0;
                }

                else if(c == 'b')
                {
                    // Døde celler
                    importPattern[x][y] = false;
                    x += Math.max(1, count);
                    count = 0;
                }

                else if(c == '$')
                {
                    // Ny linje
                    y += Math.max(1, count);
                    x=0;
                    count = 0;
                }

                else if(c == '!')
                {
                    String binary = "";
                    for(int x1 = 0; x1 < importPattern.length; x1++)
                    {
                        for(int y1 = 0; y1 < importPattern[0].length; y1++)
                        {
                            if(importPattern[x1][y1])
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
                else
                {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * This method is called whenever a test method needs a RLE file.
     *
     * This method contains a String that is exactly the same as a RLE file.
     *
     * @return String RLEstring, which contains a RLE file pattern.
     */
    public String RLEGliderExample (){
        String RLEstring =
                "#N Glider\n" +
                        "#O Richard K. Guy\n" +
                        "#C The smallest, most common, and first discovered spaceship. Diagonal, has period 4 and speed c/4.\n" +
                        "#C www.conwaylife.com/wiki/index.php?title=Glider\n" +
                        "x = 3, y = 3, rule = B3/S23\n" +
                        "bob$2bo$3o!";

        return RLEstring;
    }
}
