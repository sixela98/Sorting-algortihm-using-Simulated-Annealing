package myProject;

public class Swap {
	
	private int first;
	private int second;
	private boolean isActual;
	
	//Constuctor
	//Creates a swap given the first and second element to swap

	public Swap(int first, int second) {
		
		this.first = first;
		this.second = second;
		this.isActual = false;
		
	}
	
	//Return first
	
	public int getFirst() {
		
		return first;
		
	}
	
	//Set first
	
	public void setFirst(int first) {
		
		this.first = first;
		
	}
	
	//Return second
	
	public int getSecond() {
		
		return second;
		
	}

	//Set second
	
	public void setSecond(int second) {
		
		this.second = second;
		
	}
	
	//Return isActual
	
	public boolean isActual() {
		
		return isActual;
		
	}
	
	//Set isActual
	
	public void setisActual(boolean flag) {
		
		this.isActual = flag;
		
	}
	
}
