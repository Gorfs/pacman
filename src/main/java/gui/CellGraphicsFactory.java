package gui;

import config.Cell;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.MazeState;


/**
 * Class CellGraphicsFactory is used to update the maze
 */
public class CellGraphicsFactory {
    private final double scale;

    /**
     * Constructor used to set up the scaling
     * @param scale value of the base scaling
     */
    public CellGraphicsFactory(double scale) {
        this.scale = scale;
    }

    /**
     * Method used to create a group of graphics for a cell.
     * @param state current maze state.
     * @param pos position of the current cell.
     * @return a method that update the group of graphic that represent a cell.
     */
    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
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
        // if there is a wall in the cell
        var wallX = new Rectangle();
        var wallY = new Rectangle();
        if (cell.initialContent() == Cell.Content.WALL) {
            // set wall color
            dot.setFill(Color.BLUEVIOLET);
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
                    wallX.setFill(Color.BLUEVIOLET);
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
                    wallY.setFill(Color.BLUEVIOLET);
                    group.getChildren().add(wallY);
                }
            }
        } else {
            // If there isn't a wall in the cell
            dot.setFill(Color.YELLOW);
        }

        return new GraphicsUpdater() {
            /**
             * Method that update the graphics for each cell
             * @param deltaT time between two frames in nanoseconds
             */
            @Override
            public void update(long deltaT) {
                dot.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}
