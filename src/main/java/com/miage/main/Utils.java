package com.miage.main;

import com.miage.game.Board;
import com.miage.game.LogDisplay;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class Utils {

    private final static Logger LOGGER = LogManager.getLogger(Utils.class.getName());
    

    /**
     * Return the week number of the provided date
     * @param date
     * @return 
     */
    public static int getWeek( LocalDate date ){
        return (int) (Math.ceil(date.getDayOfYear()/7));
    }

    /**
     * Return the year number of the provided date
     * @param date
     * @return 
     */
    public static int getYear( LocalDate date ){
        return date.getYear();
    }
    
    /**
        * Save the game (the board into a file).
        * @author david
        * @param boardToSave board to be save
        * @param fileToSave file where the board will be saved
        */
       public static void saveGame(Board boardToSave){
           try {
               /*
                * default directory is C:/Users/[loginUser]/Documents/JenseitsVonTheben.
                * if directory doesn't exist, it's create the directory before save the file named "board.jvt"
                */
               String saveDirectory = javax.swing.filechooser.FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\JenseitsVonTheben";
               File directory = new File(saveDirectory);
               if(!directory.exists())
                   if(!new File(saveDirectory).mkdir())
                       throw new IOException();
               FileOutputStream backupFile = new FileOutputStream(saveDirectory+"/board.boobs");
               ObjectOutputStream oos = new ObjectOutputStream(backupFile);
               boardToSave.setLogDisplay(LogDisplay.getLogBackup());
               oos.writeObject(boardToSave);
               oos.flush();
               oos.close();
            }catch (IOException e) {
                /*
                 * Changer l'action de l'exception
                 */
               e.printStackTrace();
            }
       }
       
       /**
        * Load the game (the board into a file).
        * @author david
        * @param fileToLoad file where the board will be loaded
        * @return boardToSave board to be save
        */
       public static Board loadGame(){
           Board boardLoaded = null;
           try {
                FileInputStream fis = new FileInputStream(javax.swing.filechooser.FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\JenseitsVonTheben\\board.boobs");
                ObjectInputStream ois = new ObjectInputStream(fis);
                boardLoaded = (Board) ois.readObject();
                LogDisplay.setLogBackup(boardLoaded.getLogDisplay());
                return boardLoaded;
            }
            catch ( FileNotFoundException e ){
                return null;
            }
            catch (IOException e) {
                /*
                 * Changer l'action de l'exception
                 */
                e.printStackTrace();
            }catch (ClassNotFoundException e) {
                /*
                 * Changer l'action de l'exception
                 */
                e.printStackTrace();
            }
           
           return boardLoaded;
       }
}
