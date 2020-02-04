package com.mrh0.qspl.vm;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;

import com.mrh0.qspl.interpreter.evaluator.EvalResult;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.Var;
import com.mrh0.qspl.type.func.IFunc;
import com.mrh0.qspl.type.func.InternalFunc;
import com.mrh0.qspl.vm.scope.Scope;
import com.mrh0.qspl.vm.scope.Scope.Policy;

public class VM {
	
	private Stack<Scope> scopeStack;
	private TContainer exports;
	
	public VM() {
		scopeStack = new Stack<Scope>();
		scopeStack.add(new Scope("origin"));
		exports = new TContainer();
		
		IFunc f = (VM vm, Val _this, ArrayList<Val> args) -> {
			System.out.println("Hello!");
			return new EvalResult();
		};
		
		setVariable(new Var("test", new InternalFunc(f)));
	}
	
	public Scope getCurrentScope() {
		if(scopeStack.isEmpty())
			Console.g.err("Empty scope stack!");
		return scopeStack.peek();
	}
	
	public Var getVariable(String name) {
		ListIterator<Scope> it = scopeStack.listIterator(scopeStack.size());
		Scope s = null;
		while(it.hasPrevious()) {
			s = it.previous();
			if(s.getPolicy() == Policy.DontReadDown)
				return s.getVar(name);
			if(s.hasVar(name)) {
				return s.getVar(name);
			}
		}
		if(s == null)
			Console.g.err("Empty scope stack!");
		return s.getVar(name);
	}
	
	public Var defVariable(String name) {
		ListIterator<Scope> it = scopeStack.listIterator(scopeStack.size());
		Scope s = null;
		while(it.hasPrevious()) {
			s = it.previous();
			if(s.getPolicy() == Policy.DontReadDown)
				return s.getVar(name, true);
			if(s.hasVar(name)) {
				return s.getVar(name, true);
			}
		}
		if(s == null)
			Console.g.err("Empty scope stack!");
		return s.getVar(name, true);
	}
	
	public void delVariable(Var v) {
		
	}
	
	public Var setVariable(Var var) {
		Var v = defVariable(var.getName());
		v.assign(var.get());
		return v;
	}
	
	public void setVariables(TContainer c) {
		for(String key : c.getKeys()) {
			Var v = defVariable(key);
			v.assign(c.get(key));
		}
	}
	
	public void pushScope(Scope scope) {
		scopeStack.push(scope);
	}
	
	public Scope popScope() {
		if(scopeStack.isEmpty())
			Console.g.err("Empty scope stack!");
		return scopeStack.pop();
	}
	
	public TContainer getExports() {
		return exports;
	}
}
