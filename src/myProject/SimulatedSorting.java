package myProject;

import java.util.Scanner;

public class SimulatedSorting {

	public BestFinal getSolution(ElementstoSort elementsToSort, String[] unsorted, String[] sorted) {

		//Set inital temperature

		double finalTemp = 100000;

		//Cooling rate

		double finalCoolingRate = 0.006;
		double coolingRate = 0.1;

		//Maximum number of tries

		  int tries = 3;

		//Create random initial solution

		CurrentFinal currentFinal = new CurrentFinal(elementsToSort);
		currentFinal.generateInitial(elementsToSort);

		currentFinal.testSolution(elementsToSort);

		for(int i = 0; i < elementsToSort.numberofElements(); i++) {

			elementsToSort.getElement(i).resetBool();
			elementsToSort.getElement(i).isItPlaced();

		}

		/*	System.out.println("Effectiveness of initial solution: " + currentFinal.getEffectiveness());
		System.out.println("Efficiency of initial solution: " + currentFinal.getEfficiency());
		System.out.println("Solution: " + currentFinal); */

		//We want to keep track of the best solution
		//First, assume the current is the best

		BestFinal bestFinal = new BestFinal(elementsToSort);
		bestFinal.copyCurrent(currentFinal); 

		/*
		 * We create an initial solution, then based on it we create a better solution that will then be perfected.
		 * The best overall solution is kept and the perfected solution is compared with the current final solution.
		 * Repeat the process with the current final solution to try and get a better one.
		 */

		//Loop until the system has cooled down

		while(finalTemp > 1) {

			//	System.out.println(finalTemp);

			currentFinal.copyBest(bestFinal);

			/*
			 * This is the first SA that will generate a better effectiveness
			 */

			double testTemp = finalTemp;

			while(testTemp > 1) {

				//System.out.println(testTemp);

				//Create a solution that will be mutated

				MutatedSolution mutatedSolution = new MutatedSolution(elementsToSort);
				mutatedSolution.copyCurrentFinal(currentFinal);

				//Mutate the solution

				//First randomly select a Swap

				int swapIndex = Utility.randomInt(0, mutatedSolution.getNumberofSwap());

				//Then select the mutation to be done

				double mutationChoice = Utility.randomDouble();

				//Then mutate the selected Swap with the selected mutation

				if((Double.compare(mutationChoice, 1.0/3.0) == -1) || (mutatedSolution.getEfficiency() > 60 && mutatedSolution.getEffectiveness()  < 100)){

					if(mutatedSolution.getEffectiveness() == 100) {

						break;

					}

					mutatedSolution.mutatebyAdding(swapIndex, elementsToSort);

				}else if((Double.compare(mutationChoice, 1.0/3.0) == 0) || (Double.compare(mutationChoice, 2.0/3.0) == -1)){

					if(mutatedSolution.getEffectiveness() == 100) {

						break;

					}

					mutatedSolution.mutatebyModify(swapIndex);

				}else {

					//If zero swap left, don't delete

					if(mutatedSolution.getNumberofSwap() > 1) {

						mutatedSolution.mutatebyDeleting(swapIndex);

					}else {

						mutatedSolution.mutatebyAdding(swapIndex, elementsToSort);

					}

				}	

				mutatedSolution.testSolution(elementsToSort);

				for(int i = 0; i < elementsToSort.numberofElements(); i++) {

					elementsToSort.getElement(i).resetBool();
					elementsToSort.getElement(i).isItPlaced();

				}

				//Decide if we should keep the mutated

				double rand = Utility.randomDouble();

				if(Utility.acceptanceProbabilityTest(currentFinal, mutatedSolution, testTemp) > rand) {

					currentFinal.copyMutated(mutatedSolution);

				}

				//Keep track of the best solution found

				if((currentFinal.getEffectiveness() > bestFinal.getEffectiveness()) || ((currentFinal.getEffectiveness() == bestFinal.getEffectiveness()) && (Double.compare((currentFinal.getPerfectRatio()),(bestFinal.getPerfectRatio())) == 1)) || (((currentFinal.getEffectiveness() == bestFinal.getEffectiveness()) && (Double.compare((currentFinal.getPerfectRatio()),(bestFinal.getPerfectRatio())) == 0)) && (Double.compare((currentFinal.getSwapRatio()),(bestFinal.getSwapRatio())) == -1)) || (((currentFinal.getEffectiveness() == bestFinal.getEffectiveness()) && (Double.compare((currentFinal.getPerfectRatio()),(bestFinal.getPerfectRatio())) == 0)) && (Double.compare((currentFinal.getSwapRatio()),(bestFinal.getSwapRatio())) == 0) && currentFinal.getCurrentSize() > bestFinal.getBestSize())) {

					bestFinal.copyCurrent(currentFinal);

				}

				//Cool the system

				testTemp *= 1 - coolingRate;

			}

			currentFinal.copyBest(bestFinal);

			//This second SA will improve the bestTest by mutating it to perfect the efficiency

			double perfectTemp = finalTemp;

			while(perfectTemp > 1) {

				//	System.out.println(perfectTemp);

				MutatedSolution mutatedSolution = new MutatedSolution(elementsToSort);
				mutatedSolution.copyCurrentFinal(currentFinal);

				//Mutate the solution

				//First randomly select a Swap

				int swapIndex = Utility.randomInt(0, mutatedSolution.getNumberofSwap());

				//Then select the mutation to be done

				double mutationChoice = Utility.randomDouble();

				if(((Double.compare(mutationChoice, 1.0/3.0) == -1) && !(mutatedSolution.getEfficiency() > 60)) || ((Double.compare(mutatedSolution.getSwapRatio(), 1.0) == 0) && (Double.compare(mutatedSolution.getPerfectRatio(), 100.0) == -1))){

					if(mutatedSolution.getEffectiveness() == 100) {

						break;

					}

					mutatedSolution.mutatebyModify(swapIndex);

				}else if((((Double.compare(mutationChoice, 1.0/3.0) == 0) || (Double.compare(mutationChoice, 2.0/3.0) == -1)) && !(mutatedSolution.getEfficiency() > 60))  || ((mutatedSolution.getEfficiency() > 60) && (Double.compare(mutatedSolution.getSwapRatio(), 1.0) == 1))){

					//If zero swap left, don't delete

					if(mutatedSolution.getNumberofSwap() > 1) {

						mutatedSolution.mutatebyDeleting(swapIndex);

					}else {

						mutatedSolution.mutatebyAdding(swapIndex, elementsToSort);

					}

				}else {

					if(mutatedSolution.getEffectiveness() == 100) {

						break;

					}

					mutatedSolution.mutatebyAdding(swapIndex, elementsToSort);

				}

				mutatedSolution.testSolution(elementsToSort);

				for(int i = 0; i < elementsToSort.numberofElements(); i++) {

					elementsToSort.getElement(i).resetBool();
					elementsToSort.getElement(i).isItPlaced();

				}

				double rand = Utility.randomDouble();

				if(Utility.acceptanceProbabilityPerfect(currentFinal, mutatedSolution, perfectTemp) > rand) {

					currentFinal.copyMutated(mutatedSolution);

				}

				//Keep the best

				if((currentFinal.getEffectiveness() > bestFinal.getEffectiveness()) || ((currentFinal.getEffectiveness() == bestFinal.getEffectiveness()) && (Double.compare((currentFinal.getPerfectRatio()),(bestFinal.getPerfectRatio())) == 1)) || (((currentFinal.getEffectiveness() == bestFinal.getEffectiveness()) && (Double.compare((currentFinal.getPerfectRatio()),(bestFinal.getPerfectRatio())) == 0)) && (Double.compare((currentFinal.getSwapRatio()),(bestFinal.getSwapRatio())) == -1)) || (((currentFinal.getEffectiveness() == bestFinal.getEffectiveness()) && (Double.compare((currentFinal.getPerfectRatio()),(bestFinal.getPerfectRatio())) == 0)) && (Double.compare((currentFinal.getSwapRatio()),(bestFinal.getSwapRatio())) == 0) && currentFinal.getCurrentSize() > bestFinal.getBestSize())) {

					bestFinal.copyCurrent(currentFinal);
				}

				//Cool the system

				perfectTemp *= 1 - coolingRate;

			}

			/*		System.out.println("Effectiveness of best final: " + bestFinal.getEffectiveness());
			System.out.println("Efficiency of best final: " + bestFinal.getEfficiency());
			System.out.println("Best final: " + bestFinal);
			System.out.println("Perfect ratio: " + bestFinal.getPerfectRatio());
			System.out.println("Swap ratio: " + bestFinal.getSwapRatio());
			System.out.println("SwapToSize ratio: " + bestFinal.getSwapToSizeRatio());
			System.out.println("Number of swaps: " + bestFinal.getBestSize());


			for(int i = 0; i < bestFinal.getUnsortedSize(); i++) {

				System.out.print(bestFinal.getUnsortedIndex(i) + " ");

			}

			System.out.println();*/

			//Check if solution was found and cool the system

			if((bestFinal.getEffectiveness() < 100)  || (bestFinal.getEfficiency() < 100) ) {

				finalTemp *= 1 - finalCoolingRate;

		 		if(Double.compare(finalTemp, 1) == -1 && bestFinal.getEffectiveness() != 100) {

					finalTemp = 1000;
					tries--;

					if(tries == 0) {

						finalTemp = 100000;

						currentFinal.generateInitial(elementsToSort);

						currentFinal.testSolution(elementsToSort);

						for(int i = 0; i < elementsToSort.numberofElements(); i++) {

							elementsToSort.getElement(i).resetBool();
							elementsToSort.getElement(i).isItPlaced();

						}

						bestFinal.copyCurrent(currentFinal);

						tries = 3;

					}

				} 
		/*	if(finalTemp > 1.0) {

				finalTemp *= 1 - finalCoolingRate;*/
				
			}else {

				finalTemp = 1.0;

			}

		}

		return bestFinal;

	}

	public static void main(String[] args) {

		ElementstoSort elementsToSort = new ElementstoSort();
		String myUnsorted = null;
		String mySorted = null;
		Scanner sc = new Scanner(System.in);
		boolean again = true;

		while(again) {

			System.out.println("What type of array do you want to sort?");
			System.out.println("1. Nearly sorted");
			System.out.println("2. Inversely sorted");
			System.out.println("3. Random");
			System.out.println("4. Random large");

			int arrayType = sc.nextInt();

			if(arrayType == 4) {
				//Radom large

				myUnsorted = ("7126.0,5549.0,3974.0,4974.0,6777.0,7539.0,8394.0,845.0,5179.0,4926.0,7455.0,8842.0,2637.0,5985.0,5433.0,7137.0,8615.0,2215.0,9205.0,4807.0,7286.0,8170.0,9974.0,5311.0,3134.0,4479.0,1781.0,1413.0,3465.0,2260.0,9281.0,978.0,9271.0,7714.0,9847.0,2578.0,839.0,9948.0,8534.0,4610.0,7209.0,877.0,1871.0,7871.0,9697.0,7056.0,688.0,541.0,3134.0,2606.0,8308.0,1777.0,9843.0,6690.0,2473.0,8866.0,6905.0,323.0,7078.0,7403.0,5850.0,7854.0,2821.0,9587.0,7930.0,6417.0,391.0,6911.0,3535.0,3708.0,1558.0,11.0,655.0,1621.0,8970.0,9180.0,1460.0,6608.0,1685.0,403.0,5641.0,9956.0,8199.0,8321.0,3185.0,6063.0,3570.0,9960.0,8320.0,3077.0,1072.0,3273.0,5602.0,7598.0,3447.0,305.0,3777.0,2323.0,7410.0,7338.0");
				mySorted = ("11.0,305.0,323.0,391.0,403.0,541.0,655.0,688.0,839.0,845.0,877.0,978.0,1072.0,1413.0,1460.0,1558.0,1621.0,1685.0,1777.0,1781.0,1871.0,2215.0,2260.0,2323.0,2473.0,2578.0,2606.0,2637.0,2821.0,3077.0,3134.0,3134.0,3185.0,3273.0,3447.0,3465.0,3535.0,3570.0,3708.0,3777.0,3974.0,4479.0,4610.0,4807.0,4926.0,4974.0,5179.0,5311.0,5433.0,5549.0,5602.0,5641.0,5850.0,5985.0,6063.0,6417.0,6608.0,6690.0,6777.0,6905.0,6911.0,7056.0,7078.0,7126.0,7137.0,7209.0,7286.0,7338.0,7403.0,7410.0,7455.0,7539.0,7598.0,7714.0,7854.0,7871.0,7930.0,8170.0,8199.0,8308.0,8320.0,8321.0,8394.0,8534.0,8615.0,8842.0,8866.0,8970.0,9180.0,9205.0,9271.0,9281.0,9587.0,9697.0,9843.0,9847.0,9948.0,9956.0,9960.0,9974.0");

			}else if(arrayType == 2) {

				//Inversely

				myUnsorted = ("996,991,986,982,223,958,941,21,902,893,880,879,879,432,868,764,860,853,849,844,651,830,292,818,812,799,794,786,775,768,864,759,756,746,690,534,671,659,838,408,637,619,613,608,589,576,574,562,550,541,536,681,528,525,280,509,53,480,466,444,435,872,431,637,400,380,352,317,306,302,300,294,820,513,267,263,245,243,967,221,210,200,192,191,190,185,175,164,157,153,139,91,77,71,485,40,36,23,905,8");
				mySorted = ("8,21,23,36,40,53,71,77,91,139,153,157,164,175,185,190,191,192,200,210,221,223,243,245,263,267,280,292,294,300,302,306,317,352,380,400,408,431,432,435,444,466,480,485,509,513,525,528,534,536,541,550,562,574,576,589,608,613,619,637,637,651,659,671,681,690,746,756,759,764,768,775,786,794,799,812,818,820,830,838,844,849,853,860,864,868,872,879,879,880,893,902,905,941,958,967,982,986,991,996");

			}else if(arrayType == 1) {

				//Nearly

				myUnsorted = ("989,11,25,35,36,38,242,971,61,85,436,104,129,141,210,213,236,858,242,342,244,252,266,285,295,297,297,308,308,331,39,348,369,373,378,387,390,391,400,406,411,419,420,431,639,466,471,476,715,500,755,506,507,528,528,538,563,569,580,633,636,90,642,670,676,683,686,693,706,708,495,722,753,500,764,802,811,812,858,814,825,832,812,242,866,872,873,895,901,923,925,925,937,946,961,965,48,980,7,999");
				mySorted = ("7,11,25,35,36,38,39,48,61,85,90,104,129,141,210,213,236,242,242,242,244,252,266,285,295,297,297,308,308,331,342,348,369,373,378,387,390,391,400,406,411,419,420,431,436,466,471,476,495,500,500,506,507,528,528,538,563,569,580,633,636,639,642,670,676,683,686,693,706,708,715,722,753,755,764,802,811,812,812,814,825,832,858,858,866,872,873,895,901,923,925,925,937,946,961,965,971,980,989,999");

				//Random

			}else if(arrayType == 3) {

				myUnsorted = ("87.0,4.0,92.0,58.0,74.0,2.0,54.0,17.0,10.0,22.0,8.0,33.0,9.0,4.0,67.0,26.0,93.0,37.0,37.0,62.0,1.0,46.0,93.0,63.0,24.0,35.0,82.0,91.0,47.0,54.0,7.0,27.0,76.0,27.0,2.0,35.0,67.0,94.0,92.0,68.0,71.0,13.0,7.0,50.0,4.0,63.0,8.0,31.0,39.0,58.0,67.0,95.0,29.0,67.0,97.0,91.0,99.0,52.0,68.0,78.0,25.0,19.0,82.0,63.0,63.0,0.0,60.0,26.0,60.0,22.0,68.0,7.0,67.0,35.0,6.0,46.0,21.0,52.0,51.0,0.0,83.0,64.0,25.0,99.0,78.0,28.0,91.0,52.0,95.0,26.0,59.0,83.0,77.0,56.0,11.0,18.0,2.0,26.0,95.0,99.0");
				mySorted = ("0.0,0.0,1.0,2.0,2.0,2.0,4.0,4.0,4.0,6.0,7.0,7.0,7.0,8.0,8.0,9.0,10.0,11.0,13.0,17.0,18.0,19.0,21.0,22.0,22.0,24.0,25.0,25.0,26.0,26.0,26.0,26.0,27.0,27.0,28.0,29.0,31.0,33.0,35.0,35.0,35.0,37.0,37.0,39.0,46.0,46.0,47.0,50.0,51.0,52.0,52.0,52.0,54.0,54.0,56.0,58.0,58.0,59.0,60.0,60.0,62.0,63.0,63.0,63.0,63.0,64.0,67.0,67.0,67.0,67.0,67.0,68.0,68.0,68.0,71.0,74.0,76.0,77.0,78.0,78.0,82.0,82.0,83.0,83.0,87.0,91.0,91.0,91.0,92.0,92.0,93.0,93.0,94.0,95.0,95.0,95.0,97.0,99.0,99.0,99.0");

			}


			String[] myUnsorteds = myUnsorted.split(",");
			String[] mySorteds = mySorted.split(",");

			for(int i = 0; i < myUnsorteds.length; i++) {

				elementsToSort.addElement(new Element(Double.parseDouble(myUnsorteds[i]), Double.parseDouble(mySorteds[i])));

			}

			//	elementsToSort.setSums();

			/*unsorted.addElement(4.0);
		unsorted.addElement(3.0);
		unsorted.addElement(2.0);
		unsorted.addElement(1.0);
		unsorted.addElement(0.0);

		sorted.add(0.0);
		sorted.add(1.0);
		sorted.add(2.0);
		sorted.add(3.0);
		sorted.add(4.0);*/

		//	double temp = 1000;
		//	double coolingRate = 0.5;

			SimulatedSorting simulatedSorting = new SimulatedSorting();

			CurrentFinal currentFinals = new CurrentFinal(elementsToSort);
			currentFinals.copyBest(simulatedSorting.getSolution(elementsToSort, myUnsorteds, mySorteds));

			BestFinal bestFinals = new BestFinal(elementsToSort);
			bestFinals.copyCurrent(currentFinals); 

		/*	while(temp > 1) {

			 	System.out.println(temp);
				
				MutatedSolution mutatedSolutions = new MutatedSolution(elementsToSort);
				mutatedSolutions.copyBest(simulatedSorting.getSolution(elementsToSort, myUnsorteds, mySorteds));

				if(mutatedSolutions.getMutatedSize() < currentFinals.getCurrentSize()) {

					currentFinals.copyMutated(mutatedSolutions);
					bestFinals.copyCurrent(currentFinals);

				}
				
				temp *= 1 - coolingRate;


			}*/

			System.out.println();
			System.out.println("Effectiveness of final solution: " + bestFinals.getEffectiveness());
			System.out.println("Efficiency of final solution: " + bestFinals.getEfficiency());
			System.out.println("Solution: " + bestFinals);
			System.out.println("Perfect ratio: " + bestFinals.getPerfectRatio());
			System.out.println("Swap ratio: " + bestFinals.getSwapRatio());
			System.out.println("SwapToSize ratio: " + bestFinals.getSwapToSizeRatio());
			System.out.println("Number of swaps: " + bestFinals.getBestSize());


			elementsToSort.swaps(bestFinals);

			//	elementsToSort.print();

			for(int i = 0; i < bestFinals.getUnsortedSize(); i++) {

				System.out.print(bestFinals.getUnsortedIndex(i) + " ");

			}

			System.out.println();

			System.out.println("Do you want to try another type? Y/N");

			String againChoice = sc.next();

			if(againChoice.contains("y") || againChoice.contains("Y")) {

				elementsToSort.clear();
				myUnsorteds = null;
				mySorteds = null;
				myUnsorted = null;
				mySorted = null;

			}else if(againChoice.contains("n") || againChoice.contains("N")) {

				again = false;

			}

		}

	}
}
