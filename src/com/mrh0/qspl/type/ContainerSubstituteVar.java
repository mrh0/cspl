package com.mrh0.qspl.type;

public class ContainerSubstituteVar extends Var{

	private Val source;
	
	public ContainerSubstituteVar(String name, Val source) {
		super(name, TUndefined.getInstance());
		this.source = source;
	}
	
	@Override
	public Val assign(Val v) {
		source.add(new Var(this.getName(), v));
		return v;
	}
	
	public Val getSource() {
		return this.source;
	}
}
