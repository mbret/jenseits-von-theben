package com.miage.main;

import com.miage.interfaces.KnowledgeElement;
import com.miage.interfaces.UsableElement;
import com.miage.areas.ExcavationArea;
import com.miage.cards.AssistantCard;
import com.miage.cards.Card;
import com.miage.cards.EthnologicalKnowledgeCard;
import com.miage.cards.ExpoCard;
import com.miage.cards.GeneralKnowledgeCard;
import com.miage.cards.SpecificKnowledgeCard;
import com.miage.cards.ZeppelinCard;
import com.miage.config.ConfigManager;
import com.miage.game.Board;
import com.miage.game.LogDisplay;
import com.miage.game.Player;
import com.miage.game.PlayerToken;
import com.miage.tokens.GeneralKnowledgeToken;
import com.miage.tokens.SpecificKnowledgeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Map
 *  HashMap  : key->value, not ordered
 *  TreeMap  : key->value, ordered
 * 
 * Collection
 *  List
 *      ArrayList   : ordered, duplicate
 *      LinkedList  : ordered, duplicate
 *  Set
 *      HashSet     : unordered, unduplicate
 *      TreeSet     : unordered, unduplicate
 * @author Maxime
 */

public class Main {

    private final static Logger LOGGER = LogManager.getLogger(Main.class.getName());
    
    public static void main(String[] args) throws IOException, Exception {

        // We try to load all required config files
        try{
            ConfigManager.getInstance().loadAll();
        }
        catch( IOException e ){
            LOGGER.fatal("Unable to load config files");
            LOGGER.debug( e );
            System.exit( 1 );
        }
        
        // Create new players
        List<Player> players = new ArrayList();
        players.add( new Player( "maxime", new PlayerToken( "#40A497" )));
        players.add( new Player( "anne la plus belle <3", new PlayerToken( "#111111" )));
        players.add( new Player( "gael la pelle", new PlayerToken( "#502222" )));
        players.add( new Player( "rouchard coeur de lion, richmont la raclette", new PlayerToken( "#40A100" )));

        // Create new game
        Board board = new Board(4, players);
        
        // Usefull vars
        Player currentPlayer;
        Main m = new Main();

        
        /*
         * Main loop
         * - we got the upcoming player
         * - if there is no upcoming player it means that the game is over
         */
        while( (currentPlayer = board.getUpcomingPlayer()) != null ){
            
            // player actions parameters, defined dynamically depending of what the game need in specific action case
            HashMap<String, Object> playerActionParams = new HashMap();
            playerActionParams.put("player", currentPlayer); // wet set the current player
            playerActionParams.put("usedElements", new ArrayList<UsableElement>());
            playerActionParams.put("areaToExcavate", null); // put here one of the board excavationArea the player want to excavate
            playerActionParams.put("cardToPickUp", null); // put here one of the fourCurrentCard the player chose to pick up
            playerActionParams.put("expoCardToDo", null); // put here one of the board expoCard the player chose to do
            playerActionParams.put("nbWeeksForExcavation", null);
        
            /**
             * The player round actions are tested
             *  - If there are no action available this player end the game
             */
            boolean hasOneActionPossible = false;
            
            if( currentPlayer.hasCarCard() ){
                // add active card GUI
            }

            /**
             * We test here all available actions for the user
             * 
             */
            // TEST ACTION_CHANGE_FOUR_CARDS
            /*
            if( ! board.isPlayerAbleToMakeRoundAction(Player.ACTION_CHANGE_FOUR_CARDS, playerActionParams) ){
                // deactivate gui function
            }
            else{
                hasOneActionPossible = true;
            }
            */
            // TEST ACTION_EXCAVATE
            /*
            for (ExcavationArea area : board.getAreas( ExcavationArea.class ).values()) {
                playerActionParams.put("areaToExcavate", area);
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_EXCAVATE, playerActionParams)){
                    // deactivate gui function
                }
                else{
                    hasOneActionPossible = true;
                }
            }
            */
            // TEST ACTION_ORGANIZE_EXPO
            /*
            for (ExpoCard card : board.getExpoCards()) {
                playerActionParams.put("expoCardToDo", card);
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_ORGANIZE_EXPO, playerActionParams)){
                    // deactivate gui function
                }
                else{
                    hasOneActionPossible = true;
                }
            }
            */
            // TEST ACTION_PICK_CARD
            /*
            for (Card card : board.getFourCurrentCards()) {
                playerActionParams.put("cardToPickUp", card);
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_PICK_CARD, playerActionParams)){
                    // deactivate gui function
                }
                else{
                    hasOneActionPossible = true;
                }
            }
            */

            /**
             * NOW WE DO THE PLAYER ACTION
             * 
             * Here if the player can make one action then we do his selected action
             */
            if( hasOneActionPossible ){
                
                // Here we set some action parmaeters exemple 
                playerActionParams.put("areaToExcavate", (ExcavationArea)board.getAreas().get( "egypt" )); // the player decide to excavate egypt area
                // Because the player want to excavate we set some active cards
                currentPlayer.getCards().add( new GeneralKnowledgeCard(0, null, null, 0, 0) );
                currentPlayer.getTokens().add( new GeneralKnowledgeToken(null, null, null, 1) );
                ((List<UsableElement>)playerActionParams.get("usedElements")).addAll(Arrays.asList(
                       new AssistantCard(0, null, null, 0),
                       new AssistantCard(0, null, null, 0),
                       new EthnologicalKnowledgeCard(0, null, null, 0, 0, null)
                ));

                // Other cards the player could want to use
                /*
                ((List<UsableElement>)playerActionParams.get("usedElements")).addAll(Arrays.asList(
                       currentPlayer.getSpecificCards( ZeppelinCard.class ).get(0) // player use his zeppelin card
                ));
                */
                
               /**
                * Here we get the wanted player's action (GUI function)
                * - we do this action
                */
               // CASE OF ACTION_CHANGE_FOUR_CARDS
               //board.doPlayerRoundAction( Player.ACTION_CHANGE_FOUR_CARDS, playerActionParams);

               // CASE OF ACTION_PICK_CARD
               //board.doPlayerRoundAction(  Player.ACTION_PICK_CARD, playerActionParams);

               // CASE OF ACTION_ORGANIZE_EXPO
               //board.doPlayerRoundAction( Player.ACTION_ORGANIZE_EXPO, playerActionParams);

               // CASE OF ACTION_EXCAVATE
               //board.doPlayerRoundAction(  Player.ACTION_EXCAVATE, playerActionParams);
            }
            else{
                // If the player cannot do anything more then we move his playerToken to the endGame position
                board.movePlayerToEndGamePosition( currentPlayer );
            }
        }

        /**
         * This is the end game
         */
        for (Player player : board.getPlayerTokensAndPlayers().values()) {
//            player.calculatePoint();
        }
        
        // blabla ...
        
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
       public void saveGame(Board boardToSave){
           try {
               /*
                * default directory is C:/Users/[loginUser]/Documents/JenseitsVonTheben.
                * if directory doesn't exist, it's create the directory before save the file named "board.boobs"
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
       public Board loadGame(){
           Board boardLoaded = null;
           try {
                FileInputStream fis = new FileInputStream(javax.swing.filechooser.FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\JenseitsVonTheben\\board.boobs");
                ObjectInputStream ois = new ObjectInputStream(fis);
                boardLoaded = (Board) ois.readObject();
                LogDisplay.setLogBackup(boardLoaded.getLogDisplay());
                return boardLoaded;
            }catch (IOException e) {
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
