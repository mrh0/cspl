package com.mrh0.qspl.type.var;

import com.mrh0.qspl.type.Val;

public class VarDef extends Var{

	public VarDef(Var v) {
		super(v);
	}
	
	public VarDef(String name, Val value) {
		super(name, value);
	}
	
	@Override
	public String toString() {
		return "("+getName()+"="+get()+")";
	}
	
	@Override
	public boolean isDefinition() {
		return true;
	}
}
