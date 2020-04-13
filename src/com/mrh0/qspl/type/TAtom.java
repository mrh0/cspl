package com.mrh0.qspl.type;

import java.util.HashMap;
import java.util.Map;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.type.var.Var;

public class TAtom implements Val{
	
	private final String name;
	
	private TAtom(String name) {
		this.name = name;
		
		atomStore.put(name, this);
	}
	
	private TAtom(TAtom atom) {
		this.name = atom.name;
		
		atomStore.put(name, this);
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
			return this == v;
		return false;
	}
	
	@Override
	public double getRelativeValue(Val v) {
		if(v.isAtom())
			return name.compareTo(from(v).name);
		return Double.MAX_VALUE;
	}
	
	@Override
	public int compare(Val v) {
		if(v.isAtom())
			return name.compareTo(from(v).name);
		return Integer.MAX_VALUE;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	private static Map<String, TAtom> atomStore;
	
	public static void init() {
		atomStore = new HashMap<String, TAtom>();
	}
	
	public static TAtom get(String name) {
		if(!atomStore.containsKey(name))
			return new TAtom(name);
		return atomStore.get(name);
	}
	
	public static TAtom from(Val v) {
		if(v instanceof TAtom)
			return (TAtom)v;
		if(v instanceof Var && v.isAtom())
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to atom.");
		return null;
	}
	
	public Val is(Val v) {
		return new TNumber(TAtom.class.isInstance(v));
	}
}
