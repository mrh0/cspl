package com.mrh0.qspl.tokenizer;

import java.util.List;

import com.mrh0.qspl.tokenizer.statement.Statement;
import com.mrh0.qspl.tokenizer.token.TokenType;
import com.mrh0.qspl.util.StringUtil;

public class Block {
	private Statement[] statements;
	private TokenType blockType;
	
	public Block(List<Statement> a, TokenType blockType) {
		statements = a.toArray(new Statement[0]);
		this.blockType = blockType;
	}
	
	public Statement[] getStatements() {
		return statements;
	}
	
	public TokenType getType() {
		return blockType;
	}
	
	@Override
	public String toString() {
		return StringUtil.arrayToString(this.getStatements());
	}
}
