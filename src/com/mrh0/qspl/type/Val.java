package com.mrh0.qspl.type;

import java.util.ArrayList;
import java.util.List;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.iterator.IIterable;
import com.mrh0.qspl.type.number.TNumber;

public interface Val {
	@Deprecated
	public default int getType() {return 0;};
	@Deprecated
	public default Val duplicate() {return this;};
	public boolean booleanValue();
	public String getTypeName();
	@Deprecated
	public default Object getValue() {
		return this;
	}
	
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
	
	public default boolean isContainer() {
		return false;
	}
	
	public default boolean isAtom() {
		return false;
	}
	
	public default boolean isIterable() {
		return this instanceof IIterable;
	}
	
	public default boolean isDefinition() {
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
	public default Val pow(Val v) {
		Console.g.err("Cannot preform operation powerof on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default Val as(Val v) {
		if(v.isString())
			return new TString(this.toString());
		Console.g.err("Cannot preform operation as on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default Val is(Val v) {
		Console.g.err("Cannot preform operation is on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default Val logicalAnd(Val v) {
		return TNumber.create(this.booleanValue() && v.booleanValue());
	}
	public default Val logicalOr(Val v) {
		return this.booleanValue()?this:v;
	}
	public default Val logicalXor(Val v) {
		return this.booleanValue()?(v.booleanValue()?TNumber.create(false):this):v;
	}
	public default Val logicalNot() {
		return TNumber.create(!this.booleanValue());
	}
	public default Val approx() {
		Console.g.err("Cannot preform operation approximate on " + this.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default Val bitwiseAnd(Val v) {
		Console.g.err("Cannot preform operation bitwise-and on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	public default Val bitwiseOr(Val v) {
		Console.g.err("Cannot preform operation bitwise-or on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	public default Val bitwiseXor(Val v) {
		Console.g.err("Cannot preform operation bitwise-xor on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default Val shiftLeft(Val v) {
		Console.g.err("Cannot preform operation shift-left on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	public default Val shiftRight(Val v) {
		Console.g.err("Cannot preform operation shift-right on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	public default Val rotateLeft(Val v) {
		Console.g.err("Cannot preform operation rotate-left on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	public default Val rotateRight(Val v) {
		Console.g.err("Cannot preform operation rotate-right on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default Val push(Val v) {
		Console.g.err("Cannot preform operation push on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default Val pull(Val v) {
		Console.g.err("Cannot preform operation pull on " + this.getTypeName() + " with " + v.getTypeName());
		return TUndefined.getInstance();
	}
	
	public default boolean equals(Val v) {
		//Console.g.err("Default equals operation on " + this.getTypeName() + " with " + v.getTypeName());
		return equals((Object)v);
	}
	
	public default int compare(Val v) {
		//Console.g.log("Default compare operation on " + this.getTypeName() + " with " + v.getTypeName());
		if(this.equals(v))
			return 0;
		return getRelativeValue(v) < v.getRelativeValue(this)?-1:1; //<:-1 =:0 >:1 !=:MIN
	}
	
	public default double getRelativeValue(Val v) {
		return Integer.MIN_VALUE;
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
	
	public default Val accessor(Val arg) {
		ArrayList<Val> a = new ArrayList<Val>();
		a.add(arg);
		return accessor(a);
	}
	
	public default Val accessor(List<Val> args) {
		Console.g.err("Cannot preform operation accessor with " + args.size() + (args.size()==1?" argument on ":" arguments on ") + this.getTypeName());
		return TUndefined.getInstance();
	}
}
