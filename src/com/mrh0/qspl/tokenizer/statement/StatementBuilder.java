package com.mrh0.qspl.tokenizer.statement;

import java.util.LinkedList;
import java.util.Stack;

import com.mrh0.qspl.tokenizer.Block;
import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.tokenizer.token.TokenType;
import com.mrh0.qspl.tokenizer.token.Tokens;

public class StatementBuilder {
	private Stack<Token> opStack;
	private LinkedList<Token> postfix;
	private LinkedList<Statement> block;
	
	public StatementBuilder() {
		block = new LinkedList<Statement>();
		reset();
	}
	
	public void finishStatement() {
		while(!opStack.isEmpty()) {
			postfix.add(opStack.pop());
		}
		if(postfix.size() > 0)
			block.add(new Statement(postfix));//optimized()
		reset();
	}
	
	private void reset() {
		opStack = new Stack<Token>();
		postfix = new LinkedList<Token>();
	}
	
	public void feed(Token cur) {
		TokenType t = cur.getType();
		String s = cur.getToken();
		
		
		if(t == TokenType.IDENTIFIER || t == TokenType.LITERAL || t == TokenType.VAL_KEYWORD || t == TokenType.KEYWORD || t == TokenType.STRING)
			postfix.add(cur);
		else if(t == TokenType.SEPERATOR) { //Potentially remove
			if(s.equals("(")) //Potentially move down
				opStack.add(cur);
			else if(s.equals(")")) {
				Token top = opStack.pop();
				while(top.getToken().charAt(0) != '(') {
					postfix.add(top);
					top = opStack.pop();
				}
			}
			else {
				postfix.add(cur);
			}
		}
		/*else if(t == TokenType.END) {
			while(!opStack.isEmpty()) {
				postfix.add(opStack.pop());
			}
			postfix.add(cur);
		}*/
		else {
			//System.out.print("["+s + ":" + t.getName()+"]");
			while(!opStack.isEmpty() && Tokens.opValue(opStack.peek().getToken(), opStack.peek().getType()) >= Tokens.opValue(s, t)) {
				postfix.add(opStack.pop());
			}
			opStack.push(cur);
		}
	}
	
	public Block makeBlock(TokenType blockType) {
		return new Block(block, blockType);
	}
	
	public LinkedList<Token> optimized(){
		Stack<Token> ostack = new Stack<Token>();
		LinkedList<Token> opti = new LinkedList<Token>();
		for(int i = 0; i < postfix.size(); i++) {
			if(postfix.get(i).getType() == TokenType.OPERATOR) {
				String result = "0";
				//If op arguments are literals/strings, do op;
				ostack.push(new Token(result, TokenType.LITERAL));
			}
			else
				ostack.push(postfix.get(i));
		}
		while(!ostack.isEmpty())
			opti.add(ostack.pop());
		return opti; 
	}
}
