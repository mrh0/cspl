package com.mrh0.qspl.type;

import com.mrh0.qspl.io.console.Console;

public class TNumber implements Val{

	private static int typeId;
	private double value;
	
	public TNumber() {
		value = 0;
	}
	
	public TNumber(TNumber n) {
		value = n.get();
	}
	
	public TNumber(int val) {
		value = val;
	}
	
	public TNumber(double val) {
		value = val;
	}
	
	public TNumber(long val) {
		value = val;
	}
	
	public TNumber(short val) {
		value = val;
	}
	
	public TNumber(boolean val) {
		value = val?1:0;
	}
	
	public TNumber(String val) {
		value = Double.parseDouble(val);
	}
	
	@Override
	public int getType() {
		return typeId;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public Val duplicate() {
		return new TNumber(this);
	}

	@Override
	public boolean booleanValue() {
		return value > 0.0;
	}
	
	@Override
	public boolean isNumber() {
		return true;
	}
	
	@Override
	public String getTypeName() {
		return "number";
	}
	
	@Override
	public Val add(Val v) {
		if(v.isNumber())
			return new TNumber(value + from(v).get());
		Console.g.err("Cannot preform operation add on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	@Override
	public Val sub(Val v) {
		if(v.isNumber())
			return new TNumber(value - from(v).get());
		Console.g.err("Cannot preform operation subtact on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	@Override
	public Val multi(Val v) {
		if(v.isNumber())
			return new TNumber(value * from(v).get());
		Console.g.err("Cannot preform operation multiply on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	@Override
	public Val div(Val v) {
		if(v.isNumber())
			return new TNumber(value / from(v).get());
		Console.g.err("Cannot preform operation divide on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	@Override
	public boolean equals(Val v) {
		if(v.isNumber()) {
			TNumber n = TNumber.from(v);
			return n.get() == value;
		}
		return super.equals(v);
	}
	
	/*@Override
	public int compare(Val v) {
		if(v.isNumber()) {
			TNumber n = TNumber.from(v);
			return n.get() > value?-1:(n.get() < value?1:0);
		}
		return Val.super.compare(v);
	}*/
	
	@Override
	public double getRelativeValue(Val v) {
		return value;
	}
	
	public double get() {
		return value;
	}
	
	public int integerValue() {
		return (int)value;
	}

	public static TNumber from(Val v) {
		if(v instanceof TNumber)
			return (TNumber)v;
		if(v instanceof Var && v.isNumber())
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to number.");
		return null;
	}

	@Override
	public Object getValue() {
		return value;
	}
}