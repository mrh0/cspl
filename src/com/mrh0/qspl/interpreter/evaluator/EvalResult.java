package com.mrh0.qspl.interpreter.evaluator;

import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;

public class EvalResult{
	private Val result;
	private Exception error;
	private StatementEval.IfChainState pass = StatementEval.IfChainState.PASS;
	
	public EvalResult(Val result) {
		this.result = result;
	}
	
	public EvalResult(Val result, StatementEval.IfChainState pass) {
		this.result = result;
		this.pass = pass;
	}
	
	public EvalResult() {
		this.result = TUndefined.getInstance();
	}

	public Val getResult() {
		return this.result;
	}
	
	public StatementEval.IfChainState didPass() {
		return this.pass;
	}
}