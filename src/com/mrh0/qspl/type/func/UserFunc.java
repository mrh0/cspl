package com.mrh0.qspl.type.func;

import java.util.ArrayList;
import com.mrh0.qspl.interpreter.evaluator.EvalResult;
import com.mrh0.qspl.interpreter.evaluator.StatementEval;
import com.mrh0.qspl.tokenizer.token.TokenBlock;
import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.vm.VM;
import com.mrh0.qspl.vm.scope.Scope;

public class UserFunc extends TFunc{

	private TokenBlock block;
	private TContainer defaults;
	
	public UserFunc(TokenBlock block) {
		this.block = block;
	}
	
	public UserFunc(TokenBlock block, TContainer defaults) {
		this.block = block;
		this.defaults = defaults;
	}
	
	@Override
	public EvalResult execute(VM vm, Val _this, ArrayList<Val> args) {
		vm.pushScope(new Scope("ufunc"));
		vm.setVariables(defaults);
		EvalResult result = StatementEval.evalCodeBlock(block, vm);
		vm.popScope();
		return result;
	}
}
