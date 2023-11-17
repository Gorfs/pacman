package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.GhostsController;
import gui.App;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static model.Ghost.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.io.File;

public final class MazeState {
    private final GhostsController[] ghostsController;
    private static MazeConfig config;
    private static int height;
    private static int width;

    private static boolean[][] gridState;

    private static List<Critter> critters;
    private static int score; //J'ai passé la variable en static pour pouvoir la réinitialiser

    private final Map<Critter, RealCoordinates> initialPos;
    private static int lives = 3;
    private static int livesC = 3;
    private static boolean gameEnded = false; //Variable qui permet de signaler si la partie est terminée

    public MazeState(GhostsController[] ghostsController, MazeConfig config) {
        this.ghostsController = ghostsController;
        MazeState.config = config;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, CLYDE, BLINKY, INKY, PINKY);
        gridState = new boolean[height][width];
        initialPos = Map.of(
                PacMan.INSTANCE, config.getPacManPos().toRealCoordinates(1.0),
                BLINKY, config.getBlinkyPos().toRealCoordinates(1.0),
                INKY, config.getInkyPos().toRealCoordinates(1.0),
                CLYDE, config.getClydePos().toRealCoordinates(1.0),
                PINKY, config.getPinkyPos().toRealCoordinates(1.0)
        );
        resetCritters();
    }

    public class EffetSonoreManager {
        private static float volume = 0.6f;

        public static void setVolume(float volumeLevel) {
            if(volumeLevel < 0.0f) volume = 0.0f;
            else if(volumeLevel > 1.0f) volume = 1.0f;
            else volume = volumeLevel;
        }

        public static float getVolume() {
            return volume;
        }
    }

    public static List<Critter> getCritters() {
        return critters;
    }

    public double getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static int getLives(){
        return lives;
    }

    public void setLives(int l){lives=l; livesC=l;}


    public static boolean getGameEnded(){ //Cette fonction permet aux objets de vérifier si la partie est terminée.
        return gameEnded;
    }

    public static void restart(){ //Cette fonction permet de réinitialiser les valeurs à leur état d'origine
        gameEnded = false;
        lives = livesC;
        score = 0;
    }

    public static boolean[][] getGridState(){ //Need it for the Pacman Class
        return gridState;
    }

    public void update(long deltaTns) {
        for  (var critter: critters) {
            var curPos = critter.getPos();
            var nextPos = critter.nextPos(deltaTns);
            // Get possible next pos for critter
            var nextNextPos = critter.nextNextPos(deltaTns);

            var curNeighbours = curPos.intNeighbours();
            var nextNeighbours = nextPos.intNeighbours();
            // Get possible next cell
            var nextNextNeighbours = nextNextPos.intNeighbours();

            // Set direction to the next direction if direction is NONE.
            if (critter.getDirection() == Direction.NONE) {
                critter.setDirection(critter.getNextDirection());
                critter.setNextDirection(Direction.NONE);
            }

            // Get the next direction of ghosts
            if (critter instanceof Ghost) {
                if (Objects.equals(critter.toString(), "BLINKY")) ghostsController[2].setDirection((Ghost) critter, config);

                if (critter.getDirection() == Direction.NONE && critter.getNextDirection() == Direction.NONE) {
                    critter.setDirection(Direction.EAST);
                }
            }

            if (!curNeighbours.containsAll(nextNeighbours)) { // the critter would overlap new cells. Do we allow it?
                // for next cell, check if this is a wall.
                for (var n: nextNeighbours)
                    if (config.getCell(n).initialContent() == Cell.Content.WALL) {
                        // check if the critter is going to the wall and set his direction to direction.NONE if it is.
                        switch (critter.getDirection()) {
                            case NORTH -> {
                                if (Objects.equals(n, curPos.plus(RealCoordinates.NORTH_UNIT).round())) {
                                    nextPos = curPos.floorY();critter.setDirection(Direction.NONE);
                                }
                            }
                            case EAST -> {
                                if (Objects.equals(n, curPos.plus(RealCoordinates.EAST_UNIT).round())) {
                                    nextPos = curPos.ceilX();critter.setDirection(Direction.NONE);
                                }
                            }
                            case SOUTH -> {
                                if (Objects.equals(n, curPos.plus(RealCoordinates.SOUTH_UNIT).round())) {
                                    nextPos = curPos.ceilY();critter.setDirection(Direction.NONE);
                                }
                            }
                            case WEST -> {
                                if (Objects.equals(n, curPos.plus(RealCoordinates.WEST_UNIT).round())) {
                                    nextPos = curPos.floorX();critter.setDirection(Direction.NONE);
                                }
                            }
                        }

                    }
                // for possible next cell, check if this is not a wall.
                for (var n: nextNextNeighbours)
                    if (config.getCell(n).initialContent() != Cell.Content.WALL) {
                        // check if the critter is going this way and set his direction to direction.NONE  and update nextPos if it is.
                        switch (critter.getNextDirection()) {
                            case NORTH -> {
                                if (Objects.equals(n, curPos.plus(RealCoordinates.NORTH_UNIT).round())) {
                                    nextPos = new RealCoordinates(Math.round(nextPos.x()), nextPos.y());
                                    critter.setDirection(Direction.NONE);
                                }
                            }
                            case EAST -> {
                                if (Objects.equals(n, curPos.plus(RealCoordinates.EAST_UNIT).round())) {
                                    nextPos = new RealCoordinates(nextPos.x(), Math.round(nextPos.y()));
                                    critter.setDirection(Direction.NONE);
                                }
                            }
                            case SOUTH -> {
                                if (Objects.equals(n, curPos.plus(RealCoordinates.SOUTH_UNIT).round())) {
                                    nextPos = new RealCoordinates(Math.round(nextPos.x()), nextPos.y());
                                    critter.setDirection(Direction.NONE);
                                }
                            }
                            case WEST -> {
                                if (Objects.equals(n, curPos.plus(RealCoordinates.WEST_UNIT).round())) {
                                    nextPos = new RealCoordinates(nextPos.x(), Math.round(nextPos.y()));
                                    critter.setDirection(Direction.NONE);
                                }
                            }
                        }
                    }
            }
            critter.setPos(nextPos.warp(width, height));
        }

        for (var critter : critters) {
            if (critter instanceof Ghost && critter.getPos().round().equals(PacMan.INSTANCE.getPos().round())) {
                if (PacMan.INSTANCE.isEnergized() && ((Ghost) critter).isScaredMode()) {
                    critter.setDirection(Direction.NONE);
                    critter.setPos(initialPos.get(critter));
                    ((Ghost) critter).setScaredMode(false);
                } else {
                    if (!PacMan.INSTANCE.isStartedDeathAni()) {
                        PacMan.INSTANCE.setDying(true);
                        resetCritters();
                        music_death();
                    } playerLost();
                    return;
                }
            }
        }
        if(allPointsCollected()){
            resetCritters();
            resetGrid();
        }
    }

    public static boolean allPointsCollected() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!gridState[i][j] && config.getCell(new IntCoordinates(j, i)).initialContent() == Cell.Content.DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gridState[i][j] = false;
            }
        }
    }

    public static void addScore(int increment) {
        score += increment;
        music_score(); //lorsque le score++ lance le music score
    }


    private void playerLost() {
        if (!PacMan.INSTANCE.getIsDying()) {
            lives--;
            if (lives == 0) {
                App.stopBackgroundMusic(); // lorsqu'on a plus de vie, on arrête le bgm
                music_gameover(); // Et on lance le music de game over
                gameEnded = true; //Le joueur n'a plus de vie, la partie est terminée.
            }
            PacMan.INSTANCE.setStartedDeathAni(false);

        }
    }

    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setNextDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));

    }

    private void resetCritters() {
        for (var critter: critters) resetCritter(critter);
    }

    public static MazeConfig getConfig() {
        return config;
    }

    public static int getScore(){
        return score;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }

    // ...


    //les 3 fonctions pour lancer les effets sonores de score, death et game over
    public static void music_score(){
        try {
            File audioFile = new File("src/main/resources/music/score.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(EffetSonoreManager.getVolume()));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void music_death(){
        try {
            File audioFile = new File("src/main/resources/music/death2.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(EffetSonoreManager.getVolume()));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void music_gameover(){
        try {
            File audioFile = new File("src/main/resources/music/game_over.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(EffetSonoreManager.getVolume()));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
