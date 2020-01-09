package com.mrh0.qspl.tokenizer;

import java.util.ArrayList;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.tokenizer.statement.StatementStack;
import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.tokenizer.token.TokenType;

public class SecondPass {
	
	
	private int line;
	private StatementStack stmts;
	
	public SecondPass(ArrayList<Token> tokens) {
		stmts = new StatementStack();
		stmts.push();
		line = 1;
		Console.g.setCurrentLine(line);
		
		for(int i = 0; i < tokens.size(); i++) {
			Token cur = tokens.get(i);
			TokenType t = cur.getType();
			String s = cur.getToken();
			
			if(t == TokenType.CODE_BLOCK) {
				continue;
			}
			else if(t == TokenType.SEPERATOR) {
				if(s.equals(","))
					stmts.finishStatement();
				continue;
			}
			else if(t == TokenType.LN_BRK) {
				Console.g.setCurrentLine(line++);
				continue;
			}
			else if(t == TokenType.END) {
				stmts.finishStatement();
				continue;
			}
			else if(t == TokenType.BEGIN_BLOCK) {
				stmts.push();
				continue;
			}
			else if(t == TokenType.END_BLOCK) {
				stmts.finishStatement();
				stmts.pop(s.equals("}")?TokenType.OBJ_BLOCK:TokenType.ARY_BLOCK);
				continue;
			}
			else {
				/*while(indent+1 < stmts.depth()) {
					stmts.finishStatement();
					stmts.pop();
				}*/
			}
			
			stmts.feed(cur);
		}
		
		/*for(Token i : postfix) {
			System.out.print(i.getToken() + " ");
		}*/
		System.out.println("sp: "+get().toString());
	}
	
	public Block get() {
		Block b = null;
		while(!stmts.isEmpty())
			b = stmts.pop(TokenType.CODE_BLOCK);
		return b;
	}
}
