package Areas;

import java.util.HashMap;
import java.util.Map;

public abstract class Area {
	
	private int num;
	private String name;
	
	
	
	public Area(int nu, String n){
		
		this.name = n;
		this.num = nu;
		
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
