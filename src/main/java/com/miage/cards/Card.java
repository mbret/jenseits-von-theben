

package com.miage.cards;
import com.miage.areas.Area;
import com.miage.game.Deck;

/**
 * Card
 * 
 * @author maxime
 */
public abstract class Card {

	/**
	 * name: used as key inside program
	 */
	private String name;

	/**
	 * displayName: name used graphical
	 */
	private String displayName;

	private String areaName;

        /**
         * Used in .properties to define each cards uniquely
         * Use this id to retrieve image file
         */
        private int id;

	private int weekCost = 0;

	/**
	 * 
	 * @param name
	 * @param areaName
	 * @param weekCost 
	 */
	public Card(int id, String name, String areaName, int weekCost){
            this.id = id;
            this.name = name;
            this.areaName = areaName;
            this.weekCost = weekCost;
	}
        
        

	/**
	 * 
	 * @param name
	 * @param areaName 
	 */
	public Card(int id, String name, String areaName){
            this.id = id;
		this.name = name;
		this.areaName = areaName;
	}

	public String getName() {
		return name;
	}

	public int getWeekCost() {
		return weekCost;
	}

	public void setWeekCost(int weekCost) {
		this.weekCost = weekCost;
	}


	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String toString(){
		return name+","+areaName+","+weekCost;
	}

	abstract public boolean isDiscardable();



	/**
	 * Cast the abstract card in the correct sub instance
	 * @author Gael
	 * @return a downcast of this
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

        

}
