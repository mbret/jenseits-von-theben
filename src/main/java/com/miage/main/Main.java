package com.miage.main;

import Interface.KnowledgeElement;
import Interface.UsableElement;
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

    public static void main(String[] args) throws IOException {

        Set<Player> players = new HashSet(){{
            this.add( new Player( "maxime", new PlayerToken( "#40A497" )));
            this.add( new Player( "anne la plus belle <3", new PlayerToken( "#111111" )));
            this.add( new Player( "gael la pelle", new PlayerToken( "#502222" )));
            this.add( new Player( "rouchard coeur de lion, richmont la raclette", new PlayerToken( "#40A100" )));
        }};

        Board board = new Board(4, players);
        List<UsableElement> usedElements; // list of knowledge element a player want to use during his round
        Player currentPlayer;
        boolean endGame = false;
        HashMap<String, Object> playerActionsParams = new HashMap(); // player actions parameters, defined dynamically depending of what the game need in specific action case
        
        /**
         * Main loop
         * - we got the upcoming player
         * - if there is no upcoming player it means that the game is over
         */
        while( (currentPlayer = board.getUpcomingPlayer()) != null ){
            
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
            if( ! board.isPlayerAbleToMakeRoundAction(Player.ACTION_CHANGE_FOUR_CARDS, currentPlayer, null, null, null) ){
                // deactivate gui function
            }
            else{
                hasOneActionPossible = true;
            }
            // TEST ACTION_EXCAVATE
            for (ExcavationArea area : board.getAreas( ExcavationArea.class ).values()) {
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_EXCAVATE, currentPlayer, area, null, null)){
                    // deactivate gui function
                }
                else{
                    hasOneActionPossible = true;
                }
            }
            // TEST ACTION_ORGANIZE_EXPO
            for (ExpoCard card : board.getExpoCards()) {
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_ORGANIZE_EXPO, currentPlayer, null, null, card)){
                    // deactivate gui function
                    hasOneActionPossible = true;
                }
                else{
                    hasOneActionPossible = true;
                }
            }
            // TEST ACTION_PICK_CARD
            for (int i = 0; i < 4; i++) {
                if( ! board.isPlayerAbleToMakeRoundAction( Player.ACTION_PICK_CARD, currentPlayer, null, i, null)){
                    // deactivate gui function
                }
                else{
                    hasOneActionPossible = true;
                }
            }

            /**
             * Here if the player can make one action then we do his selected action
             */
            if( hasOneActionPossible ){
                
               /**
                * Here we get the wanted player's action (GUI function)
                * - we do this action
                */
                
               // List of cards the user will use for this current round
               usedElements = new ArrayList( Arrays.asList(
                       currentPlayer.getSpecificCards( ZeppelinCard.class ).get(0) // player use his zeppelin card
               ) );

               // CASE OF ACTION_CHANGE_FOUR_CARDS
               board.doPlayerRoundAction( 
                       Player.ACTION_CHANGE_FOUR_CARDS, 
                       currentPlayer, 
                       usedElements, 
                       /* areaToExcavate */ null, 
                       /* cardToPickUp */ null, 
                       /* expoCardToDo */ null,
                       /* nbWeekForExcavation */ null);

               // CASE OF ACTION_PICK_CARD
               Card cardToPickUp = board.getFourCurrentCards().get( 2 ); // the player clicked on carte 3
               board.doPlayerRoundAction( 
                       Player.ACTION_PICK_CARD, 
                       currentPlayer, 
                       usedElements, 
                       /* areaToExcavate */ null, 
                       cardToPickUp, 
                       /* expoCardToDo */ null,
                       /* nbWeekForExcavation */ null);

               // CASE OF ACTION_ORGANIZE_EXPO
               ExpoCard expoCardToDo = board.getExpoCards().get( 1 );
               board.doPlayerRoundAction(
                       Player.ACTION_ORGANIZE_EXPO, 
                       currentPlayer, 
                       usedElements, 
                       /* areaToExcavate */ null, 
                       /* cardToPickUp */ null, 
                       expoCardToDo,
                       /* nbWeekForExcavation */ null);

               // CASE OF ACTION_EXCAVATE
               ExcavationArea areaToExcavate = (ExcavationArea)board.getAreas().get( "egypt" );
               usedElements.addAll(Arrays.asList(
                       new GeneralKnowledgeCard(0, null, null, 0, 0), // player use some specific knowledge card
                       new GeneralKnowledgeToken(null, null, null, 1), // player use some specific knowledge token
                       new AssistantCard(0, null, null, 0),
                       new EthnologicalKnowledgeCard(0, null, null, 0, 0, null)
               ));
               board.doPlayerRoundAction( 
                       Player.ACTION_EXCAVATE, 
                       currentPlayer, 
                       usedElements, 
                       areaToExcavate, 
                       /* cardToPickUp */ null, 
                       /* expoCardToDo */ null,
                       /* nbWeekForExcavation */ null);
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
}
