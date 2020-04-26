package com.mrh0.qspl.vm;

import java.util.ListIterator;
import java.util.Stack;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.TArray;
import com.mrh0.qspl.type.TAtom;
import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.var.Var;
import com.mrh0.qspl.vm.queue.EvalQueue;
import com.mrh0.qspl.vm.scope.Scope;
import com.mrh0.qspl.vm.scope.Scope.Policy;

public class VM {
	
	private Stack<Scope> scopeStack;
	private TContainer exports;
	private Scope root;
	private Val previous;
	private EvalQueue queue;
	private Module currentModule;
	
	public VM() {
		queue = new EvalQueue(this);
		scopeStack = new Stack<Scope>();
		root = new Scope("root");
		scopeStack.add(root);
		exports = new TContainer();
		TAtom.init();
	}
	
	public Scope getCurrentScope() {
		if(scopeStack.isEmpty())
			Console.g.err("Empty scope stack!");
		return scopeStack.peek();
	}
	
	public Var getVariable(String name) {
		if(name.equals("_"))
			return new Var("_", TUndefined.getInstance());
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
		if(name.equals("_"))
			return new Var("_", TUndefined.getInstance());
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
		if(var.getName().equals("_"))
			return new Var("_", TUndefined.getInstance());
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
	
	public void setVariables(TContainer c, Arguments a) {
		TArray args = new TArray();
		for(int i = 0; i < Math.max(a.size(), c.size()); i++) {
			Val r = TUndefined.getInstance();
			if(i < c.size()) {
				Val d = c.get(i);
				r = a.get(i, d);
				if(r.isUndefined())
					r = d;
			}
			if(i<c.size()) {
				Var v = defVariable(c.getKeys().get(i));
				v.assign(r);
			}
			args.add(r);
		}
		Var v = defVariable("arguments");
		v.assign(args);
	}
	
	public void pushScope(Scope scope) {
		scopeStack.push(scope);
	}
	
	public Scope popScope() {
		if(scopeStack.isEmpty())
			Console.g.err("Empty scope stack!");
		return scopeStack.pop();
	}
	
	public void include(TContainer c) {
		for(String key : c.getKeys()) {
			root.setVar(new Var(key, c.get(key)));
		}
	}
	
	public TContainer getExports() {
		return exports;
	}
	
	public void setPreviousResult(Val v) {
		this.previous = v;
	}
	
	public Val getPreviousResult() {
		return this.previous;
	}
	
	 public void startQueue() {
		 queue.start();
	 }
	 
	 public void stopQueue() {
		 queue.stop();
	 }
}
