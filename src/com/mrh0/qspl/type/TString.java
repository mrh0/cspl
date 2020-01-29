package com.mrh0.qspl.type;

import com.mrh0.qspl.io.console.Console;

public class TString implements Val{

	private String value;
	
	public TString() {
		value = "";
	}
	
	public TString(TString s) {
		value = s.get();
	}
	
	public TString(String s) {
		value = s;
	}
	
	public TString(Object o) {
		value = o.toString();
	}
	
	@Override
	public int getType() {
		return 0;
	}

	@Override
	public Val duplicate() {
		return new TString(this);
	}

	@Override
	public boolean booleanValue() {
		return value.length() > 0;
	}
	
	@Override
	public boolean isString() {
		return true;
	}
	
	@Override
	public String getTypeName() {
		return "string";
	}
	
	@Override
	public Val add(Val v) {
		return new TString(value + v.toString());
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public String get() {
		return value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	public static TString from(Val v) {
		if(v instanceof TString)
			return (TString)v;
		if(v instanceof Var && v.isString())
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to string.");
		return null;
	}
}
