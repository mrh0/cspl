package com.mrh0.qspl.interpreter.evaluator;

import java.util.ArrayList;
import java.util.Stack;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.tokenizer.statement.Statement;
import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.tokenizer.token.TokenBlock;
import com.mrh0.qspl.tokenizer.token.TokenType;
import com.mrh0.qspl.tokenizer.token.TokenVal;
import com.mrh0.qspl.type.TContainer;
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
	private BlockType bt;
	
	private enum BlockType {
		CODE, ARRAY, OBJECT, CONTAINER, ACCESSOR
	}
	
	public  StatementEval(Statement s, VM vm, BlockType bt) {
		this.s = s;
		this.vals = new Stack<Val>();
		this.vm = vm;
		this.bt  = bt;
	}
	
	public  StatementEval(Statement s, VM vm) {
		this.s = s;
		this.vals = new Stack<Val>();
		this.vm = vm;
		this.bt  = BlockType.CODE;
	}
	
	public StatementEval(StatementEval se) {
		this.s = se.s;
		this.vals = new Stack<Val>();
		this.vm = se.vm;
		this.bt  = se.bt;
	}
	
	public EvalResult eval() {
		for(int i = 0; i < s.length(); i++) {
			Token t = s.getToken(i);
			Console.g.setCurrentLine(t.getLine());
			Val hl;
			Val vl;
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
				case "++":
					vals.push(vals.pop().increment(new TNumber(1)));
					break;
				case "--":
					vals.push(vals.pop().decrement(new TNumber(1)));
					break;
				case "==":
					hl = vals.pop();
					vals.push(new TNumber(vals.pop().compare(hl)==0));
					break;
				case "<":
					hl = vals.pop();
					vals.push(new TNumber(vals.pop().compare(hl)==-1));
					break;
				case ">":
					hl = vals.pop();
					System.out.println(vals.peek() + ">" + hl +":" + new TNumber(vals.peek().compare(hl)==1));
					vals.push(new TNumber(vals.pop().compare(hl)==1));
					break;
				case "=":
					hl = vals.pop();
					vl = bt == BlockType.CODE?vals.pop():vals.pop().duplicate();
					vals.push(vl.assign(hl));
					break;
				default:
					System.err.println("Unknown op: " + t.getToken());
					break;
				}
			}
			else if(t.isBlock()) {
				if(t.getType() == TokenType.CODE_BLOCK)
					vals.push(evalBlock((TokenBlock)t, vm).getResult());
				else if(t.getType() == TokenType.OBJ_BLOCK)
					vals.push(evalContainerBlock((TokenBlock)t, vm).getResult());
				else if(t.getType() == TokenType.ACCESSOR_BLOCK)
					vals.push(evalAccessorBlock((TokenBlock)t, vm, vals.pop()).getResult());
				else if(t.getType() == TokenType.IF_BLOCK) {
					if(vals.peek().booleanValue())
						vals.push(evalBlock((TokenBlock)t, vm).getResult());
				}
				else if(t.getType() == TokenType.WHILE_BLOCK) {
					Val r = TUndefined.getInstance();
					if(vals.peek().booleanValue()) {
						r = evalBlock((TokenBlock)t, vm).getResult();
						/*try {Thread.sleep(500);
						} catch (InterruptedException e) {e.printStackTrace();}*/
						i = -1;
						this.vals = new Stack<Val>();
						continue;
					}
				}
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
				vals.push(var);
			}
			else if(t.isTailKeyword()) {
				switch(t.getToken()) {
					case "out":
						Console.g.log(vals.isEmpty()?TUndefined.getInstance():vals.peek().getValue());
						break;
					case "err":
						Console.g.err(vals.isEmpty()?TUndefined.getInstance():vals.peek().getValue());
						break;
					case "val":
						if(vals.peek().isVariable())
							vals.push(((Var)vals.pop()).get());
						break;
					case "let":
						if(!vals.isEmpty() && bt == BlockType.CODE) {
							Val v = vals.peek();
							if(v.isVariable()) {
								vm.setVariable((Var)v);
							}
						}
						else {
							Console.g.err("Keyword 'let' used in illegal context.");
						}
						break;
					case "del":
						//vm.delVariable();
						break;
				}
			}
			else if(t.isValKeyword()) {
				switch(t.getToken()) {
					case "true":
						vals.push(new TNumber(true));
						break;
					case "false":
						vals.push(new TNumber(false));
						break;
					case "undefined":
						vals.push(TUndefined.getInstance());
						break;
				}
			}
		}
		// With statement result:
		if(!vals.isEmpty() && bt == BlockType.CODE) {
			Val v = vals.peek();
			if(v.isVariable()) {
				vm.setVariable((Var)v);
			}
		}
		return new EvalResult(vals.isEmpty()?TUndefined.getInstance():vals.pop());
	}
	
	public static EvalResult evalBlock(TokenBlock b, VM vm) {
		return evalCodeBlock(b, vm);
	}
	
	public static EvalResult evalCodeBlock(TokenBlock b, VM vm) {
		Statement[] l = b.getBlock().getStatements();
		EvalResult r = new EvalResult(TUndefined.getInstance());
		for(int i = 0; i < l.length; i++) {
			r = new StatementEval(l[i], vm).eval();
		}
		return new EvalResult(r.getResult());
	}
	
	public static EvalResult evalObjectBlock(TokenBlock b, VM vm) {
		Statement[] l = b.getBlock().getStatements();
		for(int i = 0; i < l.length; i++) {
			
		}
		return new EvalResult(TUndefined.getInstance());
	}
	
	public static EvalResult evalContainerBlock(TokenBlock b, VM vm) {
		Statement[] l = b.getBlock().getStatements();
		TContainer c = new TContainer();
		for(int i = 0; i < l.length; i++) {
			c.add(new StatementEval(l[i], vm, BlockType.CONTAINER).eval().getResult());
		}
		return new EvalResult(c);
	}
	
	public static EvalResult evalAccessorBlock(TokenBlock b, VM vm, Val toAccess) {
		Statement[] l = b.getBlock().getStatements();
		ArrayList<Val> args = new ArrayList<Val>();
		for(int i = 0; i < l.length; i++) {
			args.add(new StatementEval(l[i], vm, BlockType.CONTAINER).eval().getResult());
		}
		return new EvalResult(toAccess.accessor(args.toArray(new Val[0])));
	}
}
