package com.mrh0.qspl.tokenizer.token;

import com.mrh0.qspl.tokenizer.Block;

public class TokenBlock extends Token{

	private Block block;
	
	public TokenBlock(TokenType type, Block block) {
		super("%%BLK%%", type);
		this.block = block;
	}
	
	public Block getBlock() {
		return this.block;
	}

	@Override
	public String toString() {
		
		return (block.getType()==TokenType.ARY_BLOCK?"*[":"*{")+block.toString()+(block.getType()==TokenType.ARY_BLOCK?"]":"}");
	}
}
