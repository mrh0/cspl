package com.mrh0.qspl.type;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.type.var.Var;

public class TGeneric<T> implements Val{

	
	private final T value;
	private String typeName;
	public TGeneric(T value, String typeName) {
		this.value = value;
	}
	
	@Override
	public boolean booleanValue() {
		return value != null;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public TAtom getTypeAtom() {
		return TAtom.get(typeName);
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public boolean equals(Val v) {
		return value.equals(v);
	}
	
	@Override
	public double getRelativeValue(Val v) {
		return value.equals(v)?0:Val.super.getRelativeValue(v);
	}

	public T get() {
		return value;
	}
	
	public T getValue() {
		return value;
	}
	
	@Override
	public Val is(Val v) {
		if(!(v instanceof TGeneric<?>))
			return new TNumber(false);
		if(getTypeName().equals(v.getTypeName()))
			return new TNumber(true);
		return new TNumber(false);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> TGeneric<T> from(Val v) {
		if(v instanceof TGeneric<?>) {
			try {
				return (TGeneric<T>)v;
			}
			catch(Exception e) {
				Console.g.err("Cannot convert " + v.getTypeName() + " to specific generic.");
				return null;
			}
		}
		if(v instanceof Var && v.isContainer())
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to any generic.");
		return null;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
