package myProject;

import java.util.ArrayList;

//The current best solution

public class BestFinal {

	//Holds the best solution of swaps

	private ArrayList<Swap> bestFinal = new ArrayList<Swap>();
	private ArrayList<Integer> unsortedIndexes = new ArrayList<Integer>();

	//Effectiveness and efficiency of the best test solution

	private int effectiveness;
	private int efficiency;
	private double perfectRatio;
	private double swapRatio;
	private double swapToSizeRatio;

	//Constructor
	//Generate an empty solution

	public BestFinal(ElementstoSort unsorted) {

		for (int i = 0; i < Math.sqrt(unsorted.numberofElements()); i++) {

			bestFinal.add(new Swap(0,0));

		}

	}

	//Copy the current when selected

	public void copyCurrent(CurrentFinal currentFinal) {

		bestFinal.clear();
		unsortedIndexes.clear();

		for(int i = 0; i < currentFinal.getCurrentSize(); i++) {

			bestFinal.add(new Swap((currentFinal.getSwap(i)).getFirst(),(currentFinal.getSwap(i)).getSecond()));

		}

		for(int i = 0; i < currentFinal.getUnsortedSize(); i++) {

			unsortedIndexes.add(currentFinal.getUnsortedIndex(i));

		}

		this.setEffectiveness(currentFinal);
		this.setPerfectRatio(currentFinal);
		this.setSwapRatio(currentFinal);
		this.setEfficiency(currentFinal);
		this.swapToSizeRatio = currentFinal.getSwapToSizeRatio();


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

	public ArrayList<Swap> getBest(){

		return bestFinal;

	}

	public Swap getSwap(int index) {

		return bestFinal.get(index);

	}

	public int getUnsortedIndex(int index) {

		return unsortedIndexes.get(index);

	}

	public int getBestSize() {

		return bestFinal.size();

	}

	public int getUnsortedSize() {

		return unsortedIndexes.size();

	}
	
	public double getSwapToSizeRatio() {
		
		return swapToSizeRatio;
		
	}

	public void setSwap(int index, Swap swap) {

		Swap newSwap = new Swap(swap.getFirst(), swap.getSecond());

		bestFinal.set(index, newSwap);

	}

	public void setEffectiveness(CurrentFinal current) {

		this.effectiveness = current.getEffectiveness();

	}

	public void setEfficiency(CurrentFinal current) {

		this.efficiency = current.getEfficiency();

	}

	public void setPerfectRatio(CurrentFinal current) {

		this.perfectRatio = current.getPerfectRatio();

	}

	public void setSwapRatio(CurrentFinal current) {

		this.swapRatio = current.getSwapRatio();

	}

	@Override

	//Prints the solution algorithm

	public String toString() {

		String mySolution = "(" + getSwap(0).getFirst() + "," + getSwap(0).getSecond() + ")";

		for(int i = 1; i < getBestSize(); i++) {

			mySolution += ", (" + getSwap(i).getFirst() + "," + getSwap(i).getSecond() + ")";

		}

		return mySolution;

	}

}
