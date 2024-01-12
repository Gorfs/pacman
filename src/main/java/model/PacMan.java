package model;

import config.Cell;

import config.Constants;
import geometry.RealCoordinates;
import gui.OptionInGame;

import static model.MazeState.allPointsCollected;
import static model.MazeState.getCritters;

import java.util.Random;

/**
 * Implements Pac-Man character using singleton pattern.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private Direction nextDirection = Direction.NONE;
    private RealCoordinates pos;

    private long nanoSeconds = 0L;
    private long nanoSecondsForSpeed = 0L;

    private static boolean energizerPaused = false; // pour gérer le timer de setEnergized

    private static boolean energized = false;
    private static boolean almostNormal = false;
    private static boolean ghostSpeedChanged = false; // gérer le timer de la vitesse des ghost qui change en prenant un bonus

    private double speed = 4; // vitesse normale de pacman
    private final Random random = new Random();

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

    public static OptionInGame gameMenu;
    public static String name;
    public static PacMan INSTANCE = new PacMan(null, null);

    public PacMan(OptionInGame gameMenu2, String name) {
        gameMenu=gameMenu2;
        PacMan.name = name;
    }

    // Result currently not used, so I made it return void
    public void getInstance(String name){
        INSTANCE = new PacMan(gameMenu, name);
        // return INSTANCE;
    }

    public static PacMan getPacMan(){return INSTANCE;}

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
    public double getSpeed() {
        // Changed so that when pacman is dying, it doesn't move anymore.
        return getIsDying()? 0:(isEnergized() ? 6 : speed);
    }

    @Override
    public void setSpeed(double speed){
        this.speed = speed;
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

    public void update(long deltaT){ //I moved what is related directly to Pacman
        if(ghostSpeedChanged) updateGhostSpeed(deltaT);
        var pacPos = INSTANCE.getPos().round();
        // Debug.out(config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).toString());
        if (!MazeState.getGridState()[pacPos.y()][pacPos.x()] && !allPointsCollected()) {
            if (MazeState.getConfig().getCell(pacPos).initialContent() == Cell.Content.DOT) {
                MazeState.addScore(Constants.DOT_SCORE);
            } else if (MazeState.getConfig().getCell(pacPos).initialContent() == Cell.Content.ENERGIZER){
                // make the pacman energized -->
                MazeState.addScore(Constants.ENERGIZER_SCORE);
                for (var critter:getCritters())
                    if (critter instanceof Ghost) ((Ghost) critter).setScaredMode(true);
                setEnergized(true);
            }

            MazeState.getGridState()[pacPos.y()][pacPos.x()] = true;
        }
        if(MazeState.getFruitsGridState()[pacPos.y()][pacPos.x()] && !MazeState.allPointsCollected()){
            MazeState.addScore(MazeState.getFruit(MazeState.id).points());
            MazeState.getFruitsGridState()[pacPos.y()][pacPos.x()] = false;
        }
        if(MazeState.getBonusGridState()[pacPos.y()][pacPos.x()] && !MazeState.allPointsCollected()){
            MazeState.addScore(100);
            MazeState.getBonusGridState()[pacPos.y()][pacPos.x()] = false;
            bonusRandomAction();
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

    public void setEnergizerPaused(boolean p) {
        energizerPaused = p;
    }

    public void updateEnergizer(long deltaT) {
        if (!energizerPaused) nanoSeconds = nanoSeconds + deltaT;
        System.out.println(nanoSeconds * 1E-9);
        if (nanoSeconds * 1E-9 >= 8) almostNormal = true;
        if (nanoSeconds * 1E-9 >= 10) {
            nanoSeconds = 0L;
            setEnergized(false);
            almostNormal = false;
        }
    }

    // this method is like a timer of 10 seconds for the ghost speed changing
    public void updateGhostSpeed(long deltaT){
        if (!energizerPaused) nanoSecondsForSpeed = nanoSecondsForSpeed + deltaT;
        System.out.println(nanoSecondsForSpeed * 1E-9);
        if (nanoSecondsForSpeed * 1E-9 >= 10 || MazeState.getGameEnded()) {
            nanoSecondsForSpeed = 0L;
            // reset ghost speeds
            for(var critter : getCritters()){
                if(critter instanceof Ghost) critter.setSpeed(2);
            }
            ghostSpeedChanged = false;
        }
    }

    public void bonusRandomAction(){
        int id = random.nextInt(4);
        switch(id){
            case 0:
                resetGhosts();
                break;
            case 1:
                teleport(INSTANCE);
                break;
            case 2:
                teleportGhosts();
                break;
            case 3:
                setRandomGhostSpeed();
                ghostSpeedChanged = true;
                break;
        }
    }

    public void resetGhosts(){
        for(var critter : getCritters()){
            if (critter instanceof Ghost) MazeState.resetCritter(critter);}
    }

    public void teleport(Critter critter){
        boolean w = false;
        while(!w){
            int x = random.nextInt(21);
            int y = random.nextInt(21);
            // make sure it is not teleporting out of the walls, or in a wall
            if(MazeState.getConfig().getCell(new RealCoordinates(x, y).round()).initialContent() == Cell.Content.DOT){
                critter.setPos(new RealCoordinates(x, y));
                w = true;
            }
        }
    }

    public void teleportGhosts(){
        for(var critter : getCritters()){
            if(critter instanceof Ghost) teleport(critter);
        }
    }

    public void setRandomGhostSpeed(){
        double newSpeed = random.nextInt(6);
        while(newSpeed == 2){
            newSpeed = random.nextInt(6);
        }
        for(var critter : getCritters()){
            if(critter instanceof Ghost) critter.setSpeed(newSpeed);
        }
    }

}
