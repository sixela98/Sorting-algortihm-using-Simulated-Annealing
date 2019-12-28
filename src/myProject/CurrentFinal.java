package myProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//The current final solution

public class CurrentFinal {

	//Holds the current solution of swaps

	private ArrayList<Swap> currentFinal = new ArrayList<Swap>();
	private ArrayList<Integer> unsortedIndexes = new ArrayList<Integer>();

	//Effectiveness and efficiency of the current solution

	private int effectiveness;
	private int efficiency;
	private double perfectRatio;
	private double swapRatio;
	private double swapToSizeRatio;

	//Constructor
	//Generate an empty solution

	public CurrentFinal(ElementstoSort elementstoSort) {

		for (int i = 0; i < Math.sqrt(elementstoSort.numberofElements()); i++) {

			currentFinal.add(new Swap(0,0));

		}

	}

	public void copyMutated(MutatedSolution mutated) {

		currentFinal.clear();
		unsortedIndexes.clear();

		for(int i = 0; i < mutated.getMutatedSize(); i++) {

			currentFinal.add(new Swap((mutated.getSwap(i)).getFirst(),(mutated.getSwap(i)).getSecond()));

		}

		for(int i = 0; i < mutated.getUnsortedSize(); i++) {

			unsortedIndexes.add(mutated.getUnsortedIndex(i));

		}

		this.effectiveness = mutated.getEffectiveness();
		this.perfectRatio = mutated.getPerfectRatio();
		this.swapRatio = mutated.getSwapRatio();
		this.efficiency = mutated.getEfficiency();
		this.swapToSizeRatio = mutated.getSwapToSizeRatio();

	}

	public void copyBest(BestFinal bestFinal) {

		currentFinal.clear();
		unsortedIndexes.clear();

		for(int i = 0; i < bestFinal.getBestSize(); i++) {

			currentFinal.add(new Swap((bestFinal.getSwap(i)).getFirst(),(bestFinal.getSwap(i)).getSecond()));

		}

		for(int i = 0; i < bestFinal.getUnsortedSize(); i++) {

			unsortedIndexes.add(bestFinal.getUnsortedIndex(i));

		}

		this.effectiveness = bestFinal.getEffectiveness();
		this.perfectRatio = bestFinal.getPerfectRatio();
		this.swapRatio = bestFinal.getSwapRatio();
		this.efficiency = bestFinal.getEfficiency();
		this.swapToSizeRatio = bestFinal.getSwapToSizeRatio();

	}

	//Regular gets and sets

	public int getEffectiveness() {

		return effectiveness;

	}

	public int getEfficiency() {

		return efficiency;

	}

	public double getPerfectRatio() {

		return perfectRatio;

	}

	public double getSwapRatio() {

		return swapRatio;

	}

	public ArrayList<Swap> getCurrent(){

		return currentFinal;

	}

	public Swap getSwap(int index) {

		return currentFinal.get(index);

	}

	public int getUnsortedIndex(int index) {

		return unsortedIndexes.get(index);

	}

	public int getCurrentSize() {

		return currentFinal.size();

	}

	public int getUnsortedSize() {

		return unsortedIndexes.size();

	}
	
	public double getSwapToSizeRatio() {
		
		return swapToSizeRatio;
		
	}

	public void setSwap(int index, Swap swap) {

		Swap newSwap = new Swap(swap.getFirst(), swap.getSecond());

		currentFinal.set(index, newSwap);

	}

	public void testSolution(ElementstoSort elementsToSort) {

		//Efficiency

		double perfectSwap = 0.00;  //Swap that puts an integer at its correct position
		double actualSwap = 0.00;   //Swap that does an actual swap
		double totalSwap = 0.00;   //Total number of swap

		for(int i = 0; i < this.getCurrentSize(); i++) {

			if(this.getSwap(i).getFirst() == this.getSwap(i).getSecond()) {

				break;  //continue until the last swap (a swap with first and second being the same is a deleted swap

			}else {

				elementsToSort.swap(this.getSwap(i));

				if(currentFinal.get(i).isActual()) {

					actualSwap++;

					if((Double.compare(elementsToSort.getElement(getSwap(i).getFirst()).getAfterSorted(), elementsToSort.getElement(getSwap(i).getFirst()).getFinal()) == 0) || (Double.compare(elementsToSort.getElement(getSwap(i).getSecond()).getAfterSorted(), elementsToSort.getElement(getSwap(i).getSecond()).getFinal()) == 0)) {

						perfectSwap++;

					}

				}

				totalSwap++;

			}

		}

		double perfectRatio = (perfectSwap/actualSwap) * 100;

		this.perfectRatio = perfectRatio;

		double ratio = (totalSwap/actualSwap);

		this.swapRatio = ratio;

		double efficiency = ((this.getPerfectRatio())/(this.getSwapRatio()));

		this.efficiency = (int)efficiency;
		
		double swapToSizeRatio = (totalSwap/elementsToSort.numberofElements())*100;
		
		this.swapToSizeRatio = swapToSizeRatio;

		//Effectiveness

		double same = 0.00;

		unsortedIndexes.clear();

		for(int i = 0; i < elementsToSort.numberofElements(); i++) {

			if(Double.compare(elementsToSort.getElement(i).getAfterSorted(), elementsToSort.getElement(i).getFinal()) == 0) {

				same++;

			}else {

				unsortedIndexes.add(i);

			}

		}

		double size = elementsToSort.numberofElements();
		double ratioEffectiveness = (same/size) * 100;

		this.effectiveness = (int)ratioEffectiveness;

		//Unswap unsorted back to original

		for(int i = (int)totalSwap - 1; i >= 0; i--) {

			elementsToSort.unSwap(this.getSwap(i));

		}

	}

	//Creates a random initial solution

	public void generateInitial(ElementstoSort elementstoSort) {

		Random r = new Random();

		for (int swapIndex = 0; swapIndex < currentFinal.size(); swapIndex++) {

			while(getSwap(swapIndex).getFirst() == getSwap(swapIndex).getSecond()) {

				setSwap(swapIndex, new Swap(r.nextInt(elementstoSort.numberofElements()), r.nextInt(elementstoSort.numberofElements())));

			}

		}

		//Randomly reorder the solution

		ArrayList<Swap> newList = new ArrayList<Swap>();

		for(int i = 0; i < getCurrentSize(); i++){

			newList.add(this.getSwap(i));

		}

		Collections.shuffle(newList);

		for(int i = 0; i < currentFinal.size(); i++){

			this.setSwap(i, new Swap(newList.get(i).getFirst(),newList.get(i).getSecond()));

		}

	}

	//Creates a random child solution

	public void generateChild() {

		ArrayList<Swap> newList = new ArrayList<Swap>();
		
		for(int i = 0; i < getCurrentSize(); i++){

			if(Double.compare(Utility.randomDouble(), 0.5) == -1){
			
			newList.add(this.getSwap(i));
			
			}

		}

		currentFinal.clear();
		
		for(int i = 0; i < newList.size(); i++){

			currentFinal.add(newList.get(i));
		}

	}

	@Override

	//Prints the solution algorithm

	public String toString() {

		String mySolution = "(" + getSwap(0).getFirst() + "," + getSwap(0).getSecond() + ")";

		for(int i = 1; i < getCurrentSize(); i++) {

			mySolution += ", (" + getSwap(i).getFirst() + "," + getSwap(i).getSecond() + ")";

		}

		return mySolution;

	}

}
