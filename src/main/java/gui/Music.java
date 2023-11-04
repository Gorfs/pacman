package gui;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Music {
    private static Clip bgmClip;
    private static float volume = 0.6f;
    private static float sfxVolume = 0.6f;
    
    public static void setVolume(float volumeLevel) {
            if(volumeLevel < 0.0f) volume = 0.0f;
            else if(volumeLevel > 1.0f) volume = 1.0f;
            else volume = volumeLevel;
    }
    
    public static float getVolume() {
            return volume;
    }

    public static void setSFXVolume(float volume) {
        sfxVolume = volume;
    }
    
    public static float getSFXVolume() {
        return sfxVolume;
    }

    public static void stopBackgroundMusic() { // fonction pour arrêter le bgm
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    public static void playBackgroundMusic() { // fonction pour lancer le bgm
        try {
            File audioFile = new File("src/main/resources/music/bgm.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(getVolume()));
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); 
            bgmClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //les 3 fonctions pour lancer les effets sonores de score, death et game over
    public static void music_score(){
        try {
            File audioFile = new File("src/main/resources/music/score.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(getSFXVolume()));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void music_death(){
        try {
            File audioFile = new File("src/main/resources/music/death2.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(getSFXVolume()));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void music_gameover(){
        try {
            File audioFile = new File("src/main/resources/music/game_over.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(getSFXVolume()));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
