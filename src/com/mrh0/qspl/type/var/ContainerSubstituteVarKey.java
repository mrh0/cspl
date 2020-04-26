package com.mrh0.qspl.type.var;

import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.vm.VM;

public class ContainerSubstituteVarKey extends Var implements ISubstituteVar{

	private TContainer source;
	
	public ContainerSubstituteVarKey(String name, TContainer source) {
		super(name, TUndefined.getInstance());
		this.source = source;
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
		this.source.remove(getName());
	}
	
	@Override
	public Val get() {
		return source.get(getName());
	}
	
	@Override
	public boolean isSubstitute() {
		return true;
	}
}
