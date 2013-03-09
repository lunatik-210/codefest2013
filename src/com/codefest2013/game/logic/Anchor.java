package com.codefest2013.game.logic;

public class Anchor {

	public enum Balance {
		LEFT_SHIFT,
		RIGHT_SHIFT,
		STABLE
	}
	
	private int value;
	private int min;
	private int max;
	
	public Anchor( int max, int min ) {
		setMin(min);
		setMax(max);
		setValue((getMin()+getMax())/2);
	}
	
	public void addWeight(int value) {
		setValue(getValue()+value);
	}
	
	public Balance getBalance() {
		if ( min < value && value < max ) {
			return Balance.STABLE;
		}
		if( value < min ) {
			return Balance.LEFT_SHIFT;
		}
		return Balance.RIGHT_SHIFT;
	}

	public int getValue() {
		return value;
	}

	private void setValue(int value) {
		this.value = value;
	}

	private int getMin() {
		return min;
	}

	private void setMin(int min) {
		this.min = min;
	}

	private int getMax() {
		return max;
	}

	private void setMax(int max) {
		this.max = max;
	}

}
