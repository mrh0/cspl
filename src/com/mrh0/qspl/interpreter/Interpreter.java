package com.mrh0.qspl.interpreter;

import com.mrh0.qspl.interpreter.evaluator.EvalResult;
import com.mrh0.qspl.interpreter.evaluator.Evaluator;
import com.mrh0.qspl.tokenizer.Tokenizer;
import com.mrh0.qspl.vm.VM;

public class Interpreter {
	private VM vm;
	private Tokenizer tokens;
	private Evaluator e;
	
	public Interpreter(VM vm, Tokenizer tokens) {
		this.vm = vm;
		this.tokens  = tokens;
		this.e = new Evaluator(vm, tokens);
	}
	
	public EvalResult eval() {
		return e.eval();
	}
}
