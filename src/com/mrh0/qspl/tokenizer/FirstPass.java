package com.mrh0.qspl.tokenizer;

import java.util.ArrayList;
import java.util.Stack;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.tokenizer.token.Token;
import com.mrh0.qspl.tokenizer.token.TokenType;
import com.mrh0.qspl.tokenizer.token.Tokens;;

public class FirstPass {
	
	//Init
	private String code;
	private ArrayList<Token> tokens;
	private Stack<Character> bracketBalancer;
	
	//Current
	private StringBuilder ctoken;
	private TokenType ctype;
	private int pos;
	private int line = 0;
	private int targetindent = 0;
	private int indent = 0;
	private boolean importCall = false;
	
	private Stack<Boolean> indentStack;

	public FirstPass(String code, ArrayList<Token> tokens) {
		this.code = code;
		this.tokens = tokens;
		line = 1;
		Console.g.setCurrentLine(line);
		
		ctoken = new StringBuilder();
		ctype = TokenType.NONE;
		
		bracketBalancer = new Stack<Character>();
		indentStack = new Stack<Boolean>();
		
		//In code text:
		this.pos = 0;
		
		//For comments:
		boolean inlineComment = false;
		boolean blockComment = false;
		char lastc = '\0';
		
		char inString = '\0'; // \0:not in, ':in, ":in
		
		while(hasNext()) {
			char c = next();
			if(indent < targetindent && ctype != TokenType.SEPERATOR && ctype != TokenType.LN_BRK && ctype != TokenType.NONE) {
				//System.out.println("Outdent " + (targetindent - indent) + ":" + ctype);
				for(int i = (targetindent - indent); i > 0; i--) {
					tokens.add(new Token("}", TokenType.END_BLOCK, line));
					if(indentStack.pop()) {
						//System.out.println("Add ;");
						tokens.add(new Token(";", TokenType.END, line));
					}
				}
				targetindent = indent;
			}
			
			
			if(c == '\r' || c == '\n') {
				indent = 0;
				//System.out.println("newline");
				Console.g.setCurrentLine(line++);
				end();
				consume('\r', TokenType.LN_BRK);
				end();
				
				inlineComment = false;
				continue;
			}
			
			//Check for end to inline-comments
			if(inlineComment && c != '\r' && c != '\n')
				continue;
			
			//Check for end to block-comments
			if(blockComment) {
				if(Tokens.isCloseComment(""+lastc+c))
					blockComment = false;
				lastc = c;
				continue;
			}
			
			if(inString != '\0') {
				if(c == inString) {
					inString = '\0';
					end();
					continue;
				}
				consume(c, TokenType.STRING);
				continue;
			}
			
			
			if(Tokens.isWhitespace(c)) {
				end();	
				if(c == '\t')
					indent++;
				continue;
			}
			else if(c == '\"' || c == '\'') {
				inString = c;
				end();
				consume(TokenType.STRING);
				continue;
			}
			else if(Tokens.isSeperator(c)) {
				TokenType sept = TokenType.SEPERATOR;
				
				
				//Check bracket balance:
				if(Tokens.isOpenBracket(c)) {
					bracketBalancer.push(c);
					indentStack.push(false);
					if(c != '(')
						sept = TokenType.BEGIN_BLOCK;
				}
				if(Tokens.isCloseBracket(c)) {
					if(c != ')')
						sept = TokenType.END_BLOCK;
					if(bracketBalancer.isEmpty())
						error("Unexpected: '" + c + "'");
					else {
						indentStack.pop();
						char sc = ((char)bracketBalancer.pop());
						if(sc != Tokens.getOpenBracket(c))
							error("Expected: " + Tokens.getClosedBracket(sc));
					}
				}
				end();
				consume(c, sept);
				end();
				continue;
			}
			else if(Tokens.isOperator(c)) {
				if(ctype != TokenType.OPERATOR)
					end();
				consume(c, TokenType.OPERATOR);
				
				String fs = ctoken.toString();
				if(fs.length() < 2) {
					Token p = getPervious();
					if(fs.equals("-") && (!p.isIdentifier() && !p.isLiteral() && !p.isValKeyword() && !p.isString() && !Tokens.isCloseBracket(p.getToken())))
						ctype=TokenType.LITERAL;
					continue;
				}
				if(Tokens.isComment(fs.substring(fs.length() - 2))) {
					inlineComment = true;
					ctoken = new StringBuilder().append(fs.substring(0, fs.length() - 2));
				}
				else if(Tokens.isOpenComment(fs.substring(fs.length() - 2))) {
					blockComment = true;
					ctoken = new StringBuilder().append(fs.substring(0, fs.length() - 2));
				}
				continue;
			}
			else if(Tokens.isEndOfStatement(c)) {
				if(ctype != TokenType.END)
					end();
				if(c == ';' && importCall) {
					tokens.add(new Token("import", TokenType.TAIL_KEYWORD, line));
					importCall = false;
				}
				consume(c, TokenType.END);
				continue;
			}
			else if(Tokens.isAppendBlock(c)) {
				if(ctype != TokenType.APPEND)
					end();
				consume(c, TokenType.APPEND);
				continue;
			}
			else if(ctype == TokenType.APPEND && ctoken.length() == 1 && Tokens.canBeIdentifier(c)) {
				consume(c, TokenType.IDENTIFIER);
				continue;
			}
			else if(ctype == TokenType.IDENTIFIER && Tokens.canBeIdentifier(c)) {
				consume(c, TokenType.IDENTIFIER);
				continue;
			}
			else if(ctype != TokenType.IDENTIFIER) {
				if(Tokens.canBeLiteral(c)) {
					if(ctype == TokenType.LITERAL) {
						consume(c, TokenType.LITERAL);
						if(ctoken.length() >= 2 && ctoken.substring(ctoken.length()-2).equals("..")) {
							ctoken.delete(ctoken.length()-2, ctoken.length());
							end();
							tokens.add(new Token("..", TokenType.OPERATOR, line));
						}
						continue;
					}
					else {
						/*if(ctoken.toString().equals("-")) {
							consume(c, TokenType.LITERAL);
							continue;
						}*/
						end();
						consume(c, TokenType.LITERAL);
						continue;
					}
				}
				else if(Tokens.canBeStartOfIdentifier(c)) {
					end();
					consume(c, TokenType.IDENTIFIER);
				}
				continue;
			}
			
			
			
			error("Unknown char: '" + c + "':'" + ctype + "'");
		}
		end();
		while(!bracketBalancer.isEmpty())
			error("Expected: '" + Tokens.getClosedBracket(bracketBalancer.pop()) + "'", "end of code.");
	}
	
	//Next char:
	private char next() {
		return code.charAt(this.pos++);
	}
	
	//Has next char:
	private boolean hasNext() {
		return pos < code.length();
	}
	
	//Add char to token:
	private void consume(char c, TokenType t) {
		ctoken.append(c);
		ctype = t;
	}
	
	private void consume(TokenType t) {
		ctype = t;
	}
	
	private Token getPervious() {
		if(tokens.size() <= 0)
			Console.g.err("Prev?");
		return tokens.get(tokens.size()-1);
	}
	
	//Create token:
	private void end() {
		String rtoken = ctoken.toString();
		
		//Ignore if:
		if(rtoken.length() == 0 && ctype == TokenType.NONE)
			return;
		if(rtoken.length() == 0 && ctype != TokenType.STRING) {
			ctype = TokenType.NONE;
			return;
		}
		if(ctype == TokenType.NONE) {
			ctoken = new StringBuilder();
			return;
		}
		if(ctype == TokenType.APPEND) {
			tokens.add(new Token(rtoken, ctype, line));
			
			tokens.add(new Token("{", TokenType.BEGIN_BLOCK, line));
			targetindent++;
			indentStack.push(true);
			//System.out.println("TargetIndent: " + targetindent);
			
			clear();
			return;
		}
		
		//Do last check on token:
		ctype = lastCheck(rtoken);
		//Add start parentheses round function def
		if(ctype == TokenType.PRE_BLOCK_KEYWORD) {
			if(rtoken.equals("func") || rtoken.equals("fn"))
				tokens.add(new Token("(", TokenType.SEPERATOR, line));
		}
		else if(ctype == TokenType.KEYWORD) {
			if(rtoken.equals("from")) {
				if(importCall) {
					rtoken = "importfrom";
					importCall = false;
					ctype = TokenType.TAIL_KEYWORD;
				}
				else {
					Console.g.err("Keyword 'from' cannot be used in this context.");
				}
			}
		}
		else if(ctype == TokenType.TAIL_KEYWORD) {
			if(rtoken.equals("import")) {
				importCall = true;
				clear();
				return;
			}
		}
		
		tokens.add(new Token(rtoken, ctype, line));
		
		//Reset:
		clear();
	}
	
	private void clear() {
		ctoken = new StringBuilder();
		ctype = TokenType.NONE;
	}
	
	//Change token properties before creating:
	private TokenType lastCheck(String token) {
		if(ctype == TokenType.IDENTIFIER) {
			if(Tokens.isOpKeyword(token)) {
				return TokenType.OP_KEYWORD;
			}
			else if(Tokens.isInlineKeyword(token)) {
				return TokenType.KEYWORD;
			}
			else if(Tokens.isValueKeyword(token)) {
				return TokenType.VAL_KEYWORD;
			}
			else if(Tokens.isTailKeyword(token)) {
				return TokenType.TAIL_KEYWORD;
			}
			else if(Tokens.isPreBlockKeyword(token)) {
				return TokenType.PRE_BLOCK_KEYWORD;
			}
			else if(ctoken.charAt(0) == ':') {
				return TokenType.ATOM;
			}
		}
		return ctype;
	}
	
	private void error(String message) {
		Console.g.err(message + ", Near: '" + code.substring(Math.max(pos-10, 0), Math.min(pos+10, code.length())) + "'");
	}
	
	private void error(String message, String near) {
		Console.g.err(message + ", Near: " + near);
	}
}
