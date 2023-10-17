package model;

import config.Cell;
import geometry.IntCoordinates;
import java.util.Timer;
import geometry.RealCoordinates;
// import misc.Debug;

import java.util.TimerTask;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private Direction nextDirection = Direction.NONE;
    private RealCoordinates pos;
    private static boolean energized;
    private static Timer timer = new Timer("timer", true);


    public PacMan() {
    }

    public static PacMan INSTANCE = new PacMan();
    public String name;
    private PacMan(String name){
        this.name=name;
    }
    public PacMan getInstance(String name){
        if(INSTANCE.name==null){
            INSTANCE=new PacMan(name);
        }
        return INSTANCE;
    }
    
    public static String getName(){return INSTANCE.name;}


    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public double getSpeed() {
        return isEnergized() ? 6 : 4;
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


    public void update(){ //I moved what is related directly to Pacman
        var pacPos = INSTANCE.getPos().round();
        // Debug.out(config.getCell(new IntCoordinates(pacPos.y(), pacPos.x())).toString());
        if (!MazeState.getGridState()[pacPos.y()][pacPos.x()] && !MazeState.allPointsCollected()) {
            if (MazeState.getConfig().getCell(new IntCoordinates(pacPos.y(), pacPos.x())).initialContent() == Cell.Content.DOT) {
                MazeState.addScore(1);
            }else if (MazeState.getConfig().getCell(pacPos).initialContent() == Cell.Content.ENERGIZER){
                // make the pacman energized -->
                MazeState.addScore(15);
                PacMan.setEnergized();
            }
            MazeState.getGridState()[pacPos.y()][pacPos.x()] = true;
        }
    }
    public static void setEnergized() {

        // function will now no longer take a boolean,
        //  but suppose that we always want to "energize" pacman rather than de-energize him
        if (!energized){
        setEnergized(true);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                // Debug.out("started timer");
                try{
                    // not sure what the thread.sleep does, since the timing is done via the timer.schedule command, but it works.
                    for (int i = 0; i < 10; i++){
                        Thread.sleep(0);
                    } setEnergized(false);
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                    System.out.println("oh no, anyway.... (the timer for the energizer went wrong , got an intrerruptedException error)");
                }
            }
        }, 10000);
        // timer's second argument is in milliseconds, s 1000 ms = 1s
        // setEnergized(false);
       } else System.out.println("already energized, chill out pls");
    }

}
