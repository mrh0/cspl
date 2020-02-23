package com.mrh0.qspl.vm.extension;

public interface Module {
	public String getName();
	public String getAuthor();
	public String getDescription();
	public void extend(ModuleScope ext);
}
