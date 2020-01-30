package com.mrh0.qspl.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mrh0.qspl.util.StringUtil;

public class TContainer implements Val{
	
	private ArrayList<String> keyIndecies;
	private Map<String, Var> map;
	
	public TContainer() {
		keyIndecies = new ArrayList<String>();
		map = new HashMap<String, Var>();
	}
	
	public TContainer(Map<String, Var> map, ArrayList<String> keys) {
		this.keyIndecies = keys;
		this.map = map;
	}
	
	public enum ContainerType {
		LIST, MAP, MIXED, EMPTY
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
		return map.size() > 0;
	}

	@Override
	public String getTypeName() {
		return "container";
	}

	@Override
	public Object getValue() {
		return this;
	}
	
	@Override
	public Val add(Val v) {
		if(v.isVariable()) {
			Var var = (Var)v;
			map.put(var.getName(), new Var(var));
			if(!keyIndecies.contains(var.getName()))
				keyIndecies.add(var.getName());
		}
		else {
			map.put(keyIndecies.size()+"", new Var(keyIndecies.size()+"", v));
			keyIndecies.add(keyIndecies.size()+"");
		}
		return this;
	}
	
	public Val get(int i) {
		if(i >= map.size())
			return TUndefined.getInstance();
		return map.getOrDefault(keyIndecies.get(i<0?keyIndecies.size()+i:i), new ContainerSubstituteVar((i<0?keyIndecies.size()+i:i)+"", this));
	}
	
	public Val get(String key) {
		return map.getOrDefault(key, new ContainerSubstituteVar(key, this)); //return ref instead
	}
	
	@Override
	public Val accessor(Val...args) {
		if(args.length == 0) 
			return new TNumber(map.size());
		else if(args.length == 1) {
			if(args[0].isNumber()) {
				return get(TNumber.from(args[0]).integerValue());
			}
			else if(args[0].isString()) {
				return get(TString.from(args[0]).get());
			}
		}
		return TUndefined.getInstance();
	}
	
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("{");
		for(int i = 0; i < keyIndecies.size(); i++) {
			r.append(map.getOrDefault(keyIndecies.get(i), new Var(keyIndecies.get(i), TUndefined.getInstance())));
			if(i+1 < keyIndecies.size())
				r.append(", ");
		}
		r.append("}");
		return r.toString();
	}
}
