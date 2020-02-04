package com.mrh0.qspl.vm.extension;

import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.Var;

public class ExtensionScope {
	private TContainer exports;
	
	protected ExtensionScope() {
		exports = new TContainer();
	}
	
	public void export(String name, Val value) {
		exports.put(new Var(name, value));
	}
	
	protected TContainer getExports() {
		return exports;
	}
}
