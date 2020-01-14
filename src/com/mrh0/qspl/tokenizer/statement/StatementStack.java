package com.mrh0.qspl.tokenizer.statement;

import java.util.Stack;

import com.mrh0.qspl.tokenizer.Block;
import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.tokenizer.token.TokenBlock;
import com.mrh0.qspl.tokenizer.token.TokenType;

public class StatementStack {
	private Stack<StatementBuilder> stack;
	
	
	public StatementStack() {
		stack = new Stack<StatementBuilder>();
	}
	
	public void push(TokenType type, boolean terminates) {
		stack.push(new StatementBuilder(type, terminates));
	}
	
	public void push(TokenType type) {
		push(type, false);
	}
	
	public void feed(Token t) {
		stack.peek().feed(t);
	}
	
	private boolean term = false;
	public TokenBlock pop() {
		StatementBuilder sb = stack.pop();
		Block b = sb.makeBlock();
		term = sb.terminates();
		TokenBlock tb = new TokenBlock(sb.getBlockType(), b);
		if(!stack.isEmpty())
			stack.peek().feed(tb);
		return tb;
	}
	
	public boolean didLastTerminate() {
		return term;
	}
	
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	public int depth() {
		return stack.size();
	}
	
	public void finishStatement() {
		stack.peek().finishStatement();
	}
}
