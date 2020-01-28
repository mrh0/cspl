package com.mrh0.qspl.vm;

import java.util.ListIterator;
import java.util.Stack;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.Var;
import com.mrh0.qspl.vm.scope.Scope;
import com.mrh0.qspl.vm.scope.Scope.Policy;

public class VM {
	
	private Stack<Scope> scopeStack;
	
	public VM() {
		scopeStack = new Stack<Scope>();
		scopeStack.add(new Scope("origin"));
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
				//System.out.println(name + " is Defined");
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
				//System.out.println(name + " is Defined");
				return s.getVar(name, true);
			}
		}
		if(s == null)
			Console.g.err("Empty scope stack!");
		return s.getVar(name, true);
	}
	
	public Var setVariable(Var var) {
		Var v = defVariable(var.getName());
		v.assign(var.get());
		return v;
	}
	
	public void pushScope(Scope scope) {
		scopeStack.push(scope);
	}
	
	public Scope popScope() {
		if(scopeStack.isEmpty())
			Console.g.err("Empty scope stack!");
		return scopeStack.pop();
	}
}
