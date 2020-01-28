package com.mrh0.qspl.type;

public class Var implements Val{
	
	private Val value;
	private String name;

	public Var(String name, Val value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "var("+name+": "+value+")";
	}

	@Override
	public int getType() {
		return value.getType();
	}

	@Override
	public Val duplicate() {
		return new Var(name, value.duplicate());
	}

	@Override
	public boolean booleanValue() {
		return value.booleanValue();
	}
	
	@Override
	public boolean isArray() {
		return value.isArray();
	}
	
	@Override
	public boolean isFunction() {
		return value.isFunction();
	}
	
	@Override
	public boolean isNumber() {
		return value.isNumber();
	}
	
	@Override
	public boolean isObject() {
		return value.isObject();
	}
	
	@Override
	public boolean isString() {
		return value.isString();
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
		return "var("+name+": "+value.getTypeName()+")";
	}
	
	@Override
	public Val add(Val v) {
		return value.add(v);
	}
	
	@Override
	public Val sub(Val v) {
		return value.sub(v);
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
		return value = value.add(v);
	}
	
	@Override
	public Val decrement(Val v) {
		if(!value.isNumber())
			return TUndefined.getInstance();
		return value = value.sub(v);
	}
	
	public Val get() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Val assign(Val v) {
		//System.out.println("Assigning: " + v + " to: " + this);
		if(v.isVariable()) {
			value = ((Var)v).get();
			return this;
		}
		value = v;
		return this;
	}

	@Override
	public Object getValue() {
		return value.getValue();
	}
}
