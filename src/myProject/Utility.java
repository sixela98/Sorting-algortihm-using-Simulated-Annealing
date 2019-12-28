package myProject;

import java.util.Random;

public class Utility {

	//Calculates the acceptance probability for the Final

	public static double acceptanceProbabilityTest(CurrentFinal currentFinal, MutatedSolution mutatedSolution, double temperature) {

		//If mutation has a better effectiveness, set it as the current

		if(mutatedSolution.getEffectiveness() > currentFinal.getEffectiveness()) {

			return 1.0;

		}

		//If both has the same effectiveness, select the one with a better perfectRatio

		else if((mutatedSolution.getEffectiveness() == currentFinal.getEffectiveness()) && (Double.compare((mutatedSolution.getPerfectRatio()),(currentFinal.getPerfectRatio())) == 1)) {

			return 1.0;	

		}

		//If same effectiveness and perfectRatio, keep the lowest swapRatio

		else if(((mutatedSolution.getEffectiveness() == currentFinal.getEffectiveness()) && (Double.compare((mutatedSolution.getPerfectRatio()),(currentFinal.getPerfectRatio())) == 0)) && (Double.compare((mutatedSolution.getSwapRatio()),(currentFinal.getSwapRatio())) == -1)) {

			return 1.0;

		}		
		
		//If same effectiveness and perfectRatio and swapRatio, keep the smaller size

		else if(((mutatedSolution.getEffectiveness() == currentFinal.getEffectiveness()) && (Double.compare((mutatedSolution.getPerfectRatio()),(currentFinal.getPerfectRatio())) == 0)) && (Double.compare((mutatedSolution.getSwapRatio()),(currentFinal.getSwapRatio())) == 0) && (mutatedSolution.getMutatedSize() < currentFinal.getCurrentSize())) {

			return 1.0;

		}else {

			//If the new is worse, calculate the acceptance probability

			return Math.exp((mutatedSolution.getEffectiveness() - currentFinal.getEffectiveness())/temperature);

		}

	}

	//Calculates the acceptance probability for the perfection

	public static double acceptanceProbabilityPerfect(CurrentFinal currentFinal, MutatedSolution mutatedSolution, double temperature) {

		if(mutatedSolution.getEffectiveness() > currentFinal.getEffectiveness()) {  //If effectiveness got better, keep

			return 1.0;

		}else if((mutatedSolution.getEffectiveness() == currentFinal.getEffectiveness()) && Double.compare(mutatedSolution.getSwapRatio(),currentFinal.getSwapRatio()) == -1) {	//If FinalRatio was improved, set mutated as the current

			return 1.0;


		}else if((mutatedSolution.getEffectiveness() == currentFinal.getEffectiveness()) && (Double.compare(mutatedSolution.getPerfectRatio(),currentFinal.getPerfectRatio()) == 1) && (Double.compare(mutatedSolution.getSwapRatio(),currentFinal.getSwapRatio()) == 0)) {	//Else if the same FinalRatio, check for the swapRatio and set mutated if lower

			return 1.0;

		}		
		
		//If same effectiveness and perfectRatio and swapRatio, keep the smaller size

		else if(((mutatedSolution.getEffectiveness() == currentFinal.getEffectiveness()) && (Double.compare((mutatedSolution.getPerfectRatio()),(currentFinal.getPerfectRatio())) == 0)) && (Double.compare((mutatedSolution.getSwapRatio()),(currentFinal.getSwapRatio())) == 0) && (mutatedSolution.getMutatedSize() < currentFinal.getCurrentSize())) {

			return 1.0;

		}else {

			return Math.exp((mutatedSolution.getEfficiency() - currentFinal.getEfficiency())/temperature);

		}

	}

	//Returns a random double betwenn [0.0 , 1.0]

	static double randomDouble() {

		Random r = new Random();

		return r.nextInt(1000)/1000.0;

	}

	//Returns a random int between [min, max)

	public static int randomInt(int min, int max) {

		Random r = new Random();

		double d = min + r.nextDouble() * (max - min);

		return (int)d;

	}

}
