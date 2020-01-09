package com.mrh0.qspl.tokenizer;

import java.util.ArrayList;

import com.mrh0.qspl.tokenizer.token.Token;

public class Tokenizer {
	private StringBuilder codeBuilder;
	
	public Tokenizer() {
		codeBuilder = new StringBuilder();
	}
	
	public void insertCode(String code) {
		codeBuilder.append(code);
	}
	
	//Interface
	public ArrayList<Token> tokenize() {
		//Get code and clear builder.
		String code = codeBuilder.toString();
		codeBuilder = new StringBuilder();
		ArrayList<Token> tokens = new ArrayList<Token>();
		
		new FirstPass(code, tokens);
		new SecondPass(tokens);
		
		return tokens;
	}
}
