package com.mrh0.qspl.type;

import com.mrh0.qspl.type.number.TNumber;

public class TUndefined implements Val{
	
	private static TUndefined instance = null;
	
	public TUndefined() {
		if(instance == null)
			instance = this;
	}

	@Override
	public Val duplicate() {
		return TUndefined.getInstance();
	}

	@Override
	public boolean booleanValue() {
		return false;
	}
	
	@Override
	public boolean isUndefined() {
		return true;
	}
	
	@Override
	public String getTypeName() {
		return "undefined";
	}
	
	@Override
	public String toString() {
		return "undefined";
	}
	
	@Override
	public boolean equals(Val v) {
		return this == v;
	}
	
	@Override
	public int compare(Val v) {
		return this == v?0:Integer.MIN_VALUE;
	}
	
	@Override
	public double getRelativeValue(Val v) {
		return Double.MIN_VALUE;
	}

	public static TUndefined getInstance() {
		if(instance == null)
			return new TUndefined();
		return instance;
	}

	@Override
	public Object getValue() {
		return "undefined";
	}
	
	public Val is(Val v) {
		return new TNumber(v.isUndefined());
	}
}
