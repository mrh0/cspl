package com.mrh0.qspl.type.var;

import java.util.ArrayList;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.vm.VM;

public class Var implements Val{
	
	private Val value;
	private String name;
	private boolean locked = false;

	public Var(String name, Val value) {
		this.name = name;
		this.value = value.isVariable()?((Var)value).get():value;
		if(name.toUpperCase().equals(name) && name.charAt(0) > '9' && name.charAt(0) < '0') //!name.matches("[a-z]+|\\d+")
			this.locked = true;
	}
	
	public Var(String name, Val value, boolean locked) {
		this.name = name;
		this.value = value.isVariable()?((Var)value).get():value;
		this.locked = locked;
	}
	
	public Var(Var v) {
		this.name = v.getName();
		this.value = v.value;
		this.locked = v.locked;
	}
	
	public void delete(VM vm) {
		vm.delVariable(this);
	}
	
	@Override
	public String toString() {
		return value.toString();//"("+name+"="+value+")";
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
	public boolean isDefinition() {
		return false;
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
	public int compare(Val v) {
		return value.compare(v);
	}
	
	@Override
	public boolean equals(Val v) {
		return value.equals(v);
	}
	
	@Override
	public double getRelativeValue(Val v) {
		return value.getRelativeValue(v);
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
		if(this.locked && !this.isUndefined()) {
			Console.g.err("Cannot assign a constant (" + this + ")");
			return new VarDef(this);
		}
		if(v.isVariable()) {
			value = ((Var)v).get();
			return new VarDef(this);
		}
		value = v;
		return new VarDef(this);
	}

	@Override
	public Object getValue() {
		return value.getValue();
	}
	
	@Override
	public Val accessor(ArrayList<Val> args) {
		return value.accessor(args);
	}
	
	public static Var from(Val v) {
		if(v.isVariable())
			return (Var)v;
		return null;
	}
}
