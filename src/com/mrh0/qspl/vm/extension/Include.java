package com.mrh0.qspl.vm.extension;

import java.net.MalformedURLException;

import com.mrh0.qspl.interpreter.Interpreter;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.io.file.FileIO;
import com.mrh0.qspl.tokenizer.Tokenizer;
import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.var.Var;
import com.mrh0.qspl.vm.VM;

public class Include {
	public static TContainer include(String path) {
		if(path.endsWith(".qs") || path.endsWith(".qspl")) {
			return fromFile(path);
		}
		if(path.endsWith(".jar")) {
			return fromJar(path);
		}
		Console.g.err("Unknown import type: '" + path + "'.");
		return new TContainer();
	}
	
	public static TContainer fromFile(String path) {
		String code = FileIO.readFromFile(path);
		if(code.length() == 0) 
			return new TContainer();
		Tokenizer tokens = new Tokenizer();
		tokens.insertCode(code);
		VM vm = new VM();
		Interpreter interp = new Interpreter(vm, tokens);
		
		
		
		vm.setVariable(new Var("this", TUndefined.getInstance()));
		//Common.defineCommons(vm.getCurrentScope());
		interp.eval();
		TContainer r = vm.getExports();
		return r;
	}
	
	public static TContainer fromJar(String path) {
		String[] args = path.split("@");
		if(args.length != 2) {
			Console.g.err("Invalid jar import path: '" + path + "'.");
			return new TContainer();
		}
		
		try {
			return fromExtension(JarLoader.getExtension(args[1], args[0]));
		} catch (ClassNotFoundException | MalformedURLException | InstantiationException | IllegalAccessException e) {
			Console.g.err("Error occured with loading extension jar: '" + path + "'.");
			e.printStackTrace();
		}
		return new TContainer();
	}
	
	public static TContainer fromExtension(Module ext) {
		ModuleScope es = new ModuleScope();
		ext.extend(es);
		return es.getExports();
	}
}
