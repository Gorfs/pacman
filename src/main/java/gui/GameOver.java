package gui;

import geometry.IntCoordinates;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.MazeState;

public class GameOver {

    private final double scale;
    private int lives;

    public GameOver(double scale){
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos){
        Label gameOverText = new Label("Lives : " + String.valueOf(lives));

        HBox hb = new HBox();
        hb.getChildren().addAll(gameOverText);


        return new GraphicsUpdater() {
            @Override
            public void update(){
                lives = MazeState.getLives();
                hb.getChildren().remove(0);
                Label livesText = new Label("Lives : " + String.valueOf(lives));
                livesText.setStyle("-fx-font-size:20; -fx-text-fill:red;");
                hb.getChildren().add(livesText);
                hb.setTranslateX(pos.x());
                hb.setTranslateY(pos.y());

                if (MazeState.getGameEnded()){
                    hb.getChildren().remove(0);
                    Label gameOverText = new Label("LOL T'ES MORT TU PUES");
                    gameOverText.setStyle("-fx-font-size:50; -fx-text-fill:white;");
                    hb.getChildren().add(gameOverText);
                    hb.setTranslateX(25);
                    hb.setTranslateY(250);
                }
            }

            @Override
            public Node getNode(){
                return hb;
            }
        };
    }
    
}
