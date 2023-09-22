package gui;

import geometry.IntCoordinates;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import model.MazeState;
import misc.Debug;

public class Menu {

    private final double scale;
    private int score;

    public Menu(double scale){
        this.scale = scale;


    }
    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos){
        // var group = new Group();

        Label scoreText = new Label("Score:" + String.valueOf(score) );
        
        HBox hb = new HBox();
        hb.getChildren().addAll(scoreText);
        
        // hb.setSpacing(0);

        // Failed attempt to make the score text white
        // scoreText.setTextFill(Paint.valueOf("#000"));
        // hb.setStyle("#FFF");
        

        return new GraphicsUpdater() {
            @Override
            public void update() {
                // Debug.out("score updated");
                score = MazeState.getScore();
                hb.getChildren().remove(0);
                hb.getChildren().add(new Label("score :" + String.valueOf(score)));
                hb.setTranslateX(pos.x());
                hb.setTranslateY(pos.y());
            }

            @Override
            public Node getNode() {
                return hb;
            }
        };
        
    }
}
