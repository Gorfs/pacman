package gui;

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


public class CellGraphicsFactory {
    private final double scale;
    private static final Image cherryImage, strawberryImage, orangeImage, appleImage, melonImage, galaxianImage, bellImage, keyImage;

    static{
        cherryImage = new Image(CellGraphicsFactory.class.getResourceAsStream("/cherry.png"));
        strawberryImage = new Image(CellGraphicsFactory.class.getResourceAsStream("/strawberry.png"));
        orangeImage = new Image(CellGraphicsFactory.class.getResourceAsStream("/orange.png"));
        appleImage = new Image(CellGraphicsFactory.class.getResourceAsStream("/apple.png"));
        melonImage = new Image(CellGraphicsFactory.class.getResourceAsStream("/melon.png"));
        galaxianImage = new Image(CellGraphicsFactory.class.getResourceAsStream("/galaxian.png"));
        bellImage = new Image(CellGraphicsFactory.class.getResourceAsStream("/bell.png"));
        keyImage = new Image(CellGraphicsFactory.class.getResourceAsStream("/key.png"));
    }

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
                dot.setVisible(!state.getGridState(pos));
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
