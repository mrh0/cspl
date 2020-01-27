package com.mrh0.qspl.interpreter.evaluator;

import java.util.Stack;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.tokenizer.statement.Statement;
import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.tokenizer.token.TokenBlock;
import com.mrh0.qspl.tokenizer.token.TokenVal;
import com.mrh0.qspl.type.TNumber;
import com.mrh0.qspl.type.TString;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.Var;
import com.mrh0.qspl.vm.VM;

public class StatementEval {
	private Statement s;
	private Stack<Val> vals;
	private VM vm;
	
	public  StatementEval(Statement s, VM vm) {
		this.s = s;
		this.vals = new Stack<Val>();
		this.vm = vm;
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
				case "=":
					hl = vals.pop();
					vals.push(vals.pop().assign(hl));
					System.out.println("Assigned: " + (Var)vals.peek());
					break;
				default:
					System.err.println("Unknown op: " + t.getToken());
					break;
				}
			}
			else if(t.isBlock()) {
				vals.push(evalBlock((TokenBlock)t, vm).getResult());
			}
			else if(t.hasValue()) {
				vals.push(((TokenVal)t).getValue());
			}
			else if(t.isString()) { // Not needed (i think). Will do hasValue.
				vals.push(new TString(t.getToken()));
			}
			else if(t.isLiteral()) { // Not needed (i think). Will do hasValue.
				vals.push(new TNumber(t.getToken()));
			}
			else if(t.isIdentifier()) {
				Var var = vm.getVariable(t.getToken());
				System.out.println("pushed: " + var);
				vals.push(var);
			}
			else if(t.isTailKeyword())
				Console.g.log(vals.isEmpty()?TUndefined.getInstance():vals.peek().getValue());
		}
		// With statement result:
		if(!vals.isEmpty()) {
			Val v = vals.peek();
			if(v.isVariable())
				vm.setVariable((Var)v);
		}
		return new EvalResult(vals.isEmpty()?TUndefined.getInstance():vals.pop());
	}
	
	public static EvalResult evalBlock(TokenBlock b, VM vm) {
		return evalCodeBlock(b, vm);
	}
	
	public static EvalResult evalCodeBlock(TokenBlock b, VM vm) {
		Statement[] l = b.getBlock().getStatements();
		EvalResult r;
		for(int i = 0; i < l.length; i++) {
			r = new StatementEval(l[i], vm).eval();
		}
		return new EvalResult(TUndefined.getInstance());
	}
	
	public static EvalResult evalObjectBlock(TokenBlock b, VM vm) {
		Statement[] l = b.getBlock().getStatements();
		for(int i = 0; i < l.length; i++) {
			
		}
		return new EvalResult(TUndefined.getInstance());
	}
}
