package model;

import config.Cell;
import controllers.PacmanController;

import java.util.Timer;

import config.Constants;
import geometry.RealCoordinates;
import gui.GameMenu2;

import java.util.TimerTask;

import static model.MazeState.allPointsCollected;
import static model.MazeState.getCritters;

/**
 * Implements Pac-Man character using singleton pattern.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private Direction nextDirection = Direction.NONE;
    private RealCoordinates pos;

    private static long energizedTime = 11000L;//compteur pour le temps d'énergie
    // Currently not used so I commented this line
    // public static void setCompteur(long compteur) {PacMan.compteur = compteur;}
    public static long getEnergizedTime() {return energizedTime;}

    // represents the time in unix milis when the timer has ended
    private static long milisTimerTime = 0L;

    // just to get the time when the energized timer has ended.
    public static long getMilisTimerTime(){
        return milisTimerTime;
    }

    private static boolean timerMarche = false;// pour gérer le timer de setEnergized

    public static void setTimerMarche(boolean t){timerMarche = t;}



    private static boolean timer2Marche = false;// pour gérer le timer de chrono

    public static void setTimer2Marche(boolean t) {timer2Marche = t;}
    public static boolean getTimer2Marche() {return timer2Marche;}
    private static boolean energized = false;
    private static boolean almostNormal = false;
    private static final long ENERGIZED_DURATION = 10000; // the energized duration is 10 seconds (timer is in milliseconds)
    private static final long ALMOST_NORMAL_DURATION = 2000; // the ghost flashing animation should last 2 seconds
    // Currently not used, so I commented this line
    // private static Timer timer = new Timer("timer", true);

    // movement animation related
    private float timerAni = 0;
    // each keyframe we need to change sprite
    private final float[] checkpointAni = {0.15F,0.3F};

    // Death animation related
    private float deathTimerAni = 0;
    // each keyframe we need to change sprite
    private final float[] checkpointDeathAni = {.1F,.2F,.3F,.4F,.5F};
    // When to start death animation
    private boolean isDying = false;
    // So we don't have death animation instantly when death animation is finish and then create a loop.
    private boolean startedDeathAni = false;

    public static GameMenu2 gameMenu;
    public static String name;
    public static PacMan INSTANCE = new PacMan(null, null);

    public PacMan(GameMenu2 gameMenu2, String name) {
        gameMenu=gameMenu2;
        PacMan.name = name;
    }

    // Result currently not used, so I made it return void
    public void getInstance(String name){
        INSTANCE = new PacMan(gameMenu, name);
        // return INSTANCE;
    }

    // Currently not used, so I commented this line
    // public String getName(){return PacMan.name;}


    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public float[] getCheckpointAni() {
        return checkpointAni;
    }

    public float[] getCheckpointDeathAni() {
        return checkpointDeathAni;
    }

    public float getDeathTimerAni() {
        return deathTimerAni;
    }

    @Override
    public void setTimerAni(float timerAni) {
        this.timerAni = timerAni;
    }

    @Override
    public float getTimerAni() {
        return timerAni;
    }

    @Override
    public int getSpeed() {
        // Changed so that when pacman is dying, it doesn't move anymore.
        return getIsDying()? 0:(isEnergized() ? 6 : 4);
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    // Storing next move
    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    @Override
    public void setPos(RealCoordinates pos) {
        this.pos = pos;
    }

    /**
     *
     * @return whether Pac-Man just ate an energizer
     */
    public boolean isEnergized() {
        // power pellet lasts for 10 seconds 
        return energized;
    }

    // this function is just if you need it, not currently used I believe
    public void setEnergized(boolean e){
        energized = e;
    }

    public static boolean isAlmostNormal(){
        return almostNormal;
    }

    public static void setAlmostNormal(boolean e){
        almostNormal = e;
    }


    public void update(long deltaT){ //I moved what is related directly to Pacman
        var pacPos = INSTANCE.getPos().round();
        // Debug.out(config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).toString());
        if (!MazeState.getGridState()[pacPos.y()][pacPos.x()] && !allPointsCollected()) {
            if (MazeState.getConfig().getCell(pacPos).initialContent() == Cell.Content.DOT) {
                MazeState.addScore(Constants.DOT_SCORE);
            } else if (MazeState.getConfig().getCell(pacPos).initialContent() == Cell.Content.ENERGIZER){
                // make the pacman energized -->
                MazeState.addScore(Constants.ENERGIZER_SCORE);
                for (var critter:getCritters()) if (critter instanceof Ghost) ((Ghost) critter).setScaredMode(true);
                timer2Marche=false;
                timerMarche=false;
                setEnergized(energizedTime);
            }
            MazeState.getGridState()[pacPos.y()][pacPos.x()] = true;
        }
        if(MazeState.getFruitsGridState()[pacPos.y()][pacPos.x()] && !MazeState.allPointsCollected()){
            MazeState.addScore(MazeState.getFruit(MazeState.id).getPoints());
            MazeState.getFruitsGridState()[pacPos.y()][pacPos.x()] = false;
        }
        if (this.isDying) {
            this.deathTimerAni += (float) ((float) deltaT * 1E-9);
            if (this.deathTimerAni > this.checkpointDeathAni[this.checkpointDeathAni.length - 1]) {
                this.deathTimerAni = 0.0F;
                this.isDying = false;
            }
        }
    }

    public boolean getIsDying() {
        return this.isDying;
    }

    public void setDying(boolean dying) {
        if (!this.startedDeathAni) {
            this.startedDeathAni = true;
            this.isDying = dying;
        }
    }

    public boolean isStartedDeathAni() {
        return startedDeathAni;
    }

    public void setStartedDeathAni(boolean deathAni) {
        this.startedDeathAni = deathAni;
    }


    public void setEnergized(long temps) {
        // Time is in miliseconds
        // function will now no longer take a boolean,
        milisTimerTime = (System.currentTimeMillis() + temps);
        if (!energized){
            setEnergized(true);
            Thread checker = new EnergyChecker();
            checker.start();
        }else{
            milisTimerTime = System.currentTimeMillis() + temps;
        }    
    }
}