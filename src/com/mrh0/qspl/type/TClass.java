package com.mrh0.qspl.type;

public class TClass implements Val{

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public Val duplicate() {
		return this;
	}

	@Override
	public boolean booleanValue() {
		return true;
	}

	@Override
	public String getTypeName() {
		return "generic_class";
	}

	@Override
	public Object getValue() {
		return this;
	}

}
