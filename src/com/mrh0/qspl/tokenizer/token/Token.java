package com.mrh0.qspl.tokenizer.token;

public class Token {
	private final String token;
	private TokenType type;
	public final int line;
	
	public Token(String token, TokenType type, int line) {
		this.token = token;
		this.type = type;
		this.line = line;
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
	
	public void setType(TokenType type) {
		this.type = type;
	}
	
	public boolean isOperator() {
		return type == TokenType.OPERATOR;
	}
	
	public boolean isLiteral() {
		return type == TokenType.LITERAL;
	}
	
	public boolean isString() {
		return type == TokenType.STRING;
	}
	
	public boolean isSeperator() {
		return type == TokenType.SEPERATOR;
	}
	
	public boolean isIdentifier() {
		return type == TokenType.IDENTIFIER;
	}
	
	public boolean isKeyword() {
		return type == TokenType.KEYWORD;
	}
	
	public boolean isOpKeyword() {
		return type == TokenType.OP_KEYWORD;
	}
	
	public boolean isTailKeyword() {
		return type == TokenType.TAIL_KEYWORD;
	}
	
	public boolean isValKeyword() {
		return type == TokenType.VAL_KEYWORD;
	}
	
	public boolean isPreBlockKeyword() {
		return type == TokenType.PRE_BLOCK_KEYWORD;
	}
	
	public boolean isBlock() {
		return this instanceof TokenBlock;
	}
	
	public boolean hasValue() {
		return this instanceof TokenVal;
	}
	
	public boolean hasBlock() {
		return false;
	}
	
	public int getLine() {
		return line;
	}
}
