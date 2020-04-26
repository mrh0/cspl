package com.mrh0.qspl.type.var;

import java.util.List;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.TAtom;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.vm.VM;

public class Var implements Val{
	
	private Val value;
	private String name;
	private boolean locked = false;

	public Var(String name, Val value) {
		this.name = name;
		set(value.isVariable()?((Var)value).get():value);
		if(name.toUpperCase().equals(name) && name.charAt(0) > '9' && name.charAt(0) < '0') //!name.matches("[a-z]+|\\d+")
			this.locked = true;
	}
	
	@Override
	public TAtom getTypeAtom() {
		return value.getTypeAtom();
	}
	
	public Var(String name, Val value, boolean locked) {
		this.name = name;
		set(value.isVariable()?((Var)value).get():value);
		this.locked = locked;
	}
	
	public Var(Var v) {
		this.name = v.getName();
		set(v.value);
		this.locked = v.locked;
	}
	
	public void delete(VM vm) {
		vm.delVariable(this);
	}
	
	@Override
	public String toString() {
		return get().toString();//"("+name+"="+value+")";
	}

	@Override
	public int getType() {
		return get().getType();
	}

	@Override
	public Val duplicate() {
		return new Var(name, get().duplicate());
	}

	@Override
	public boolean booleanValue() {
		return get().booleanValue();
	}
	
	@Override
	public boolean isArray() {
		return get().isArray();
	}
	
	@Override
	public boolean isFunction() {
		return get().isFunction();
	}
	
	@Override
	public boolean isNumber() {
		return get().isNumber();
	}
	
	@Override
	public boolean isObject() {
		return get().isObject();
	}
	
	@Override
	public boolean isString() {
		return get().isString();
	}

	@Override
	public boolean isUndefined() {
		return get().isUndefined();
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
	public boolean isIterable() {
		return get().isIterable();
	}
	
	@Override
	public String getTypeName() {
		return "var("+name+": "+get().getTypeName()+")";
	}
	
	@Override
	public Val add(Val v) {
		return get().add(v);
	}
	
	@Override
	public Val sub(Val v) {
		return get().sub(v);
	}
	
	@Override
	public Val multi(Val v) {
		return get().multi(v);
	}
	
	@Override
	public Val div(Val v) {
		return get().div(v);
	}
	
	@Override
	public Val mod(Val v) {
		return get().mod(v);
	}
	
	@Override
	public int compare(Val v) {
		return get().compare(v);
	}
	
	@Override
	public boolean equals(Val v) {
		return get().equals(v);
	}
	
	@Override
	public double getRelativeValue(Val v) {
		return get().getRelativeValue(v);
	}
	
	@Override
	public Val increment(Val v) {
		if(!get().isNumber())
			return TUndefined.getInstance();
		return set(get().add(v));
	}
	
	@Override
	public Val decrement(Val v) {
		if(!get().isNumber())
			return TUndefined.getInstance();
		return set(get().sub(v));
	}
	
	@Override
	public Val approx() {
		return get().approx();
	}
	
	public Val get() {
		return value;
	}
	
	private Val set(Val value) {
		return this.value = value;
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
			set(((Var)v).get());
			return new VarDef(this);
		}
		set(v);
		return new VarDef(this);
	}

	@Override
	public Object getValue() {
		return get().getValue();
	}
	
	@Override
	public Val accessor(List<Val> args) {
		return get().accessor(args);
	}
	
	public static Var from(Val v) {
		if(v.isVariable())
			return (Var)v;
		return null;
	}
	
	public Val is(Val v) {
		return get().is(v);
	}
}
