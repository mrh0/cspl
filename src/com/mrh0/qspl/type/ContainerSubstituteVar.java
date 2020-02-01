package com.mrh0.qspl.type;

public class ContainerSubstituteVar extends Var{

	private TContainer source;
	
	public ContainerSubstituteVar(String name, TContainer source) {
		super(name, TUndefined.getInstance());
		this.source = source;
	}
	
	@Override
	public Val assign(Val v) {
		source.put(new Var(this.getName(), v));
		return v;
	}
	
	public Val getSource() {
		return this.source;
	}
}
