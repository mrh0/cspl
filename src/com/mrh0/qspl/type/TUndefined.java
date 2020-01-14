package com.mrh0.qspl.type;

public class TUndefined implements Val{
	
	private static TUndefined instance = null;
	
	public TUndefined() {
		if(instance == null)
			instance = this;
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
	public boolean isUndefined() {
		return true;
	}
	
	@Override
	public String getTypeName() {
		return "undefined";
	}
	
	@Override
	public String toString() {
		return "undefined";
	}

	public static TUndefined getInstance() {
		if(instance == null)
			return new TUndefined();
		return instance;
	}
}
