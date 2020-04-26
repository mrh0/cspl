package com.mrh0.qspl.type.var;

import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.vm.VM;

public interface ISubstituteVar {
	public Val getSource();
	public void delete(VM vm);
}
