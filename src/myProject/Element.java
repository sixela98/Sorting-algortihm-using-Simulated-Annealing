package myProject;

//An element contains the initial apparence of the object to be sorted and its final
//apparence. The three booleans will serve during the testSolution() part

public class Element {

	private double _initial;
	private double _final;
	private double _afterSorted;
	private boolean gotSwaped = false;
	private boolean gotPlaced = false;
	private boolean isPlaced = false;

	//Constructor to set the initial look of the element

	public Element(double unsorted, double sorted) {

		this._initial = unsorted;
		this._final = sorted;
		this._afterSorted = unsorted;

	}

	//Regular gets and sets

	public double getInitial() {

		return _initial;

	}

	public double getFinal() {

		return _final;

	}

	public double getAfterSorted() {

		return _afterSorted;

	}

	public boolean wasSwaped() {

		return gotSwaped;

	}

	public boolean gotPlaced() {

		return gotPlaced;

	}

	public boolean isPlaced() {

		return isPlaced;

	}

	public void setInitial(double unsorted) {

		this._initial = unsorted;

	}

	public void setFinal(double sorted) {

		this._final = sorted;

	}

	public void setAfterSorted(double afterSorted) {

		this._afterSorted = afterSorted;

	}

	public boolean gotSwaped() {

		if(Double.compare(getInitial(), getAfterSorted()) != 0) {

			return true;

		}else {

			return false;

		}

	}

	public void didPlaced() {

		isItPlaced();

		gotPlaced = isPlaced();

	}

	public void isItPlaced() {

		double compare = _afterSorted - _final;

		if((int)compare == 0) {

			this.isPlaced = true;

		}

	}

	public void resetBool() {

		this.gotPlaced = false;
		this.gotSwaped = false;
		this.isPlaced = false;

	}

}
