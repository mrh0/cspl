package com.mrh0.qspl.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.iterator.IIterable;
import com.mrh0.qspl.type.iterator.IKeyIterable;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.type.var.ContainerSubstituteVarIndex;
import com.mrh0.qspl.type.var.ContainerSubstituteVarKey;
import com.mrh0.qspl.type.var.Var;
import com.mrh0.qspl.util.StringUtil;

public class TContainer implements Val, IIterable, IKeyIterable{
	
	private final ArrayList<String> keyIndecies;
	private final Map<String, Val> map;
	
	public TContainer() {
		keyIndecies = new ArrayList<String>();
		map = new HashMap<String, Val>();
	}
	
	@Override
	public TAtom getTypeAtom() {
		return TAtom.get("object");
	}
	
	public TContainer(Map<String, Val> map, ArrayList<String> keys) {
		this.keyIndecies = keys;
		this.map = map;
	}
	
	public TContainer(Var...values) {
		this.keyIndecies = new ArrayList<String>();
		this.map = new HashMap<String, Val>();
		for(Var v : values) {
			keyIndecies.add(v.getName());
			map.put(v.getName(), v);
		}
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
	
	public int size() {
		return keyIndecies.size();
	}
	
	public void put(Val v) {
		if(v.isDefinition()) {
			Var var = (Var)v;
			map.put(var.getName(), var.get());
			if(!keyIndecies.contains(var.getName()))
				keyIndecies.add(var.getName());
		}
		else if(v.isVariable()) {
			Var var = (Var)v;
			map.put(var.getName(), TUndefined.getInstance());
			if(!keyIndecies.contains(var.getName()))
				keyIndecies.add(var.getName());
		}
		else {
			map.put(keyIndecies.size()+"", new Var(keyIndecies.size()+"", v));
			keyIndecies.add(keyIndecies.size()+"");
		}
	}
	
	public void put(String name, Val v) {
		map.put(name, v);
		if(!keyIndecies.contains(name))
			keyIndecies.add(name);
	}
	
	/*public Val get(int i) {
		if(i >= map.size())
			return TUndefined.getInstance();
		return map.getOrDefault(keyIndecies.get(i<0?keyIndecies.size()+i:i), new ContainerSubstituteVar((i<0?keyIndecies.size()+i:i)+"", this));
	}
	
	public Val get(String key) {
		return new ContainerSubstituteVar(map.getOrDefault(key, new ContainerSubstituteVar(key, this)), this); //return ref instead
	}*/
	
	public Val get(String key) {
		return map.getOrDefault(key, TUndefined.getInstance());
	}
	
	public Val get(int i) {
		String key = keyIndecies.get(i<0?keyIndecies.size()+i:i);
		return map.getOrDefault(key, TUndefined.getInstance());
	}
	
	@Override
	public Val accessor(List<Val> args) {
		if(args.size() == 0) 
			return TNumber.create(map.size());
		else if(args.size() == 1) {
			if(args.get(0).isNumber()) {
				return new ContainerSubstituteVarIndex(TNumber.from(args.get(0)).integerValue(), this);
			}
			else if(args.get(0).isString()) {
				return new ContainerSubstituteVarKey(TString.from(args.get(0)).get(), this);
			}
		}
		return TUndefined.getInstance();
	}
	
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("{");
		for(int i = 0; i < keyIndecies.size(); i++) {
			r.append(keyIndecies.get(i)+"="+map.getOrDefault(keyIndecies.get(i), TUndefined.getInstance()));
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
			for(int i = 0; i < keyIndecies.size(); i++) {
				map.put(keyIndecies.get(i), c.get(keyIndecies.get(i)));
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
	
	public void remove(Var v) {
		if(map.containsKey(v.getName())) {
			map.remove(v.getName());
			keyIndecies.remove(v.getName());
		}
	}
	
	public void remove(String key) {
		if(map.containsKey(key)) {
			map.remove(key);
			keyIndecies.remove(key);
		}
	}
	
	public void remove(int index) {
		if(map.containsKey(keyIndecies.get(index))) {
			map.remove(keyIndecies.get(index));
			keyIndecies.remove(index);
		}
	}
	
	public String varArrayString() {
		StringBuilder r = new StringBuilder();
		r.append("[");
		for(int i = 0; i < size(); i++) {
			Val v = map.get(keyIndecies.get(i));
			r.append(v.isUndefined()?keyIndecies.get(i):(keyIndecies.get(i)+"="+v.toString()));
			if(i+1 < size())
				r.append(", ");
		}
		r.append("]");
		return r.toString();
	}
	
	public Val is(Val v) {
		return new TNumber(TContainer.class.isInstance(v));
	}
	
	@Override
	public boolean equals(Val v) {
		return v == this;
	}
	
	public class TContainerIterator implements Iterator<Val> {
		private TContainer c;
		private Iterator<String> it;
		public TContainerIterator(TContainer c) {
			this.c = c;
			this.it = c.keyIndecies.iterator();
		}
		
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public Val next() {
			return c.get(it.next());
		}
	}
	
	public class TContainerKeyIterator implements Iterator<Val> {
		private Iterator<String> c;
		public TContainerKeyIterator(TContainer c) {
			this.c = c.keyIndecies.iterator();
		}
		
		@Override
		public boolean hasNext() {
			return c.hasNext();
		}

		@Override
		public Val next() {
			return new TString(c.next());
		}
	}

	@Override
	public Iterator<Val> iterator() {
		return new TContainerIterator(this);
	}

	@Override
	public Iterator<Val> keyIterator() {
		return new TContainerKeyIterator(this);
	}
	
	public JSONObject toJSON() {
		JSONObject o = new JSONObject();
		for(String key : map.keySet()) {
			Val vt = map.get(key);
			if(vt.isContainer())
				o.put(key, TContainer.from(vt).toJSON());
			else if(vt.isArray())
				o.put(key, TArray.from(vt).toJSON());
			else if(vt.isNumber())
				o.put(key, TNumber.from(vt).get());
			else if(vt.isString())
				o.put(key, TString.from(vt).get());
			else
				o.put(key, vt.toString());
		}
		return o;
	}
	
	public static TContainer fromJSON(JSONObject j) {
		TContainer o = new TContainer();
		for(String key : j.keySet()) {
			Object i = j.get(key);
			if(i instanceof JSONObject)
				o.put(key, TContainer.fromJSON((JSONObject)i));
			else if(i instanceof JSONArray)
				o.put(key, TArray.fromJSON((JSONArray)i));
			else if(i instanceof Double)
				o.put(key, new TNumber((double)i));
			else if(i instanceof Integer)
				o.put(key, new TNumber((int)i));
			else
				o.put(key, new TString(i.toString()));
		}
		return o;
	}
}
