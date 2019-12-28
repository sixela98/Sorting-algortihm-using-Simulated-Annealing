package myProject;

import java.util.ArrayList;

public class ElementstoSort {

	//private Double[] unsortedGroups = new Double[5];
	//private Double[] sortedGroups = new Double[5];
	
	//Hold the elements in double format

	private static ArrayList<Element> elementsToSort = new ArrayList<Element>();

	/*public ElementstoSort() {
		
		for(int i = 0; i < 5; i++) {
			
			unsortedGroups[i] = 0.0;
			sortedGroups[i] = 0.0;
		}
		
	}*/
	
	//Adds an element
	
	public void addElement(Element element) {

		elementsToSort.add(element);

	}

	//Returns the number of elements

	public int numberofElements() {

		return elementsToSort.size();

	}

	//Returns an element giving its index

	public Element getElement(int index) {

		return elementsToSort.get(index);

	}

	//Set a double

	public void setElement(int index, Element element) {

		elementsToSort.set(index, element);

	}

	//Set the sums
	
/*	public void setSums() {
		
		int index = 0;
		
		for(int i = 0; i < 5; i++) {
			
			int counter = 0;
			
			for(int j = index; j < index + numberofElements()/5; j++) {
				
				unsortedGroups[i] += getElement(j).getInitial();
				sortedGroups[i] += getElement(j).getFinal();
				counter++;
					
			}
			
			index += counter;
			
		}
		
		for(int j = index; j < numberofElements(); j++) {
			
			unsortedGroups[5] += getElement(j).getInitial();
			sortedGroups[5] += getElement(j).getFinal();
			
		}
		
	}*/
	
	//Swaps the elements of the array following the scheme of the best algorithm

	public void swaps(BestFinal bestFinal){

		//Sort

		for(int i = 0; i < bestFinal.getBestSize(); i++) {

			swap(bestFinal.getSwap(i));

		}

	}

	//Swap function, checks and swap to place the smaller at first

	public void swap(Swap swap) {

		if((swap.getSecond() > swap.getFirst()) && (Double.compare((this.getElement(swap.getSecond()).getAfterSorted()), (this.getElement(swap.getFirst()).getAfterSorted())) == -1)) {
			
			double temp = this.getElement(swap.getFirst()).getAfterSorted();
			this.getElement(swap.getFirst()).setAfterSorted(this.getElement(swap.getSecond()).getAfterSorted());
			this.getElement(swap.getSecond()).setAfterSorted(temp);
			
			this.getElement(swap.getFirst()).gotSwaped();
			this.getElement(swap.getSecond()).gotSwaped();
			
			this.getElement(swap.getFirst()).didPlaced();
			this.getElement(swap.getSecond()).didPlaced();
			
			swap.setisActual(true);

		}else if((swap.getFirst() > swap.getSecond()) && Double.compare(this.getElement(swap.getFirst()).getAfterSorted(), this.getElement(swap.getSecond()).getAfterSorted()) == -1) {

			double temp = this.getElement(swap.getSecond()).getAfterSorted();
			this.getElement(swap.getSecond()).setAfterSorted(this.getElement(swap.getFirst()).getAfterSorted());
			this.getElement(swap.getFirst()).setAfterSorted(temp);
			
			this.getElement(swap.getFirst()).gotSwaped();
			this.getElement(swap.getSecond()).gotSwaped();
			
			this.getElement(swap.getFirst()).didPlaced();
			this.getElement(swap.getSecond()).didPlaced();
			
			swap.setisActual(true);

		}

	}

	//Unswap function, checks and swap to place the bigger at first

	public void unSwap(Swap swap) {

		if(swap.isActual() && (swap.getSecond() > swap.getFirst()) && (Double.compare((this.getElement(swap.getSecond()).getAfterSorted()), (this.getElement(swap.getFirst()).getAfterSorted())) ==  1)) {

			double temp = this.getElement(swap.getFirst()).getAfterSorted();
			this.getElement(swap.getFirst()).setAfterSorted(this.getElement(swap.getSecond()).getAfterSorted());
			this.getElement(swap.getSecond()).setAfterSorted(temp);
			swap.setisActual(false);

		}else if(swap.isActual() && (swap.getFirst() > swap.getSecond()) && Double.compare(this.getElement(swap.getFirst()).getAfterSorted(), this.getElement(swap.getSecond()).getAfterSorted()) == 1) {

			double temp = this.getElement(swap.getSecond()).getAfterSorted();
			this.getElement(swap.getSecond()).setAfterSorted(this.getElement(swap.getFirst()).getAfterSorted());
			this.getElement(swap.getFirst()).setAfterSorted(temp);
			swap.setisActual(false);

		}

	}

	//Print the array

	public void print() {

		for(int i = 0; i < numberofElements(); i++) {

			System.out.println(i + " " + getElement(i).getInitial() + " " + getElement(i).getFinal() + " " + getElement(i).getAfterSorted() + " "+ getElement(i).isPlaced());

		}

		System.out.println();

	}

	//Clear the array

	public void clear() {

		elementsToSort.clear();

	}

}
