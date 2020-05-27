package com.mrh0.qspl.vm.queue;

import com.mrh0.qspl.interpreter.evaluator.EvalResult;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.func.TFunc;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.vm.VM;

public abstract class CallbackEntry extends QueueEntry{

	private TFunc callback;
	public CallbackEntry(TFunc callback) {
		this.callback = callback;
	}
	
	public EvalResult finish(VM vm, Arguments args) {
		return getCallback().execute(vm, TNumber.create(getId()), args);
	}
	
	public Arguments getArgs() {
		return new Arguments();
	}
	
	public TFunc getCallback() {
		return this.callback;
	}
	
	@Override
	public void end(ExecutionState state, VM vm) {
		if(state == ExecutionState.READY)
			finish(vm, getArgs());
	}
}
