package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Ghost;
import model.MazeState;
import model.PacMan;


public final class CritterGraphicsFactory {
    private final double scale;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(Critter critter) {
        var size = 1.0;
        var url = (critter instanceof PacMan) ? "pacman.png" :
                switch ((Ghost) critter) {
                    case BLINKY -> "ghost_blinky.png";
                    case CLYDE -> "ghost_clyde.png";
                    case INKY -> "ghost_inky.png";
                    case PINKY -> "ghost_pinky.png";
                };
        var image = new ImageView(new Image(url, scale * size, scale * size, true, true));
        return new GraphicsUpdater() {
            @Override
            public void update() {
                if (MazeState.getGameEnded() == false){
                    image.setVisible(true);
                    image.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                    image.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);
                    // Debug.out("sprite updated");
                }
                else{
                    image.setVisible(false); //Ici tous les sprites disparaissent parce que "image" contient tous les sprites (joueur + ghosts)
                    //Si cela ne convient pas il faudra trouver un moyen de séparer les deux types d'images.
                }
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }
}
