package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import misc.Debug;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static model.Ghost.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.io.File;

//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

public final class MazeState {
    private final MazeConfig config;
    private final int height;
    private final int width;

    private final boolean[][] gridState;

    private final List<Critter> critters;
    private static int score;

    private final Map<Critter, RealCoordinates> initialPos;
    private static int lives = 3;

    public static int getLives(){
        return lives;
    }

    public MazeState(MazeConfig config) {
        this.config = config;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, Ghost.CLYDE, BLINKY, INKY, PINKY);
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

    public List<Critter> getCritters() {
        return critters;
    }

    public double getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update(long deltaTns) {
        // FIXME: too many things in this method. Maybe some responsibilities can be delegated to other methods or classes?
        for  (var critter: critters) {
            var curPos = critter.getPos();
            var nextPos = critter.nextPos(deltaTns);
            var curNeighbours = curPos.intNeighbours();
            var nextNeighbours = nextPos.intNeighbours();
            if (!curNeighbours.containsAll(nextNeighbours)) { // the critter would overlap new cells. Do we allow it?
                switch (critter.getDirection()) {
                    case NORTH -> {
                        for (var n: curNeighbours) if (config.getCell(n).northWall()) {
                            nextPos = curPos.floorY();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                    case EAST -> {
                        for (var n: curNeighbours) if (config.getCell(n).eastWall()) {
                            nextPos = curPos.ceilX();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                    case SOUTH -> {
                        for (var n: curNeighbours) if (config.getCell(n).southWall()) {
                            nextPos = curPos.ceilY();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                    case WEST -> {
                        for (var n: curNeighbours) if (config.getCell(n).westWall()) {
                            nextPos = curPos.floorX();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                }

            }

            critter.setPos(nextPos.warp(width, height));
        }
        // FIXME Pac-Man rules should somehow be in Pacman class
        var pacPos = PacMan.INSTANCE.getPos().round();
        // Debug.out(config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).toString());
        if (!gridState[pacPos.y()][pacPos.x()] && config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).initialContent() == Cell.Content.DOT) {
            addScore(1);
            gridState[pacPos.y()][pacPos.x()] = true;
        }else if (!gridState[pacPos.y()][pacPos.x()] && config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).initialContent() == Cell.Content.ENERGIZER){
            // make the pacman energized -->
            addScore(15);
            gridState[pacPos.y()][pacPos.x()] = true;
        }
        for (var critter : critters) {
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos)) {
                if (PacMan.INSTANCE.isEnergized()) {
                    addScore(10);
                    resetCritter(critter);
                } else {
                    playerLost();
                    music_death(); 
                    return;
                }
            }
        }
    }

    private void addScore(int increment) {
        score += increment;
        displayScore();
        music_score();
    }

    private void displayScore() {
        System.out.println("Score: " + score);
    }

    private void playerLost() {
        // FIXME: this should be displayed in the JavaFX view, not in the console. A game over screen would be nice too.
        lives--;
        if (lives == 0) {
            System.out.println("Game over!");
            System.exit(0);
        }
        System.out.println("Lives: " + lives);
        resetCritters();
    }

    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
    }

    private void resetCritters() {
        for (var critter: critters) resetCritter(critter);
    }

    public MazeConfig getConfig() {
        return config;
    }

    public static int getScore(){
        return score;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }

    // ...


    
    public void music_score(){
        try {
            File audioFile = new File("src/main/resources/score.wav"); 
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(0.5f); // 在这里设置初始音量
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void music_death(){
        try {
            File audioFile = new File("src/main/resources/death.wav"); 
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(1f); // 在这里设置初始音量
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
