package gui;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.*;

/**
 * Classe gérant la musique de fond et les effets sonores du jeu.
 * Permet de régler le volume et de contrôler la lecture des sons.
 */
public class Music {
    
    private static Clip bgmClip; // Clip pour la musique de fond
    private static float volume = 0.6f; // Volume de la musique de fond
    private static float sfxVolume = 0.6f; // Volume des effets sonores

    /**
     * Définit le niveau de volume de la musique de fond.
     *
     * @param volumeLevel Le niveau de volume souhaité, entre 0.0 (silence) et 1.0 (volume maximal).
     *                    Si le niveau spécifié est inférieur à 0.0, le volume sera réglé sur 0.0.
     *                    Si le niveau spécifié est supérieur à 1.0, le volume sera réglé sur 1.0.
     */
    public static void setVolume(float volumeLevel) {
        if (volumeLevel < 0.0f) volume = 0.0f;
        else volume = Math.min(volumeLevel, 1.0f);
        if (bgmClip != null && bgmClip.isRunning()) {
            FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));
        }
    }

    /**
     * Retourne le niveau de volume actuel de la musique de fond.
     *
     * @return Le niveau de volume actuel.
     */
    public static float getVolume() {
        return volume;
    }

    /**
     * Définit le niveau de volume pour les effets sonores.
     *
     * @param volume Le niveau de volume souhaité pour les effets sonores.
     */
    public static void setSFXVolume(float volume) {
        sfxVolume = volume;
    }

    /**
     * Retourne le niveau de volume actuel des effets sonores.
     *
     * @return Le niveau de volume actuel pour les effets sonores.
     */
    public static float getSFXVolume() {
        return sfxVolume;
    }
    
    /**
     * Arrête la musique de fond si elle est en cours de lecture.
     */
    public static void stopBackgroundMusic() { 
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    /**
     * Joue la musique de fond en boucle continue.
     */
    public static void playBackgroundMusic() { 
        try {
            InputStream audioSrc = Music.class.getResourceAsStream("/music/bgm.wav");
            // Doit être mis en buffer pour supporter les marquages et les réinitialisations
            assert audioSrc != null;
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(getVolume()));
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); 
            bgmClip.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Joue l'effet sonore pour l'obtention d'un score.
     */
    public static void music_score(){
        try {
            InputStream audioSrc = Music.class.getResourceAsStream("/music/score.wav");
            //Doit être mis en buffer pour supporter les marquages et les réinitialisations
            assert audioSrc != null;
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            importAudioFile(bufferedIn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Joue l'effet sonore pour la mort du pacman.
     */
    public static void music_death(){
        try {
            InputStream audioSrc = Music.class.getResourceAsStream("/music/death2.wav");
            //Doit être mis en buffer pour supporter les marquages et les réinitialisations
            assert audioSrc != null;
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            importAudioFile(bufferedIn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Joue l'effet sonore pour la fin de la partie.
     */
    public static void music_gameOver(){
        try {
            InputStream audioSrc = Music.class.getResourceAsStream("/music/game_over.wav");
            //Doit être mis en buffer pour supporter les marquages et les réinitialisations
            assert audioSrc != null;
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            importAudioFile(bufferedIn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param bufferedIn file in inputStream version
     * @throws UnsupportedAudioFileException An UnsupportedAudioFileException is an exception indicating that
     *                                       an operation failed because a file did not contain valid data of
     *                                       a recognized file type and format.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     *                     This class is the general class of exceptions produced by
     *                     failed or interrupted I/O operations.
     * @throws LineUnavailableException A LineUnavailableException is an exception indicating that a line cannot
     *                                  be opened because it is unavailable. This situation arises most commonly
     *                                  when a requested line is already in use by another application.
     */
    private static void importAudioFile(InputStream bufferedIn) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
        Clip soundEffect = AudioSystem.getClip();
        soundEffect.open(audioInputStream);
        FloatControl gainControl = (FloatControl) soundEffect.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(getSFXVolume()));
        soundEffect.start();
    }
}
