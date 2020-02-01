package com.mrh0.qspl.io.console;

import java.io.PrintStream;
import com.mrh0.qspl.vm.scope.Scope;

public class Console {
	public static Console g;
	
	public int currentLine = 0;
	private Scope currentScope;
	
	public static final PrintStream defaultLogStream = System.out;
	public static final PrintStream defaultErrStream = System.err;
	private PrintStream logStream;
	private PrintStream errStream;
	
	private String getLogPrefix() {
		return "[OUT:"+currentLine+""+getScope()+"]: ";
	}
	
	private String logSurfix = "";
	private String logSeperator = ",";
	
	private String getErrPrefix() {
		return "[ERR:"+currentLine+""+getScope()+"]: ";
	}
	
	private String errSurfix = "";
	private String errSeperator = ",";
	
	public Console(PrintStream stream) {
		this.logStream = stream;
		this.errStream = stream;
	}
	
	public Console(PrintStream log, PrintStream err) {
		this.logStream = log;
		this.errStream = err;
	}
	
	public Console() {
		this.logStream = defaultLogStream;
		this.errStream = defaultErrStream;
	}
	
	public void log(Object...s) {
		this.logStream.print(getLogPrefix());
		for(int i = 0; i < s.length; i++) {
			this.logStream.print(s[i]);
			if(i+1 < s.length)
				this.logStream.print(logSeperator);
		}
		this.logStream.println(logSurfix);
	}
	
	public void err(Object...s) {
		this.errStream.print(getErrPrefix());
		for(int i = 0; i < s.length; i++) {
			this.errStream.print(s[i]);
			if(i+1 < s.length)
				this.errStream.print(errSeperator);
		}
		this.errStream.println(errSurfix);
	}
	
	public void setCurrentLine(int i) {
		currentLine = i;
	}
	
	public void setScope(Scope s) {
		this.currentScope = s;
	}
	
	private String getScope() {
		return currentScope == null?"":":"+currentScope.getName();
	}
	
	public static void setGlobal(Console c) {
		g = c;
	}
	
	public void setGlobal() {
		g = this;
	}
}
