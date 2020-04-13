package com.mrh0.qspl.vm.module;

import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.var.Var;
import com.mrh0.qspl.type.var.VarDef;

public class ModuleScope {
	private TContainer exports;
	
	protected ModuleScope() {
		exports = new TContainer();
	}
	
	public void export(String name, Val value) {
		exports.put(new VarDef(name, value));
	}
	
	protected TContainer getExports() {
		return exports;
	}
}
