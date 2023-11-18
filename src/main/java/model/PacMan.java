package model;

import config.Cell;
import geometry.IntCoordinates;
import java.util.Timer;
import geometry.RealCoordinates;
// import misc.Debug;
import gui.GameMenu2;

import java.util.TimerTask;

import javax.swing.plaf.synth.SynthScrollBarUI;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private Direction nextDirection = Direction.NONE;
    private RealCoordinates pos;
    private static boolean energized;
    private static long compteur = 11000L;
    
    public static void setCompteur(long compteur) {PacMan.compteur = compteur;}
    public static long getCompteur() {return compteur;}

    private static boolean timerMarche = true;

    public static boolean getTimerMarche(){return timerMarche;}
    public static void setTimerMarche(boolean t){timerMarche = t;}

    private static boolean timer2Marche = true;

    public static void setTimer2Marche(boolean t) {timer2Marche = t;}
    public static boolean getTimer2Marche() {return timer2Marche;}

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
    public static PacMan INSTANCE = new PacMan(gameMenu, name);

    public PacMan(GameMenu2 gameMenu2, String name) {
        gameMenu=gameMenu2;
        PacMan.name=name;
    }

    public PacMan getInstance(String name){
        INSTANCE=new PacMan(gameMenu, name);
        return INSTANCE;
    }
    
    public String getName(){return PacMan.name;}


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
    public static boolean isEnergized() {
        // power pellet lasts for 10 seconds 
        return energized;
    }

    // this function is just if you need it, not currently used I believe
    public static void setEnergized(boolean e){
        energized = e;
    }


    public void update(long deltaT){ //I moved what is related directly to Pacman
        var pacPos = INSTANCE.getPos().round();
        // Debug.out(config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).toString());
        if (!MazeState.getGridState()[pacPos.y()][pacPos.x()] && !MazeState.allPointsCollected()) {
            if (MazeState.getConfig().getCell(pacPos).initialContent() == Cell.Content.DOT) {
                MazeState.addScore(1);
            }else if (MazeState.getConfig().getCell(pacPos).initialContent() == Cell.Content.ENERGIZER){
                // make the pacman energized -->
                MazeState.addScore(15);
                PacMan.setEnergized(10000L);
                compteur=11000L;
                PacMan.chrono();
                timer2Marche=false;
                timerMarche=false;
            }
            MazeState.getGridState()[pacPos.y()][pacPos.x()] = true;
        }
        if (this.isDying) {
            this.deathTimerAni += (float) ((float) deltaT * 1E-9);
            System.out.println(this.deathTimerAni);
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

    public static void chrono(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(timer2Marche==false){compteur-=1000L;}
                if(compteur>1000){chrono();}
                System.out.println(compteur);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000);
        
    }

    public static void setEnergized(long temps) {
        gameMenu.setVisible(false);
        // function will now no longer take a boolean,
        //  but suppose that we always want to "energize" pacman rather than de-energize him
        if (!energized){
            timerMarche=false;
        setEnergized(true);
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // Debug.out("started timer");
                try{
                    // not sure what the thread.sleep does, since the timing is done via the timer.schedule command, but it works.
                    for (int i = 0; i < 10; i++){
                        Thread.sleep(0);
                    } if (timer2Marche==false){setEnergized(false);} timerMarche=true;
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                    System.out.println("oh no, anyway.... (the timer for the energizer went wrong , got an intrerruptedException error)");
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, temps);
        
        // timer's second argument is in milliseconds, s 1000 ms = 1s
        // setEnergized(false);
       } else System.out.println("already energized, chill out pls");
    }


}
