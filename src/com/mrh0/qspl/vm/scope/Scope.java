package com.mrh0.qspl.vm.scope;

import java.util.HashMap;
import java.util.Map;

import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.Var;

public class Scope {
	private Map<String, Var> variables;
	private String name;
	private Policy policy = Policy.Open;
	
	public enum Policy{
		Open,
		DontReadDown
	}
	
	public Scope(String name) {
		this.variables = new HashMap<String, Var>();
		this.name = name;
	}
	
	public Scope(String name, Policy policy) {
		this.variables = new HashMap<String, Var>();
		this.name = name;
		this.policy = policy;
	}
	
	public Scope(Policy policy) {
		this.variables = new HashMap<String, Var>();
		this.name = policy+":anonymous:scope";
		this.policy = policy;
	}
	
	public Scope() {
		this.variables = new HashMap<String, Var>();
		this.name = "anonymous:scope";
	}
	
	public String getName() {
		return name;
	}
	
	@Deprecated
	public Var setVar(String name, Val value) {
		return setVar(new Var(name, value));
	}
	
	public Var setVar(Var v) {
		//System.out.println("Defining: " + v.getName() + ": " + v.get());
		variables.put(v.getName(), v);
		return v;
	}
	
	public Var getVar(String name) {
		//if(!hasVar(name))
		//	setVar(new Var(name, TUndefined.getInstance()));
		return variables.getOrDefault(name, new Var(name, TUndefined.getInstance()));
	}
	
	public Var getVar(String name, boolean def) {
		if(!hasVar(name) && def)
			setVar(new Var(name, TUndefined.getInstance()));
		return variables.getOrDefault(name, new Var(name, TUndefined.getInstance()));
	}
	
	public boolean hasVar(String name) {
		return variables.containsKey(name);
	}
	
	public boolean isIsolated() {
		return policy != Policy.Open;
	}
	
	public Policy getPolicy() {
		return policy;
	}
}
