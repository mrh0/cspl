package com.mrh0.qspl.tokenizer;

import java.util.ArrayList;

import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.tokenizer.token.TokenBlock;

public class Tokenizer {
	private StringBuilder codeBuilder;
	private ArrayList<Token> tokens;
	
	public Tokenizer() {
		codeBuilder = new StringBuilder();
	}
	
	public void insertCode(String code) {
		codeBuilder.append(code);
	}
	
	//Interface
	public TokenBlock tokenize() {
		//Get code and clear builder.
		String code = codeBuilder.toString();
		codeBuilder = new StringBuilder();
		tokens = new ArrayList<Token>();
		
		new FirstPass(code, tokens);
		return new SecondPass(tokens).get();
	}
	
	public String toString() {
		String r = "";
		for(Token t : tokens){
			r += t.toString() + " ";
		}
		return r;
	}
}
