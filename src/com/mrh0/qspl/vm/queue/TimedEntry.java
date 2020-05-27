package com.mrh0.qspl.vm.queue;

import com.mrh0.qspl.type.func.TFunc;
import com.mrh0.qspl.vm.VM;

public class TimedEntry extends CallbackEntry{

	private long triggerTime;
	
	public TimedEntry(TFunc callback, long triggerTimeMilis) {
		super(callback);
		this.triggerTime = triggerTimeMilis;
	}
	
	public long getTriggerTime() {
		return this.triggerTime;
	}

	@Override
	public ExecutionState getState() {
		return getTriggerTime() < System.currentTimeMillis()?ExecutionState.READY:ExecutionState.AWAIT;
	}

	@Override
	public void start(VM vm) {
	}
}
