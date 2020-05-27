package com.mrh0.qspl.vm.queue;

import com.mrh0.qspl.type.func.TFunc;
import com.mrh0.qspl.vm.VM;

public class StateEntry extends CallbackEntry {
	
	private ExecutionState state;
	
	public StateEntry(TFunc callback) {
		super(callback);
	}

	public ExecutionState getState() {
		return this.state;
	}
	
	public void setState(ExecutionState state) {
		synchronized(this.state) {
			this.state = state;
		}
	}

	@Override
	public void start(VM vm) {
		
	}
}
