package game;

public class Chrono {
	
	private int values[][];
	
	public Chrono(){
		this.values = new int[12][12];
	}
	
	public void initializationValues(){
		
		
		/*
		 * 1 points of knowledge
		 */
		this.values[0][0] = 0; this.values[0][1] = 0; this.values[0][2] = 0; this.values[0][3] = 0; this.values[0][4] = 0;
		this.values[0][5] = 0; this.values[0][6] = 0; this.values[0][7] = 1; this.values[0][8] = 1; this.values[0][9] = 1;
		this.values[0][10] = 1; this.values[0][11] = 2;
		
		
		/*
		 * 2 points of knowledge
		 */
		this.values[1][0] = 0; this.values[1][1] = 0; this.values[1][2] = 0; this.values[1][3] = 0; this.values[1][4] = 0;
		this.values[1][5] = 1; this.values[1][6] = 1; this.values[1][7] = 1; this.values[1][8] = 1; this.values[1][9] = 2;
		this.values[1][10] = 2; this.values[1][11] = 2;
		
		
		/*
		 * 3 points of knowledge
		 */
		this.values[2][0] = 0; this.values[2][1] = 0; this.values[2][2] = 1; this.values[2][3] = 1; this.values[2][4] = 1;
		this.values[2][5] = 2; this.values[2][6] = 2; this.values[2][7] = 2; this.values[2][8] = 3; this.values[2][9] = 3;
		this.values[2][10] = 3; this.values[2][11] = 4;
		
		
		/*
		 * 4 points of knowledge
		 */
		this.values[3][0] = 0; this.values[3][1] = 1; this.values[3][2] = 1; this.values[3][3] = 2; this.values[3][4] = 2;
		this.values[3][5] = 3; this.values[3][6] = 3; this.values[3][7] = 4; this.values[3][8] = 4; this.values[3][9] = 4;
		this.values[3][10] = 5; this.values[3][11] = 5;
		
		
		
		/*
		 * 5 points of knowledge
		 */
		this.values[4][0] = 1; this.values[4][1] = 1; this.values[4][2] = 2; this.values[4][3] = 3; this.values[4][4] = 3;
		this.values[4][5] = 4; this.values[4][6] = 4; this.values[4][7] = 5; this.values[4][8] = 5; this.values[4][9] = 6;
		this.values[4][10] = 7; this.values[4][11] = 8;
		
		
		/*
		 * 6 points of knowledge
		 */
		this.values[5][0] = 1; this.values[5][1] = 2; this.values[5][2] = 3; this.values[5][3] = 4; this.values[5][4] = 4;
		this.values[5][5] = 5; this.values[5][6] = 5; this.values[5][7] = 5; this.values[5][8] = 6; this.values[5][9] = 6;
		this.values[5][10] = 7; this.values[5][11] = 8;
		
		
		/*
		 * 7 points of knowledge
		 */
		this.values[6][0] = 1; this.values[6][1] = 2; this.values[6][2] = 4; this.values[6][3] = 4; this.values[6][4] = 5;
		this.values[6][5] = 5; this.values[6][6] = 6; this.values[6][7] = 6; this.values[6][8] = 7; this.values[6][9] = 8;
		this.values[6][10] = 8; this.values[6][11] = 9;
		
		
		/*
		 * 8 points of knowledge
		 */
		this.values[7][0] = 1; this.values[7][1] = 3; this.values[7][2] = 4; this.values[7][3] = 5; this.values[7][4] = 5;
		this.values[7][5] = 6; this.values[7][6] = 6; this.values[7][7] = 7; this.values[7][8] = 8; this.values[7][9] = 9;
		this.values[7][10] = 9; this.values[7][11] = 10;
		
		
		/*
		 * 9 points of knowledge
		 */
		this.values[8][0] = 2; this.values[8][1] = 3; this.values[8][2] = 5; this.values[8][3] = 5; this.values[8][4] = 6;
		this.values[8][5] = 6; this.values[8][6] = 7; this.values[8][7] = 8; this.values[8][8] = 9; this.values[8][9] = 9;
		this.values[8][10] = 10; this.values[8][11] = 10;
		
		
		/*
		 * 10 points of knowledge
		 */
		this.values[9][0] = 2; this.values[9][1] = 3; this.values[9][2] = 5; this.values[9][3] = 5; this.values[9][4] = 6;
		this.values[9][5] = 7; this.values[9][6] = 8; this.values[9][7] = 9; this.values[9][8] = 9; this.values[9][9] = 10;
		this.values[9][10] = 10; this.values[9][11] = 10;
		
		
		
		/*
		 * 11 points of knowledge
		 */
		this.values[10][0] = 2; this.values[10][1] = 4; this.values[10][2] = 5; this.values[10][3] = 6; this.values[10][4] = 6;
		this.values[10][5] = 7; this.values[10][6] = 8; this.values[10][7] = 9; this.values[10][8] = 10; this.values[10][9] = 10;
		this.values[10][10] = 10; this.values[10][11] = 10;
		
		
		
		/*
		 * 12 points of knowledge
		 */
		this.values[11][0] = 3; this.values[11][1] = 4; this.values[11][2] = 5; this.values[11][3] = 6; this.values[11][4] = 7;
		this.values[11][5] = 8; this.values[11][6] = 9; this.values[11][7] = 10; this.values[11][8] = 10; this.values[11][9] = 10;
		this.values[11][10] = 10; this.values[11][11] = 10;
		
		
	
	}
	
	
	public int numberTokens(int knowledges, int weeks){
		return this.values[knowledges][weeks];
	}

}
