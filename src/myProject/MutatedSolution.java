package myProject;

import java.util.ArrayList;

//The mutated solution from the current one

public class MutatedSolution {

	//Holds the mutated solution

	private ArrayList<Swap> mutatedSolution = new ArrayList<Swap>();
	private ArrayList<Integer> unsortedIndexes = new ArrayList<Integer>();

	//Effectiveness and efficiency of the mutated solution

	private int effectiveness;
	private int efficiency;
	private double perfectRatio;
	private double swapRatio;
	private double swapToSizeRatio;

	//Constructor
	//Generate an empty solution

	public MutatedSolution(ElementstoSort unsorted) {

		for (int i = 0; i < Math.sqrt(unsorted.numberofElements()); i++) {

			mutatedSolution.add(new Swap(0,0));

		}

	}

	//Copy the current solution

	public void copyCurrentFinal(CurrentFinal current) {

		mutatedSolution.clear();
		unsortedIndexes.clear();

		for(int i = 0; i < current.getCurrentSize(); i++) {

			mutatedSolution.add(new Swap((current.getSwap(i)).getFirst(),(current.getSwap(i)).getSecond()));

		}

		for(int i = 0; i < current.getUnsortedSize(); i++) {

			unsortedIndexes.add(current.getUnsortedIndex(i));

		}

		this.effectiveness = current.getEffectiveness();
		this.perfectRatio = current.getPerfectRatio();
		this.swapRatio = current.getSwapRatio();
		this.efficiency = current.getEfficiency();
		this.swapToSizeRatio = current.getSwapToSizeRatio();

	}
	
	public void copyBest(BestFinal bestFinal) {

		mutatedSolution.clear();
		unsortedIndexes.clear();

		for(int i = 0; i < bestFinal.getBestSize(); i++) {

			mutatedSolution.add(new Swap((bestFinal.getSwap(i)).getFirst(),(bestFinal.getSwap(i)).getSecond()));

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

	public ArrayList<Swap> getMutated(){

		return mutatedSolution;

	}

	public Swap getSwap(int index) {

		return mutatedSolution.get(index);

	}

	public int getUnsortedIndex(int index) {

		return unsortedIndexes.get(index);

	}


	public int getMutatedSize() {

		return mutatedSolution.size();

	}

	public int getNumberofSwap() {

		int numberofSwap = 0;

		for(int i = 0; i < getMutatedSize(); i++) {

			if(getSwap(i).getFirst() == getSwap(i).getSecond()) {

				break;

			}else {

				numberofSwap++;

			}

		}

		return numberofSwap;

	}

	public int getUnsortedSize() {

		return unsortedIndexes.size();

	}
	
	public double getSwapToSizeRatio() {
		
		return swapToSizeRatio;
		
	}

	public boolean isUnsortedEmpty() {

		if(getUnsortedSize() == 0) {
			
			return true;
			
		}else {
			
			return false;
			
		}

	}

	public void setSwap(int index, Swap swap) {

		Swap newSwap = new Swap(swap.getFirst(), swap.getSecond());

		mutatedSolution.set(index, newSwap);

	}

	public void testSolution(ElementstoSort elementsToSort) {

		//Efficiency

		double perfectSwap = 0.00;  //Swap that puts an integer at its correct position
		double actualSwap = 0.00;   //Swap that does an actual swap
		double totalSwap = 0.00;   //Total number of swap

		for(int i = 0; i < this.getMutatedSize(); i++) {

			if(this.getSwap(i).getFirst() == this.getSwap(i).getSecond()) {

				break;  //continue until the last swap (a swap with first and second being the same is a deleted swap

			}else {

				elementsToSort.swap(this.getSwap(i));

				if(mutatedSolution.get(i).isActual()) {

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


	//Mutates by modification which will toss a coin and either modify
	//first or second

	public void mutatebyModify(int index) {

		if(!isUnsortedEmpty()) {

			if(Math.random() < 0.5) {

				getSwap(index).setFirst(unsortedIndexes.get(Utility.randomInt(0, unsortedIndexes.size())));

				while(getSwap(index).getFirst() == getSwap(index).getSecond()) {

					getSwap(index).setFirst(unsortedIndexes.get(Utility.randomInt(0, unsortedIndexes.size())));

				}

			}else {

				getSwap(index).setSecond(unsortedIndexes.get(Utility.randomInt(0, unsortedIndexes.size())));

				while(getSwap(index).getFirst() == getSwap(index).getSecond()) {

					getSwap(index).setSecond(unsortedIndexes.get(Utility.randomInt(0, unsortedIndexes.size())));

				}

			}

		}

	}

	//Mutates by adding a swap

	public void mutatebyAdding(int index, ElementstoSort elementstoSort) {

		if(!isUnsortedEmpty()) {

			Swap swap = new Swap(0,0);
 
			while(swap.getFirst() == swap.getSecond()){

				swap.setFirst(unsortedIndexes.get(Utility.randomInt(0, unsortedIndexes.size())));
				swap.setSecond(unsortedIndexes.get(Utility.randomInt(0, unsortedIndexes.size())));

			}

			if(getNumberofSwap() < getMutatedSize()) {

				for(int i = getNumberofSwap(); i > index; i--) {

					getSwap(i).setFirst(getSwap(i-1).getFirst());
					getSwap(i).setSecond(getSwap(i-1).getSecond());

				}

			}else {

				Swap newSwap = new Swap(0,0);
				mutatedSolution.add(newSwap);
				
				for(int i = getNumberofSwap(); i > index; i--) {

					getSwap(i).setFirst(getSwap(i-1).getFirst());
					getSwap(i).setSecond(getSwap(i-1).getSecond());

				}

			}

			getSwap(index).setFirst(swap.getFirst());
			getSwap(index).setSecond(swap.getSecond());

		}

	}

	//Mutates by deleting a swap

	public void mutatebyDeleting(int index) {

		mutatedSolution.remove(index);

	}

	@Override

	//Prints the solution algorithm

	public String toString() {

		String mySolution = "(" + getSwap(0).getFirst() + "," + getSwap(0).getSecond() + ")";

		for(int i = 1; i < getMutatedSize(); i++) {

			mySolution += ", (" + getSwap(i).getFirst() + "," + getSwap(i).getSecond() + ")";

		}

		return mySolution;

	}

}
