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


    public PacMan() {
    }
    /**On pourra mettre dans le menu le choix du nom qui s'affichera au dessus du pacman,
     * en plus si on refait un round, le pacman garde quand meme le nom en l'initialisant
     */
    public static PacMan INSTANCE = new PacMan();
    public String name;
    private PacMan(String name){
        this.name=name;
    }
    public static PacMan getInstance(String name){
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
    public static boolean isEnergized() {
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
                    // not sure what the thread.sleep does, since the timing is done via the timer.schedule command, but it works.
                    for (int i = 0; i < 10; i++){
                        Thread.sleep(0);
                    }
                    setEnergized(false);
                                    } catch (InterruptedException e) {
                    // e.printStackTrace();
                    System.out.println("oh no, anyway.... (the timer for the energizer went wrong , got an intrerruptedException error)");
                }
            }
        }, 10000);
        // timer's second argument is in milliseconds, s 1000 ms = 1s

        // setEnergized(false);


           }else{
            System.out.println("already energized, chill out pls");
           }   }

}
