package com.mrh0.qspl.type;

import com.mrh0.qspl.io.console.Console;

public interface Val {
	public int getType();
	public Val duplicate();
	public boolean booleanValue();
	public String getTypeName();
	public Object getValue();
	
	//Defaults:
	public default boolean isUndefined() {
		return false;
	}
	
	public default boolean isNumber() {
		return false;
	}
	
	public default boolean isString() {
		return false;
	}
	
	public default boolean isObject() {
		return false;
	}
	
	public default boolean isArray() {
		return false;
	}
	
	public default boolean isFunction() {
		return false;
	}
	
	public default boolean isVariable() {
		return false;
	}
	
	//Arithmetic Operations:
	public default Val add(Val v) {
		Console.g.err("Cannot preform operation add on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	public default Val sub(Val v) {
		Console.g.err("Cannot preform operation subtract on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	public default Val multi(Val v) {
		Console.g.err("Cannot preform operation multiply on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	public default Val div(Val v) {
		Console.g.err("Cannot preform operation divide on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	public default Val mod(Val v) {
		Console.g.err("Cannot preform operation modulo on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default Val increment(Val v) {
		Console.g.err("Cannot preform operation increment on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	public default Val decrement(Val v) {
		Console.g.err("Cannot preform operation decrement on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default Val assign(Val v) {
		Console.g.err("Cannot preform operation assign on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default Val accessor() {
		return accessor(new Val[0]);
	}
	
	public default Val accessor(Val arg0) {
		Val[] a = {arg0};
		return accessor(a) ;
	}
	
	public default Val accessor(Val...args) {
		Console.g.err("Cannot preform operation accessor with " + args.length + (args.length==1?" argument on ":" arguments on ") + this.getTypeName());
		return TUndefined.getInstance();
	}
}
