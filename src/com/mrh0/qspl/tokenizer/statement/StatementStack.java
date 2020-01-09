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
	
	public void push() {
		stack.push(new StatementBuilder());
	}
	
	public void feed(Token t) {
		stack.peek().feed(t);
	}
	
	public Block pop(TokenType blockType) {
		Block b = stack.pop().makeBlock(blockType);
		if(!stack.isEmpty())
			stack.peek().feed(new TokenBlock(TokenType.CODE_BLOCK, b));
		return b;
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
