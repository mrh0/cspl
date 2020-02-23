package com.mrh0.qspl.tokenizer.statement;

import java.util.LinkedList;
import java.util.Stack;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.tokenizer.Block;
import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.tokenizer.token.TokenType;
import com.mrh0.qspl.tokenizer.token.TokenVal;
import com.mrh0.qspl.tokenizer.token.Tokens;
import com.mrh0.qspl.type.TAtom;
import com.mrh0.qspl.type.TString;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.number.TNumber;

public class StatementBuilder {
	private Stack<Token> opStack;
	private LinkedList<Token> postfix;
	private LinkedList<Statement> block;
	private TokenType blockType;
	private boolean terminatesStatement = false;
	
	public StatementBuilder(TokenType blocktype, boolean terminates) {
		block = new LinkedList<Statement>();
		blockType = blocktype;
		this.terminatesStatement = terminates;
		reset();
	}
	
	public void finishStatement() {
		while(!opStack.isEmpty()) {
			postfix.add(opStack.pop());
		}
		if(postfix.size() > 0)
			block.add(new Statement(optimized()));//optimized() //postfix
		reset();
	}
	
	private void reset() {
		opStack = new Stack<Token>();
		postfix = new LinkedList<Token>();
	}
	
	public void feed(Token cur) {
		TokenType t = cur.getType();
		String s = cur.getToken();
		
		if(t == TokenType.IDENTIFIER  || t == TokenType.VAL_KEYWORD || t == TokenType.KEYWORD
				|| t == TokenType.ACCESSOR_BLOCK || t == TokenType.OBJ_BLOCK || t == TokenType.ARY_BLOCK  || t == TokenType.ARG_BLOCK 
				) {//|| t == TokenType.CODE_BLOCK || t == TokenType.IF_BLOCK || t == TokenType.WHILE_BLOCK
			postfix.add(cur);
		}
		else if(t == TokenType.LITERAL) {
			postfix.add(new TokenVal(cur.getToken(), TNumber.create(cur.getToken()), cur.getLine()));
		}
		else if(t == TokenType.STRING) {
			postfix.add(new TokenVal(cur.getToken(), new TString(cur.getToken()), cur.getLine()));
		}
		else if(t == TokenType.ATOM) {
			postfix.add(new TokenVal(cur.getToken(), TAtom.get(cur.getToken()), cur.getLine()));
		}
		else if(t == TokenType.SEPERATOR) {
			if(s.equals("("))
				opStack.add(cur);
			else if(s.equals(")")) {
				Token top = opStack.pop();
				while(!top.getToken().equals("(")) {
					postfix.add(top);
					try {
						top = opStack.pop();
					}
					catch(Exception e) {
						Console.g.err("Parentheses priority error (probably).");
						e.printStackTrace();
					}
				}
			}
			else {
				postfix.add(cur);
			}
		}
		else {
			while(!opStack.isEmpty() && Tokens.opValue(opStack.peek().getToken(), opStack.peek().getType()) >= Tokens.opValue(s, t)) {
				postfix.add(opStack.pop());
			}
			opStack.push(cur);
		}
	}
	
	public Block makeBlock() {
		return new Block(block);
	}
	
	public boolean terminates() {
		return terminatesStatement;
	}
	
	public LinkedList<Token> optimized(){
		Stack<Token> ostack = new Stack<Token>();
		LinkedList<Token> opti = new LinkedList<Token>();
		for(int i = 0; i < postfix.size(); i++) {
			if(postfix.get(i).getType() == TokenType.OPERATOR && !Tokens.optimizeIgnoreOp(postfix.get(i).getToken())) { //!postfix.get(i).getToken().equals("=") && !postfix.get(i).getToken().equals("!")
				Token rv = ostack.pop();
				Token lv;
				if(ostack.isEmpty())
					lv = new TokenVal("", TNumber.create(0), rv.getLine());
				else
					lv = ostack.pop();
				if((lv.getType() == TokenType.LITERAL || lv.getType() == TokenType.STRING) && (rv.getType() == TokenType.LITERAL || rv.getType() == TokenType.STRING)) {
					Val r = TUndefined.getInstance();
					TokenType t = TokenType.LITERAL;
					/*if(!(rv instanceof TokenVal))
						System.err.println("failed optimize: " + lv + " " +postfix.get(i) +" "+ rv);
					if(!(lv instanceof TokenVal))
						System.err.println("failed optimize: " + rv + " " + postfix.get(i) +" "+ rv);*/
					
					Val rvv = ((TokenVal)rv).getValue();
					Val lvv = ((TokenVal)lv).getValue();
					switch(postfix.get(i).getToken()) {
						case "+":
							r = lvv.add(rvv);
							if(r.isString())
								t = TokenType.STRING;
							break;
						case "-":
							
							r = lvv.sub(rvv);
							break;
						case "*":
							r = lvv.multi(rvv);
							break;
						case "/":
							r = lvv.div(rvv);
							break;
						case "%":
							r = lvv.mod(rvv);
							break;
						case "<":
							r = TNumber.create(lvv.compare(rvv)==-1);
							break;
						case ">":
							r = TNumber.create(lvv.compare(rvv)==1);
							break;
						case "<=":
							r = TNumber.create(lvv.compare(rvv)!=1);
							break;
						case ">=":
							r = TNumber.create(lvv.compare(rvv)!=-1);
							break;
						case "==":
							r = TNumber.create(lvv.equals(rvv));
							break;
						case "!=":
							r = TNumber.create(!lvv.equals(rvv));
							break;
						default:
							System.err.println("failed optimize: " + postfix.get(i));
							break;
					}
					ostack.push(new TokenVal(r.getValue()+"", r, rv.getLine()));
				}
				else {
					ostack.push(lv);
					ostack.push(rv);
					ostack.push(postfix.get(i));
				}
			}
			else
				ostack.push(postfix.get(i));
		}
		while(!ostack.isEmpty())
			opti.add(0, ostack.pop());
		return opti; 
	}
	
	public TokenType getBlockType() {
		return blockType;
	}
}
