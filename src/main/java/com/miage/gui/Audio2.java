/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.gui;

/**
 *
 * @author Anne-Sophie
 */
import java.io.*;
import java.net.URL;
import sun.audio.*;
 
public class Audio2  {
 
  private AudioData audiodata;
  private AudioStream as;
  private ContinuousAudioDataStream audiostream;
  private URL url;
  private boolean pause;
  //static int length;
 
  public Audio2(String filename) throws java.io.IOException {
       
        URL u = getClass().getResource(filename);
	FileInputStream in = new FileInputStream(filename);
	as = new AudioStream(in);   
        pause = false;
        
 
 
 
  }
 
  public void play() throws InterruptedException {
    AudioPlayer.player.start(as);
    
  }
 
 
 
  public void stop() {
      AudioPlayer.player.stop(as);
      pause = true;
 
  }
  
  public void pause() throws InterruptedException{
     
  }
  
  public boolean estPause(){
      return pause;
  }
  
  public void loop() throws IOException{
      
        audiodata = new AudioStream(url.openStream()).getData();
        audiostream = new ContinuousAudioDataStream(audiodata);
        AudioPlayer.player.start(audiostream);
      
  }
 
}
