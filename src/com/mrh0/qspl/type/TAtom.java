package com.mrh0.qspl.type;

import com.mrh0.qspl.io.console.Console;

public class TAtom implements Val{
	
	private final String name;
	
	public TAtom(String name) {
		this.name = name;
	}
	
	public TAtom(TAtom atom) {
		this.name = atom.name;
	}
	
	@Override
	public boolean isAtom() {
		return true;
	}

	@Override
	public boolean booleanValue() {
		return true;
	}

	@Override
	public String getTypeName() {
		return "atom";
	}
	
	@Override
	public boolean equals(Val v) {
		if(v.isAtom())
			return name.equals(((TAtom)v).name);
		return false;
	}
	
	@Override
	public double getRelativeValue(Val v) {
		if(v.isAtom())
			return 0;
		return Double.MAX_VALUE;
	}
	
	@Override
	public int compare(Val v) {
		Console.g.log("Default compare operation on " + this.getTypeName() + " with " + v.getTypeName());
		return 0;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
