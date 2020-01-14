package com.mrh0.qspl.type;

public class Var implements Val{
	
	private Val value;

	@Override
	public int getType() {
		return value.getType();
	}

	@Override
	public Val duplicate() {
		return value.duplicate();
	}

	@Override
	public boolean booleanValue() {
		return value.booleanValue();
	}

	@Override
	public boolean isUndefined() {
		return value.isUndefined();
	}

	@Override
	public boolean isVariable() {
		return true;
	}
	
	@Override
	public String getTypeName() {
		return "var("+value.getTypeName()+")";
	}
	
	@Override
	public Val add(Val v) {
		return value.add(v);
	}
	
	@Override
	public Val sub(Val v) {
		return Val.super.sub(v);
	}
	
	@Override
	public Val multi(Val v) {
		return value.multi(v);
	}
	
	@Override
	public Val div(Val v) {
		return value.div(v);
	}
	
	@Override
	public Val mod(Val v) {
		return value.mod(v);
	}
	
	@Override
	public Val increment(Val v) {
		if(!value.isNumber())
			return TUndefined.getInstance();
		return value.add(new TNumber(1));
	}
	
	@Override
	public Val decrement(Val v) {
		if(!value.isNumber())
			return TUndefined.getInstance();
		return value.sub(new TNumber(1));
	}
	
	public Val get() {
		return value;
	}
}
