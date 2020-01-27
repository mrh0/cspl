package com.mrh0.qspl.interpreter.evaluator;

import com.mrh0.qspl.type.Val;

public class EvalResult{
	private Val result;
	private Exception error;
	
	public EvalResult(Val result) {
		this.result = result;
	}
	
	public Val getResult() {
		return this.result;
	}
}