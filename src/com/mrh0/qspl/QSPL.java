package com.mrh0.qspl;

import com.mrh0.qspl.interpreter.Interpreter;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.io.file.Read;
import com.mrh0.qspl.tokenizer.Tokenizer;
import com.mrh0.qspl.util.TimeUtil;
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
		
		long start2 = TimeUtil.getMilis();
		int i=0;
		int x = 0;
		while(i < 100000000) {
			x = 5 * 6 + i;
			i++;
		}
		System.out.println(x);
		System.out.println("runtime: " + TimeUtil.getDifMilis(start2) + "ms");
		
		long start = TimeUtil.getMilis();
		interp = new Interpreter(vm, tokens);
		interp.eval();
		System.out.println("runtime: " + TimeUtil.getDifMilis(start) + "ms");
		
		//System.out.print(tokens.toString());
	}
}
