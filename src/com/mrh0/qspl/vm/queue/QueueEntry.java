package com.mrh0.qspl.vm.queue;

import com.mrh0.qspl.interpreter.evaluator.EvalResult;
import com.mrh0.qspl.vm.VM;

public abstract class QueueEntry {
	private long id;
	private static long next = 0;
	
	public enum ExecutionState {
		READY, AWAIT, CANCEL
	}
	
	public QueueEntry(long id) {
		this.id = id;
	}
	
	public QueueEntry() {
		this.id = next++;
	}
	
	public abstract ExecutionState getState();
	
	public abstract void start(VM vm);
	
	public abstract void end(ExecutionState state, VM vm);
	
	public void cancel(VM vm) {
		end(ExecutionState.CANCEL, vm);
	}
	
	public void ready(VM vm) {
		end(ExecutionState.READY, vm);
	}
	
	public long getId() {
		return id;
	}
}
