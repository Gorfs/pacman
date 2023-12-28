package model;

import misc.Debug;

public class EnergyChecker extends Thread {
    // thread that turns off energized when the timer is finished
    public void run(){
        long time = PacMan.getMilisTimerTime();
        while(System.currentTimeMillis() < time){
            try {
                Thread.sleep(100);
                Debug.out("Energized timer has been waited for again");
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        PacMan.INSTANCE.setEnergized(false);
    }
    
}
