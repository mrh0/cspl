package com.mrh0.qspl.tokenizer.token;

public enum TokenType {
	KEYWORD(0, "kw"),
	TAIL_KEYWORD(1, "t_kw"),
	OP_KEYWORD(2, "op_kw"),
	VAL_KEYWORD(2, "val_kw"),
	CODE_BLOCK(3, "c_b"),
	OBJ_BLOCK(4, "o_b"),
	ARY_BLOCK(5, "a_b"),
	IDENTIFIER(6, "id"),
	LITERAL(7, "lt"),
	OPERATOR(8, "op"),
	SEPERATOR(9, "sep"),
	STRING(10, "str"),
	NONE(11, "na"),
	LN_BRK(12, "ln_brk"), //Line break
	END(13, "end"),
	BEGIN_BLOCK(14, "b_blk"),
	END_BLOCK(15, "e_blk");
	
	private int id;
	private String name;
	
	private TokenType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String getName() {
		return name;
	}
}
