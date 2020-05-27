package com.mrh0.qspl.type.func;

import com.mrh0.qspl.interpreter.evaluator.EvalResult;
import com.mrh0.qspl.interpreter.evaluator.StatementEval;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.tokenizer.token.TokenBlock;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.vm.VM;
import com.mrh0.qspl.vm.scope.Scope;

public class TAnFunc extends TUserFunc{

	public TAnFunc(TokenBlock block) {
		super(block);
	}
	
	@Override
	public EvalResult execute(VM vm, Val _this, Arguments args) {
		int bl = Console.g.currentLine;
		vm.pushScope(new Scope("afunc"));
		vm.setVariablesSimple(defaults, args);
		EvalResult result = StatementEval.evalCodeBlock(block, vm);
		vm.popScope();
		Console.g.currentLine = bl;
		if(StatementEval.exiting)
			return StatementEval.cancelExit();
		return result;
	}
}
