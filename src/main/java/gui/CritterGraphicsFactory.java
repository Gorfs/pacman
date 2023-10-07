package gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import model.*;


public final class CritterGraphicsFactory {
    private final double scale;

    private float timer;

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

        Image fullImage = new Image(url);

        int y = 0, x = 0;
        final int width = 65, height = 65;
        var croppedPortion =  new Rectangle2D(x, y, width, height);

        // target width and height:
        double scaledWidth = size * scale;
        double scaledHeight = size * scale;

        ImageView image = new ImageView(fullImage);
        image.setViewport(croppedPortion);
        image.setFitWidth(scaledWidth);
        image.setFitHeight(scaledHeight);
        image.setSmooth(true);

        return new GraphicsUpdater() {
            @Override
            public void update(long deltaT) {
                if (!MazeState.getGameEnded()){

                    if (critter.getDirection() != Direction.NONE) {
                        timer = (float) (timer + deltaT * 1E-9);
                        if (timer > .2) timer = 0;
                        if (timer < .1) croppedImage(image, critter, x);
                        else if (timer < .2) croppedImage(image, critter, width);


                    } else {
                        var croppedPortion = new Rectangle2D(x, y, width, height);
                        image.setViewport(croppedPortion);
                    }

                    image.setVisible(true);
                    image.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                    image.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);
                    // Debug.out("sprite updated");
                }
                else{
                    image.setVisible(false); //Ici tous les sprites disparaissent parce que "image" contient tous les sprites (joueur + ghosts)
                    //Si cela ne convient pas, il faudra trouver un moyen de séparer les deux types d'images.
                }
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }

    private void croppedImage(ImageView image, Critter critter, int x) {
        image.setViewport(switch (critter.getDirection()) {
            case NORTH -> new Rectangle2D(x, 65 * 3, 65, 65);
            case WEST -> new Rectangle2D(x, 65 * 2, 65, 65);
            case SOUTH -> new Rectangle2D(x, 65, 65, 65);
            default -> new Rectangle2D(x, 0, 65, 65);
        });
    }
}
