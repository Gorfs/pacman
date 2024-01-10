package gui;

import java.util.Objects;
import java.util.Random;

import config.Cell;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.MazeState;
import model.PacMan;


/**
 * Class CellGraphicsFactory is used to update the maze
 */
public class CellGraphicsFactory {
    private final double scale;
    private final Color colorWalls;
    private static final Image cherryImage, strawberryImage, orangeImage, appleImage, melonImage, galaxianImage, bellImage, keyImage;

    static{
        cherryImage = new Image(Objects.requireNonNull(CellGraphicsFactory.class.getResourceAsStream("/cherry.png")));
        strawberryImage = new Image(Objects.requireNonNull(CellGraphicsFactory.class.getResourceAsStream("/strawberry.png")));
        orangeImage = new Image(Objects.requireNonNull(CellGraphicsFactory.class.getResourceAsStream("/orange.png")));
        appleImage = new Image(Objects.requireNonNull(CellGraphicsFactory.class.getResourceAsStream("/apple.png")));
        melonImage = new Image(Objects.requireNonNull(CellGraphicsFactory.class.getResourceAsStream("/melon.png")));
        galaxianImage = new Image(Objects.requireNonNull(CellGraphicsFactory.class.getResourceAsStream("/galaxian.png")));
        bellImage = new Image(Objects.requireNonNull(CellGraphicsFactory.class.getResourceAsStream("/bell.png")));
        keyImage = new Image(Objects.requireNonNull(CellGraphicsFactory.class.getResourceAsStream("/key.png")));
    }


    /**
     * Constructor used to set up the scaling
     * @param scale value of the base scaling
     */
    public CellGraphicsFactory(double scale) {
        this.scale = scale;
        //Set random color for walls
        Color[] colors = {Color.BLUE, Color.RED, Color.PINK, Color.ORANGE, Color.CYAN, Color.YELLOW, Color.GREEN, Color.PURPLE, Color.WHITE, Color.BROWN};
        Random rand = new Random();
        int n = rand.nextInt(10);
        colorWalls = colors[n];
    }

    /**
     * Method used to create a group of graphics for a cell.
     * @param state current maze state.
     * @param pos position of the current cell.
     * @return a method that update the group of graphic that represent a cell.
     */
    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
        boolean wall = false;
        // New group to stock every graphics for one cell
        var group = new Group();
        // Place it to the right position
        group.setTranslateX(pos.x()*scale);
        group.setTranslateY(pos.y()*scale);
        // Get cell content
        var cell = MazeState.getConfig().getCell(pos);
        // Draw a dot
        var dot = new Circle();
        group.getChildren().add(dot);
        dot.setRadius(switch (cell.initialContent()) {
            case DOT -> scale/10;
            case ENERGIZER -> scale/5;
            case NOTHING -> 0;
            case WALL -> scale/4;});

        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);
        var cherryImageView = new ImageView(cherryImage);
        cherryImageView.setFitWidth(scale);
        cherryImageView.setFitHeight(scale);
        cherryImageView.setVisible(false);
        group.getChildren().add(cherryImageView);
        var strawberryImageView = new ImageView(strawberryImage);
        strawberryImageView.setFitWidth(scale);
        strawberryImageView.setFitHeight(scale);
        strawberryImageView.setVisible(false);
        group.getChildren().add(strawberryImageView);
        var orangeImageView = new ImageView(orangeImage);
        orangeImageView.setFitWidth(scale);
        orangeImageView.setFitHeight(scale);
        orangeImageView.setVisible(false);
        group.getChildren().add(orangeImageView);
        var appleImageView = new ImageView(appleImage);
        appleImageView.setFitWidth(scale);
        appleImageView.setFitHeight(scale);
        appleImageView.setVisible(false);
        group.getChildren().add(appleImageView);
        var melonImageView = new ImageView(melonImage);
        melonImageView.setFitWidth(scale);
        melonImageView.setFitHeight(scale);
        melonImageView.setVisible(false);
        group.getChildren().add(melonImageView);
        var galaxianImageView = new ImageView(galaxianImage);
        galaxianImageView.setFitWidth(scale);
        galaxianImageView.setFitHeight(scale);
        galaxianImageView.setVisible(false);
        group.getChildren().add(galaxianImageView);
        var bellImageView = new ImageView(bellImage);
        bellImageView.setFitWidth(scale);
        bellImageView.setFitHeight(scale);
        bellImageView.setVisible(false);
        group.getChildren().add(bellImageView);
        var keyImageView = new ImageView(keyImage);
        keyImageView.setFitWidth(scale);
        keyImageView.setFitHeight(scale);
        keyImageView.setVisible(false);
        group.getChildren().add(keyImageView);
        // if there is a wall in the cell
        var wallX = new Rectangle();
        var wallY = new Rectangle();
        if (cell.initialContent() == Cell.Content.WALL) {
            wall = true;
            // set wall color
            dot.setFill(colorWalls);
            if (pos.x() < state.getWidth() - 1) {
                // Get position of the right cell
                IntCoordinates right = pos.toRealCoordinates(1.0).plus(RealCoordinates.EAST_UNIT).round();
                if (MazeState.getConfig().getCell(right).initialContent() == Cell.Content.WALL) {
                    // Draw the link between walls if the right cell is also a wall
                    wallX.setHeight(scale/2);
                    wallX.setWidth(scale);
                    wallX.setY(scale/4);
                    wallX.setX(scale/2);
                    // set wall color
                    wallX.setFill(colorWalls);
                    group.getChildren().add(wallX);
                }
            } if (pos.y() < state.getHeight() - 1) {
                // Get position of the bottom cell
                IntCoordinates bottom = pos.toRealCoordinates(1.0).plus(RealCoordinates.SOUTH_UNIT).round();
                if (MazeState.getConfig().getCell(bottom).initialContent() == Cell.Content.WALL) {
                    // Draw the link between walls if the right cell is also a wall
                    wallY.setHeight(scale);
                    wallY.setWidth(scale/2);
                    wallY.setY(scale/2);
                    wallY.setX(scale/4);
                    // set wall color
                    wallY.setFill(colorWalls);
                    group.getChildren().add(wallY);
                }
            }
        } else {
            // If there isn't a wall in the cell
            dot.setFill(Color.YELLOW);
        }

        boolean finalWall = wall;
        return new GraphicsUpdater() {
            final float[] timer = {0F,1F,0F};
            int i = 0;
            boolean t = true;
            /**
             * Method that update the graphics for each cell
             * @param deltaT time between two frames in nanoseconds
            */
            @Override
            public void update(long deltaT) {
                dot.setVisible(!state.getGridState(pos));
                /* Just a try to change the color of the walls every second (to delete if not useful)
                 */
                if (PacMan.INSTANCE.isEnergized() && finalWall){
                    if (t) {
                        timer[i] += (float) (deltaT * 1E-9);
                        if (timer[i] >= 1) timer[i] = 1;
                    } else {
                        timer[i] -= (float) (deltaT * 1E-9);
                        if (timer[i] <= 0) timer[i] = 0;
                    }
                    var temp = Color.color(timer[0], timer[1], timer[2]);
                    dot.setFill(temp);
                    wallX.setFill(temp);
                    wallY.setFill(temp);
                    if (timer[i] == 1 || timer[i] == 0) {
                        i++;
                        t = !t;
                        if (i >= 3) i = 0;
                    }
                }
                cherryImageView.setVisible(state.getFruitsGridState(pos) && MazeState.getFruit(MazeState.id).getName().equals("cherry"));
                strawberryImageView.setVisible(state.getFruitsGridState(pos) && MazeState.getFruit(MazeState.id).getName().equals("strawberry"));
                orangeImageView.setVisible(state.getFruitsGridState(pos) && MazeState.getFruit(MazeState.id).getName().equals("orange"));
                appleImageView.setVisible(state.getFruitsGridState(pos) && MazeState.getFruit(MazeState.id).getName().equals("apple"));
                melonImageView.setVisible(state.getFruitsGridState(pos) && MazeState.getFruit(MazeState.id).getName().equals("melon"));
                galaxianImageView.setVisible(state.getFruitsGridState(pos) && MazeState.getFruit(MazeState.id).getName().equals("galaxian"));
                bellImageView.setVisible(state.getFruitsGridState(pos) && MazeState.getFruit(MazeState.id).getName().equals("bell"));
                keyImageView.setVisible(state.getFruitsGridState(pos) && MazeState.getFruit(MazeState.id).getName().equals("key"));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}

