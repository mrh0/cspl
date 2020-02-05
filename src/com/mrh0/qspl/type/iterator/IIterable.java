package com.mrh0.qspl.type.iterator;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.var.Var;

public interface IIterable extends Iterable<Val>{
	public static IIterable from(Val v) {
		if(v instanceof IIterable)
			return (IIterable)v;
		if(v instanceof Var && v.isIterable())
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to Iterable.");
		return null;
	}
}
