package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import geometry.IntCoordinates;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.MazeState;

public class GameOver {

    private final double scale;

    public GameOver(double scale){
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos){
        VBox vb = new VBox();
        vb.setVisible(false);
        Label titleText = new Label("Game Over"); //To change the font size you need to change the value in load font below
        titleText.setTranslateX(-7);
        titleText.setStyle("-fx-text-fill:white;");
        InputStream is = getClass().getResourceAsStream("/fonts/TeleSys.ttf");
        Font titleFont = Font.loadFont(is, 50); //TeleSys works great, OpeningHoursMonoVF is ok but not great, Pocod and Technodelic-Regular are not working right now. 
        titleText.setFont(titleFont);
        HBox hbox1 = new HBox();
        hbox1.getChildren().add(titleText);
        hbox1.setAlignment(Pos.BASELINE_CENTER);
        Label restartText = new Label("Press Enter to restart");
        restartText.setTranslateX(-13);
        restartText.setStyle("-fx-text-fill:red; -fx-border-color:red;"); //To change the font size you need to change the value in load font below
        is = getClass().getResourceAsStream("/fonts/TeleSys.ttf");
        Font restartFont = Font.loadFont(is, 40); //TeleSys works great, OpeningHoursMonoVF is ok but not great, Pocod and Technodelic-Regular are not working right now. 
        restartText.setFont(restartFont);
        HBox hbox2 = new HBox();
        hbox2.getChildren().add(restartText);
        hbox2.setAlignment(Pos.BASELINE_CENTER);
        vb.getChildren().addAll(hbox1, hbox2);
        vb.setTranslateX(115);
        vb.setTranslateY(225);

        return new GraphicsUpdater() {
            @Override
            public void update(long deltaT){
                
                if (MazeState.getGameEnded()){
                    vb.setVisible(true);
                }
                else{
                    vb.setVisible(false);
                }
            }

            @Override
            public Node getNode(){
                return vb;
            }
        };
    }
    
}
