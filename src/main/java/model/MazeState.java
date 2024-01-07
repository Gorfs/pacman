package model;

import config.Cell;
import config.Constants;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.GameMenu2;
import controllers.GhostsController;
import controllers.PacmanController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

import static model.Ghost.*;

/**
 * Class MazeState is used to update the maze while the PLAYER is playing.
 */
public final class MazeState {
    private final GhostsController[] ghostsController;
    private static MazeConfig config;
    private static int height;
    private static int width;

    private static boolean[][] gridState;
    private static boolean[][] fruitsGridState; // Another grid for the fruits
    private static boolean fruitsTimerStarted = false;
    // liste avec tous les fruits possibles et compteur id qui permet d'accéder aux données d'un fruit (nom, points, seuil de score pour passer à un autre fruit)
    private static ArrayList<Fruit> fruits;
    public static int id;

    private final GameMenu2 optionMenu;

    private static List<Critter> critters;
    private static int score; // J'ai passé la variable en static pour pouvoir la réinitialiser

    private static Map<Critter, RealCoordinates> initialPos;

    private static int defaultLives = Constants.NORMAL_LIVES;
    private static int lives = defaultLives;
    private static boolean gameEnded = false; //Variable qui permet de signaler si la partie est terminée

    //private static int s = 2; //speed

    /**
     * Constructor used to initialise the maze and the entities on the window and being able to update the maze.
     * @param ghostsController Array that contains all the ghosts' controller.
     * @param config variable that represent the initial version of the maze
     * @param gameMenu variable that contains in game option menu
     */
    public MazeState(GhostsController[] ghostsController, MazeConfig config, GameMenu2 gameMenu) {
        this.optionMenu = gameMenu;
        this.ghostsController = ghostsController;
        MazeState.config = config;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, CLYDE, BLINKY, INKY, PINKY);
        gridState = new boolean[height][width];
        fruitsGridState = new boolean[height][width];
        id = 0;
        fruits = new ArrayList<>();
        fruits.add(new Fruit("cherry", 100, 100));
        fruits.add(new Fruit("strawberry", 300, 500));
        fruits.add(new Fruit("orange", 500, 1000));
        fruits.add(new Fruit("apple", 700, 1500));
        fruits.add(new Fruit("melon", 1000, 2000));
        fruits.add(new Fruit("galaxian", 2000, 2500));
        fruits.add(new Fruit("bell", 3000, 3000));
        fruits.add(new Fruit("key", 5000, 3500));
        initialPos = Map.of(
                PacMan.INSTANCE, config.pacManPos().toRealCoordinates(1.0),
                BLINKY, config.blinkyPos().toRealCoordinates(1.0),
                INKY, config.inkyPos().toRealCoordinates(1.0),
                CLYDE, config.clydePos().toRealCoordinates(1.0),
                PINKY, config.pinkyPos().toRealCoordinates(1.0)
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

    public static int getDefaultLives(){
        return defaultLives;
    }

    public static int getLives(){
        return lives;
    }

    public void setLives(int l){lives=l; defaultLives =l;}


    public static boolean getGameEnded(){ //Cette fonction permet aux objets de vérifier si la partie est terminée.
        return gameEnded;
    }

    /**
     * This method is used to restart the game. It restarts lives, score maze and critters.
     */
    public void restart(){
        gameEnded = false;
        lives = defaultLives;
        score = 0;
        id = 0;
        resetGrid();
        resetFruitsGrid();
        resetCritters();
        fruitsTimerStarted = false;
    }

    public static boolean[][] getGridState(){ //Need it for the Pacman Class
        return gridState;
    }

    public static boolean[][] getFruitsGridState(){
        return fruitsGridState;
    }

    public static Fruit getFruit(int id){
        return fruits.get(id);
    }

    public void update(long deltaTns) {
        // Pause game if we're in the menu
        if(!optionMenu.isVisible()){
            for (var critter: critters) {
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
                    // Update scared mode for ghosts
                    if (!PacMan.INSTANCE.isEnergized()) ((Ghost) critter).setScaredMode(false);
                    // Get new direction for each ghosts
                    if (Objects.equals(critter.toString(), "INKY"))
                        ghostsController[3].setDirection((Ghost) critter, config, deltaTns);
                    else if (Objects.equals(critter.toString(), "BLINKY"))
                        ghostsController[2].setDirection((Ghost) critter, config, deltaTns);
                    else if (Objects.equals(critter.toString(), "PINKY"))
                        ghostsController[1].setDirection((Ghost) critter, config, deltaTns);
                    else if (Objects.equals(critter.toString(), "CLYDE"))
                        ghostsController[0].setDirection((Ghost) critter, config, deltaTns);
                    // Update direction to EAST if the ghost just respawned and do not move
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
                // Update position of critter once calculated
                critter.setPos(nextPos.warp(width, height));
            }

            // Check for collision
            for (var critter : critters) {
                if (critter instanceof Ghost && critter.getPos().round().equals(PacMan.INSTANCE.getPos().round())) {
                    // If pacman is energized it can eat the ghost else it dies.
                    if (PacMan.INSTANCE.isEnergized() && ((Ghost) critter).isScaredMode()) {
                        resetCritter(critter);
                    } else {
                        if (!PacMan.INSTANCE.isStartedDeathAni()){
                            PacMan.INSTANCE.setDying(true);
                            resetCritters();
                            gui.Music.music_death();
                        }
                        playerLost();
                        return;
                    }
                }
            }
            // When pacman collected all the dots, reset the map
            if(allPointsCollected()){
                resetCritters();
                resetFruitsGrid();
            resetGrid();
        }
        // If the score is higher than 100 (threshold for the 1st fruit, cherry) we start to generate fruits in the map
        if(score>fruits.get(0).getThresholds() && !fruitsTimerStarted){
            generateRandomFruits();
            fruitsTimerStarted = true;
        }
        if(id<fruits.size()-1){
            if(score>fruits.get(id+1).getThresholds()) id++;
        }
    }
}

    // This function generate every 20 seconds fruits randomly in places where a dot was already collected, the fruits disapears after 10 seconds
    public void generateRandomFruits(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                    if(!gameEnded){
                        boolean w = false;
                        Random rand = new Random();
                        while(!w){
                            int x = rand.nextInt(height);
                            int y = rand.nextInt(width);
                            if(gridState[x][y]){
                                fruitsGridState[x][y] = true;
                                timer.schedule(new TimerTask(){
                                    @Override
                                    public void run(){
                                        fruitsGridState[x][y] = false;
                                    }
                                }, 10000);
                                w = true;
                            }
                        }
                    } else{
                        // stop generating fruits when the player lose
                        timer.cancel();
                        timer.purge();
                    }
            }
        }, 10000, 20000);
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

    public static void resetGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gridState[i][j] = false;
            }
        }
    }

    public static void resetFruitsGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fruitsGridState[i][j] = false;
            }
        }
    }

    public static void addScore(int increment) {
        score += increment;
        // Play a sound when it eats something
        gui.Music.music_score();
    }


    private void playerLost() {
        if (PacMan.INSTANCE.getIsDying()) {
            lives--;
            PacmanController.resetLastKeyCode();
            if (lives == 0) {
                gui.Music.stopBackgroundMusic(); // lorsqu'on a plus de vie, on arrête le bgm
                gui.Music.music_gameOver(); // Et on lance le music de game over
                gameEnded = true; //Le joueur n'a plus de vie, la partie est terminée.
            }
            PacMan.INSTANCE.setStartedDeathAni(false);
        }
    }

    private void resetCritter(Critter critter) {
        if (critter instanceof Ghost ) {
            addScore(Constants.GHOST_SCORE);
            if (Objects.equals(critter.toString(), "INKY"))
                ghostsController[3].startAI();
            else if (Objects.equals(critter.toString(), "BLINKY"))
                ghostsController[2].startAI();
            else if (Objects.equals(critter.toString(), "PINKY"))
                ghostsController[1].startAI();
            else if (Objects.equals(critter.toString(), "CLYDE"))
                ghostsController[0].startAI();
            ((Ghost) critter).setScaredMode(false);
            if (!((Ghost) critter).isScatterMode()) ((Ghost) critter).changeScatterMode();
        }
        if (critter instanceof PacMan && PacMan.INSTANCE.isEnergized()) PacMan.INSTANCE.setEnergized(false);
        critter.setDirection(Direction.NONE);
        critter.setNextDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
    }

    public void resetCritters() {
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

    public boolean getFruitsGridState(IntCoordinates pos) {
        return fruitsGridState[pos.y()][pos.x()];
    }
}
