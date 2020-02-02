package com.mrh0.qspl.type.func;

import java.util.ArrayList;

import com.mrh0.qspl.interpreter.evaluator.EvalResult;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.vm.VM;

public abstract class TFunc implements Val{

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public Val duplicate() {
		return this;
	}

	@Override
	public boolean booleanValue() {
		return true;
	}

	@Override
	public String getTypeName() {
		return "func";
	}

	@Override
	public Object getValue() {
		return this;
	}

	public abstract EvalResult execute(VM vm, Val _this, ArrayList<Val> args);
}
