package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import misc.Debug;

import java.util.List;
import java.util.Map;

import static model.Ghost.*;

public final class MazeState {
    private final MazeConfig config;
    private final int height;
    private final int width;

    private final boolean[][] gridState;

    private final List<Critter> critters;
    private static int score; //J'ai passé la variable en static pour pouvoir la réinitialiser

    private final Map<Critter, RealCoordinates> initialPos;
    private static int lives = 3;
    private static boolean gameEnded = false; //Variable qui permet de signaler si la partie est terminée

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

    public static int getLives(){
        return lives;
    }

    public static boolean getGameEnded(){ //Cette fonction permet aux objets de vérifier si la partie est terminée.
        return gameEnded;
    }

    public static void restart(){ //Cette fonction permet de réinitialiser les valeurs à leur état d'origine
        gameEnded = false;
        lives = 3;
        score = 0;
    }

    public void update(long deltaTns) {
        for  (var critter: critters) {
            var curPos = critter.getPos();
            var nextPos = critter.nextPos(deltaTns);
            var curNeighbours = curPos.intNeighbours();
            var nextNeighbours = nextPos.intNeighbours();
            if (!curNeighbours.containsAll(nextNeighbours)) { // the critter would overlap new cells. Do we allow it?
                for (var n: curNeighbours) if (config.getCell(n).initialContent() == Cell.Content.WALL) {
                    switch (critter.getDirection()) {
                        case NORTH -> nextPos = curPos.floorY();
                        case EAST -> nextPos = curPos.ceilX();
                        case SOUTH -> nextPos = curPos.ceilY();
                        case WEST -> nextPos = curPos.floorX();
                    }
                    critter.setDirection(Direction.NONE);
                    break;
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
                    return;
                }
            }
        }
    }

    private void addScore(int increment) {
        score += increment;
        displayScore();
    }

    private void displayScore() {
        System.out.println("Score: " + score);
    }

    private void playerLost() {
        lives--;
        if (lives == 0) {
            gameEnded = true; //Le joueur n'a plus de vie, la partie est terminée.
        }
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
}
