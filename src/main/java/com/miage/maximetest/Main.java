/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.miage.maximetest;

import com.miage.config.ConfigManager;
import com.miage.game.Board;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxime
 */
public class Main {
    
    
    public static void main(String[] args) throws IOException {
        
        ConfigManager configManager = null;
        
        // init configManager
        try {
            configManager = ConfigManager.getInstance();
            configManager.init();
        } catch (IOException ex) {
            Logger.getLogger(com.miage.main.Main.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        Board board = new Board( 2 );

    }
    
    
    
}
