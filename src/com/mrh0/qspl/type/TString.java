package com.mrh0.qspl.type;

import java.util.ArrayList;
import java.util.Iterator;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.iterator.IIterable;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.type.var.Var;

public class TString implements Val, IIterable{

	private String value;
	
	public TString() {
		value = "";
	}
	
	@Override
	public TAtom getTypeAtom() {
		return TAtom.get("string");
	}
	
	public TString(TString s) {
		value = s.get();
	}
	
	public TString(String s) {
		value = s;
	}
	
	public TString(Object o) {
		value = o.toString();
	}
	
	public TString(StringBuilder sb) {
		value = sb.toString();
	}
	
	@Override
	public int getType() {
		return 0;
	}

	@Override
	public Val duplicate() {
		return new TString(this);
	}

	@Override
	public boolean booleanValue() {
		return value.length() > 0;
	}
	
	@Override
	public boolean isString() {
		return true;
	}
	
	@Override
	public String getTypeName() {
		return "string";
	}
	
	@Override
	public Val add(Val v) {
		return new TString(value + v.toString());
	}
	
	@Override
	public Val div(Val v) {
		if(v.isString())
			return new TArray(value.split(TString.from(v).get()));
		if(v.isNumber()) {
			TArray a = new TArray();
			int i = TNumber.from(v).integerValue();
			a.add(new TString(value.substring(0, i)));
			a.add(new TString(value.substring(i)));
			return a;
		}
		Val.super.div(v);
		return TUndefined.getInstance();
	}
	
	public Val accessor(ArrayList<Val> args) {
		if(args.size() == 0) 
			return TNumber.create(value.length());
		else if(args.size() == 1) {
			if(args.get(0).isNumber()) {
				return new TString(get(TNumber.from(args.get(0)).integerValue()));
			}
		}
		else if(args.size() == 2) {
			if(args.get(0).isNumber() && args.get(1).isNumber()) {
				StringBuilder sb = new StringBuilder();
				int as = TNumber.from(args.get(0)).integerValue();
				int ae = TNumber.from(args.get(1)).integerValue();
				
				if(as == ae) {
					sb.append(get(as));
				}
				else if(as < ae && as < 0 && ae >= 0) {
					for(int i = size()+as; i > ae-1; i--) {
						sb.append(get(i));
					}
				}
				else if(as > ae && as >= 0 && ae < 0){
					for(int i = as; i < size()+ae+1; i++) {
						sb.append(get(i));
					}
				}
				else if(as < ae) {
					for(int i = as; i < ae+1; i++) {
						sb.append(get(i));
					}
				}
				else if(as > ae){
					for(int i = as; i > ae-1; i--) {
						sb.append(get(i));
					}
				}
				return new TString(sb);
			}
		}
		return TUndefined.getInstance();
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public String get() {
		return value;
	}
	
	public String get(int i) {
		return value.charAt(i)+"";
	}
	
	public int size() {
		return value.length();
	}

	@Override
	public Object getValue() {
		return value;
	}

	public static TString from(Val v) {
		if(v instanceof TString)
			return (TString)v;
		if(v instanceof Var && v.isString())
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to string.");
		return null;
	}
	
	@Override
	public Val assign(Val v) {
		if(v.isString()) {
			return TNumber.create(value.matches(TString.from(v).get()));
		}
		return Val.super.assign(v);
	}
	
	public class TStringIterator implements Iterator<Val> {
		private int index;
		private String str;
		public TStringIterator(TString a) {
			str = a.get();
			index = 0;
		}
		
		@Override
		public boolean hasNext() {
			return index < str.length();
		}

		@Override
		public Val next() {
			return new TString(str.charAt(index++));
		}
	}

	@Override
	public Iterator<Val> iterator() {
		return new TStringIterator(this);
	}
	
	public Val is(Val v) {
		return new TNumber(TString.class.isInstance(v));
	}
	
	@Override
	public int compare(Val v) {
		return value.compareTo(v.toString());
	}
}
