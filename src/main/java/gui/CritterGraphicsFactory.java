package gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public final class CritterGraphicsFactory {
    private final double scale;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(Critter critter) throws Exception {
        var size = 1.0;
        var url = (critter instanceof PacMan) ? "src/main/resources/pacman.png" :
                switch ((Ghost) critter) {
                    case BLINKY -> "src/main/resources/ghosts/ghost_blinky.png";
                    case CLYDE -> "src/main/resources/ghosts/ghost_clyde.png";
                    case INKY -> "src/main/resources/ghosts/ghost_inky.png";
                    case PINKY -> "src/main/resources/ghosts/ghost_pinky.png";
                };
        InputStream is = Files.newInputStream(Paths.get(url));
        Image fullImage = new Image(is);
        is.close();
        int y = 0, x = 0;
        final int width = 65, height = 65;
        var croppedPortion =  new Rectangle2D(x, y, width, height);

        // target width and height:
        double scaledWidth = size * scale;
        double scaledHeight = size * scale;

        // Initialize image var.
        ImageView image = new ImageView(fullImage);
        image.setViewport(croppedPortion);
        image.setFitWidth(scaledWidth);
        image.setFitHeight(scaledHeight);
        image.setSmooth(true);

        return new GraphicsUpdater() {
            @Override
            public void update(long deltaT) throws IOException {
                if (!MazeState.getGameEnded()){
                    // Pacman doesn't have a scared version so i check if critter isn't pacman
                    if (!(critter instanceof PacMan)) {
                        Image fullImage;
                        InputStream is = Files.newInputStream(Paths.get(url));
                        InputStream isAlternative = Files.newInputStream(Paths.get("src/main/resources/ghosts/scared_ghost.png"));
                        // If pacman is energized, change its sprite to the one scared, else keep the not scared one.
                        if (PacMan.isEnergized())
                            fullImage = new Image(isAlternative);
                        else fullImage = new Image(is);
                        is.close();
                        isAlternative.close();
                        image.setImage(fullImage);
                        // Crop the image to get the right sprite.
                        image.setViewport(croppedPortion);
                        image.setFitWidth(scaledWidth);
                        image.setFitHeight(scaledHeight);
                        image.setSmooth(true);
                    }
                    // If critter is moving update animation, else just keep the curent sprite.
                    if (critter.getDirection() != Direction.NONE && !PacMan.INSTANCE.getIsDying()) {
                        // I added to each critter an animation timer called timerAni that update each frame critter is moving.
                        critter.setTimerAni((float) (critter.getTimerAni() + deltaT * 1E-9));
                        // Reset timerAni when all sprites were used once.
                        if (critter.getTimerAni() > critter.getCheckpointAni()[critter.getCheckpointAni().length - 1])
                            critter.setTimerAni(0);
                        // For each sprite, display it a certain amount of time.
                        for (int i = 0; i < critter.getCheckpointAni().length; i++) {
                            if (critter.getTimerAni() < critter.getCheckpointAni()[i]) {
                                croppedImage(image, critter, (width * i));
                                break;
                            }
                        }
                    } else if (PacMan.INSTANCE.getIsDying() && critter instanceof PacMan) {
                        // Updated dying animation for pacman
                        for (int i = 0; i < PacMan.INSTANCE.getCheckpointDeathAni().length; i++) {
                            if (PacMan.INSTANCE.getDeathTimerAni() < PacMan.INSTANCE.getCheckpointDeathAni()[i]) {
                                image.setViewport(new Rectangle2D(width*i, 65*4, 65, 65));
                                break;
                            }
                        }
                    } else {
                        var croppedPortion = new Rectangle2D(x, y, width, height);
                        image.setViewport(croppedPortion);
                    }
                    // Display critter's new sprite at the right pos
                    image.setVisible(true);
                    if (!PacMan.INSTANCE.getIsDying()) {
                        image.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                        image.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);
                    }
                    // Debug.out("sprite updated");
                } else {
                    image.setVisible(false);
                    //Ici tous les sprites disparaissent parce que "image" contient tous les sprites (joueur + ghosts)
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
        // Update image and get the pos of the sprite we need in the image.
        // By default, we are using the sprite that going to the right.
        image.setViewport(switch (critter.getDirection()) {
            case NORTH -> new Rectangle2D(x, 65 * 3, 65, 65);
            case WEST -> new Rectangle2D(x, 65 * 2, 65, 65);
            case SOUTH -> new Rectangle2D(x, 65, 65, 65);
            default -> new Rectangle2D(x, 0, 65, 65);
        });
    }
}
