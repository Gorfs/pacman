package gui;

import geometry.IntCoordinates;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.MazeState;

public class Menu {

    private final double scale;
    private int score;

    public Menu(double scale){
        this.scale = scale;


    }
    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos){
        // var group = new Group();

        HBox menu = new HBox();

        Label scoreText = new Label("Score:" + String.valueOf(score) );
        Label livesText = new Label("Current lives: " + String.valueOf(MazeState.getLives()));
        
        HBox scoreHb = new HBox();
        HBox livesHb = new HBox();
        scoreHb.getChildren().addAll(scoreText);
        livesHb.getChildren().addAll(livesText);
        menu.getChildren().addAll(scoreHb, livesHb);
        
        menu.setSpacing(30);
        livesText.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        // Failed attempt to make the score text white
        // scoreText.setTextFill(Paint.valueOf("#000"));
        // hb.setStyle("#FFF");
        

        return new GraphicsUpdater() {
            @Override
            public void update() {
                // Debug.out("score updated");
                score = MazeState.getScore();
                scoreHb.getChildren().remove(0);
                livesHb.getChildren().remove(0);
                scoreHb.getChildren().add(new Label("score :" + String.valueOf(score)));
                livesHb.getChildren().add(new Label("Lives :" + String.valueOf(MazeState.getLives())));
                
                menu.setTranslateX(pos.x()-30);
                menu.setTranslateY(pos.y());

            }

            @Override
            public Node getNode() {
                return menu;
            }
        };
        
    }
}
