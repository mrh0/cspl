package com.mrh0.qspl.type.number;

public class TInt extends TNumber{
	private int value;
	
	public TInt(int n) {
		this.value  = n;
	}
	
	public TInt(boolean n) {
		this.value  = n?1:0;
	}

	@Override
	public double get() {
		return value;
	}
}
