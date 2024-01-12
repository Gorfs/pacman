package gui;

import config.Constants;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import model.*;

import java.io.IOException;
import java.io.InputStream;


/**
 * Class CritterGraphicsFactory is used to update graphics of critter (pacman and ghosts)
 */
public final class CritterGraphicsFactory {
    private final double scale;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
    }

    /**
     * Method used to create a group of graphics for a critter.
     * @param critter variable that represent a critter (pacman or ghosts)
     * @return graphicsUpdater method that update the sprite of the critter
     * @throws IOException Signals that an I/O exception to some sort has occurred.
     *                     This class is the general class of exceptions produced by
     *                     failed or interrupted I/O operations.
     */
    public GraphicsUpdater makeGraphics(Critter critter) throws IOException {
        var size = 1.0;
        var url = (critter instanceof PacMan) ? Constants.PACMAN_PNG :
                switch ((Ghost) critter) {
                    case BLINKY -> Constants.BLINKY_PNG;
                    case INKY -> Constants.INKY_PNG;
                    case PINKY -> Constants.PINKY_PNG;
                    case CLYDE -> Constants.CLYDE_PNG;
                };
        InputStream is = getClass().getResourceAsStream(url);
        assert is != null;
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
            private boolean isWhite = false;
            private long lastToggleTime = 0;

            /**
             * Method that update the graphics for each cell
             * @param deltaT time between two frames in nanoseconds
             */
            @Override
            public void update(long deltaT) {
                if (!MazeState.getGameEnded()){
                    // Only ghosts have a scared version, so I check if critter is a ghost
                    if (critter instanceof Ghost) {
                        Image fullImage;
                        InputStream is = getClass().getResourceAsStream(url);
                        InputStream is2 = getClass().getResourceAsStream("/ghosts/scared_ghost.png");
                        InputStream is3 = getClass().getResourceAsStream("/ghosts/ghost_white.png");
                        // If pacman is energized, change its sprite to the one scared, else keep the not scared one.
                        if (PacMan.INSTANCE.isEnergized() && ((Ghost) critter).isScaredMode()) {
                            // If pacman is energized AND is almost normal, switch the sprite of ghosts every 0,1 second (the white one and the scared one)
                            if (PacMan.isAlmostNormal()) {
                                long currentTime = System.currentTimeMillis();
                                if(currentTime - lastToggleTime > 100){
                                    isWhite = !isWhite;
                                    lastToggleTime = currentTime;
                                }
                                if (isWhite) {
                                    assert is3 != null;
                                    fullImage = new Image(is3);
                                } else {
                                    assert is2 != null;
                                    fullImage = new Image(is2);
                                }
                            } else {
                                assert is2 != null;
                                fullImage = new Image(is2);
                            }
                        } else {
                            assert is != null;
                            fullImage = new Image(is);
                        }
                        image.setImage(fullImage);
                        // Crop the image to get the right sprite.
                        image.setViewport(croppedPortion);
                        image.setFitWidth(scaledWidth);
                        image.setFitHeight(scaledHeight);
                        image.setSmooth(true);
                    }
                    // If critter is moving update animation, else just keep the current sprite.
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
                } else {
                    image.setVisible(false);

                }
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }

    /**
     * Method that used to crop an image. Here it's used to separate different sprite of critters from the sprite sheet
     * @param image sprite sheet file
     * @param critter variable that represent the critter
     * @param x position of the sprite we want to get
     */
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
