package com.mrh0.qspl.tokenizer.token;

public class Token {
	private String token;
	private TokenType type;
	
	public Token(String token, TokenType type) {
		this.token = token;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type==TokenType.STRING?"'"+token+"' ":token;//+":"+type.getName();
	}
	
	public TokenType getType() {
		return type;
	}
	
	public String getToken() {
		return token;
	}
	
	public boolean isOperator() {
		return type == TokenType.OPERATOR;
	}
	
	public boolean isLiteral() {
		return type == TokenType.LITERAL;
	}
	
	public boolean isSeperator() {
		return type == TokenType.SEPERATOR;
	}
	
	public boolean isIdentifier() {
		return type == TokenType.IDENTIFIER;
	}
	
	public boolean hasBlock() {
		return false;
	}
}
