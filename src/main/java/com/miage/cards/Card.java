package com.miage.cards;
import com.miage.game.Deck;

/**
 * Card
 * 
 * @author maxime
 */
public abstract class Card {

    /**
     * name: used as key inside program
     * @deprecated 
     */
    private String name;

    /**
     * displayName: name used graphical
     */
    private final String displayName;

    /**
     * 
     */
    private final String areaName;

    /**
     * Used in .properties to define each cards uniquely
     * Use this id to retrieve image file
     */
    private int id;

    /**
     * 
     */
    private int weekCost = 0;

    
    /**
     * 
     * @param displayName
     * @param areaName
     * @param id 
     * @param weekCost 
     */
    public Card(int id, String displayName, String areaName, int weekCost) {
        this.displayName = displayName;
        this.areaName = areaName;
        this.id = id;
        this.weekCost = weekCost;
    }

    /**
     * 
     * @param displayName
     * @param areaName
     * @param id
     * @param weekCost 
     * @deprecated 
     */
    public Card(String displayName, String areaName, int id, int weekCost) {
        this(id, displayName, areaName, weekCost);
    }
    
    /**
     * 
     * @param areaName
     * @param id 
     * @param weekCost 
     * @deprecated 
     */
    public Card(String areaName, int id, int weekCost) {
        this(id, "displayName", areaName, weekCost);
    }
        
        
    /**
     * 
     * @param id
     * @param name
     * @param areaName 
     * @deprecated 
     */
    public Card(int id, String name, String areaName){
        this(id, "displayName", areaName, 0);
    }

    /**
     * 
     * @param id
     * @param areaName
     * @param weekCost 
     * @deprecated 
     */
    public Card(int id, String areaName, int weekCost) {
        this(id, "displayName", areaName, weekCost);
    }
    
    
    
    
        
    /***********************************************************************************************
     *
     *                                  Methods
     * 
     ***********************************************************************************************/

    /**
     * Methods
     * @return
     */
    @Override
    public String toString(){
            return displayName+","+areaName+","+weekCost;
    }

    
    abstract public boolean isDiscardable();


    /**
     * Cast the abstract card in the correct sub instance
     * @author Gael
     * @return a downcast of this
     * @deprecated 
     */
    public Card downCastCard(){

        if(this instanceof AssistantCard){
                AssistantCard cardReturned = (AssistantCard) this;
                return cardReturned;
        }
        else if(this instanceof CarCard){
                CarCard cardReturned = (CarCard) this;
                return cardReturned;
        }
        else if(this instanceof CongressCard){
                CongressCard cardReturned = (CongressCard) this;
                return cardReturned;
        }
        else if(this instanceof EthnologicalKnowledgeCard){
                EthnologicalKnowledgeCard cardReturned = (EthnologicalKnowledgeCard) this;
                return cardReturned;
        }
        else if(this instanceof ExcavationAuthorizationCard){
                ExcavationAuthorizationCard cardReturned = (ExcavationAuthorizationCard) this;
                return cardReturned;
        }
        else if(this instanceof ExpoCard){
                ExpoCard cardReturned = (ExpoCard) this;
                return cardReturned;
        }
        else if(this instanceof GeneralKnowledgeCard){
                GeneralKnowledgeCard cardReturned = (GeneralKnowledgeCard) this;
                return cardReturned;
        }
        else if(this instanceof ShovelCard){
                ShovelCard cardReturned = (ShovelCard) this;
                return cardReturned;
        }
        else if(this instanceof SpecificKnowledgeCard){
                SpecificKnowledgeCard cardReturned = (SpecificKnowledgeCard) this;
                return cardReturned;
        }
        else{
                ZeppelinCard cardReturned = (ZeppelinCard) this;
                return cardReturned;
        }

    }

	
    /**
     * 
     * add this card to the sideDeck in param
     * 
     * @author Gael
     * @param sideDeck
     */
    public void discardCard(Deck sideDeck){
        sideDeck.addCard(this);
    }

    
    
    /***********************************************************************************************
     *
     *                                  Getter & Setter
     * 
     ***********************************************************************************************/
    
    /**
     * 
     * @return 
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeekCost() {
        return weekCost;
    }

    public void setWeekCost(int weekCost) {
        this.weekCost = weekCost;
    }

    /**
     * 
     * @return
     * @deprecated
     */
    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAreaName() {
        return areaName;
    }
 
    

}
