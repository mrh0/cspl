package com.mrh0.qspl.type;

public class TContainer implements Val{
	
	public enum ContainerType{
		LIST, MAP, MIXED
	}

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
		return null;
	}

	@Override
	public Object getValue() {
		return null;
	}
}
