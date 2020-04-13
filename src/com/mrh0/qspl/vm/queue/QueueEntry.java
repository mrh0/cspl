package com.mrh0.qspl.vm.queue;

import com.mrh0.qspl.type.Val;

public abstract class QueueEntry {
	private long id;
	public enum ExecutionState {
		READY, AWAIT, CANCEL, READY_CANCEL
	}
	
	public QueueEntry(long id) {
		this.id = id;
	}
	
	public abstract ExecutionState getExecutionState();
	public abstract Val execute();
}
