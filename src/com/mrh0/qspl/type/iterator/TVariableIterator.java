package com.mrh0.qspl.type.iterator;

import java.util.Iterator;

import com.mrh0.qspl.type.TArray;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.type.var.Var;

public class TVariableIterator extends TIterator{
	
	private Var out;
	private Iterator<Val> iterator;
	
	public TVariableIterator(Var v, IIterable it) {
		this.out = v;
		this.iterator = it.iterator();
	}

	public TVariableIterator(Var v, IKeyIterable it) {
		this.out = v;
		this.iterator = it.keyIterator();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Val next() {
		Val r = iterator.next();
		out.assign(r);
		return r;
	}

	@Override
	public String toString() {
		return "(" + out.getName() + "->" + iterator + ")";
	}
	
	public Val is(Val v) {
		return new TNumber(TVariableIterator.class.isInstance(v));
	}
}
