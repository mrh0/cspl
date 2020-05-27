package com.mrh0.qspl.type.iterator;

import java.util.Iterator;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.var.Var;

public interface IKeyIterable {
	public static IKeyIterable from(Val v) {
		if(v instanceof IKeyIterable)
			return (IKeyIterable)v;
		if(v.isVariable() && v.isKeyIterable())
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to KeyIterable.");
		return null;
	}
	
	public Iterator<Val> keyIterator();
}
