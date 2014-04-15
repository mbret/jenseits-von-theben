/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.game;

import java.io.*;
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
    
    /**
     * String containing the audio file path.
     */
    public final static String newGame = "src/main/resources/audio/newGame.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String startGame = "src/main/resources/audio/startGame.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String newYear = "src/main/resources/audio/newYear.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String pickCard = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String makeExpo = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String Excavate = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String pickTokenEmpty = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String pickTokenPoint = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String useCar = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String useZepplin = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String useChronotime = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String move = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String changeFourCurrentCards = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String playerWin = "src/main/resources/audio/gong.wav";
    
    /**
     * String containing the audio file path.
     */
    public final static String playerLoose = "src/main/resources/audio/gong.wav";
    
    /**
     * Play the sound corresponding of the action.
     * @author david
     * @param action action which the player make
     */
    public static void play(String action){
        try {
            switch(action){
                case "newGame":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.newGame))));
                    break;
                case "startGame":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.startGame))));
                    break;
                case "newYear":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.newYear))));
                    break;
                case "pickCard":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.pickCard))));
                    break;
                case "makeExpo":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.makeExpo))));
                    break;
                case "Excavate":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.Excavate))));
                    break;
                case "pickTokenEmpty":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.pickTokenEmpty))));
                    break;
                case "pickTokenPoint":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.pickTokenPoint))));
                    break;
                case "useCar":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.useCar))));
                    break;
                case "useZepplin":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.useZepplin))));
                    break;
                case "useChronotime":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.useChronotime))));
                    break;
                case "move":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.move))));
                    break;
                case "changeFourCurrentCards":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.changeFourCurrentCards))));
                    break;
                case "playerWin":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.playerWin))));
                    break;
                case "playerLoose":
                    AudioPlayer.player.start(new AudioStream(new FileInputStream(new File(Sound.playerLoose))));
                    break;
            }
        } catch (IOException ex) {
            /*
             * to change ...
             * manage the exception
             */
        }
    }
    
    /**
     * Stop the preceding sound played (if it playing).
     * @author david
     * @param action 
     */
    public static void stop(){
        AudioPlayer.player.stop(as);
    }
    
    /*public static void main(String[] args){
        Sound.play("newGame");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        Sound.stop();
        Sound.play("startGame");
    }*/
}
