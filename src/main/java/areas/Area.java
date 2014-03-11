package areas;


public abstract class Area {
	
	private int num;
	private String name;
	
	
	
	public Area(int num, String name){
		
		this.name = name;
		this.num = num;
		
	}

	

	public int getNum() {
		return num;
	}



	public String getName() {
		return name;
	}
	
	



	public String toString(){
		return "Area : "+this.getName();
	}
	
	
	
	
	
	
	
	
	

}
