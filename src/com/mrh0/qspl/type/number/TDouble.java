package com.mrh0.qspl.type.number;

public class TDouble extends TNumber{
	private double value;
	
	public TDouble(double n) {
		this.value  = n;
	}
	
	public TDouble(boolean n) {
		this.value  = n?1.0:0.0;
	}

	@Override
	public double get() {
		return value;
	}
}
