package com.mrh0.qspl.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TContainer implements Val{
	
	private ArrayList<String> keyIndecies;
	private Map<String, Val> map;
	private ContainerType type;
	
	public TContainer() {
		keyIndecies = new ArrayList<String>();
		map = new HashMap<String, Val>();
	}
	
	public TContainer(Map<String, Val> map, ArrayList<String> keys) {
		this.keyIndecies = keys;
		this.map = map;
	}
	
	public enum ContainerType {
		LIST, MAP, MIXED, EMPTY
	}
	
	private void decideType() {
		
	}

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public Val duplicate() {
		return TUndefined.getInstance();
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
		return map;
	}
	
	@Override
	public Val add(Val v) {
		if(v.isVariable()) {
			Var var = (Var)v;
			map.put(var.getName(), var.get());
			if(!keyIndecies.contains(var.getName()))
				keyIndecies.add(var.getName());
		}
		else {
			map.put(keyIndecies.size()+"", v);
			keyIndecies.add(keyIndecies.size()+"");
		}
		return this;
	}
}
