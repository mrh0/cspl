package com.mrh0.qspl.tokenizer.token;

import com.mrh0.qspl.io.console.Console;

public class Tokens {
	// v4 tokens
	@Deprecated
	public static boolean isKeyword(String s) {
		return isValueKeyword(s) || isTailKeyword(s) || isInlineKeyword(s) || isOpKeyword(s);
	}
	
	public static boolean isValueKeyword(String s) {
		switch(s) {
			case "prev":
				return true;
			case "time":
				return true;
			case "line":
				return true;
			case "true":
				return true;
			case "false":
				return true;
			case "undefined":
				return true;
		}
		return false;
	}
	
	public static boolean isTailKeyword(String s) {
		switch(s) {
			case "out":
				return true;
			case "error":
				return true;
			case "val":
				return true;
			case "exit":
				return true;
			case "import":
				return true;
			case "importfrom":
				return true;
			case "export":
				return true;
			case "delete":
				return true;
			case "with":
				return true;
			case "assert":
				return true;
		}
		return false;
	}
	
	public static boolean isInlineKeyword(String s) {
		switch(s) {
			case "break":
				return true;
			case "continue":
				return true;
			case "from":
				return true;
		}
		return false;
	}
	
	public static boolean isPreBlockKeyword(String s) {
		switch(s) {
			case "if":
				return true;
			case "for":
				return true;
			case "switch":
				return true;
			case "case":
				return true;
			case "func":
				return true;
			case "else":
				return true;
			case "loop":
				return true;
		}
		return false;
	}
	
	public static boolean isOpKeyword(String s) {
		switch(s) {
			case "let":
				return true;
			case "in":
				return true;
			case "of":
				return true;
			case "as":
				return true;
			case "is":
				return true;
			case "#":
				return true;
			case "new":
				return true;
			case "$":
				return true;
			case "where":
				return true;
			case "orderasc":
				return true;
			case "orderdesc":
				return true;
		}
		return false;
	}
	
	public static boolean canBeLiteral(char c) {
		if(c == '-')
			return true;
		if(c == '.')
			return true;
		if(c >= '0' && c <= '9')
			return true;
		return false;
	}
	
	public static boolean canBeStartOfIdentifier(char c) {
		if(c >= 'A' && c <= 'Z')
			return true;
		if(c >= 'a' && c <= 'z')
			return true;
		if(c == '$')
			return true;
		if(c == '_')
			return true;
		return false;
	}
	
	public static boolean isIndent(char c) {
		return c == '\t';
	}
	
	public static boolean isEndOfStatement(char c) {
		return c == ';';
	}
	
	public static boolean isAppendBlock(char c) {
		return c == ':';
	}
	
	public static boolean canBeIdentifier(char c) {
		return canBeStartOfIdentifier(c) || canBeLiteral(c);
	}
	
	public static boolean isString(char c) {
		if(c == '\'')
			return true;
		if(c == '\"')
			return true;
		return false;
	}
	
	public static boolean isSeperator(char c) {
		if(c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}' || c == '\t' || c == ',')
			return true;
		return false;
	}
	
	public static boolean isBracket(char c) {
		if(c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}')
			return true;
		return false;
	}
	
	public static boolean isBracket(String s) {
		if(s.length() > 1)
			return false;
		char c = s.charAt(0);
		if(c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}')
			return true;
		return false;
	}
	
	public static boolean isOpenBracket(char c) {
		return (c == '(' || c == '['|| c == '{');
	}
	
	public static boolean isCloseBracket(char c) {
		return (c == ')' || c == ']'|| c == '}');
	}
	
	public static boolean isCloseBracket(String s) {
		if(s.length() > 1)
			return false;
		char c = s.charAt(0);
		return (c == ')' || c == ']'|| c == '}');
	}
	
	public static boolean isOpenSyntaxBracket(char c) {
		return (c == '['|| c == '{');
	}
	
	public static boolean isCloseSyntaxBracket(char c) {
		return (c == ']'|| c == '}');
	}
	
	public static char getClosedBracket(char c) {
		if(c == '(')
			return ')';
		if(c == '[')
			return ']';
		if(c == '{')  
			return '}';
		return '\0';
	}
	
	public static char getOpenBracket(char c) {
		if(c == ')')
			return '(';
		if(c == ']')
			return '[';
		if(c == '}')  
			return '{';
		return '\0';
	}
	
	public static boolean isWhitespace(char c) {
		if(c == '\t' || c == '\n' || c == '\r' || c == ' ')
			return true;
		return false;
	}
	
	public static boolean isOperator(char c) {
		if(c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '!' || c == '=' || c == '<' || c == '>' || c == '&' || c == '|' || c == '?' || c == '~' || c == '^' || c == '#')
			return true;
		return false;
	}
	
	public static boolean isComment(String c) {
		return c.equals("//");
	}
	
	public static boolean isOpenComment(String s) {
		return s.equals("/*");
	}
	
	public static boolean isCloseComment(String s) {
		return s.equals("*/");
	}
	
	public static boolean isLineComment(String s) {
		return s.equals("//");
	}
	
	public static int opValue(String s, TokenType t) {
		switch(t) {
			case OP_KEYWORD:
				switch(s) {
					case "in":
						return 1;
					case "let":
						return 20;
					case "where":
						return 1;
					case "orderasc":
						return 1;
					case "orderdesc":
						return 1;
				}
				return 20;
			case TAIL_KEYWORD:
				return -500;
			case PRE_BLOCK_KEYWORD:
				return -400;
				
			case CODE_BLOCK:
				return -300;
			/*case IF_BLOCK:
				return -200;*/
			/*case WHILE_BLOCK:
				return -200;*/
				
			case ARY_BLOCK:
				return 25;
			case OBJ_BLOCK:
				return 25;
			case APPEND:
				return 50;
		}
		
		switch(s) {
			case "(":
				return -1000;
				
			case "#":
				return 13;
			case "...":
				return 12;
			case "<-":
				return 12;
			case "->":
				return 12;
			case "**":
				return 11;
			case "++":
				return 11;
			case "--":
				return 11;
			case "is":
				return 11;
			case "as":
				return 11;
			case "?":
				return 11;
			case "!":
				return 11;
			case "~":
				return 11;
			
			case "*":
				return 10;
			case "/":
				return 10;
			case "%":
				return 10;
			
			case "+":
				return 9;
			case "-":
				return 9;
			
			case "<":
				return 8;
			case "<=":
				return 8;
			case ">":
				return 8;
			case ">=":
				return 8;
			case "!=":
				return 7;
			case "==":
				return 7;
			
			
			case "&":
				return 6;
			case "^":
				return 5;
			case "|":
				return 4;
			
			case "&&":
				return 3;
			case "||":
				return 2;
			
			case "=":
				return 0;
			case "+=":
				return 0;
			case "-=":
				return 0;
			case "*=":
				return 0;
			case "/=":
				return 0;
			case "%=":
				return 0;
		}
		Console.g.err("Unidentified Operator: '" + s + "'");
		return 0;
	}
	
	public static boolean optimizeIgnoreOp(String op) {
		switch(op) {
			case "=":
				return true;
			case "!":
				return true;
			case "~":
				return true;
			case "#":
				return true;
				
			case "+=":
				return true;
			case "-=":
				return true;
			case "*=":
				return true;
			case "/=":
				return true;
			case "%=":
				return true;
			case "...":
				return true;
			case "is":
				return true;
			case "as":
				return true;
		}
		return false;
	}
}
