package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
import javafx.scene.text.Font;
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
        // initializing JavaFX items and styles.
        HBox menu = new HBox();
        menu.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Label scoreText = new Label("Score:" + String.valueOf(score) );
        ImageView livesImage = new ImageView( new Image("heart3.png",scale*size , scale * size, true, true));
        
        HBox scoreHb = new HBox();
        HBox livesHb = new HBox();
        // adding nodes to parent "menu"
        scoreHb.getChildren().addAll(scoreText);
        livesHb.getChildren().addAll(livesImage);
        menu.getChildren().addAll(livesHb, scoreHb);
        
        menu.setSpacing(20);
        menu.setTranslateX(pos.x() * 20 );
        menu.setTranslateY(0);

        return new GraphicsUpdater() {
            @Override
            public void update(long deltaT) {
                // runs on every update cycle.
                if (MazeState.getGameEnded()){
                    livesHb.setVisible(false);
                    return;
                }
                livesHb.setVisible(true);
                // Debug.out("score updated");
                score = MazeState.getScore();
                scoreHb.getChildren().remove(0);
                livesHb.getChildren().remove(0);

                // updating the score on the Label.
                Label scoreText = new Label("Score : " + String.valueOf(MazeState.getScore()));

                // la division de la taille reduit la taille de l'image progressivement pour que elle prend pas tous l'ecran.
                ImageView livesImage = new ImageView( new Image(("heart" + String.valueOf(MazeState.getLives()) + ".png"),scale*(size)/(4 - MazeState.getLives()) , scale * size, true, true));
                
                // custom font settings.
                scoreText.setStyle("-fx-text-fill: white;"); //To change the font size you need to change the value in load font below
                try{
                    Font scoreFont = Font.loadFont(new FileInputStream(new File("src/main/resources/fonts/TeleSys.ttf")), 12); //TeleSys works great, OpeningHoursMonoVF is ok but not great, Pocod and Technodelic-Regular are not working right now.
                    scoreText.setFont(scoreFont);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                // the actual update of the item.
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
