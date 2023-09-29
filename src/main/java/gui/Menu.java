package gui;

import geometry.IntCoordinates;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.MazeState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Menu {

    private final double scale;
    private int score;
    private double size = 0.5;

    public Menu(double scale){
        this.scale = scale;


    }
    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos){
        // var group = new Group();

        HBox menu = new HBox();

        Label scoreText = new Label("Score:" + String.valueOf(score) );
        Label livesText = new Label("Current lives: " + String.valueOf(MazeState.getLives()));
        ImageView livesImage = new ImageView( new Image("heart3.png",scale*size , scale * size, true, true));
        
        HBox scoreHb = new HBox();
        HBox livesHb = new HBox();
        scoreHb.getChildren().addAll(scoreText);
        livesHb.getChildren().addAll(livesImage);
        menu.getChildren().addAll(scoreHb, livesHb);
        
        menu.setSpacing(20);
        livesText.setTextFill(Color.rgb(154, 155, 155));

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

               // Label livesText = new Label("Lives : " + String.valueOf(MazeState.getLives()));
               
                Label scoreText = new Label("Score : " + String.valueOf(MazeState.getScore()));
                ImageView livesImage = new ImageView( new Image(("heart" + String.valueOf(MazeState.getLives()) + ".png"),scale*size , scale * size, true, true));
                
                // livesText.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
                scoreText.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

                scoreHb.getChildren().add(scoreText);
                livesHb.getChildren().add(livesImage);
                
                scoreHb.setAlignment(Pos.BOTTOM_RIGHT);
                livesHb.setAlignment(Pos.BOTTOM_LEFT);


            }

            @Override
            public Node getNode() {
                return menu;
            }
        };
        
    }
}
