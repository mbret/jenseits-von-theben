/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.gui;

import com.miage.areas.ExcavationArea;
import com.miage.cards.*;
import com.miage.config.ConfigManager;
import com.miage.game.*;
import com.miage.interfaces.ActivableElement;
import com.miage.interfaces.ActiveElement;
import com.miage.interfaces.CombinableElement;
import com.miage.interfaces.UsableElement;
import com.miage.tokens.Token;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.IOException;
import static java.lang.reflect.Array.set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.log4j.LogManager;

/**
 * Class containing all elements (Map, Log, Cards, etc...) It's the main class
 * which serve to play
 *
 * @author Richard
 */
public class MapPanel extends javax.swing.JPanel {

    private final static org.apache.log4j.Logger LOGGER = LogManager.getLogger(MapPanel.class.getName());
    
    // Variables relating to the game
    private Board currentBoard;
    private Player currentPlayer;
    private ArrayList<UsableElement> currentPlayerUsingElements;
    private HashMap<String, Object> playerActionParams; // the hashmap that contains the action that a player can do
    private boolean hasPlayerOneActionPossible; //boolean that serves to know if a player can do an action (here if he can play)

    // variables relating to the UI
    // Used to work through list (set/update event, update UI)
    // The order is very important
    private HashMap<ActivableElement, Component> listOfUsableElementsComponent;    // component instanciated once
    private HashMap<UsableElement, Component> listOfUsingElementsComponent;            // contain all active element a player is using (object because of CarCard)
    private LinkedHashMap<Component, ExpoCard> listOfExpoCardsComponent;  // component instanciated once, only change linked expoCard (all should be always ordened)
    private LinkedHashMap<Component, Card> listOfBoardCardsComponent;     // components instanciated once, only cards are changed (all should be always ordened)
    private HashMap<Component, ExcavationArea> listOfExcavationSiteComponent; 
    
    private Player currentPlayerLeftPanel;

    private final MapPanel instance = null;
    
    /**
     * Factory constructor
     * @param board
     * @return
     * @throws Exception 
     */
    public static MapPanel create( Board board ) throws Exception{
        MapPanel instance = new MapPanel( board );
        instance.switchNewPlayer();
        return instance;
    }
    
    /**
     * Creates new form MapPanel
     *
     * @param board Board initialize in the menu panel
     */
    private MapPanel(Board board) throws IOException, Exception {
        
        // Init graphical components
        initComponents();
        
        // Init the board and the tabbed pane with name's players
        this.currentBoard = board; // active board
        
        // Init list of board cards component (the four cards)
        this.listOfBoardCardsComponent = new LinkedHashMap();
        this._updateBoardCardsComponent( this.currentBoard.getFourCurrentCards() );
        this._updateBoardCardsUI();
        
        // Init list of expo cards component
        this.listOfExpoCardsComponent = new LinkedHashMap();
        this._updatExpoCardsComponent( this.currentBoard.getExpoCards() );
        this._updateExpoCardsUI();
        
        // Init list of usable element component
        this.listOfUsableElementsComponent = new HashMap();
        // ... rest will be updated inside (switchplayer) function
        
        // Init list of using element component
        this.listOfUsingElementsComponent = new HashMap();
        // ... rest will be updated inside (switchplayer) function
        
        // Init list of excavation component
        this.listOfExcavationSiteComponent = new HashMap();
        this._updateExcavationSiteComponent( new ArrayList( this.currentBoard.getAreas( ExcavationArea.class ).values() ) ) ;
        this._updateExcavationSiteUI();
        
        // Add event on click (change four cards)
        changeFourCardsjButton.addActionListener(
            new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    _actionChangeFourcardsButtonActionPerformed(evt, true);
                }
            }
        );
        
        
        
        /*
            Update the left panel
        */
        this.playerPanel.setVisible(false); // hide player panel
        this.menuCardsPlayerTab.setVisible(false); // hide left panel
        displayedCardTokenPanel.setVisible(false);
        // add and set dynamicaly player tab on left panel
        this.menuCardsPlayerTab.removeTabAt(0);
        for (Player player : this.currentBoard.getPlayers()) {
            javax.swing.JPanel playerLeftPanel = new javax.swing.JPanel();
            playerLeftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
            playerLeftPanel.setBorder( BorderFactory.createEmptyBorder() );
            this.menuCardsPlayerTab.addTab( player.getName(), playerLeftPanel);
        }
//        this.menuCardsPlayerTab.setSelectedIndex(0); // Select the first player in the tabbed pane and update the current player with the first one
        try {
//            leftPanelViewingPlayer = this.currentBoard.getUpcomingPlayer(); // set the viewing player on the left panel
//            this.getPlayerTab( menuCardsPlayerTab );
        } catch (Exception ex) {
            Logger.getLogger(MapPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    
    
    /***********************************************************************************************
     *
     *                                  Various methods
     * 
     ***********************************************************************************************/
    
    /**
     * Get the new upcoming player and init everything about him.
     * <br/>- update actions listeners 
     */
    private void switchNewPlayer() {
        try{
            
            this.currentPlayer = this.currentBoard.getUpcomingPlayer();
            if( this.currentPlayer == null ){
                JOptionPane.showMessageDialog( this, "Plus aucun joueur ne peut jouer" );
            }
            else{

                /**
                 * For each round
                 * - reset or update useful intern vars
                 * - reset params of action method
                 * - update component to match the actual player
                 * - update UI to match the actual player
                 * - Test and lock player's action
                 */
                
                // Update intern vars
                this.currentPlayerLeftPanel = this.currentPlayer; // active left panel player
                this.currentPlayerUsingElements = new ArrayList(); //Init the displayed list and hashmap of usable Card

                // Init the params of the player's action
                this.playerActionParams = new HashMap();
                this.playerActionParams.put("player", currentPlayer); // wet set the current player
                this.playerActionParams.put("usedElements", this.currentPlayerUsingElements);
                this.playerActionParams.put("areaToExcavate", null); // put here one of the board excavationArea the player want to excavate
                this.playerActionParams.put("cardToPickUp", null); // put here one of the fourCurrentCard the player chose to pick up
                this.playerActionParams.put("expoCardToDo", null); // put here one of the board expoCard the player chose to do
                this.playerActionParams.put("nbTokenToPickUp", null); // number of tokens the player is allowed to pick up inside area
                
                // Update player's component
                this._updateUsableElementComponent( this.currentPlayer.getAllUsableElements() );
                this._updateUsingElementComponent( this.currentPlayerUsingElements );
                
                // Update UI
                this._updatePlayerUsableElementUI();
                this._updatePlayerUsingElementUI();

                /*
                    Test the players actions
                */
                LOGGER.debug("Test player able to do all actions");
                this.hasPlayerOneActionPossible = false;
                // Set active car card in case of player has
                if( currentPlayer.hasCarCard() ){
                    // set visible
                }

                // TEST ACTION_CHANGE_FOUR_CARDS
                if( ! this.currentBoard.isPlayerAbleToMakeRoundAction(Player.ACTION_CHANGE_FOUR_CARDS, playerActionParams) ){
                    
                    // We remove and set the new listener
                    changeFourCardsjButton.removeMouseListener( changeFourCardsjButton.getMouseListeners()[0] ); // we remove the previous mouse listener listener
                    changeFourCardsjButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            _actionChangeFourcardsButtonActionPerformed(evt, false);
                        }
                    });
                }
                else{
                    hasPlayerOneActionPossible = true;
                }

                // TEST ACTION_EXCAVATE
                List<ExcavationArea> excaAreas = new ArrayList( this.currentBoard.getAreas( ExcavationArea.class ).values() );
                for (int i = 0; i < excaAreas.size(); i++) {
                
                    final ExcavationArea area = excaAreas.get(i);
                    playerActionParams.put("areaToExcavate", area);
                    if( ! this.currentBoard.isPlayerAbleToMakeRoundAction( Player.ACTION_EXCAVATE, playerActionParams)){
                        
                        // We remove and set the new listener
                        JLabel excavationSiteLabel = (JLabel) this.listOfExcavationSiteComponent.keySet().toArray()[i];
                        final int idExpoCard = i;
                        excavationSiteLabel.removeMouseListener( excavationSiteLabel.getMouseListeners()[0] ); // we remove the previous mouse listener listener
                        excavationSiteLabel.addMouseListener(
                            new java.awt.event.MouseAdapter() {
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    _actionExcavationSiteMouseClicked(evt, area, false);
                                }
                            }
                        );
                    }
                    else{
                        hasPlayerOneActionPossible = true;
                    }
                }

                // TEST ACTION_ORGANIZE_EXPO
                for (int i = 0; i < this.currentBoard.getExpoCards().size(); i++) {

                    ExpoCard card = this.currentBoard.getExpoCards().get( i );
                    playerActionParams.put("expoCardToDo", card);
                    if( ! this.currentBoard.isPlayerAbleToMakeRoundAction( Player.ACTION_ORGANIZE_EXPO, playerActionParams)){
                        
                        // We remove and set the new listener
                        JLabel expoCardLabel = (JLabel) this.listOfExpoCardsComponent.keySet().toArray()[i];
                        final int idExpoCard = i;
                        expoCardLabel.removeMouseListener( expoCardLabel.getMouseListeners()[0] ); // we remove the previous mouse listener listener
                        expoCardLabel.addMouseListener(
                            new java.awt.event.MouseAdapter() {
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    _actionBoardExpoLabelMouseClicked(evt, idExpoCard, false);
                                }
                            }
                        );

                    }
                    else{
                        hasPlayerOneActionPossible = true;
                    }
                }

                // TEST ACTION_PICK_CARD
                for (int i = 0; i < this.currentBoard.getFourCurrentCards().size(); i++) {

                    Card card = this.currentBoard.getFourCurrentCards().get(i);
                    playerActionParams.put("cardToPickUp", card);
                    final int idCardToPickUp = this.currentBoard.getFourCurrentCards().indexOf( card );

                    if( ! this.currentBoard.isPlayerAbleToMakeRoundAction( Player.ACTION_PICK_CARD, playerActionParams)){

                        // We remove and set the new listener
                        JLabel boardCardLabel = (JLabel) this.listOfBoardCardsComponent.keySet().toArray()[i];
                        boardCardLabel.removeMouseListener( boardCardLabel.getMouseListeners()[0] ); // we remove the previous mouse listener listener
                        boardCardLabel.addMouseListener(
                            new java.awt.event.MouseAdapter() {
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    _actionBoardCardLabelMouseClicked(evt, idCardToPickUp, false);
                                }
                            }
                        );
                    }
                    else{
                        hasPlayerOneActionPossible = true;
                    }
                }

//                JOptionPane.showMessageDialog( this, "Joueur " + this.currentPlayer.getName() + ", c'est à vous de jouer !");
            }
        }
        catch( Exception ex ){
            LOGGER.fatal( ex );
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void _updateUsingElementComponent( List<UsableElement> elements ){
        LOGGER.debug("_updateUsingElementComponent");
        
        this.listOfUsingElementsComponent.clear();
        
        for (final UsableElement element : elements) {
            
            javax.swing.JLabel elementLabel = new javax.swing.JLabel();

            // Add the click event
            if( element instanceof ActivableElement ){
                elementLabel.addMouseListener(
                    new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            _actionUsingElementLabelMouseClicked(evt, element );
                        }
                    }
                );
            }
            
            this.listOfUsingElementsComponent.put( element, elementLabel);
        }
        
    }
    
    private void _updateUsableElementComponent( List<ActivableElement> elements ){
        LOGGER.debug("_updateUsableElementComponent");
        
        this.listOfUsableElementsComponent.clear();
        
        for (final ActivableElement element : elements) {
            
            javax.swing.JLabel elementLabel = new javax.swing.JLabel();

            // Add the click event
            elementLabel.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        _actionUsableElementLabelMouseClicked(evt, element );
                    }
                }
            );
            
            this.listOfUsableElementsComponent.put( element, elementLabel);
        }
    }
    
    /**
     * /!\ not optimized
     * @param expoCards 
     */
    private void _updatExpoCardsComponent( List<ExpoCard> expoCards ){
        LOGGER.debug("_updatExpoCardsComponent");
        this.listOfExpoCardsComponent.clear();
        for (int i = 0; i < expoCards.size(); i++) {
            
            JLabel expoCardComponent = new JLabel();
            
            final int idExpoCard = i;
            expoCardComponent.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        _actionBoardExpoLabelMouseClicked(evt, idExpoCard, true);
                    }
                }
            );
            
            this.listOfExpoCardsComponent.put(
                    expoCardComponent, 
                    expoCards.get(i));
        }
    }
    
    /**
     * Not optimized
     * <br/>Remove all component
     * <br/>For each card add new component
     * <br/>For each component add a listener
     * @param cards 
     */
    private void _updateBoardCardsComponent( List<Card> cards ){
        
        this.listOfBoardCardsComponent.clear(); // reset all
        
        for (int i = 0; i < cards.size(); i++) {
            
            Card card = cards.get( i );
            final int idCard = i;
            JLabel boardcardComponent = new JLabel();
            listOfBoardCardsComponent.put(
                    boardcardComponent, // add new component
                    card);              // link to the card
            
            // Add the event to the newly creted component
            boardcardComponent.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        _actionBoardCardLabelMouseClicked(evt, idCard, true);
                    }
                }
            );
        }
    }
    
    private void _updateExcavationSiteComponent( List<ExcavationArea> areas ) throws IOException{
        for (final ExcavationArea excavationArea : areas) {
            JLabel excavationSiteLabel = new JLabel();
            
            excavationSiteLabel.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        _actionExcavationSiteMouseClicked(evt, excavationArea, true);
                    }
                }
            );
            
            this.listOfExcavationSiteComponent.put( excavationSiteLabel, excavationArea);
        
        }
    }
    
    
    
    /***********************************************************************************************
     *
     *                                  Update UI
     * 
     ***********************************************************************************************/
    
    /**
     * Update the display off all four cards from the list of component
     */
    private void _updateBoardCardsUI(){
        this.boardCardsContainerPanel.removeAll();
        for (Map.Entry<Component, Card> entry : this.listOfBoardCardsComponent.entrySet()) {
            
            this.boardCardsContainerPanel.add( entry.getKey() );
            
            // Set icon depend of cards
            ((JLabel)entry.getKey()).setIcon( new javax.swing.ImageIcon(getClass().getResource("/images/cards/" + entry.getValue().getId() + ".jpg")) );
            ((JLabel)entry.getKey()).setCursor( new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR) );
            
            // Do wathever you want abotu display here
        }
        this.boardCardsContainerPanel.updateUI();
    }
    
    /**
     * 
     * 
     */
    private void _updatePlayerUsableElementUI(){
        LOGGER.debug("_updatePlayerUsableElementUI: refresh of usable element panel");
        
        this.usableElementsMenuPanel.removeAll();
        for (Map.Entry<ActivableElement, Component> entry : this.listOfUsableElementsComponent.entrySet()) {
            
            // card
            if( entry.getKey() instanceof Card){
                ((JLabel)entry.getValue()).setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/" + ((Card)entry.getKey()).getId() + ".jpg")));
            }
            // token
            else{
                ((JLabel)entry.getValue()).setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/token/" + ((Token)entry.getKey()).getId() + ".jpg")));
            }
            
            this.usableElementsMenuPanel.add( entry.getValue() );
                
        }
        this.usableElementsMenuPanel.updateUI();
    }
    
    /**
     * 
     */
    private void _updatePlayerUsingElementUI(){
        LOGGER.debug("_updatePlayerUsingElementUI:");
        this.usingElementsMenuPanel.removeAll();
        
        for (Map.Entry<UsableElement, Component> entry : this.listOfUsingElementsComponent.entrySet()) {
            
            // card
            if( entry.getKey() instanceof Card){
                ((JLabel)entry.getValue()).setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/" + ((Card)entry.getKey()).getId() + ".jpg")));
            }
            // token
            else{
                ((JLabel)entry.getValue()).setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/token/" + ((Token)entry.getKey()).getId() + ".jpg")));
            }
            
            this.usingElementsMenuPanel.add( entry.getValue() );
        }
        
        // Extra display for car
        if(this.currentPlayer.hasCarCard()){
            // move there / here ...
        }
        
        this.usingElementsMenuPanel.updateUI();
        
    }
    
    /**
     * 
     */
    private void _updateExpoCardsUI(){
        LOGGER.debug("_updateExpoCardsUI");
        
        this.expoCardsContainerPanel.removeAll();
        
        for (Map.Entry<Component, ExpoCard> entry : this.listOfExpoCardsComponent.entrySet()) {
            
            // Do wathever you want about display here
            ((JLabel)entry.getKey()).setIcon( new ImageIcon(getClass().getResource("/images/cards/" + entry.getValue().getId() + ".jpg")) );
            ((JLabel)entry.getKey()).setCursor( new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR) );
            
            // Set the position and others ...
            
            this.expoCardsContainerPanel.add( entry.getKey() );
            LOGGER.debug("_updateExpoCardsUI: One expoCard Label has been added to the container");
        }

        this.expoCardsContainerPanel.updateUI();
    }
    
    private void _updateExcavationSiteUI() throws IOException{
        this.excavationContainerPanel.removeAll();
        for (Map.Entry<Component, ExcavationArea> entry : this.listOfExcavationSiteComponent.entrySet()) {
            
            ((JLabel)entry.getKey()).setIcon( new ImageIcon( getClass().getResource( ConfigManager.getInstance().getConfig(ConfigManager.GENERAL_CONFIG_NAME).getProperty("path.images") + "excavate-icon.png")) );
            ((JLabel)entry.getKey()).setCursor( new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR) );
            
            // ... set the positions and others
            
            this.excavationContainerPanel.add( entry.getKey() );
        }
        this.excavationContainerPanel.updateUI();
    }
    
    
    
    /***********************************************************************************************
     *
     *                                  Animations
     * 
     ***********************************************************************************************/
    
    /**
     * Animate the action of picking cards
     * @param pickedCards
     * @param indexOfCardOnBoard 
     */
    private void _animatePickingCard( Card pickedCard, int indexOfCardOnBoard ){
        // ...
//        JOptionPane.showMessageDialog( this, "Vous venez de piocher une carte");
        // ...
    }
    
    private void _animatePlayerMoving(){}
    
    private void _animatePlayerExcavating(){}
    
    private void _animatePickingTokens( List<Token> tokensPicked ){
        JOptionPane.showMessageDialog( this, "Vous venez de piocher : " + tokensPicked);
        for (Token token : tokensPicked) {
            // ...
        }
    }
    
    
    /***********************************************************************************************
     *
     *                                  Own events
     * 
     ***********************************************************************************************/
    
    /**
     * Trigger when a player click on one of the four cards on board.
     * <br/>Do the main action
     * <br/>Update usable element UI
     * <br/>Update using element UI
     * <br/>Update expo cards UI
     * @param evt
     * @param label 
     */
    private void _actionBoardCardLabelMouseClicked(java.awt.event.MouseEvent evt, int idCard, boolean playerIsAble) {
        LOGGER.debug("boardCardLabelMouseClicked: Player click on one card = " + evt.getComponent().getName());
        
        if( playerIsAble ){
            
//            HashMap<String, Object> returnedInfo = null;
            Card pickedCard = null;
            
            //Put the card to pick up inside the hashmap
            playerActionParams.put("cardToPickUp", this.currentBoard.getFourCurrentCards().get( idCard ) );
            playerActionParams.put("usedElements", this.currentPlayerUsingElements);
            
            // DO THE MAIN ACTION 
            try {
                pickedCard = (Card) ((HashMap<String, Object>) currentBoard.doPlayerRoundAction(Player.ACTION_PICK_CARD, playerActionParams)).get("pickedCard");
            } catch (Exception ex) {
                LOGGER.fatal( ex );
                System.exit(0);
            }
            
            // Animate the action of picking
            this._animatePickingCard( pickedCard, idCard);
                    
            // We eventually add the final picked card to using eement if this card is an activeElement
            if( pickedCard instanceof ActiveElement ){
                this.currentPlayerUsingElements.add( (UsableElement) pickedCard);
            }
            
            // Update the card linked to each component
            this._updatExpoCardsComponent( this.currentBoard.getExpoCards() );
            this._updateBoardCardsComponent( this.currentBoard.getFourCurrentCards() );
            
            _updatePlayerUsableElementUI();
            _updatePlayerUsingElementUI();
            _updateExpoCardsUI();
            _updateBoardCardsUI();
            
            this.switchNewPlayer();
        }
        else{
            JOptionPane.showMessageDialog( this , "Vous ne pouvez pas piocher ");
        }
    }    
    
    private void _actionBoardExpoLabelMouseClicked(java.awt.event.MouseEvent evt, int idExpoCard, boolean playerIsAble) {
        LOGGER.debug("boardExpoLabelMouseClicked: Player click on one expo card = " + evt.getComponent().getName());
        
        if( playerIsAble ){
            Card expoCardToDo = this.currentBoard.getExpoCards().get( idExpoCard );
            
            //Put the card to pick up inside the hashmap
            playerActionParams.put("expoCardToDo", expoCardToDo );
            playerActionParams.put("usedElements", this.currentPlayerUsingElements);
            
            // DO THE MAIN ACTION
            try {
                currentBoard.doPlayerRoundAction(Player.ACTION_ORGANIZE_EXPO, playerActionParams);
            } catch (Exception ex) {
                LOGGER.fatal( ex );
                System.exit(0);
            }
            
            this._updatExpoCardsComponent( this.currentBoard.getExpoCards() );
            
            _updatePlayerUsableElementUI();
            _updatePlayerUsingElementUI();
            _updateExpoCardsUI();
            
        }
        else{
            JOptionPane.showMessageDialog( this , "Vous ne pouvez effectuer cette exposition");
        }
    }

    /**
     * When the player click on the activabe element on the panel right
     * @param evt
     * @param usableElement 
     */
    private void _actionUsableElementLabelMouseClicked(java.awt.event.MouseEvent evt, ActivableElement element){
        if( this.currentPlayerUsingElements.contains( (UsableElement)element )){
            JOptionPane.showMessageDialog( this , "Vous utilisez déjà cet element ");
        }
        else{
            this.currentPlayerUsingElements.add( (UsableElement)element );
            this._updateUsingElementComponent( this.currentPlayerUsingElements );
            this._updatePlayerUsingElementUI();
        }
    }
    
    /**
     * When the player want to remove an activated element
     * @param evt
     * @param usableElement 
     */
    private void _actionUsingElementLabelMouseClicked(java.awt.event.MouseEvent evt, UsableElement element ){
        if( ! (element instanceof ActivableElement) ){
            JOptionPane.showMessageDialog( this, "Vous ne pouvez pas désactiver cet element");
        }
        else{
            this.currentPlayerUsingElements.remove( element );
            this._updateUsingElementComponent( this.currentPlayerUsingElements );
            this._updatePlayerUsingElementUI();
        }
        
    }

    private void _actionChangeFourcardsButtonActionPerformed(java.awt.event.ActionEvent evt, boolean playerIsAble){
        LOGGER.debug("changeFourcardsButtonActionPerformed:");
        
        if( playerIsAble ){
            
            playerActionParams.put("usedElements", this.currentPlayerUsingElements);
            
            // DO THE MAIN ACTION
            try {
                currentBoard.doPlayerRoundAction(Player.ACTION_CHANGE_FOUR_CARDS, playerActionParams);
            } catch (Exception ex) {
                LOGGER.fatal( ex.getMessage() );
                ex.printStackTrace();
                System.exit( 0 );
            }
            
            // Update the card linked to each component
            this._updatExpoCardsComponent( this.currentBoard.getExpoCards() );
            this._updateBoardCardsComponent( this.currentBoard.getFourCurrentCards() );
            
            _updateExpoCardsUI();
            _updateBoardCardsUI();
            
            this.switchNewPlayer();

        }
        else{
            JOptionPane.showMessageDialog( this , "Vous ne pouvez pas changer les quatres cartes ");
        }
    }
    
    private void _actionExcavationSiteMouseClicked( java.awt.event.MouseEvent evt, ExcavationArea area, boolean playerIsAble ){
        if( ! playerIsAble ){
            JOptionPane.showMessageDialog( this, "Vous ne pouvez pas fouiller " + area.getName());
        }
        else{
            playerActionParams.put("areaToExcavate", area);
 
            List<Token> tokensJustPickedUp = null;
            
            // DO THE MAIN ACTION
            try {
                tokensJustPickedUp = (List<Token>) currentBoard.doPlayerRoundAction(Player.ACTION_CHANGE_FOUR_CARDS, playerActionParams).get("tokensJustPickedUp");
            } catch (Exception ex) {
                LOGGER.fatal( ex.getMessage() );
                ex.printStackTrace();
                System.exit( 0 );
            }
            
            this._animatePickingTokens( tokensJustPickedUp );
            
            this.switchNewPlayer();
            
            JOptionPane.showMessageDialog( this, "Vous venez de fouiller " + area.getName());
        }
    }
    
    
    
    /***********************************************************************************************
     *
     *                               Netbeans & auto generated
     * 
     ***********************************************************************************************/
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        arrowMenuLabel = new javax.swing.JLabel();
        changeFourCardsjButton = new javax.swing.JButton();
        excavationContainerPanel = new javax.swing.JPanel();
        expoCardsContainerPanel = new javax.swing.JPanel();
        usingElementsMenuPanel = new javax.swing.JPanel();
        usableElementsMenuPanel = new javax.swing.JPanel();
        boardCardsContainerPanel = new javax.swing.JPanel();
        playerPanel = new javax.swing.JPanel();
        berlinAssistantLabel = new javax.swing.JLabel();
        moscowCarLabel = new javax.swing.JLabel();
        berlinEtnoLabel = new javax.swing.JLabel();
        romaZeppelinLabel = new javax.swing.JLabel();
        londonExcavationLabel = new javax.swing.JLabel();
        berlinCongressPLabel = new javax.swing.JLabel();
        berlinGenKnowledgeLabel = new javax.swing.JLabel();
        moscowScienKnowledgeLabel = new javax.swing.JLabel();
        londonShovelLabel = new javax.swing.JLabel();
        berlinSmallExpoLabel = new javax.swing.JLabel();
        mesopotamiaExcavationLabel = new javax.swing.JLabel();
        palestineExcavationLabel = new javax.swing.JLabel();
        egyptExcavationLabel = new javax.swing.JLabel();
        creteExcavationLabel = new javax.swing.JLabel();
        greeceExcavationLabel = new javax.swing.JLabel();
        mesopotamiaNullTokenLabel = new javax.swing.JLabel();
        palestineNullTokenLabel = new javax.swing.JLabel();
        greeceNullTokenLabel = new javax.swing.JLabel();
        creteNullTokenLabel = new javax.swing.JLabel();
        egyptNullTokenLabel = new javax.swing.JLabel();
        playerBackgroundLabel = new javax.swing.JLabel();
        menuCardsPlayerTab = new javax.swing.JTabbedPane();
        player1Panel = new javax.swing.JPanel();
        logMenu = new javax.swing.JPanel();
        logMenuScrollBar = new javax.swing.JScrollBar();
        displayedCardTokenPanel = new javax.swing.JPanel();
        backgroundLabel = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        arrowMenuLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background/menuArrow.png"))); // NOI18N
        arrowMenuLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                arrowMenuLabelMouseEntered(evt);
            }
        });
        add(arrowMenuLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -3, -1, 770));

        changeFourCardsjButton.setText("Changer les quatres cartes");
        changeFourCardsjButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        add(changeFourCardsjButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 410, 190, 50));

        excavationContainerPanel.setOpaque(false);
        add(excavationContainerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, 540, 360));

        expoCardsContainerPanel.setOpaque(false);
        add(expoCardsContainerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 440, 400, 300));
        add(usingElementsMenuPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 550, 320, 220));
        add(usableElementsMenuPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 180, 320, 370));

        boardCardsContainerPanel.setOpaque(false);
        add(boardCardsContainerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 240, 350));

        playerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playerPanelMouseExited(evt);
            }
        });
        playerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        berlinAssistantLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/18.jpg"))); // NOI18N
        berlinAssistantLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                berlinAssistantLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                berlinAssistantLabelMouseExited(evt);
            }
        });
        playerPanel.add(berlinAssistantLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, -1, -1));

        moscowCarLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/5.jpg"))); // NOI18N
        moscowCarLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                moscowCarLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                moscowCarLabelMouseExited(evt);
            }
        });
        playerPanel.add(moscowCarLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        berlinEtnoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/85.jpg"))); // NOI18N
        berlinEtnoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                berlinEtnoLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                berlinEtnoLabelMouseExited(evt);
            }
        });
        playerPanel.add(berlinEtnoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, -1, -1));

        romaZeppelinLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/3.jpg"))); // NOI18N
        romaZeppelinLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                romaZeppelinLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                romaZeppelinLabelMouseExited(evt);
            }
        });
        playerPanel.add(romaZeppelinLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        londonExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/1.jpg"))); // NOI18N
        londonExcavationLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                londonExcavationLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                londonExcavationLabelMouseExited(evt);
            }
        });
        playerPanel.add(londonExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        berlinCongressPLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/9.jpg"))); // NOI18N
        berlinCongressPLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                berlinCongressPLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                berlinCongressPLabelMouseExited(evt);
            }
        });
        playerPanel.add(berlinCongressPLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        berlinGenKnowledgeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/31.jpg"))); // NOI18N
        berlinGenKnowledgeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                berlinGenKnowledgeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                berlinGenKnowledgeLabelMouseExited(evt);
            }
        });
        playerPanel.add(berlinGenKnowledgeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        moscowScienKnowledgeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/74.jpg"))); // NOI18N
        moscowScienKnowledgeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                moscowScienKnowledgeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                moscowScienKnowledgeLabelMouseExited(evt);
            }
        });
        playerPanel.add(moscowScienKnowledgeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, -1));

        londonShovelLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/22.jpg"))); // NOI18N
        londonShovelLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                londonShovelLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                londonShovelLabelMouseExited(evt);
            }
        });
        playerPanel.add(londonShovelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, -1, -1));

        berlinSmallExpoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/88.jpg"))); // NOI18N
        berlinSmallExpoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                berlinSmallExpoLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                berlinSmallExpoLabelMouseExited(evt);
            }
        });
        playerPanel.add(berlinSmallExpoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, -1, -1));

        mesopotamiaExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/excavations/recto/mesopotamiaExcavation.jpg"))); // NOI18N
        playerPanel.add(mesopotamiaExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 550, -1, -1));

        palestineExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/excavations/recto/palestineExcavation.jpg"))); // NOI18N
        playerPanel.add(palestineExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 550, -1, -1));

        egyptExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/excavations/recto/egyptExcavation.jpg"))); // NOI18N
        playerPanel.add(egyptExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 550, -1, -1));

        creteExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/excavations/recto/creteExcavation.jpg"))); // NOI18N
        playerPanel.add(creteExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, -1));

        greeceExcavationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/excavations/recto/greeceExcavation.jpg"))); // NOI18N
        playerPanel.add(greeceExcavationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 550, -1, -1));

        mesopotamiaNullTokenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/mesopotamia/blueNull.png"))); // NOI18N
        mesopotamiaNullTokenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mesopotamiaNullTokenLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mesopotamiaNullTokenLabelMouseExited(evt);
            }
        });
        playerPanel.add(mesopotamiaNullTokenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 650, -1, -1));

        palestineNullTokenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/palestine/greenNull.png"))); // NOI18N
        palestineNullTokenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                palestineNullTokenLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                palestineNullTokenLabelMouseExited(evt);
            }
        });
        playerPanel.add(palestineNullTokenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 650, -1, -1));

        greeceNullTokenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/greece/orangeNull.png"))); // NOI18N
        greeceNullTokenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                greeceNullTokenLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                greeceNullTokenLabelMouseExited(evt);
            }
        });
        playerPanel.add(greeceNullTokenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 650, -1, -1));

        creteNullTokenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/crete/purpleNull.png"))); // NOI18N
        creteNullTokenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                creteNullTokenLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                creteNullTokenLabelMouseExited(evt);
            }
        });
        playerPanel.add(creteNullTokenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 650, -1, -1));

        egyptNullTokenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/egypt/yellowNull.png"))); // NOI18N
        egyptNullTokenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                egyptNullTokenLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                egyptNullTokenLabelMouseExited(evt);
            }
        });
        playerPanel.add(egyptNullTokenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 650, -1, -1));

        playerBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background/playerBackground.jpg"))); // NOI18N
        playerPanel.add(playerBackgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        add(playerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 25, -1, -1));

        player1Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        menuCardsPlayerTab.addTab("Joueur 1", player1Panel);

        add(menuCardsPlayerTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 770));

        logMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        logMenu.add(logMenuScrollBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, -1, 180));

        add(logMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 320, 180));

        displayedCardTokenPanel.setOpaque(false);
        add(displayedCardTokenPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 550, 730));

        backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background/map.jpg"))); // NOI18N
        add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    /**
     * When you put your mouse in the left of the screen, it shows the player
     * Menu
     *
     * @param evt the mouse event that serves for launching the method
     */
    private void arrowMenuLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arrowMenuLabelMouseEntered
        menuCardsPlayerTab.setVisible(true);
        playerPanel.setVisible(true);
        arrowMenuLabel.setVisible(false);
        backgroundLabel.setEnabled(false);
//        boardCard1Label.setEnabled(false);
//        boardCard2Label.setEnabled(false);
//        boardCard3Label.setEnabled(false);
//        boardCard4Label.setEnabled(false);
    }//GEN-LAST:event_arrowMenuLabelMouseEntered


    /**
     * Shows cards owned by the current player
     *
     * @param cl Class of the card to show
     */
    private void displayPlayerCard(Class cl) {
        try {
            //Get the current player
            this.getPlayerTab(menuCardsPlayerTab);
            if (currentPlayer.getCards().size() > 0) {
                displayedCardTokenPanel.setVisible(true);
                // For each card of the list, compare the class of the card with the class in parameter, if it's good, display
                for (Card c : currentPlayer.getCards()) {
                    if (c.getClass().getName().equals(cl.getName())) {
                        javax.swing.JLabel imageCard = new javax.swing.JLabel();
                        imageCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cards/" + c.getId() + ".jpg")));
                        displayedCardTokenPanel.add(imageCard);
                    }
                }
                displayedCardTokenPanel.updateUI();
            }
        } catch (Exception ex) {
            Logger.getLogger(MapPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Shows tokens owned by the current player
     *
     * @param color Color of the token to show
     */
    private void displayPlayerAreaToken(String color) {
        try {
            //Get the current player
            this.getPlayerTab(menuCardsPlayerTab);
            if (currentPlayer.getTokens().size() > 0) {
                displayedCardTokenPanel.setVisible(true);
                // For each token of the player's list, compare the color of the token with the color in parameter, if it's good, display
                for (Token t : currentPlayer.getTokens()) {
                    if (t.getColor().equals(color)) {
                        javax.swing.JLabel imageToken = new javax.swing.JLabel();
                        imageToken.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tokens/" + t.getAreaName() + "/" + t.getId() + ".png")));
                        displayedCardTokenPanel.add(imageToken);
                    }
                }
                displayedCardTokenPanel.updateUI();
            }
        } catch (Exception ex) {
            Logger.getLogger(MapPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Return the player corresponding to the tab clicked in the tabbed pane
     *
     * @param tp the player Tabbed Pane
     * @return The Player corresponding to the tab selected
     */
    private void getPlayerTab(javax.swing.JTabbedPane tp) throws Exception {
//        switch (tp.getSelectedIndex()) {
//            case 0:
//                currentBoard.setCurrentPlayerToken(playerToken1);
//                currentPlayer = player1;
//                break;
//            case 1:
//                currentBoard.setCurrentPlayerToken(playerToken2);
//                currentPlayer = player2;
//                break;
//            case 2:
//                currentBoard.setCurrentPlayerToken(playerToken3);
//                currentPlayer = player3;
//                break;
//            case 3:
//                currentBoard.setCurrentPlayerToken(playerToken4);
//                currentPlayer = player4;
//                break;
//        }
//
//        //Test if the current player can do something
//        canPlayPlayer = this.playerCanPlay(currentPlayer, playerActionParams);
    }

    /**
     * Test if a player can do an action between - Excavate - Change 4 current
     * cards - Pick a card - Doing an expo
     *
     * @param pl the player concerned by the action
     * @param plAcParam the list of the player action parameters
     * @return
     */
    private boolean playerCanPlay(Player pl, HashMap<String, Object> plAcParam) {
        boolean hasOneActionPossible = false;
        System.out.println("" + pl.getCards());
        if ((pl = currentBoard.getUpcomingPlayer()) != null) {
            try {
                
                // player actions parameters, defined dynamically depending of what the game need in specific action case
                plAcParam.put("player", pl); // wet set the current player
                plAcParam.put("usedElement", new ArrayList<ActivableElement>());
                plAcParam.put("areaToExcavate", null);
                plAcParam.put("cardToPickUp", null);
                plAcParam.put("expoCardToDo", null);
                plAcParam.put("nbWeeksForExcavation", null);


                if (pl.hasCarCard()) {
                    // add active card GUI
                }

                /**
                 * We test here all available actions for the user
                 *
                 */
                // TEST ACTION_CHANGE_FOUR_CARDS
                if (!currentBoard.isPlayerAbleToMakeRoundAction(Player.ACTION_CHANGE_FOUR_CARDS, plAcParam)) {
                    // deactivate gui function
                } else {
                    hasOneActionPossible = true;
                }
                // TEST ACTION_EXCAVATE
                for (ExcavationArea area : currentBoard.getAreas(ExcavationArea.class).values()) {
                    plAcParam.put("areaToExcavate", area);
                    if (!currentBoard.isPlayerAbleToMakeRoundAction(Player.ACTION_EXCAVATE, plAcParam)) {
                        // deactivate gui function
                    } else {
                        hasOneActionPossible = true;
                    }
                }
                // TEST ACTION_ORGANIZE_EXPO
                for (ExpoCard card : currentBoard.getExpoCards()) {
                    plAcParam.put("expoCardToDo", card);
                    if (!currentBoard.isPlayerAbleToMakeRoundAction(Player.ACTION_ORGANIZE_EXPO, plAcParam)) {
                        // deactivate gui function
                    } else {
                        hasOneActionPossible = true;
                    }
                }
                // TEST ACTION_PICK_CARD
                for (Card card : currentBoard.getFourCurrentCards()) {
                    plAcParam.put("cardToPickUp", card);
                    if (!currentBoard.isPlayerAbleToMakeRoundAction(Player.ACTION_PICK_CARD, plAcParam)) {
                        // deactivate gui function
                    } else {
                        hasOneActionPossible = true;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(MapPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return hasOneActionPossible;
    }

    /**
     * Clear the cards panel owned by a player
     */
    private void clearDiplayedCardPlayer() {
        displayedCardTokenPanel.setVisible(false);
        displayedCardTokenPanel.removeAll();
    }

    /**
     * When you put the mouse in the moscow car of the player menu, it shows all
     * the car cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void moscowCarLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moscowCarLabelMouseEntered
        this.displayPlayerCard(CarCard.class);
    }//GEN-LAST:event_moscowCarLabelMouseEntered

    /**
     * When you put the mouse out of the moscow car of the player menu, it hides
     * and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void moscowCarLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moscowCarLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_moscowCarLabelMouseExited

    /**
     * When you put the mouse in the roma zeppelin of the player menu, it shows
     * all the zeppelin cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void romaZeppelinLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_romaZeppelinLabelMouseEntered
        this.displayPlayerCard(ZeppelinCard.class);
    }//GEN-LAST:event_romaZeppelinLabelMouseEntered

    /**
     * When you put the mouse out of the roma Zeppelin of the player menu, it
     * hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void romaZeppelinLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_romaZeppelinLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_romaZeppelinLabelMouseExited

    /**
     * When you put the mouse in the london excavation of the player menu, it
     * shows all the car cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void londonExcavationLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_londonExcavationLabelMouseEntered
        this.displayPlayerCard(ExcavationAuthorizationCard.class);
    }//GEN-LAST:event_londonExcavationLabelMouseEntered

    /**
     * When you put the mouse out of the london excavation of the player menu,
     * it hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void londonExcavationLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_londonExcavationLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_londonExcavationLabelMouseExited

    /**
     * When you put the mouse in the berlin congress of the player menu, it
     * shows all the congress cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void berlinCongressPLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinCongressPLabelMouseEntered
        this.displayPlayerCard(CongressCard.class);
    }//GEN-LAST:event_berlinCongressPLabelMouseEntered

    /**
     * When you put the mouse out of the berlin congress of the player menu, it
     * hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void berlinCongressPLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinCongressPLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_berlinCongressPLabelMouseExited

    /**
     * When you put the mouse in the berlin general knowledge of the player
     * menu, it shows all the general knowledge cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void berlinGenKnowledgeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinGenKnowledgeLabelMouseEntered
        this.displayPlayerCard(GeneralKnowledgeCard.class);
    }//GEN-LAST:event_berlinGenKnowledgeLabelMouseEntered

    /**
     * When you put the mouse out of the berlin General knowledge of the player
     * menu, it hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void berlinGenKnowledgeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinGenKnowledgeLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_berlinGenKnowledgeLabelMouseExited

    /**
     * When you put the mouse in the berlin assistant of the player menu, it
     * shows all the assistant cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void berlinAssistantLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinAssistantLabelMouseEntered
        this.displayPlayerCard(AssistantCard.class);
    }//GEN-LAST:event_berlinAssistantLabelMouseEntered

    /**
     * When you put the mouse out of the berlin assistant of the player menu, it
     * hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void berlinAssistantLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinAssistantLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_berlinAssistantLabelMouseExited

    /**
     * When you put the mouse in the berlin ethnological knowledge of the player
     * menu, it shows all the ethnological knowledge cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void berlinEtnoLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinEtnoLabelMouseEntered
        this.displayPlayerCard(EthnologicalKnowledgeCard.class);
    }//GEN-LAST:event_berlinEtnoLabelMouseEntered

    /**
     * When you put the mouse out of the berlin Ethnological of the player menu,
     * it hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void berlinEtnoLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinEtnoLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_berlinEtnoLabelMouseExited

    /**
     * When you put the mouse in the moscow scientific knowledge of the player
     * menu, it shows all the scientific knowledge cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void moscowScienKnowledgeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moscowScienKnowledgeLabelMouseEntered
        this.displayPlayerCard(SpecificKnowledgeCard.class);
    }//GEN-LAST:event_moscowScienKnowledgeLabelMouseEntered

    /**
     * When you put the mouse out of the moscow scientific knowledge of the
     * player menu, it hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void moscowScienKnowledgeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moscowScienKnowledgeLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_moscowScienKnowledgeLabelMouseExited

    /**
     * When you put the mouse in the london shovel of the player menu, it shows
     * all the shovel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void londonShovelLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_londonShovelLabelMouseEntered
        this.displayPlayerCard(ShovelCard.class);
    }//GEN-LAST:event_londonShovelLabelMouseEntered

    /**
     * When you put the mouse out of the london shovel of the player menu, it
     * hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void londonShovelLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_londonShovelLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_londonShovelLabelMouseExited

    /**
     * When you put the mouse in the berlin small exposition of the player menu,
     * it shows all the exposition (big and small) cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void berlinSmallExpoLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinSmallExpoLabelMouseEntered
        this.displayPlayerCard(ExpoCard.class);
    }//GEN-LAST:event_berlinSmallExpoLabelMouseEntered

    /**
     * When you put the mouse out of the berlin small exposition of the player
     * menu, it hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void berlinSmallExpoLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berlinSmallExpoLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_berlinSmallExpoLabelMouseExited

    /**
     * When you put the mouse out of the player menu, it hides and shows some
     * elements of the screen
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void playerPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playerPanelMouseExited
        if ((evt.getXOnScreen() > playerPanel.getWidth()) || (evt.getYOnScreen() > playerPanel.getHeight())) {
            menuCardsPlayerTab.setVisible(false);
            playerPanel.setVisible(false);
            arrowMenuLabel.setVisible(true);
            backgroundLabel.setEnabled(true);
//            boardCard1Label.setEnabled(true);
//            boardCard2Label.setEnabled(true);
//            boardCard3Label.setEnabled(true);
//            boardCard4Label.setEnabled(true);
        }
    }//GEN-LAST:event_playerPanelMouseExited

    /**
     * When you put the mouse in the crete null token of the player menu, it
     * shows all crete tokens owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void creteNullTokenLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_creteNullTokenLabelMouseEntered
        displayPlayerAreaToken("purple");
    }//GEN-LAST:event_creteNullTokenLabelMouseEntered

    /**
     * When you put the mouse out of the crete null token of the player menu, it
     * hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void creteNullTokenLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_creteNullTokenLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_creteNullTokenLabelMouseExited

    /**
     * When you put the mouse in the palestine null token of the player menu, it
     * shows all palestine tokens owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void palestineNullTokenLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_palestineNullTokenLabelMouseEntered
        displayPlayerAreaToken("green");
    }//GEN-LAST:event_palestineNullTokenLabelMouseEntered

    /**
     * When you put the mouse out of the palestine null token of the player
     * menu, it hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void palestineNullTokenLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_palestineNullTokenLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_palestineNullTokenLabelMouseExited

    /**
     * When you put the mouse in the mesopotamia null token of the player menu,
     * it shows all mesopotamia tokens owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void mesopotamiaNullTokenLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mesopotamiaNullTokenLabelMouseEntered
        displayPlayerAreaToken("blue");
    }//GEN-LAST:event_mesopotamiaNullTokenLabelMouseEntered

    /**
     * When you put the mouse out of the mesopotamia null token of the player
     * menu, it hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void mesopotamiaNullTokenLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mesopotamiaNullTokenLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_mesopotamiaNullTokenLabelMouseExited

    /**
     * When you put the mouse in the greece null token of the player menu, it
     * shows all greece tokens owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void greeceNullTokenLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_greeceNullTokenLabelMouseEntered
        displayPlayerAreaToken("orange");
    }//GEN-LAST:event_greeceNullTokenLabelMouseEntered

    /**
     * When you put the mouse out of the greece null token of the player menu,
     * it hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void greeceNullTokenLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_greeceNullTokenLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_greeceNullTokenLabelMouseExited

    /**
     * When you put the mouse in the egypt null token of the player menu, it
     * shows all crete tokens owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void egyptNullTokenLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_egyptNullTokenLabelMouseEntered
        displayPlayerAreaToken("yellow");
    }//GEN-LAST:event_egyptNullTokenLabelMouseEntered

    /**
     * When you put the mouse out of the egypt null token of the player menu, it
     * hides and clean the panel cards owned by the player
     *
     * @param evt The mouse event that serves for launching the method
     */
    private void egyptNullTokenLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_egyptNullTokenLabelMouseExited
        this.clearDiplayedCardPlayer();
    }//GEN-LAST:event_egyptNullTokenLabelMouseExited

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel arrowMenuLabel;
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JLabel berlinAssistantLabel;
    private javax.swing.JLabel berlinCongressPLabel;
    private javax.swing.JLabel berlinEtnoLabel;
    private javax.swing.JLabel berlinGenKnowledgeLabel;
    private javax.swing.JLabel berlinSmallExpoLabel;
    private javax.swing.JPanel boardCardsContainerPanel;
    private javax.swing.JButton changeFourCardsjButton;
    private javax.swing.JLabel creteExcavationLabel;
    private javax.swing.JLabel creteNullTokenLabel;
    private javax.swing.JPanel displayedCardTokenPanel;
    private javax.swing.JLabel egyptExcavationLabel;
    private javax.swing.JLabel egyptNullTokenLabel;
    private javax.swing.JPanel excavationContainerPanel;
    private javax.swing.JPanel expoCardsContainerPanel;
    private javax.swing.JLabel greeceExcavationLabel;
    private javax.swing.JLabel greeceNullTokenLabel;
    private javax.swing.JPanel logMenu;
    private javax.swing.JScrollBar logMenuScrollBar;
    private javax.swing.JLabel londonExcavationLabel;
    private javax.swing.JLabel londonShovelLabel;
    private javax.swing.JTabbedPane menuCardsPlayerTab;
    private javax.swing.JLabel mesopotamiaExcavationLabel;
    private javax.swing.JLabel mesopotamiaNullTokenLabel;
    private javax.swing.JLabel moscowCarLabel;
    private javax.swing.JLabel moscowScienKnowledgeLabel;
    private javax.swing.JLabel palestineExcavationLabel;
    private javax.swing.JLabel palestineNullTokenLabel;
    private javax.swing.JPanel player1Panel;
    private javax.swing.JLabel playerBackgroundLabel;
    private javax.swing.JPanel playerPanel;
    private javax.swing.JLabel romaZeppelinLabel;
    private javax.swing.JPanel usableElementsMenuPanel;
    private javax.swing.JPanel usingElementsMenuPanel;
    // End of variables declaration//GEN-END:variables
}
