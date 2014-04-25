/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.game;

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
     * Input stream for read the soundfile.
     */
    private static InputStream in;
    
    /**
     * Audio stream for play the soundfile.
     */
    private static AudioStream as;
    
    
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
    public final static String playerWin = "src/main/resources/audio/win.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String playerLoose = "src/main/resources/audio/loose.wav";
    
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
                        AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.useChronotime))));
                        break;
                    case "clic":
                        AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.clic))));
                        break;
                    case "playerWin":
                        AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.playerWin))));
                        break;
                    case "playerLoose":
                        AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.playerLoose))));
                        break;
                    case "audioGame":
                        as = new AudioStream(new FileInputStream(new File(Sound.audioGame)));
                          AudioPlayer.player.start(as);
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
            as = new AudioStream(new FileInputStream(new File(Sound.audioGame)));
            AudioPlayer.player.start(as);
            Sound.enableSound = true;
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Stop the preceding sound played (if it playing).
     * @author david
     * @param action 
     */
    public static void stopAudioGame(){
        AudioPlayer.player.stop(as);
        Sound.enableSound = false;
    }
}
