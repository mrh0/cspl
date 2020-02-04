package com.mrh0.qspl.vm.extension;

public interface Extension {
	public String getName();
	public String getAuthor();
	public String getDescription();
	public void extend(ExtensionScope ext);
}
