package com.mrh0.qspl.type.func;

import java.util.ArrayList;

import com.mrh0.qspl.interpreter.evaluator.EvalResult;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.vm.VM;

public interface IFunc {
	public EvalResult execute(VM vm, Val _this, ArrayList<Val> args);
}
