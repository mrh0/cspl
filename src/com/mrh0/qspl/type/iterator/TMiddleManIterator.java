package com.mrh0.qspl.type.iterator;

import java.util.Iterator;

import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.var.Var;

public class TMiddleManIterator extends TIterator{
	
	private Var out;
	private Iterator<Val> iterator;
	
	public TMiddleManIterator(Var v, IIterable it) {
		this.out = v;
		this.iterator = it.iterator();
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
}
