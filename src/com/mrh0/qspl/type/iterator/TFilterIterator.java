package com.mrh0.qspl.type.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.func.TFunc;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.vm.VM;

public class TFilterIterator extends TIterator{

	private Iterator<Val> itr;
	private TFunc f;
	private VM vm;
	private Val nextObj;
	private boolean isSet = false;
	private int index;
	private int iteri;
	
	public TFilterIterator(TFunc f, IIterable itr, VM vm) {
		this.itr = itr.iterator();
		this.f = f;
		this.vm = vm;
		index = 0;
		iteri = 0;
	}

	@Override
	public boolean hasNext() {
		return isSet || setNext();
	}

	@Override
	public Val next() {
		if (!isSet && !setNext()) {
            throw new NoSuchElementException();
        }
        isSet = false;
        return nextObj;
	}
	
	private boolean setNext() {
		while(itr.hasNext()) {
			final Val v = itr.next();
			if(f.execute(vm, v, new Arguments().put(v).put(TNumber.create(index++)).put(TNumber.create(iteri))).getResult().booleanValue()) {
				iteri++;
				nextObj = v;
				isSet = true;
				return true;
			}
		}
		return false;
	}
	
	@Override
    public void remove() {
        if (isSet) {
            throw new IllegalStateException("A remove operation is illegal at this point.");
        }
        itr.remove();
    }
}
