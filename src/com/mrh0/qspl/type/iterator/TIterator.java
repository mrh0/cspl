package com.mrh0.qspl.type.iterator;

import java.util.Iterator;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.TArray;
import com.mrh0.qspl.type.TAtom;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.type.var.Var;

public abstract class TIterator implements Val, Iterator<Val>, IIterable{

	@Override
	public boolean booleanValue() {
		return hasNext();
	}
	
	@Override
	public TAtom getTypeAtom() {
		return TAtom.get("iterator");
	}

	@Override
	public String getTypeName() {
		return "iterator";
	}

	@Override
	public Object getValue() {
		return this;
	}
	
	@Override
	public boolean isIterable() {
		return true;
	}
	
	public static TIterator from(Val v) {
		if(v instanceof TIterator)
			return (TIterator)v;
		if(v instanceof Var && v instanceof TIterator)
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to iterator.");
		return null;
	}
	
	@Override
	public Iterator<Val> iterator() {
		return this;
	}
	
	@Override
	public String toString() {
		return "generic_iterator";
	}
	
	public Val is(Val v) {
		return new TNumber(TIterator.class.isInstance(v));
	}
}
