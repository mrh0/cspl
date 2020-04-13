package com.mrh0.qspl.tokenizer.token;

import com.mrh0.qspl.tokenizer.Block;

public class TokenBlock extends Token{

	private Block block;
	
	public TokenBlock(TokenType type, Block block, int line) {
		super("%%BLK%%", type, line);
		this.block = block;
	}
	
	public Block getBlock() {
		return this.block;
	}

	@Override
	public String toString() {
		switch(getType()) {
			case ACCESSOR_BLOCK:
				return "^["+block.toString()+"]^";
			case OBJ_BLOCK:
				return "¤{"+block.toString()+"}¤";
			case ARY_BLOCK:
				return "¤["+block.toString()+"]¤";
			/*case IF_BLOCK:
				return ":{"+block.toString()+"}";*/
			/*case WHILE_BLOCK:
				return "::{"+block.toString()+"}";*/
			case ARG_BLOCK:
				return "f["+block.toString()+"]";
		}
		return "{"+block.toString()+"}";
	}
}
