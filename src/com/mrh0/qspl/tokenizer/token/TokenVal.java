package com.mrh0.qspl.tokenizer.token;

import com.mrh0.qspl.type.Val;

public class TokenVal extends Token{

	private Val value;
	
	public TokenVal(String token, TokenType type, Val value) {
		super(token, type);
		this.value = value;
	}
	
	public Val getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return this.getType()==TokenType.STRING?"'"+value.toString()+"' ":value.toString();
	}
	
	@Override
	public boolean hasValue() {
		return true;
	}
}
