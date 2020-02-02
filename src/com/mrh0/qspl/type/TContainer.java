package com.mrh0.qspl.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mrh0.qspl.io.console.Console;
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
	public boolean isContainer() {
		return true;
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
		put(v);
		return this;
	}
	
	public void put(Val v) {
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
	public Val accessor(ArrayList<Val> args) {
		if(args.size() == 0) 
			return new TNumber(map.size());
		else if(args.size() == 1) {
			if(args.get(0).isNumber()) {
				return get(TNumber.from(args.get(0)).integerValue());
			}
			else if(args.get(0).isString()) {
				return get(TString.from(args.get(0)).get());
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
	
	@Override
	public Val assign(Val v) {
		if(v.isContainer()) {
			TContainer c = TContainer.from(v);
			for(String key : keyIndecies) {
				this.put(c.get(key));
			}
			return this;
		}
		return Val.super.assign(v);
	}
	
	public static TContainer from(Val v) {
		if(v instanceof TContainer)
			return (TContainer)v;
		if(v instanceof Var && v.isContainer())
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to container.");
		return null;
	}
	
	public ArrayList<String> getKeys(){
		return keyIndecies;
	}
}
