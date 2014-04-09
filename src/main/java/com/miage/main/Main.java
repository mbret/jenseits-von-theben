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
import com.miage.game.Board;
import com.miage.game.LogDisplay;
import com.miage.game.Player;
import com.miage.game.PlayerToken;
import com.miage.tokens.GeneralKnowledgeToken;
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

    public static void main(String[] args) throws IOException, Exception {

        List<Player> players = new ArrayList();
        players.add( new Player( "maxime", new PlayerToken( "#40A497" )));
        players.add( new Player( "anne la plus belle <3", new PlayerToken( "#111111" )));
        players.add( new Player( "gael la pelle", new PlayerToken( "#502222" )));
        players.add( new Player( "rouchard coeur de lion, richmont la raclette", new PlayerToken( "#40A100" )));

        // New game
        Board board = new Board(4, players);
        
        Player currentPlayer;

        
        /**
         * Main loop
         * - we got the upcoming player
         * - if there is no upcoming player it means that the game is over
         */
        while( (currentPlayer = board.getUpcomingPlayer()) != null ){
            
            // player actions parameters, defined dynamically depending of what the game need in specific action case
            HashMap<String, Object> playerActionParams = new HashMap();
            playerActionParams.put("player", currentPlayer); // wet set the current player
            playerActionParams.put("usedElements", new ArrayList<UsableElement>());
            playerActionParams.put("areaToExcavate", null);
            playerActionParams.put("cardToPickUp", null);
            playerActionParams.put("expoCardToDo", null);
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
            if( ! board.isPlayerAbleToMakeRoundAction(Player.ACTION_CHANGE_FOUR_CARDS, playerActionParams) ){
                // deactivate gui function
            }
            else{
                hasOneActionPossible = true;
            }
            // TEST ACTION_EXCAVATE
            for (ExcavationArea area : board.getAreas( ExcavationArea.class ).values()) {
                playerActionParams.put("areaToExcavate", area);
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_EXCAVATE, playerActionParams)){
                    // deactivate gui function
                }
                else{
                    hasOneActionPossible = true;
                }
            }
            // TEST ACTION_ORGANIZE_EXPO
            for (ExpoCard card : board.getExpoCards()) {
                playerActionParams.put("expoCardToDo", card);
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_ORGANIZE_EXPO, playerActionParams)){
                    // deactivate gui function
                }
                else{
                    hasOneActionPossible = true;
                }
            }
            // TEST ACTION_PICK_CARD
            for (Card card : board.getFourCurrentCards()) {
                playerActionParams.put("cardToPickUp", card);
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_PICK_CARD, playerActionParams)){
                    // deactivate gui function
                }
                else{
                    hasOneActionPossible = true;
                }
            }

            /**
             * NOW WE DO THE PLAYER ACTION
             * 
             * Here if the player can make one action then we do his selected action
             */
            if( hasOneActionPossible ){
                
                // Here we set some action parmaeters exemple 
                playerActionParams.put("player", currentPlayer); // we pass the current player
                playerActionParams.put("cardToPickUp", board.getFourCurrentCards().get( 2 )); // the player clicked on carte 3
                playerActionParams.put("expoCard", board.getExpoCards().get( 1 )); // the player do the second expo card
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
                ((List<UsableElement>)playerActionParams.get("usedElements")).addAll(Arrays.asList(
                       currentPlayer.getSpecificCards( ZeppelinCard.class ).get(0) // player use his zeppelin card
                ));

                
               /**
                * Here we get the wanted player's action (GUI function)
                * - we do this action
                */
               // CASE OF ACTION_CHANGE_FOUR_CARDS
               board.doPlayerRoundAction( Player.ACTION_CHANGE_FOUR_CARDS, playerActionParams);

               // CASE OF ACTION_PICK_CARD
               board.doPlayerRoundAction(  Player.ACTION_PICK_CARD, playerActionParams);

               // CASE OF ACTION_ORGANIZE_EXPO
               board.doPlayerRoundAction( Player.ACTION_ORGANIZE_EXPO, playerActionParams);

               // CASE OF ACTION_EXCAVATE
               board.doPlayerRoundAction(  Player.ACTION_EXCAVATE, playerActionParams);
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
            player.calculatePoint();
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
       public void saveGame(Board boardToSave, String fileToSave){
           try {
               FileOutputStream backupFile = new FileOutputStream(fileToSave+".boobs");
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
       public Board loadGame(String fileToLoad){
           Board boardLoaded = null;
           try {
                FileInputStream fis = new FileInputStream(fileToLoad);
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
