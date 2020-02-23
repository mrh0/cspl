package com.mrh0.qspl.type.func;

import com.mrh0.qspl.interpreter.evaluator.EvalResult;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.util.StringUtil;
import com.mrh0.qspl.vm.VM;

public class InternalFunc extends TFunc{
	
	private IFunc func;
	private String[] argNames;
	
	public InternalFunc(IFunc func, String...parameters) {
		this.func = func;
		this.argNames = parameters;
	}
	
	public InternalFunc(IFunc func) {
		this.func = func;
		this.argNames = new String[0];
	}

	@Override
	public EvalResult execute(VM vm, Val _this, Arguments args) {
		return new EvalResult(func.execute(vm, _this, args));
	}

	@Override
	public String getArgName(int i) {
		if(i > this.argNames.length)
			return "arg"+i;
		return this.argNames[i];
	}

	@Override
	public int getNamedArgs() {
		return this.argNames.length;
	}
	
	@Override
	public String toString() {
		return "func"+StringUtil.arrayToString("[", argNames, "]", ",");
	}
}
