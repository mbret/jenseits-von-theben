/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.gui;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.*;

/**
 *
 * @author David
 */
public class Sound{
    
    /**
     * Audio stream for play the soundfile.
     */
    private static AudioStream asMusicGame;
    
    /**
     * Audio stream for play the soundfile.
     */
    private static AudioStream asChrono;
    
    /**
     * Audio stream for play the soundfile.
     */
    private static AudioStream asFinishGame;
    
    /**
     * Boolean to enable or disable all sounds.
     */
    public static boolean enableSound = true;

    /**
     * String containing the audio file path.
     */
    public final static String pickTokenPoint = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String useChronotime = "src/main/resources/audio/chrono.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String clic = "src/main/resources/audio/clic.wav";
        
    /**
     * String containing the audio file path.
     */
    public final static String finishGame = "src/main/resources/audio/finishGame.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String audioGame = "src/main/resources/audio/musich.wav";
    
    /**
     * Play the sound corresponding of the action.
     * @author david
     * @param action action which the player make
     */
    public static void play(String action){
        try {
            if(Sound.enableSound){
                switch(action){
                    case "pickTokenPoint":
                        AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.pickTokenPoint))));
                        break;
                    case "useChronotime":
                       asChrono = new AudioStream(new FileInputStream(new File(Sound.useChronotime)));
                          AudioPlayer.player.start(asChrono);
                        break;
                    case "clic":
                        AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.clic))));
                        break;
                    case "finishGame":
                        asFinishGame = new AudioStream(new FileInputStream(new File(Sound.finishGame)));
                        AudioPlayer.player.start(asFinishGame);
                        break;
                    case "audioGame":
                        asMusicGame = new AudioStream(new FileInputStream(new File(Sound.audioGame)));
                        AudioPlayer.player.start(asMusicGame);
                        break;
                }
            }
        } catch (IOException ex) {
            /*
             * to change ...
             * manage the exception
             */
        }
    }
    
    public static void startAudioGame(){
        try {
            asMusicGame = new AudioStream(new FileInputStream(new File(Sound.audioGame)));
            AudioPlayer.player.start(asMusicGame);
            Sound.enableSound = true;
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Stop the music sound played (if it playing).
     * @author david
     * @param action 
     */
    public static void stopAudioGame(){
        AudioPlayer.player.stop(asMusicGame);
        Sound.enableSound = false;
    }
    
    /**
     * Stop the chrono sound played (if it playing).
     * @author david
     * @param action 
     */
    public static void stopAudioChrono(){
        AudioPlayer.player.stop(asChrono);
    }
    
    /**
     * Stop the chrono sound played (if it playing).
     * @author david
     * @param action 
     */
    public static void stopFinishAudioGame(){
        AudioPlayer.player.stop(asChrono);
    }
}
