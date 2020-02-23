package com.mrh0.qspl.type.func;

import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.vm.VM;

public interface IFunc {
	public Val execute(VM vm, Val _this, Arguments args);
}
