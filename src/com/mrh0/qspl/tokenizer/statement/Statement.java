package com.mrh0.qspl.tokenizer.statement;

import java.util.List;
import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.util.StringUtil;

public class Statement {
	private Token[] tokens;
	
	
	public Statement(List<Token> tokens) {
		this.tokens = tokens.toArray(new Token[0]);
		//System.out.println("Created Statement: " + tokens.toString());
	}
	
	public Token[] getTokens() {
		return tokens;
	}
	
	public Token getToken(int i) {
		return tokens[i];
	}
	
	public int length() {
		return tokens.length;
	}
	
	@Override
	public String toString() {
		return StringUtil.arrayToString("[", this.tokens, "]");
	}
	
}
