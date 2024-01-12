package gui;

import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.MazeState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InGameDisplay {

    private final double scale;
    private int score;
    private final double size = 0.7;
    
    public InGameDisplay(double scale){
        this.scale = scale;


    }
    public GraphicsUpdater makeGraphics() {
        // initializing JavaFX items and styles.
        HBox menu = new HBox();

        // the number of lives at the start of the game based on difficulty.
        int initLives = MazeState.getDefaultLives() - 1;

        Label scoreText = new Label("Score:" + score);


        // setting up the javaFX objects for the lives display.
        ImageView[] livesArray = new ImageView[initLives];
        for(int i = 0 ; i < initLives; i++){
            livesArray[i] = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pacmanSimple.png")),
                    scale*size,scale*size, true, true));
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
        menu.setTranslateX(20);
        menu.setTranslateY(670);
        return new GraphicsUpdater() {
            /**
             * Method that update the display of lives and score while playing.
             * @param deltaT time between two frames in nanoseconds
             */
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
                if (!scoreHb.getChildren().isEmpty()) {
                    scoreHb.getChildren().subList(0, scoreHb.getChildren().size()).clear();
                }
                if (initLives > 0) {
                    livesHb.getChildren().subList(0, initLives).clear();
                }

                // updating the score on the Label.
                Label scoreText = new Label("Score : " + score);

                // resetting and updating the hearts counter.
                String heartUrl = "/pacmanSimple.png";
                ImageView[] livesArray = new ImageView[initLives];
                // Debug.out(MazeState.getLives() + "");
                for(int i = 0 ; i < initLives; i++){
                    if ((MazeState.getLives() - 1) <= i){
                        heartUrl = "/empty1.png"; 
                    }
                    livesArray[i] = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(heartUrl)),
                            scale*size,scale*size, true, true));
                }
                
                // custom font settings.
                // To change the font size you need to change the value in load font below
                scoreText.setStyle("-fx-text-fill: white;");
                // TeleSys works great, OpeningHoursMonoVF is ok but not great, Pocod and Technodelic-Regular are not working right now.
                Font scoreFont = Font.loadFont(getClass().getResourceAsStream("/fonts/TeleSys.ttf"), 16);
                scoreText.setFont(scoreFont);

                // the final step, adding the updated objects back into the javaFX objects.
                scoreHb.getChildren().add(scoreText);
                for(ImageView image: livesArray) livesHb.getChildren().addAll(image);
            }

            @Override
            public Node getNode() {
                return menu;
            }
        };
        
    }
}
