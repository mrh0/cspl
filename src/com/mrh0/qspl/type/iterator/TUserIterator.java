package com.mrh0.qspl.type.iterator;

import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.TFunc;
import com.mrh0.qspl.vm.VM;

public class TUserIterator extends TIterator {

	private TFunc hasf;
	private TFunc getf;
	private Val accu;
	private VM vm;
	
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public Val next() {
		return null;
	}

}
