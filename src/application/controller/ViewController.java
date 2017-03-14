package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Created by Kristian and Aleksander
 *
 * Every action done by the user happends in this controller.
 */
public class ViewController implements Initializable
{
    RLEparser               rleParser;
    Timeline                timeline;
    ColorModel              color;
    DialogModel             dialog;
    DynamicBoardModel       board;
    FileChooserModel        fileChooser;
    GameMonitorModel        outPut;
    StatisticController     stats;

    private double cellWidth;
    private double cellHeight;
    private double boardWidth;
    private double boardHeight;
    private double canvasWidth;
    private double canvasHeight;
    private double mousePosXOnDrag;
    private double mousePosYOnDrag;
    private double deltaPosX;
    private double deltaPosY;
    private double temporaryX;
    private double temporaryY;
    private double newTranslateX;
    private double newTranslateY;
    private double zoomFactor;
    private double mousePosXOnZoom;
    private double mousePosYOnZoom;
    private int shape = 1;
    private int currentSelectedTab = 0;

    /**
     * Initialize new objects that is used later in the class.
     */
    public ViewController()
    {
        try
        {
            dialog          = new DialogModel();
            rleParser       = new RLEparser();
            color           = new ColorModel();
            board           = new DynamicBoardModel();
            fileChooser     = new FileChooserModel();
            outPut          = new GameMonitorModel();
            stats           = new StatisticController();
            timeline        = new Timeline(new KeyFrame(Duration.ZERO, event -> {
                board.nextGeneration();
                draw(getGraphicsContext2D());
            }), new KeyFrame(Duration.millis(500)));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML private Canvas gameCanvas;
    @FXML private ColorPicker cPickerAliveCell;
    @FXML private ColorPicker cPickerGrid;
    @FXML private Slider fpsSlider;
    @FXML private Slider dropShadow;
    @FXML private Button pauseBtn;
    @FXML private Button clearBtn;
    @FXML private Button playBtn;
    @FXML private Button gifStopBtn;
    @FXML private ToggleButton dragMode;
    @FXML private ToggleButton drawMode;
    @FXML private ToggleButton gridMode;
    @FXML private TextField textFieldInput;
    @FXML private TabPane tabCanvas;
    @FXML private MenuItem newRleParserFile;
    @FXML private MenuItem newRleParserUrl;
    @FXML private MenuItem closeApplication;
    @FXML private Button gifStartBtn;
    @FXML private Label countDeadCells;
    @FXML private Label countLivingCells;
    @FXML private Label countGenerations;
    @FXML private Canvas statisticCanvas;
    @FXML private TextArea outPutField;

    /**
     * This method contains several value property and are invoked
     * whenever the user changes the value by dragging a slider.
     * <p>
     * The value of dropsShadow gives a shadow effect behind each cell
     * on canvas. A high value equals wider radius and less sharpness etc.
     * <p>
     * The value of fpsSlider changes the rate of timeline and have a
     * minimum of 0 and a maximum of 60 fps.
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources)
    {

        board.setBoard(100);

        gameCanvas.widthProperty().bind(tabCanvas.widthProperty());
        gameCanvas.heightProperty().bind(tabCanvas.heightProperty());

        dropShadow.valueProperty().addListener((observable, oldValue, newValue) -> {
            DropShadow ds = new DropShadow(newValue.doubleValue(), Color.BLACK);
            outPut.initDropShadow(outPutField, newValue.intValue());
            gameCanvas.setEffect(ds);
            if(newValue.doubleValue() == 0.0) {
                gameCanvas.setEffect(null);
            }
            draw(getGraphicsContext2D());
        });

        fpsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            timeline.setRate(newValue.doubleValue());
            outPut.initFPS(outPutField, newValue.intValue());
        });
    } // Initialize()

    /**
     * This method is triggered whenever a user press the new game button.
     * <p>
     * When the button is pressed, three methods is called. The {@see ColorModel#randomColor()} sets a random color on the grid,
     * a new Board is initialized by calling the method {@see DynamicalBoardModel#initNewBoard()} and
     * the {@see #draw(GraphicsContext gc)} method is called which draws the expected result on the Canvas.
     */
    @FXML
    void initNewGame()
    {
        randomColor();
        board.initNewBoard();
        setMinCellSize();
        draw(getGraphicsContext2D());
        stats.resetGeneration(board);
        outPut.initNewGameOutPut(outPutField);
    }

    /**
     * This method is triggered whenever a user press the random game button.
     * <p>
     * When the button is pressed, three methods is called. The {@see ColorModel#randomColor()} sets a random color on the grid,
     * a new Board is initialized by calling the method {@see DynamicBoardModel#initRandomBoard(double Density)} and
     * the {@see #draw(GraphicsContext gc)} method is called which draws the expected result on the Canvas.
     */
    @FXML
    void initRandom()
    {
        randomColor();
        board.initRandomBoard(0.8);
        setMinCellSize();
        draw(getGraphicsContext2D());
        stats.resetGeneration(board);
        outPut.initRandomOutPut(outPutField);
    }

    /**
     * This method is called whenever a user press the grid mode button.
     * <p>
     * The draw method is called which draws the expected result on the Canvas.
     */
    @FXML
    void gridOption()
    {
        draw(getGraphicsContext2D());
        outPut.initGridOption(outPutField, gridMode);
    }

    /**
     * This method is called whenever a user press the drag mode button.
     * <p>
     * The mouseMove() method allows the user to drag the Board around
     * and the mouseScroll() method allows the user too zoom relative to mouse point.
     */
    @FXML
    void dragOption()
    {
        mouseMove();
        mouseScroll();
        outPut.initDragOption(outPutField);

    }

    /**
     * This method is called whenever the user press the draw mode button.
     * <p>
     * The method has several listeners depending on the user behaviour. When
     * the mouse pointer enters canvas the cursor is transformed as a Crosshair.
     * <p>
     * When an event has been triggered the cell positions where the mouse
     * has been clicked or dragged sets to true (Primary button) or false(Secondary button).
     * <p>
     * When a user choose from the pattern menu the getPattern() method is called.
     * Based on users choice of pattern it allows different types of shapes to be drawn.
     */
    @FXML
    void drawOption()
    {
        outPut.initDrawOption(outPutField);
        gameCanvas.setOnMouseEntered(e ->
        {
            if(drawMode.isSelected()) {
                gameCanvas.setCursor(Cursor.CROSSHAIR);
            } else {
                gameCanvas.setCursor(null);
                e.consume();
            }
        });

        gameCanvas.setOnMouseClicked(e ->
        {
            try
            {
                if(drawMode.isSelected())
                {
                    gameCanvas.setCursor(Cursor.CROSSHAIR);

                    // The X and Y position to be drawn on canvas
                    int x = board.getBoardXLength() * ((int) e.getX() - (int) temporaryX) / (int) boardWidth;
                    int y = board.getBoardYLength() * ((int) e.getY() - (int) temporaryY) / (int) boardHeight;

                    if(e.getButton() == MouseButton.PRIMARY) {

                        if (getShape() == 1)
                        {
                            board.setCellStateTrue(x, y);
                        }
                        else if (getShape() == 2)
                        {
                            board.setSquare(x, y);
                        }
                        else if (getShape() == 3)
                        {
                            board.setGlider(x, y);
                        }
                        else if (getShape() == 4)
                        {
                            board.setPulsar(x, y);
                        }
                        else if (getShape() == 5)
                        {
                            board.setSpaceship(x, y);
                        }
                        else if (getShape() == 6)
                        {
                            board.setGunGlider(x, y);
                        }
                    }
                    if(e.getButton() == MouseButton.SECONDARY) {
                        board.setCellStatesFalse(x, y);
                    }
                    draw(getGraphicsContext2D());
                }
                else
                {
                    gameCanvas.setCursor(null);
                    e.consume();
                }
            }
            catch (ArithmeticException ar)
            {
                initializeBoardDialog();
            }
        });

        gameCanvas.setOnMousePressed(e ->
                gameCanvas.setCursor(Cursor.CROSSHAIR));

        gameCanvas.setOnMouseDragged(e ->
        {
            try
            {
                if (drawMode.isSelected())
                {
                    gameCanvas.setCursor(Cursor.CROSSHAIR);

                    // The X and Y position to be drawn on canvas
                    int x = board.getBoardXLength() * ((int) e.getX() - (int) temporaryX) / (int) boardWidth;
                    int y = board.getBoardYLength() * ((int) e.getY() - (int) temporaryY) / (int) boardHeight;

                    if(e.getButton() == MouseButton.PRIMARY)
                    {
                        board.setCellStateTrue(x, y);
                    }
                    else if(e.getButton() == MouseButton.SECONDARY)
                    {
                        board.setCellStatesFalse(x,y);
                    }
                    draw(getGraphicsContext2D());
                }
                else
                {
                    gameCanvas.setCursor(null);
                    e.consume();
                }
            }
            catch(ArithmeticException ar)
            {
                initializeBoardDialog();
            }

        });
    }

    /**
     * This method starts a new timeline whenever the user press the play button.
     */
    @FXML
    void initPlay()
    {
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        outPut.initPlay(outPutField);
    }

    /**
     * This method starts whenever the mouse pointer hoovers the textarea outPutField.
     */
    @FXML
    void initOutPutTextArea()
    {
        outPutField.setEditable(false);
    }

    /**
     * This method pauses the current timeline whenever the user press the pause button.
     */
    @FXML
    void initPause()
    {
        timeline.pause();
        outPut.initPause(outPutField);
    }

    /**
     * This method gets the value of the property onAction.
     * The button's action, which is invoked whenever the button is
     * fired by user calls on the nextGeneration() method and draws
     * the updated Board on canvas.
     */
    @FXML
    void initGen()
    {
        board.nextGeneration();
        draw(getGraphicsContext2D());
        outPut.initNextGeneration(outPutField);
    }

    /**
     * Kommenter her Aleksander
     */
    @FXML
    void initStartGif()
    {
        int isWorking = 0;
        outPut.initGif(outPutField, isWorking);
        fileChooser.retriveSaveDialog(gifStartBtn, "GIF files", "Save GIF", "gif");
        String filePath = fileChooser.getFilePath();
        if(filePath == null) {
            outPut.initGifCancel(outPutField);
            return;
        }
        board.initGif(filePath);
        board.gifStream(5, board.getBoard());

        isWorking++;
        outPut.initGif(outPutField, isWorking);
    }

    /**
     * This method exits the application.
     */
    @FXML
    void initMenuItemClose() {
        exitPlatform();
    }

    /**
     * This method gets the value of the property onAction.
     * The value in this case is a color selected by user.
     * The {@see #draw(GraphicsContext gc)} method is called and draws the grid with the selected color.
     */
    @FXML
    void initColorPickerGrid()
    {
        draw(getGraphicsContext2D());
    }

    /**
     * This method gets the value of the property onAction.
     * The value in this case is a color selected by user.
     * The {@see #draw(GraphicsContext gc)} method is called and draws living cells with the selected color.
     */
    @FXML
    void initAliveCellColor()
    {
        draw(getGraphicsContext2D());
    }

    /**
     * This method gets the value of the property onAction and is called whenever
     * the user types the ENTER key. The value in this case is associated with
     * user input in this text field. The {@see #setBoardSizeGUIInput()} method
     * is called and based on user input it draws an empty Board if the value is
     * valid, otherwise it returns a dialog box.
     */
    @FXML
    void initNewBoardSize() {
        setBoardSizeGUIInput();
        stats.resetGeneration(board);
    }

    /**
     * This method gets the value of the property onAction.
     * The action, which is invoked whenever the MenuItem is
     * fired by user calls on the method fChooser() and imports
     * the selected pattern.
     */
    @FXML
    void initRleParserFile()
    {
        try
        {
            rleParser.retrieveFileDialog(playBtn);
            importPattern(rleParser.readGameBoardFromDisk());
        }
        catch (Exception e)
        {
            invalidFileFormatDialog();
        }
    }

    /**
     * This method will try to run {@see rleParser#readGameBoardFromUrl()}.
     * If file is of invalid format it returns {@see #invalidFileFormatDialog()}.
     */
    @FXML
    void initRleParserUrl() {
        try {
            importPattern(rleParser.readGameBoardFromUrl());
        }catch (Exception e) {
            invalidFileFormatDialog();
        }
    }

    /**
     * This method gets the value of the property onAction.
     * The action, which is invoked whenever the MenuItem is
     * fired by user it calls on the method {@see #setShape(int)}, and allows
     * the user to draw the selected shape on canvas by mouse click.
     */
    @FXML
    void initNormal() {
        setShape(1);
    }

    /**
     * This method gets the value of the property onAction.
     * The action, which is invoked whenever the MenuItem is
     * fired by user it calls on the method {@see #setShape(int)}, and allows
     * the user to draw the selected shape on canvas by mouse click.
     */
    @FXML
    void initSquare() {
        setShape(2);
    }

    /**
     * This method gets the value of the property onAction.
     * The action, which is invoked whenever the MenuItem is
     * fired by user it calls on the method {@see #setShape(int)}, and allows
     * the user to draw the selected shape on canvas by mouse click.
     */
    @FXML
    void initGlider() {
        setShape(3);
    }

    /**
     * This method gets the value of the property onAction.
     * The action, which is invoked whenever the MenuItem is
     * fired by user it calls on the method {@see #setShape(int)}, and allows
     * the user to draw the selected shape on canvas by mouse click.
     */
    @FXML
    void initClaws() {
        setShape(4);
    }

    /**
     * This method gets the value of the property onAction.
     * The action, which is invoked whenever the MenuItem is
     * fired by user it calls on the method {@see #setShape(int)}, and allows
     * the user to draw the selected shape on canvas by mouse click.
     */
    @FXML
    void initSpaceship() {
        setShape(5);
    }

    /**
     * This method gets the value of the property onAction.
     * The action, which is invoked whenever the MenuItem is
     * fired by user it calls on the method {@see #setShape(int)}, and allows
     * the user to draw the selected shape on canvas by mouse click.
     */
    @FXML
    void initGliderGun() {
        setShape(6);
    }

    /**
     * This method is called whenever a change has occurred
     * and a new drawing is expected to update the canvas.
     *
     * @param gc used to issue draw calls on canvas.
     */
    public void draw(GraphicsContext gc)
    {
        stats.initStats(countLivingCells, countDeadCells, countGenerations, board.getBoard(), board.getCountGeneration());
        boardSizeHeight();
        boardSizeWidth();
        canvasHeight();
        canvasWidth();
        clearCanvas(gc);
        getTranslatePosX();
        getTranslatePosY();

        gc.setFill(cPickerAliveCell.getValue());
        for(int x = 0; x < board.getBoardXLength(); x++)
        {
            for(int y = 0; y < board.getBoardYLength(); y++)
            {
                if(board.getCellState(x,y))
                {
                    gc.fillRect(newTranslateX, newTranslateY, cellWidth, cellHeight);
                }
                newTranslateY += cellHeight;
            }
            newTranslateY = temporaryY;
            newTranslateX += cellWidth;
        }
        if(gridMode.isSelected())
        {
            if(cellHeight > 2) {
                grid(gc);
            }
        }
    } // draw()

    /**
     * This method is called whenever the user has selected the {@see #gridMode} button.
     * <p>
     * The method draws the grid based on number of rows and columns of the current Board.
     *
     * @param gc used to issue draw calls on canvas.
     */
    public void grid(GraphicsContext gc)
    {
        getTranslatePosX();
        getTranslatePosY();

        gc.setStroke(cPickerGrid.getValue());
        gc.setLineWidth(1);
        for (int col = 0; col <= board.getBoardYLength(); col++)
        {
            double yPos = col * boardHeight/board.getBoardYLength();
            gc.strokeLine(newTranslateX, newTranslateY+yPos, boardWidth+newTranslateX, newTranslateY+yPos); // horisontalt
        }

        for (int row = 0; row <= board.getBoardXLength(); row++)
        {
            double xPos = row * boardWidth/board.getBoardXLength();
            gc.strokeLine(newTranslateX+xPos, newTranslateY, newTranslateX+xPos, boardHeight+newTranslateY); // vertikalt
        }
    } // grid()

    /**
     * This method is called whenever the user has selected the {@see #initRandom},
     * {@see #initNewGame} button, or by calling on the method {@see #importPattern(ArrayList newPattern)}
     * or {@see ViewController#setBoardSizeGUIInput()}.
     * <p>
     * The method randomly set a color value to the grid and the living cells.
     */
    public void randomColor()
    {
        cPickerAliveCell.setValue(color.randomColor());
        cPickerGrid.setValue(color.randomColor());
    } // randomColor()

    /**
     * This method is called whenever the user has triggered the {@see #initRleParserFile()} method or
     * {@see #initRleParserUrl()}.
     * <p>
     * The method runs {@see RLEparser#readGameBoardFromDisk()} and returns a tray
     * with values ​​based on the file being parsed. The returned Board is copied
     * to a new Board. It then loops through the new Board and sets cells to true or false.
     *
     * @param newPattern the board to be imported.
     */
    public void importPattern(ArrayList<ArrayList<Boolean>> newPattern)
    {
        if(newPattern == null) {
            return;
        }

        if(newPattern.size() < board.getBoardXLength())
        {
            board.setBoard(board.getBoardXLength()+50);
        }
        else if(rleParser.getBoardSizeX() > rleParser.getBoardSizeY())
        {
            board.setBoard(rleParser.getBoardSizeX()+50);
        }
        else
        {
            board.setBoard(rleParser.getBoardSizeY()+50);
        }

        for (int x = 0; x < newPattern.size(); x++)
        {
            for (int y = 0; y < newPattern.get(0).size(); y++)
            {
                if(newPattern.get(x).get(y))
                {
                    board.setCellStateTrue(x+50,y+50);
                }
            }
        }

        setMinCellSize();
        randomColor();
        draw(getGraphicsContext2D());
    } // importPattern()

    /**
     * This method is called whenever the user has selected the {@see #dragMode} ToggleButton.
     * <p>
     * The method has a scroll event which is triggered when the user scrolls the mouse.
     * The value of {@see #zoomFactor} variable changes based on which way the user scrolls.
     * The {@see #cellHeight} and {@see #cellWidth} variable changes by multiply it with the
     * current zoomFactor value. The {@see #temporaryX} and {@see #temporaryY} variable changes
     * based on the position of {@see #mousePosXOnZoom} and {@see #mousePosYOnZoom} and the
     * changing zoom factor value.
     *
     */
    public void mouseScroll()
    {
        if(dragMode.isSelected())
        {
            gameCanvas.setOnScroll(event ->
            {
                zoomFactor = 1.15;
                double deltaY = event.getDeltaY();

                if(deltaY < 0) {
                    zoomFactor = 2.0 - zoomFactor;
                }
                cellHeight *= zoomFactor;
                cellWidth *= zoomFactor;
                temporaryX -= (mousePosXOnZoom - temporaryX) * (zoomFactor - 1);
                temporaryY -= (mousePosYOnZoom - temporaryY) * (zoomFactor - 1);
                draw(getGraphicsContext2D());
                event.consume();
            });
        }
    } // mouseScroll

    /**
     * This method is called whenever the user has selected the {@see #dragMode} ToggleButton.
     * <p>
     * The method has several listeners depending on the user behaviour.
     * <p>
     * When the mouse button has been pressed a setOnMousePressed event is triggered.
     * The {@see #deltaPosX} and {@see #deltaPosY} gets new values which is later used
     * while dragging the canvas.
     * <p>
     * When the setOnMouseDragged event is triggered the {@see #temporaryX} and
     * {@see #temporaryY} values changes repeatedly while the user drags the Board
     * inside canvas. This is caused by adding the current {@see #mousePosXOnDrag}
     * and {@see #mousePosYOnDrag} values with the values of deltaPosX and deltaPosY.
     *
     */
    public void mouseMove()
    {
        if(dragMode.isSelected()) {
            gameCanvas.setOnMouseEntered(event ->
            {
                gameCanvas.setCursor(Cursor.OPEN_HAND);
                event.consume();
            });

            gameCanvas.setOnMousePressed(event ->
            {
                gameCanvas.setCursor(Cursor.CLOSED_HAND);

                deltaPosX = temporaryX - event.getSceneX();
                deltaPosY = temporaryY - event.getSceneY();

                draw(getGraphicsContext2D());
                event.consume();
            });

            gameCanvas.setOnMouseMoved(event ->
            {
                mousePosXOnZoom = event.getSceneX();
                mousePosYOnZoom = event.getSceneY();
                event.consume();
            });

            gameCanvas.setOnMouseDragged(event ->
            {
                gameCanvas.setCursor(Cursor.CLOSED_HAND);

                mousePosXOnDrag = event.getSceneX();
                mousePosYOnDrag = event.getSceneY();

                temporaryX = mousePosXOnDrag + deltaPosX;
                temporaryY = mousePosYOnDrag + deltaPosY;

                setTranslatePosX(temporaryX);
                setTranslatePosY(temporaryY);

                draw(getGraphicsContext2D());
                event.consume();
            });

            gameCanvas.setOnMouseReleased(event ->
            {
                gameCanvas.setCursor(Cursor.OPEN_HAND);
                event.consume();
            });
        }
    } // mouseMove()

    /**
     * This method is called whenever the {@see #draw(GraphicsContext gc)} method has been called.
     * <p>
     * This method set a new width value on canvas.
     */
    public void boardSizeWidth() {
        boardWidth = board.getBoardXLength()*cellWidth;
    }

    /**
     * This method is called whenever the {@see #draw(GraphicsContext gc)} method has been called.
     * <p>
     * This method set a new height value on canvas.
     */
    public void boardSizeHeight() {
        boardHeight = board.getBoardYLength()*cellHeight;
    }

    /**
     * This method is called whenever the {@see #draw(GraphicsContext gc)} method has been called.
     * <p>
     * This method switches witch canvas the board will be drawn on.
     */
    public void canvasWidth() {
        if(currentSelectedTab == 1) {
            canvasWidth = statisticCanvas.getWidth();
        }
        else {
            canvasWidth = gameCanvas.getWidth();
        }
    }

    /**
     * This method is called whenever the {@see #draw(GraphicsContext gc)} method has been called.
     * <p>
     * This method switches witch canvas the board will be drawn on.
     */
    public void canvasHeight() {
        if(currentSelectedTab == 1) {
            canvasHeight = statisticCanvas.getHeight();
        }
        else {
            canvasHeight = gameCanvas.getHeight();
        }
    }

    /**
     * This method is called whenever the {@see #draw(GraphicsContext gc)} method has been called.
     * <p>
     * This method set a new width value on canvas.
     *
     * @return the GraphicsContext associated with the canvas.
     */
    public GraphicsContext getGraphicsContext2D()
    {
        return gameCanvas.getGraphicsContext2D();
    }

    /**
     * This method is called on every time a change has happened.
     * <p>
     * The method clears the canvas.
     *
     * @param gc used to issue draw calls on canvas.
     */
    public void clearCanvas(GraphicsContext gc) {
        gc.clearRect(0,0,canvasWidth,canvasHeight);
    }

    /**
     * This method is called whenever a new, random or imported Board is initialized.
     * <p>
     * The method makes sure the size of the cells exactly covers the size of the canvas.
     */
    public void setMinCellSize()
    {
        cellWidth = gameCanvas.getWidth()/board.getBoardXLength();
        cellHeight = gameCanvas.getHeight()/board.getBoardYLength();

        if(cellHeight < cellWidth) {
            cellHeight = cellWidth;
        }
    } // SetMinCellSize()

    /**
     * This method is called whenever the X position of Board has changed.
     *<p>
     * The method sets the {@see #temporaryX} value to the Board.
     *<p>
     * @param xPos: X position of the current Board
     */
    public void setTranslatePosX(double xPos)
    {
        temporaryX = xPos;
    }

    /**
     * This method is called whenever the {@see #draw(GraphicsContext gc)} method is executed.
     * <p>
     * The method gets the {@see #newTranslateX} position of the Board.
     */
    private void getTranslatePosX()
    {
        newTranslateX = temporaryX;
    }

    /**
     * This method is called whenever the Y position of Board has changed.
     *<p>
     * The method sets the {@see #temporaryY} value to the Board.
     *<p>
     * @param yPos: Y position of the current Board
     */
    public void setTranslatePosY(double yPos)
    {
        temporaryY = yPos;
    }

    /**
     * This method is called whenever the {@see #draw(GraphicsContext gc)} method is executed.
     * <p>
     * The method gets the {@see #newTranslateY} position of the Board.
     */
    private void getTranslatePosY()
    {
        newTranslateY = temporaryY;
    }

    /**
     * This method is called whenever the {@see #initNormal} etc has been pressed.
     * <p>
     * The method sets a new unique int to the {@see #shape} value.
     * <p>
     * @param inShape The unique int value to be set.
     */
    public void setShape(int inShape)
    {
        shape = inShape;
    }

    /**
     * This method is called whenever the {@see #drawMode} has
     * been selected and the user draws on the canvas.
     * <p>
     * The method gets the unique int value set by the
     * {@see #setShape(int inShape)} method.
     *
     * @return a shape.
     */
    public double getShape()
    {
        return shape;
    }

    /**
     * This method is called whenever the {@see #initNewBoardSize()} event has been triggered.
     * <p>
     * The method gives a dialog if the input value is negative,
     * has invalid type og data or is higher than 3000.
     */
    public void setBoardSizeGUIInput()
    {
        try
        {
            // The value of the users input
            int size = Integer.parseInt(textFieldInput.getText().trim());
            outPut.initBoardSize(outPutField, size);

            if(size > 3000)
            {
                invalidInputDialog();
            }
            else
            {
                board.setBoard(size);
                textFieldInput.clear();
                setTranslatePosX(0);
                setTranslatePosY(0);
                randomColor();
                setMinCellSize();
                draw(getGraphicsContext2D());
            }
        }
        catch (NumberFormatException e)
        {
            invalidCharacterDialog();
        }
        catch(NegativeArraySizeException e)
        {
            invalidNumberDialog();
        }
    }

    /**
     * This method is called whenever the {@see #setBoardSizeGUIInput()} event has been triggered.
     * <p>
     * The method executes a dialog if the input value is a invalid character.
     */
    public void invalidNumberDialog()
    {
        textFieldInput.clear();
        dialog.setHeaderText("Invalid character.");
        dialog.setContentText("Enter a number.");
        dialog.setInformationDialog();
    }

    /**
     * This method is called whenever the {@see #setBoardSizeGUIInput()} event has been triggered.
     * <p>
     * The method executes a dialog if the input value is a invalid number.
     */
    public void invalidCharacterDialog()
    {
        textFieldInput.clear();
        dialog.setHeaderText("Invalid number.");
        dialog.setContentText("Enter a positive number.");
        dialog.setInformationDialog();
    }

    /**
     * This method is called whenever the {@see #setBoardSizeGUIInput()} event has been triggered.
     * <p>
     * The method executes a dialog if the input value is higher than 3000.
     */
    public void invalidInputDialog()
    {
        textFieldInput.clear();
        dialog.setHeaderText("Invalid input.");
        dialog.setContentText("The maximum number of cells 3000 * 3000.");
        dialog.setInformationDialog();
    }

    /**
     * This method is called whenever the {@see #initRleParserFile()}
     * or {@see #initRleParserUrl()} event has been triggered.
     * <p>
     * The method executes a dialog if the output is of invalid format.
     */
    public void invalidFileFormatDialog()
    {
        dialog.setHeaderText("Wrong file format.");
        dialog.setContentText("The program supports only RLE. files.");
        dialog.setInformationDialog();
    }

    /**
     * This method is called whenever a user draws before a board has been created.
     * <p>
     * The method gives the user a warning dialog.
     */
    public void initializeBoardDialog()
    {
        dialog.setContentText("Please create a Board until you draw.");
        dialog.setWarningDialog();
    }

    /**
     * This method is called whenever the {@see #initMenuItemClose() } event has been fired.
     * <p>
     * The method gives the user a exit game dialog.
     */
    public void exitPlatform()
    {
        dialog.setContentText("Are you sure you want to exit Game of life?");
        dialog.setExitInformationDialog();
    }

} // ViewController()
