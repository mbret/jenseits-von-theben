package game;

import java.util.LinkedList;

public class PiecesStack extends LinkedList<Piece> implements Iterable<Piece>{
	
	
	/**
	 * Add a piece into the stack depending on its timeStatus
	 * @param p Piece to add in the stack
	 */
	public void addPiece(Piece piece){
		
		int index = 0;
		
		for(Piece p : this){
			
			if(piece.getTimeState().compareTo(p.getTimeState()) < 1)
				break;
			
			index++;
		}
		
		this.add(index, piece);
		
	}
	
	/**
	 * delete the first piece of the stack and return it
	 * @return the first piece of the stack
	 */
	public Piece getFirstPiece(){
		
		Piece firstPiece = this.removeFirst();
		return firstPiece;
		
	}
	
	
	public String toString(){
		String result = "";
		
		for(Piece p : this){
			result += p.getColor()+" ";
		}
		
		return result;
	}

}
