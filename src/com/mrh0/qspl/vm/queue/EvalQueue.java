package com.mrh0.qspl.vm.queue;

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
	
	public void spawn(QueueEntry qe) {
		enqueue(qe);
		qe.start(vm);
	}
	
	public void cancel(QueueEntry qe) {
		qe.cancel(vm);
		synchronized(queue) {
			queue.remove(qe);
		}
	}
	
	public void trigger(QueueEntry qe) {
		qe.ready(vm);
		synchronized(queue) {
			queue.remove(qe);
		}
	}
	
	private void enqueue(QueueEntry qe) {
		synchronized(queue) {
			queue.add(qe);
		}
	}
	
	private QueueEntry dequeue() {
		synchronized(queue) {
			return queue.remove(queue.size()-1);
		}
	}
	
	public void start() {
		ArrayList<Integer> del;
		while(!queue.isEmpty() && run) {
			del = new ArrayList<Integer>();
			for(int i = 0; i < queue.size(); i++) {
				QueueEntry q = queue.get(i);
				ExecutionState state = q.getState();
				if(state != ExecutionState.AWAIT) {
					q.end(state, vm);
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
