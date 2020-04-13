package com.mrh0.qspl.vm.queue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.mrh0.qspl.vm.VM;
import com.mrh0.qspl.vm.queue.QueueEntry.ExecutionState;

public class EvalQueue {
	private ArrayList<QueueEntry> queue;
	private boolean run;
	private VM vm;
	
	public EvalQueue(VM vm) {
		queue = new ArrayList<QueueEntry>();
		run = true;
		this.vm = vm;
	}
	
	public void enqueue(Constructor<QueueEntry> qi) {
		try {
			queue.add(qi.newInstance(queue.size()));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private QueueEntry dequeue() {
		return queue.remove(queue.size()-1);
	}
	
	public void start() {
		ArrayList<Integer> del;
		while(!queue.isEmpty() && run) {
			del = new ArrayList<Integer>();
			for(int i = 0; i < queue.size(); i++) {
				QueueEntry q = queue.get(i);
				if(q.getExecutionState() == ExecutionState.READY) {
					q.execute();
				}
				else if(q.getExecutionState() == ExecutionState.READY_CANCEL) {
					q.execute();
					del.add(i);
				}
				else if(q.getExecutionState() == ExecutionState.CANCEL) {
					del.add(i);
				}
			}
			for(int i = 0; i < del.size(); i++) {
				queue.remove((int)del.get(i));
			}
		}
	}
	
	public void stop() {
		run = false;
	}
}
