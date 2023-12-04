package gui;

import java.util.Random;

import config.Cell;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.MazeState;


public class CellGraphicsFactory {
    private final double scale;
    private Color colorWalls;

    public CellGraphicsFactory(double scale) {
        this.scale = scale;
        //Set random color for walls
        Color[] colors = {Color.BLUE, Color.RED, Color.PINK, Color.ORANGE, Color.CYAN, Color.YELLOW, Color.GREEN, Color.PURPLE, Color.WHITE, Color.BROWN};
        Random rand = new Random();
        int n = rand.nextInt(10);
        colorWalls = colors[n];
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
        var group = new Group();
        group.setTranslateX(pos.x()*scale);
        group.setTranslateY(pos.y()*scale);
        var cell = state.getConfig().getCell(pos);
        var dot = new Circle();
        group.getChildren().add(dot);
        dot.setRadius(switch (cell.initialContent()) {
            case DOT -> scale/10;
            case ENERGIZER -> scale/5;
            case NOTHING -> 0;
            case WALL -> scale/4;});

        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);
        var wallX = new Rectangle();
        var wallY = new Rectangle();
        // if there is a wall in the cell
        if (cell.initialContent() == Cell.Content.WALL) {
            // set wall color
            dot.setFill(colorWalls);
            if (pos.x() < state.getWidth() - 1) {
                // Get position of the right cell
                IntCoordinates right = pos.toRealCoordinates(1.0).plus(RealCoordinates.EAST_UNIT).round();
                if (state.getConfig().getCell(right).initialContent() == Cell.Content.WALL) {
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
                if (state.getConfig().getCell(bottom).initialContent() == Cell.Content.WALL) {
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
        /*
        if (cell.northWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }
        if (cell.eastWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale);
            nWall.setWidth(scale/10);
            nWall.setY(0);
            nWall.setX(9*scale/10);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }
        if (cell.southWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setY(9*scale/10);
            nWall.setX(0);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }
        if (cell.westWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale);
            nWall.setWidth(scale/10);
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }*/


        return new GraphicsUpdater() {
            float timer = 0;
            @Override
            public void update(long deltaT) {
                dot.setVisible(!state.getGridState(pos));
                /* Just a try to change the color of the walls every second (to delete if not useful)
                timer += (float) (deltaT * 1E-9);
                if (timer > 1){
                    Color[] colors = {Color.BLUE, Color.RED, Color.PINK, Color.ORANGE, Color.CYAN, Color.YELLOW, Color.GREEN, Color.PURPLE, Color.WHITE, Color.BROWN};
                    Random rand = new Random();
                    int n = rand.nextInt(10);
                    Color temp = colors[n];
                    timer = 0;
                    dot.setFill(temp);
                    wallX.setFill(temp);
                    wallY.setFill(temp);
                }
                */
                
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}
