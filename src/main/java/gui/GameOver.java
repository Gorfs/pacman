package gui;

import geometry.IntCoordinates;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.MazeState;

public class GameOver {

    private final double scale;

    public GameOver(double scale){
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos){
        VBox vb = new VBox();
        vb.setVisible(false);
        Label titleText = new Label("Game Over");
        titleText.setStyle("-fx-font-size:50; -fx-text-fill:white;");
        HBox hbox1 = new HBox();
        hbox1.getChildren().add(titleText);
        hbox1.setAlignment(Pos.BASELINE_CENTER);
        Label restartText = new Label("Press Enter to restart");
        restartText.setStyle("-fx-font-size:40; -fx-text-fill:red; -fx-border-color:red;");
        HBox hbox2 = new HBox();
        hbox2.getChildren().add(restartText);
        hbox2.setAlignment(Pos.BASELINE_CENTER);
        vb.getChildren().addAll(hbox1, hbox2);
        vb.setTranslateX(115);
        vb.setTranslateY(225);

        return new GraphicsUpdater() {
            @Override
            public void update(){

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
