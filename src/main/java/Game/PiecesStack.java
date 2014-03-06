package Game;

import java.util.LinkedList;

public class PiecesStack extends LinkedList<Piece> implements Iterable<Piece>{
	
	
	/**
	 * Add a piece into the stack depending on its timeStatus
	 * @param p Piece to add in the stack
	 */
	public void addPiece(Piece p){
		
		int index = 0;
		
		for(Piece piece : this){
			
			if(p.getTimeState().compareTo(piece.getTimeState()) < 1)
				break;
			
			index++;
		}
		
		this.add(index, p);
		
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
