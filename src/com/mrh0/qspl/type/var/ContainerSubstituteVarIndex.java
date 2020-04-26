package com.mrh0.qspl.type.var;

import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.vm.VM;

public class ContainerSubstituteVarIndex extends Var implements ISubstituteVar{

	private TContainer source;
	private int index = 0;
	
	public ContainerSubstituteVarIndex(int index, TContainer source) {
		super(index+"", TUndefined.getInstance());
		this.source = source;
		this.index = index;
	}
	
	@Override
	public Val assign(Val v) {
		source.put(getName(), Val.prim(v));
		return v;
	}
	
	public Val getSource() {
		return this.source;
	}
	
	public void delete(VM vm) {
		this.source.remove(index);
	}
	
	@Override
	public Val get() {
		return source.get(index);
	}
	
	@Override
	public boolean isSubstitute() {
		return true;
	}
}
