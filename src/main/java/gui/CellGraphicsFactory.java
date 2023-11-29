package gui;

import config.Cell;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.MazeState;


public class CellGraphicsFactory {
    private final double scale;

    public CellGraphicsFactory(double scale) {
        this.scale = scale;
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
            case WALL -> scale/4;
            default -> 0;});

        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);
        // if there is a wall in the cell
        if (cell.initialContent() == Cell.Content.WALL) {
            // set wall color
            dot.setFill(Color.BLUEVIOLET);
            if (pos.x() < state.getWidth() - 1) {
                // Get position of the right cell
                IntCoordinates right = pos.toRealCoordinates(1.0).plus(RealCoordinates.EAST_UNIT).round();
                if (state.getConfig().getCell(right).initialContent() == Cell.Content.WALL) {
                    // Draw the link between walls if the right cell is also a wall
                    var wall = new Rectangle();
                    wall.setHeight(scale/2);
                    wall.setWidth(scale);
                    wall.setY(scale/4);
                    wall.setX(scale/2);
                    // set wall color
                    wall.setFill(Color.BLUEVIOLET);
                    group.getChildren().add(wall);
                }
            } if (pos.y() < state.getHeight() - 1) {
                // Get position of the bottom cell
                IntCoordinates bottom = pos.toRealCoordinates(1.0).plus(RealCoordinates.SOUTH_UNIT).round();
                if (state.getConfig().getCell(bottom).initialContent() == Cell.Content.WALL) {
                    // Draw the link between walls if the right cell is also a wall
                    var wall = new Rectangle();
                    wall.setHeight(scale);
                    wall.setWidth(scale/2);
                    wall.setY(scale/2);
                    wall.setX(scale/4);
                    // set wall color
                    wall.setFill(Color.BLUEVIOLET);
                    group.getChildren().add(wall);
                }
            }
        } else if (cell.initialContent() == Cell.Content.CHERRY){
            var cherryImage = new Image(getClass().getResourceAsStream("/cherry.png"));
            var cherryImageView = new ImageView(cherryImage);
            cherryImageView.setFitWidth(scale);
            cherryImageView.setFitHeight(scale);
            group.getChildren().add(cherryImageView);
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
            @Override
            public void update(long deltaT) {
                group.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}
