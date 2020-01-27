package com.mrh0.qspl.interpreter.evaluator;

import com.mrh0.qspl.tokenizer.Tokenizer;
import com.mrh0.qspl.tokenizer.token.TokenBlock;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.vm.VM;

public class Evaluator {
	private VM vm;
	private Tokenizer tokens;
	
	public Evaluator(VM vm, Tokenizer tokens) {
		this.vm = vm;
		this.tokens  = tokens;
	}
	
	public EvalResult eval() {
		TokenBlock tb = tokens.tokenize();
		StatementEval.evalBlock(tb, vm);
		return new EvalResult(TUndefined.getInstance());
	}
}
