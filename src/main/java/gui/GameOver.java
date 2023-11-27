package gui;

import java.io.InputStream;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.MazeState;

public class GameOver {

    public GameOver(){}

    public GraphicsUpdater makeGraphics(){
        VBox vb = new VBox();
        vb.setVisible(false);
        //To change the font size you need to change the value in load font below
        Label titleText = new Label("Game Over");
        titleText.setTranslateX(-7);
        titleText.setStyle("-fx-text-fill:white;");
        InputStream is = getClass().getResourceAsStream("/fonts/TeleSys.ttf");
        // 'TeleSys' works great, OpeningHoursMonoVF is ok but not great,
        // 'Pocod' and 'Technodelic-Regular' are not working right now.
        Font titleFont = Font.loadFont(is, 50);
        titleText.setFont(titleFont);
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(titleText);
        hBox1.setAlignment(Pos.BASELINE_CENTER);
        Label restartText = new Label("Press Enter to restart");
        restartText.setTranslateX(-13);
        // To change the font size you need to change the value in load font below
        restartText.setStyle("-fx-text-fill:red; -fx-border-color:red;");
        is = getClass().getResourceAsStream("/fonts/TeleSys.ttf");
        // 'TeleSys' works great, OpeningHoursMonoVF is ok but not great,
        // 'Pocod' and 'Technodelic-Regular' are not working right now.
        Font restartFont = Font.loadFont(is, 40);
        restartText.setFont(restartFont);
        HBox hBox2 = new HBox();
        hBox2.getChildren().add(restartText);
        hBox2.setAlignment(Pos.BASELINE_CENTER);
        vb.getChildren().addAll(hBox1, hBox2);
        vb.setTranslateX(115);
        vb.setTranslateY(225);

        return new GraphicsUpdater() {
            /**
             * Methods that display game over menu
             * @param deltaT time between two frames in nanoseconds
             */
            @Override
            public void update(long deltaT){
                vb.setVisible(MazeState.getGameEnded());
            }

            @Override
            public Node getNode(){
                return vb;
            }
        };
    }
    
}
