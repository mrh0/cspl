package com.mrh0.qspl.type;


public class ArraySubstituteVar extends Var{
	private TArray source;
	private int index;
	
	public ArraySubstituteVar(int index, TArray source) {
		super(index+"", TUndefined.getInstance());
		this.source = source;
		this.index = index;
	}
	
	@Override
	public Val assign(Val v) {
		source.set(index, new Var(this.getName(), v));
		return v;
	}
	
	public Val getSource() {
		return this.source;
	}
}

