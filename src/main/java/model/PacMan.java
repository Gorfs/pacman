package model;
import java.util.Timer;
import geometry.RealCoordinates;
import misc.Debug;

import java.util.TimerTask;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private static boolean energized;
    private static Timer timer = new Timer("timer", true);


    private PacMan() {
    }

    public static final PacMan INSTANCE = new PacMan();

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

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
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
    public static void setEnergized(boolean e){
        energized = e;
    }

    public static void setEnergized() {

        // function will now no longer take a boolean,
        //  but suppose that we always want to "energize" pacman rather than de-energize him
        if (!energized){
        setEnergized(true);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Debug.out("started timer");
                try{
                    for (int i = 0; i < 10; i++){
                        Thread.sleep(100L);
                        Debug.out("slept for 100 something");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new UnsupportedOperationException("Unimplemented method 'run'");
            }
        }, 0);
        setEnergized(false);

           }else{
            System.out.println("already energized, chill out pls");
           }   }

}
