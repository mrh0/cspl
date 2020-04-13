package com.mrh0.qspl.type.iterator;

import java.util.Iterator;

import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.util.SortingType;

public class TOrderByIterator extends TIterator {

	private Iterator<Val> itr;
	private SortingType st;
	
	public TOrderByIterator(IIterable itr, SortingType st) {
		this.itr = itr.iterator();
		this.st = st;
	}
	
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public Val next() {
		return null;
	}
	
}
