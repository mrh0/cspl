package com.mrh0.qspl.tokenizer;

import java.util.List;

import com.mrh0.qspl.tokenizer.statement.Statement;
import com.mrh0.qspl.tokenizer.token.TokenType;
import com.mrh0.qspl.util.StringUtil;

public class Block {
	private Statement[] statements;
	
	public Block(List<Statement> a) {
		statements = a.toArray(new Statement[0]);
	}
	
	public Statement[] getStatements() {
		return statements;
	}
	
	@Override
	public String toString() {
		return StringUtil.arrayToString(this.getStatements());
	}
}
