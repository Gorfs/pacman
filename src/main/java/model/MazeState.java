package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public void setLive(int l){lives=l;}
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

        // FIXME Pac-Man rules should somehow be in Pacman class
        var pacPos = PacMan.INSTANCE.getPos().round();
        // Debug.out(config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).toString());
        if (!gridState[pacPos.y()][pacPos.x()] && !allPointsCollected()) {
            if (config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).initialContent() == Cell.Content.DOT) {
                addScore(1);
            }else if (config.getCell(pacPos).initialContent() == Cell.Content.ENERGIZER){
                // make the pacman energized -->
                addScore(15);
            }
            gridState[pacPos.y()][pacPos.x()] = true;
        }

        for (var critter : critters) {
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos)) {
                if (PacMan.INSTANCE.isEnergized()) {
                    resetCritter(critter);
                } else {
                    playerLost();
                    return;
                }
            }
        }
        if(allPointsCollected()){
            resetCritters();
            resetGrid();
            return;
        }
    }

    public boolean allPointsCollected() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!gridState[i][j] && config.getCell(new IntCoordinates(i, j)).initialContent() == Cell.Content.DOT) {
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

    private void addScore(int increment) {
        score += increment;
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
