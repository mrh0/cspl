package com.mrh0.qspl.tokenizer.token;

public enum TokenType {
	KEYWORD(0, "kw"),
	TAIL_KEYWORD(1, "t_kw"),
	OP_KEYWORD(2, "op_kw"),
	VAL_KEYWORD(2, "val_kw"),
	CODE_BLOCK(3, "c_b"),
	OBJ_BLOCK(4, "o_b"),
	ARY_BLOCK(5, "a_b"),
	ACCESSOR_BLOCK(6, "acc_b"),
	IDENTIFIER(7, "id"),
	LITERAL(8, "lt"),
	OPERATOR(9, "op"),
	SEPERATOR(10, "sep"),
	STRING(11, "str"),
	NONE(12, "na"),
	LN_BRK(13, "ln_brk"), //Line break
	END(14, "end"),
	BEGIN_BLOCK(15, "b_blk"),
	END_BLOCK(16, "e_blk"),
	APPEND(17, "app"),
	IF_BLOCK(18, "if"),
	WHILE_BLOCK(19, "whl"),
	VAL(20, "val"),
	ATOM(21, "atom"),
	ARG_BLOCK(22, "arg_b"),
	PRE_BLOCK_KEYWORD(23, "pb_kw");
	
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
