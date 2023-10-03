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

    private static List<Critter> critters;
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

    public static List<Critter> getCritters() {
        return critters;
    }

    public double getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update(long deltaTns) {
        for  (var critter: critters) {
            
            
            ClydeController.setDirection();
            // here should be the setting of the next position for all the other AIs to add:




           var curPos = critter.getPos();
           var nextPos = critter.nextPos(deltaTns);

            // basic debugging to see if the bots are going the right direction
            // Debug.out("Next pos is: " + String.valueOf(nextPos));
            // Debug.out("cur pos is: " + String.valueOf(curPos));

            var curNeighbours = curPos.intNeighbours();
            var nextNeighbours = nextPos.intNeighbours();

            // Debug.out(critter.toString() + "  " +  critter.getDirection());

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
                if (!gridState[pacPos.y()][pacPos.x()] && (config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).initialContent() == Cell.Content.ENERGIZER)){
            // make the pacman energized -->
            addScore(15);
            Debug.out("picked up power pellet");
            PacMan.setEnergized();
            gridState[pacPos.y()][pacPos.x()] = true;
        }else if (!gridState[pacPos.y()][pacPos.x()] && (config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).initialContent() == Cell.Content.DOT)) {
            addScore(1);
            Debug.out(config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).initialContent() == Cell.Content.DOT ? "DOT" : "NOT DOT");
            Debug.out("Picked up a normal pellet");
            gridState[pacPos.y()][pacPos.x()] = true;

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
}
