package com.mrh0.qspl.type.number;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.Var;

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
		return String.valueOf(get());
	}

	@Override
	public Val duplicate() {
		return create(this.get());
	}

	@Override
	public boolean booleanValue() {
		return get() > 0.0;
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
			return create(get() + from(v).get());
		Console.g.err("Cannot preform operation add on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	@Override
	public Val sub(Val v) {
		if(v.isNumber())
			return create(get() - from(v).get());
		Console.g.err("Cannot preform operation subtact on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	@Override
	public Val multi(Val v) {
		if(v.isNumber())
			return create(get() * from(v).get());
		Console.g.err("Cannot preform operation multiply on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	@Override
	public Val div(Val v) {
		if(v.isNumber())
			return create(get() / from(v).get());
		Console.g.err("Cannot preform operation divide on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	@Override
	public Val pow(Val v) {
		if(v.isNumber())
			return create(Math.pow(get(),  from(v).get()));
		Console.g.err("Cannot preform operation powerof on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public Val approx() {
		return create(Math.round(get()));
	}
	
	@Override
	public boolean equals(Val v) {
		if(v.isNumber()) {
			TNumber n = TNumber.from(v);
			return n.get() == get();
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
		return get();
	}
	
	public double get() {
		return value;
	}
	
	public int integerValue() {
		return (int)get();
	}

	public static TNumber from(Val v) {
		if(v instanceof TNumber)
			return (TNumber)v;
		if(v instanceof Var && v.isNumber())
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to number.");
		return null;
	}
	
	public static TNumber create(double n) {
		return new TNumber(n);
	}
	
	public static TNumber create(int n) {
		return new TNumber(n);
	}
	
	public static TNumber create(boolean b) {
		return new TInt(b);
	}
	
	public static TNumber create(String s) {
		if(s.indexOf('.') == -1)
			return create(Double.valueOf(s));
		return create(Integer.valueOf(s));
	}

	@Override
	public Object getValue() {
		return get();
	}
}