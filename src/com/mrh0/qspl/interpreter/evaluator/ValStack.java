package com.mrh0.qspl.interpreter.evaluator;

import com.mrh0.qspl.type.Val;

public class ValStack {
	private Val[] vals;
	private int n;
	
	public ValStack() {
		vals = new Val[16];
	}
	
	public ValStack(int size) {
		vals = new Val[size];
	}
	
	public int size() {
		return n;
	}
	
	public int maxSize() {
		return vals.length;
	}
	
	public Val pop() {
		return vals[--n];
	}
	
	public Val peek() {
		return vals[n-1];
	}
	
	public void push(Val v) {
		if(n > maxSize()-1) {
			Val[] t = vals;
			vals = new Val[maxSize()*2];
			for(int i = 0; i < t.length; i++)
				vals[i] = t[i];
				
		}
		vals[n++] = v;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public void clear() {
		n = 0;
	}
}
