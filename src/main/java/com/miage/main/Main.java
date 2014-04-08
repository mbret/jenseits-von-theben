package com.miage.main;

import com.miage.game.Board;
import com.miage.game.Player;
import com.miage.game.PlayerToken;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


public class Main {

	public static void main(String[] args) throws IOException {
            
            Set<Player> players = new HashSet(){{
                this.add( new Player( "maxime", new PlayerToken( "#40A497" )));
                this.add( new Player( "anne la plus belle <3", new PlayerToken( "#111111" )));
                this.add( new Player( "gael la pelle", new PlayerToken( "#502222" )));
                this.add( new Player( "rouchard coeur de lion, richmont la raclette", new PlayerToken( "#40A100" )));
            }};
            
            Board board = new Board(4, players);
            
            Player currentPlayer;
            while( board.hasUpcomingPlayer() ){
                
                currentPlayer = board.getCurrentPlayer();
                
                if( ! board.isPlayerAbleToMakeRoundAction(Player.ACTION_CHANGE_FOUR_CARDS, currentPlayer) ){
                    // deactivate gui function
                }
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_EXCAVATE, currentPlayer)){
                    // deactivate gui function
                }
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_ORGANIZE_EXPO, currentPlayer)){
                    // deactivate gui function
                }
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_PICK_CARD, currentPlayer)){
                    // deactivate gui function
                }
                
                int ACTION_SELECTED = Player.ACTION_CHANGE_FOUR_CARDS; // selected via GUI
                
                board.doPlayerRoundAction( ACTION_SELECTED, currentPlayer );
                
            }
            
            board.calculatePoint();
            for (Player player : board.getPlayerTokensAndPlayers().values()) {
                // Display players with oints
            }
	}

        
        
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
    public void saveGame(Board boardToSave, String fileToSave){
        try {
            FileOutputStream backupFile = new FileOutputStream(fileToSave);
            ObjectOutputStream oos = new ObjectOutputStream(backupFile);
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
}
