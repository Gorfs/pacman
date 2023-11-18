package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.GameMenu2;
import gui.PacmanController;
import javafx.scene.input.KeyCode;
import misc.Debug;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static model.Ghost.*;

public final class MazeState {
    private static MazeConfig config;
    private static int height;
    private static int width;

    private static boolean[][] gridState;

    private static GameMenu2 gameMenu1;

    private  static List<Critter> critters;
    private static int score; //J'ai passé la variable en static pour pouvoir la réinitialiser

    private final Map<Critter, RealCoordinates> initialPos;

    // TODO: these should be changed to constants determined by player or in seperate file.
    private static int lives = 3;
    private static int livesC = lives;
    private static boolean gameEnded = false; //Variable qui permet de signaler si la partie est terminée

    public MazeState(MazeConfig config, GameMenu2 gameMenu) {
        gameMenu1=gameMenu;
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

    public static List<Critter> getCritters() {
        return critters;
    }

    public double getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static int getInitLives(){
        return livesC;
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
        if(!gameMenu1.isVisible()){//si on est dans les options, alors on pause le jeu
            
        ClydeController.setDirection(config);
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
                if (critter instanceof PacMan)
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

        for (var critter : critters) {
            if (critter instanceof Ghost && critter.getPos().round().equals(PacMan.INSTANCE.getPos().round())) {
                if (PacMan.isEnergized()) {
                    resetCritter(critter);
                } else {
                    playerLost();
                    if (!PacMan.INSTANCE.isStartedDeathAni()){
                        PacMan.INSTANCE.setDying(true);
                        resetCritters();
                        playerLost();
<<<<<<< HEAD
                        gui.Music.music_death();
=======
>>>>>>> d2f78a35f1aab8f55e586d548df023f2c75992bf
                    }
                
                    
                    return;
                }
            
        }
        if(allPointsCollected()){
            resetCritters();
            resetGrid();
            return;
        }
    }
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
        gui.Music.music_score(); //lorsque le score++ lance le music score
    }


    private void playerLost() {
        Debug.out("player is going to lso ea live 2");
        if (!PacMan.INSTANCE.getIsDying()) {
            lives--;
            Debug.out("Player lost a life");
            if (lives == 0) {
                gui.Music.stopBackgroundMusic(); // lorsqu'on a plus de vie, on arrête le bgm
                gui.Music.music_gameover(); // Et on lance le music de game over 
                gameEnded = true; //Le joueur n'a plus de vie, la partie est terminée.
            }
            PacMan.INSTANCE.setStartedDeathAni(false);
            
        }
    }

    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        // Forgot to add this in the issue #26
        if (critter instanceof PacMan)
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


    
    

}
