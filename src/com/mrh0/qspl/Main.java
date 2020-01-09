package com.mrh0.qspl;

import java.util.ArrayList;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.io.file.Read;
import com.mrh0.qspl.tokenizer.Tokenizer;
import com.mrh0.qspl.tokenizer.token.Token;

public class Main {

	public static void main(String[] args) {
		new Console().setGlobal();
		Tokenizer t = new Tokenizer();
		t.insertCode(Read.fromFile("C:\\MRHLang\\qspl4.qs"));
		ArrayList<Token> r = t.tokenize();
		
		for(Token i : r) {
			System.out.print(i.toString() + " ");
		}
	}
}
