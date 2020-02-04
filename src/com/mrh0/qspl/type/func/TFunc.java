package com.mrh0.qspl.type.func;

import java.util.ArrayList;

import com.mrh0.qspl.interpreter.evaluator.EvalResult;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.TNumber;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.Var;
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
	
	@Override
	public boolean isFunction() {
		return true;
	}

	public abstract EvalResult execute(VM vm, Val _this, ArrayList<Val> args);
	
	public static TFunc from(Val v) {
		if(v instanceof TFunc)
			return (TFunc)v;
		if(v instanceof Var && v.isFunction())
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to function.");
		return null;
	}
}
