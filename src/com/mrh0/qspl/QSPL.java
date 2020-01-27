package com.mrh0.qspl;

import java.util.ArrayList;

import com.mrh0.qspl.interpreter.Interpreter;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.io.file.Read;
import com.mrh0.qspl.tokenizer.Tokenizer;
import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.vm.VM;

public class QSPL {
	private VM vm;
	private Tokenizer tokens;
	private Interpreter interp;
	
	public QSPL() {
		new Console().setGlobal();
		tokens = new Tokenizer();
		tokens.insertCode(Read.fromFile("C:\\MRHLang\\qspl4.qs"));
		
		
		vm = new VM();
		interp = new Interpreter(vm, tokens);
		interp.eval();
		
		System.out.print(tokens.toString());
	}
}
