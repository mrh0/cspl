package com.mrh0.qspl.type.iterator;

import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.number.TNumber;

public class TRangeIterator extends TIterator{
	
	private final int start;
	private final int end;
	private int i;
	
	public TRangeIterator(int start, int end) {
		this.start = start;
		this.end = end;
		this.i = start;
	}

	@Override
	public boolean hasNext() {
		return start < end?i <= end:i >= end;
	}

	@Override
	public Val next() {
		return start < end?TNumber.create(i++):TNumber.create(i--);
	}
	
	@Override
	public String toString() {
		return "("+start+"..."+i+"..."+end+")";
	}
}
