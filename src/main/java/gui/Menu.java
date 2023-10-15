package gui;

import geometry.IntCoordinates;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.MazeState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Menu {

    private final double scale;
    private int score;
    private double size = 1;
    public Menu(double scale){
        this.scale = scale;


    }
    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos){
        // var group = new Group();

        HBox menu = new HBox();
        menu.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label scoreText = new Label("Score:" + String.valueOf(score) );
        ImageView livesImage = new ImageView( new Image("heart3.png",scale*size , scale * size, true, true));
        
        HBox scoreHb = new HBox();
        HBox livesHb = new HBox();
        scoreHb.getChildren().addAll(scoreText);
        livesHb.getChildren().addAll(livesImage);
        menu.getChildren().addAll(livesHb, scoreHb);
        
        menu.setSpacing(20);
        // centers the values to the top center of the screen.
        menu.setTranslateX(pos.x() * 20 );
        menu.setTranslateY(0);

        return new GraphicsUpdater() {
            @Override
            public void update() {
                if (MazeState.getGameEnded()){
                    livesHb.setVisible(false);
                    return;
                }
                livesHb.setVisible(true);
                // Debug.out("score updated");
                score = MazeState.getScore();
                scoreHb.getChildren().remove(0);
                livesHb.getChildren().remove(0);

               // Label livesText = new Label("Lives : " + String.valueOf(MazeState.getLives()));
               
                Label scoreText = new Label("Score : " + String.valueOf(MazeState.getScore()));

                // la division de la taille reduit la taille de l'image progressivement pour que elle prend pas tous l'ecran.
                ImageView livesImage = new ImageView( new Image(("heart" + String.valueOf(MazeState.getLives()) + ".png"),scale*(size)/(4 - MazeState.getLives()) , scale * size/1, true, true));
                
                // livesText.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
                scoreText.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

                scoreHb.getChildren().add(scoreText);
                livesHb.getChildren().add(livesImage);
                
                


            }

            @Override
            public Node getNode() {
                return menu;
            }
        };
        
    }
}
