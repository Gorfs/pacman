package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import config.MazeConfig;
import geometry.IntCoordinates;
import gui.GameView;
import javafx.scene.Node;
import javafx.scene.control.Label;
// unused imports are used when debugging and therefore should be kept unless pushing to master.
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import misc.Debug;
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
        // initializing JavaFX items and styles.
        HBox menu = new HBox();
        // uncomment the line under this to have a border around the menu object in the game.
        // menu.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // the number of lives at the start of the game based on difficulty.
        int initLives = MazeState.getInitLives() - 1;

        Label scoreText = new Label("Score:" + String.valueOf(score) );


        // setting up the javaFX objects for the livres display.
        ImageView[] livesArray = new ImageView[initLives];
        for(int i = 0 ; i < initLives; i++){
            livesArray[i] = new ImageView(new Image("pacmanSimple.png", scale*size,scale*size, true, true));
        }
        
        HBox scoreHb = new HBox();
        HBox livesHb = new HBox();
        // adding nodes to parent "menu"
        scoreHb.getChildren().addAll(scoreText);
        for(ImageView image: livesArray){
            livesHb.getChildren().addAll(image);
        }
        menu.getChildren().addAll(livesHb, scoreHb);
        
        menu.setSpacing(20);
        // TODO once constants are added make translateX based on width instead of random constant
        menu.setTranslateX((20));
        menu.setTranslateY(670); // TODO set width and height to use root width and height instead of constants
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



                // empty out the horizontal boxes
                for(int i = 0 ; i < scoreHb.getChildren().size(); i++){
                    scoreHb.getChildren().remove(0);
                }
                for(int i = 0 ; i < initLives ; i++){
                    livesHb.getChildren().remove(0);
                }

                // updating the score on the Label.
                Label scoreText = new Label("Score : " + String.valueOf(score));


                // resetting and updating the hearts counter.
                String heartUrl = "pacmanSimple.png";
                ImageView[] livesArray = new ImageView[initLives];
                // Debug.out(MazeState.getLives() + "");
                for(int i = 0 ; i < initLives; i++){
                    if ((MazeState.getLives() - 1) <= i){
                        heartUrl = "empty1.png"; 
                    }
                    livesArray[i] = new ImageView(new Image(heartUrl, scale*size,scale*size, true, true));
                }
                
                // custom font settings.
                scoreText.setStyle("-fx-text-fill: white;"); //To change the font size you need to change the value in load font below
                try{
                    Font scoreFont = Font.loadFont(new FileInputStream(new File("src/main/resources/fonts/TeleSys.ttf")), 16); //TeleSys works great, OpeningHoursMonoVF is ok but not great, Pocod and Technodelic-Regular are not working right now. 
                    scoreText.setFont(scoreFont);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                // the final step, adding the updated objects back into the javaFX objects.
                scoreHb.getChildren().add(scoreText);
                for(ImageView image: livesArray){
                    livesHb.getChildren().addAll(image);
                }               
            }

            @Override
            public Node getNode() {
                return menu;
            }
        };
        
    }
}
