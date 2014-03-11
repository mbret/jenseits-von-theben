package game;




import java.util.LinkedList;
import java.util.List;

import tokens.Token;
import cards.Card;
import cards.ExpoCard;
import cards.GameCard;
import cards.KnowledgeCard;
import areas.Area;
import areas.ExcavationArea;
import areas.TouristicArea;

public class Board {
	
	private int nbPlayers;

	private PiecesStack piecesStack;
	private Area areas[];
	private Piece currentPiece;
	private int distances[][];
	
	private Deck deck;
	private Deck sideDeck;
	
	private Card four[];
	
	
	public Board(int nbPlayers){
		
		this.nbPlayers = nbPlayers;
		this.piecesStack = new PiecesStack();
		this.areas = new Area[12];
		this.deck = new Deck();
		this.sideDeck = new Deck();
		this.four = new Card[4];
		
	}
	
	
	
	public Piece getCurrentPiece() {
		return currentPiece;
	}



	public void setCurrentPiece(Piece currentPiece) {
		this.currentPiece = currentPiece;
	}



	public PiecesStack getPiecesStack() {
		return piecesStack;
	}



	public Area[] getAreas() {
		return areas;
	}
	
	public Deck getDeck(){
		
		return this.deck;
	}
	
	public Deck getSideDeck(){
		return this.sideDeck;
	}
	
	/**
	 * Print the content of the deck
	 * @param deck Deck to print
	 * @return the content of the deck
	 */
	public String printDeck(Deck deck){
		
		return deck.toString();
		
	}



	/**
	 * Initialization of areas
	 */
	public void initAreas(){
		
		TouristicArea londres = new TouristicArea(0,"Londres");
		TouristicArea paris = new TouristicArea(1,"Paris");
		TouristicArea berlin = new TouristicArea(2,"Berlin");
		TouristicArea rome = new TouristicArea(3,"Rome");
		TouristicArea vienne = new TouristicArea(4,"Vienne");
		TouristicArea varsovie = new TouristicArea(5,"Varsovie");
		TouristicArea moscou = new TouristicArea(6,"Moscou");
		
		ExcavationArea grece = new ExcavationArea(7,"Grece","Orange");
		ExcavationArea crete = new ExcavationArea(8,"Crete","Violet");
		ExcavationArea egypte = new ExcavationArea(9,"Egypte","Jaune");
		ExcavationArea palestine = new ExcavationArea(10,"Palestine","Vert");
		ExcavationArea mesopotamie = new ExcavationArea(11,"Mesopotamie","Bleu");
		
		
		for(int i = 0; i < 16; i++){
			
			grece.addToken(new Token("Empty", "Orange", 0));
		}
		
		
		
		
		areas[0] = londres; areas[1] = paris; areas[2] = berlin; areas[3] = rome; areas[4] = vienne; areas[5] = varsovie;
		areas[6] = moscou; areas[7] = grece; areas[8] = crete; areas[9] = egypte; areas[10] = palestine; areas[11] = mesopotamie;
		
	}
	
	/**
	 *  Initialization of the distances
	 */
	public void initDistances(){
		
		this.distances = new int[][]{
				{0,1,1,2,2,2,3,3,3,4,4,4},	// londres -> 0
				{1,0,1,1,1,2,3,2,2,3,3,3},	// paris -> 1
				{1,1,0,2,2,1,2,2,3,3,4,4},	// berlin -> 2
				{2,1,2,0,1,2,3,1,1,2,2,2},	// rome -> 3
				{2,1,1,2,0,1,2,2,2,3,3,3},  // vienne -> 4
				{2,2,2,1,1,0,1,1,2,3,3,2},	// varsovie -> 5
				{3,3,3,2,2,1,0,2,3,4,4,3},	// moscou -> 6
				{3,2,1,2,2,1,2,0,1,2,2,1},	// grece -> 7
				{3,2,1,3,2,2,3,1,0,1,1,2},	// crete -> 8
				{4,3,2,4,3,3,4,2,1,0,1,2},	// egypte -> 9
				{4,3,4,4,3,3,4,2,1,1,0,1},	// palestine -> 10	
				{4,3,2,3,3,2,3,1,2,2,1,0},	// mesopotamie -> 11
				};
		
	}
	
	/**
	 * Return the distance between 2 areas
	 * @param area1 first area
	 * @param area2 second area
	 * @return the distance between area1 & area2
	 */
	public int distance(Area area1, Area area2){
		
		return(this.distances[area1.getNum()][area2.getNum()]);
		
	}
	
	
	/**
	 * Initialization of decks depending on the number of players
	 */
	public void initializationDecks(){
		
		Deck firstDeck = new Deck();
		
	
		/*
		 * Creation of cards
		 */
		
		
		/*
		 * GameCard
		 */
		
		// Excavation authorization
		firstDeck.addCard(new GameCard("ExcavationAuthorization", new TouristicArea(0, "Londres"), 3));
		firstDeck.addCard(new GameCard("ExcavationAuthorization", new TouristicArea(6, "Moscou"), 3));
		
		// Zeppelin
		firstDeck.addCard(new GameCard("Zeppelin", new TouristicArea(0, "Londres"), 1));
		firstDeck.addCard(new GameCard("Zeppelin", new TouristicArea(0, "Londres"), 1));
		
		// Car
		firstDeck.addCard(new GameCard("Car", new TouristicArea(6, "Moscou"), 1));
		firstDeck.addCard(new GameCard("Car", new TouristicArea(2, "Rome"), 1));
		
		// Congress
		firstDeck.addCard(new GameCard("Congress", new TouristicArea(0, "London"), 2));
		firstDeck.addCard(new GameCard("Congress", new TouristicArea(1, "Paris"), 2));
		firstDeck.addCard(new GameCard("Congress", new TouristicArea(1, "Paris"), 2));
		firstDeck.addCard(new GameCard("Congress", new TouristicArea(3, "Berlin"), 2));
		firstDeck.addCard(new GameCard("Congress", new TouristicArea(3, "Berlin"), 2));
		firstDeck.addCard(new GameCard("Congress", new TouristicArea(4, "Vienne"), 2));
		firstDeck.addCard(new GameCard("Congress", new TouristicArea(4, "Vienne"), 2));
		firstDeck.addCard(new GameCard("Congress", new TouristicArea(6, "Moscou"), 2));
		firstDeck.addCard(new GameCard("Congress", new TouristicArea(6, "Moscou"), 2));
		
		//Assistant
		firstDeck.addCard(new GameCard("Assistant", new TouristicArea(1, "Paris"), 2));
		firstDeck.addCard(new GameCard("Assistant", new TouristicArea(1, "Paris"), 2));
		firstDeck.addCard(new GameCard("Assistant", new TouristicArea(2, "Rome"), 2));
		firstDeck.addCard(new GameCard("Assistant", new TouristicArea(3, "Berlin"), 2));
		firstDeck.addCard(new GameCard("Assistant", new TouristicArea(4, "Vienne"), 2));
		firstDeck.addCard(new GameCard("Assistant", new TouristicArea(4, "Vienne"), 2));
		
		//Shovel
		firstDeck.addCard(new GameCard("Shovel", new TouristicArea(0, "Londres"), 3));
		firstDeck.addCard(new GameCard("Shovel", new TouristicArea(0, "Londres"), 3));
		firstDeck.addCard(new GameCard("Shovel", new TouristicArea(2, "Rome"), 3));
		firstDeck.addCard(new GameCard("Shovel", new TouristicArea(2, "Rome"), 3));
		firstDeck.addCard(new GameCard("Shovel", new TouristicArea(6, "Moscou"), 3));
		firstDeck.addCard(new GameCard("Shovel", new TouristicArea(6, "Moscou"), 3));
		
		/*
		 * KnowledgeCards
		 */
		
		// General Knowledge
		firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", new TouristicArea(1, "Paris"), 3, 1, "All"));
		firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", new TouristicArea(2, "Rome"), 3, 1, "All"));
		firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", new TouristicArea(3, "Berlin"), 3, 1, "All"));
		firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", new TouristicArea(4, "Vienne"), 3, 1, "All"));
		
		firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", new TouristicArea(0, "Londre"), 6, 2, "All"));
		firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", new TouristicArea(1, "Paris"), 6, 2, "All"));
		firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", new TouristicArea(3, "Berlin"), 6, 2, "All"));
		firstDeck.addCard(new KnowledgeCard("GeneralKnowledge", new TouristicArea(6, "Moscou"), 6, 2, "All"));
		
		// Specific knowledge
		
		//Grece
		
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(2, "Rome"), 1, 1, "Orange"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(3, "Berlin"), 1, 1, "Orange"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(4, "Vienne"), 1, 1, "Orange"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(6, "Moscou"), 1, 1, "Orange"));

		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(0, "Londres"), 2, 2, "Orange"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(2, "Rome"), 2, 2, "Orange"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(2, "Rome"), 2, 2, "Orange"));
		
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(0, "Londres"), 4, 3, "Orange"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(3, "Berlin"), 4, 4, "Orange"));
		
		
		//Crete
		
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(1, "Paris"), 1, 1, "Purple"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(3, "Berlin"), 1, 1, "Purple"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(3, "Berlin"), 1, 1, "Purple"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(4, "Vienne"), 1, 1, "Purple"));

		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(1, "Paris"), 2, 2, "Purple"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(2, "Rome"), 2, 2, "Purple"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(4, "Vienne"), 2, 2, "Purple"));
		
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(6, "Moscou"), 4, 3, "Purple"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(6, "Moscou"), 4, 4, "Purple"));	
		

		
		//Egypte
		
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(1, "Paris"), 1, 1, "Yellow"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(1, "Paris"), 1, 1, "Yellow"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(2, "Rome"), 1, 1, "Yellow"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(6, "Moscou"), 1, 1, "Yellow"));

		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(0, "Londres"), 2, 2, "Yellow"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(3, "Berlin"), 2, 2, "Yellow"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(3, "Berlin"), 2, 2, "Yellow"));
		
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(0, "Londres"), 4, 3, "Yellow"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(6, "Moscou"), 4, 4, "Yellow"));
		
		
		
		//Palestine
		
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(2, "Rome"), 1, 1, "Green"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(4, "Vienne"), 1, 1, "Green"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(4, "Vienne"), 1, 1, "Green"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(4, "Vienne"), 1, 1, "Green"));

		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(0, "Londres"), 2, 2, "Green"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(1, "Paris"), 2, 2, "Green"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(3, "Berlin"), 2, 2, "Green"));
		
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(0, "Londres"), 4, 3, "Green"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(1, "Paris"), 4, 4, "Green"));
		

		//Mesopotamie
		
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(1, "Paris"), 1, 1, "Blue"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(2, "Rome"), 1, 1, "Blue"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(6, "Moscou"), 1, 1, "Blue"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(6, "Moscou"), 1, 1, "Blue"));

		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(0, "Londres"), 2, 2, "Blue"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(4, "Vienne"), 2, 2, "Blue"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(4, "Vienne"), 2, 2, "Blue"));
		
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(0, "Londres"), 4, 3, "Blue"));
		firstDeck.addCard(new KnowledgeCard("SpecificKnowledge", new TouristicArea(6, "Moscou"), 4, 4, "Blue"));
		
		
		//Ethnological knowledge
		firstDeck.addCard(new KnowledgeCard("EthnologicalKnowledge", new TouristicArea(6, "Moscou"), 1, 2, "Orange"));
		firstDeck.addCard(new KnowledgeCard("EthnologicalKnowledge", new TouristicArea(1, "Paris"), 1, 2, "Purple"));
		firstDeck.addCard(new KnowledgeCard("EthnologicalKnowledge", new TouristicArea(2, "Rome"), 1, 2, "Yellow"));
		firstDeck.addCard(new KnowledgeCard("EthnologicalKnowledge", new TouristicArea(4, "Vienne"), 1, 2, "Green"));
		firstDeck.addCard(new KnowledgeCard("EthnologicalKnowledge", new TouristicArea(3, "Berlin"), 1, 2, "Blue"));
		
		
		/*
		 * Mix of the first deck and initialization of the four cards on the board
		 */
		
		firstDeck.mix();
		this.four[0] = firstDeck.draw();
		this.four[1] = firstDeck.draw();
		this.four[2] = firstDeck.draw();
		this.four[3] = firstDeck.draw();
		
		Deck deck1 = new Deck();
		Deck deck2 = new Deck();
		
		if(this.nbPlayers > 2){
			
			/*
			 * 3 decks, same size, insert little expo in the 2nd deck, mix the 2nd deck, union with first deck
			 * Big Expo in the 3rd deck
			 */
			
			deck1 = (Deck) firstDeck.divideDeck(0, (firstDeck.size()/3));
			deck2 = (Deck) firstDeck.divideDeck((firstDeck.size()/3), ((firstDeck.size()/3)+(firstDeck.size()/3)));

			this.sideDeck = (Deck) firstDeck.divideDeck(((firstDeck.size()/3)+(firstDeck.size()/3)), firstDeck.size()-1);
			
			deck2.add(new ExpoCard("LittleExpo", new TouristicArea(0, "London"), 3, false));
			deck2.add(new ExpoCard("LittleExpo", new TouristicArea(1, "Paris"), 3, false));
			deck2.add(new ExpoCard("LittleExpo", new TouristicArea(3, "Berlin"), 3, false));
			deck2.add(new ExpoCard("LittleExpo", new TouristicArea(4, "Vienne"), 3, false));
			deck2.add(new ExpoCard("LittleExpo", new TouristicArea(6, "Moscou"), 3, false));
			
			this.sideDeck.add(new ExpoCard("BigExpo", new TouristicArea(0, "London"), 4, false));
			this.sideDeck.add(new ExpoCard("BigExpo", new TouristicArea(1, "Paris"), 4, false));
			this.sideDeck.add(new ExpoCard("BigExpo", new TouristicArea(3, "Berlin"), 4, false));
			this.sideDeck.add(new ExpoCard("BigExpo", new TouristicArea(4, "Vienne"), 4, false));
			this.sideDeck.add(new ExpoCard("BigExpo", new TouristicArea(6, "Moscou"), 4, false));
			
			deck2.mix();
			this.deck.addAll(deck2);
			this.deck.addAll(deck1);
			
			
		}
		else{
			
			/*
			 * 2 decks, same size, insert little expo & big expo in the 2nd deck, mix the 2nd deck, union with first deck
			 */
			deck1 = (Deck) firstDeck.divideDeck(0, (firstDeck.size()/3));
			deck2 = (Deck) firstDeck.divideDeck((firstDeck.size()/3), ((firstDeck.size()/3)+(firstDeck.size()/3)));

			this.sideDeck = (Deck) firstDeck.divideDeck(((firstDeck.size()/3)+(firstDeck.size()/3)), firstDeck.size()-1);
			
			deck2.add(new ExpoCard("LittleExpo", new TouristicArea(0, "London"), 3, false));
			deck2.add(new ExpoCard("LittleExpo", new TouristicArea(1, "Paris"), 3, false));
			deck2.add(new ExpoCard("LittleExpo", new TouristicArea(3, "Berlin"), 3, false));
			deck2.add(new ExpoCard("LittleExpo", new TouristicArea(4, "Vienne"), 3, false));
			deck2.add(new ExpoCard("LittleExpo", new TouristicArea(6, "Moscou"), 3, false));
			
			deck2.add(new ExpoCard("BigExpo", new TouristicArea(0, "London"), 4, false));
			deck2.add(new ExpoCard("BigExpo", new TouristicArea(1, "Paris"), 4, false));
			deck2.add(new ExpoCard("BigExpo", new TouristicArea(3, "Berlin"), 4, false));
			deck2.add(new ExpoCard("BigExpo", new TouristicArea(4, "Vienne"), 4, false));
			deck2.add(new ExpoCard("BigExpo", new TouristicArea(6, "Moscou"), 4, false));
			
			deck2.mix();
			this.deck.addAll(deck2);
			this.deck.addAll(deck1);
			
			this.sideDeck = new Deck();
			
		}
		
	}
	
	



	
}
