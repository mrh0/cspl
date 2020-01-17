package com.mrh0.qspl.evaluator;

import java.util.Stack;

import com.mrh0.qspl.tokenizer.Block;
import com.mrh0.qspl.tokenizer.statement.Statement;
import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.tokenizer.token.TokenBlock;
import com.mrh0.qspl.tokenizer.token.TokenType;
import com.mrh0.qspl.tokenizer.token.TokenVal;
import com.mrh0.qspl.type.TNumber;
import com.mrh0.qspl.type.TString;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;

public class StatementEval {
	private Statement s;
	private Stack<Val> vals;
	
	public  StatementEval(Statement s) {
		this.s = s;
		this.vals = new Stack<Val>();
	}
	
	public EvalResult eval() {
		for(int i = 0; i < s.length(); i++) {
			Token t = s.getToken(i);
			Val hl;
			if(t.isOperator()) {
				switch(t.getToken()) {
				case "+":
					hl = vals.pop();
					vals.push(vals.pop().add(hl));
					break;
				case "-":
					hl = vals.pop();
					vals.push(vals.pop().sub(hl));
					break;
				case "*":
					hl = vals.pop();
					vals.push(vals.pop().multi(hl));
					break;
				case "/":
					hl = vals.pop();
					vals.push(vals.pop().div(hl));
					break;
				case "%":
					hl = vals.pop();
					vals.push(vals.pop().mod(hl));
					break;
				default:
					System.err.println("Unknown op: " + t.getToken());
					break;
			}
			}
			else if(t.isBlock()) {
				vals.push(evalBlock((TokenBlock)t).getResult());
			}
			else if(t.hasValue()) {
				vals.push(((TokenVal)t).getValue());
			}
			else if(t.isString()) { //Not needed (i think).
				vals.push(new TString(t.getToken()));
			}
			else if(t.isLiteral()) { //Not needed (i think).
				vals.push(new TNumber(t.getToken()));
			}
		}
		return new EvalResult(TUndefined.getInstance());
	}
	
	public static EvalResult evalBlock(TokenBlock b) {
		return evalCodeBlock(b);
	}
	
	public static EvalResult evalCodeBlock(TokenBlock b) {
		Statement[] l = b.getBlock().getStatements();
		EvalResult r;
		for(int i = 0; i < l.length; i++) {
			r = new StatementEval(l[i]).eval();
		}
		return new EvalResult(TUndefined.getInstance());
	}
	
	public static EvalResult evalObjectBlock(TokenBlock b) {
		Statement[] l = b.getBlock().getStatements();
		for(int i = 0; i < l.length; i++) {
			
		}
		return new EvalResult(TUndefined.getInstance());
	}
}
