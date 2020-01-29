package com.mrh0.qspl.type;

public class TArray implements Val{

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public Val duplicate() {
		return null;
	}

	@Override
	public boolean booleanValue() {
		return false;
	}

	@Override
	public String getTypeName() {
		return "array";
	}

	@Override
	public Object getValue() {
		return null;
	}

}
