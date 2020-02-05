package com.mrh0.qspl.type;

import java.util.ArrayList;
import java.util.Iterator;

import com.mrh0.qspl.type.iterator.IIterable;
import com.mrh0.qspl.type.number.TNumber;

public class TArray implements Val, IIterable{
	
	ArrayList<Var> values;
	
	public TArray() {
		values = new ArrayList<Var>();
	}
	
	public TArray(Iterable<Val> it) {
		values = new ArrayList<Var>();
		for(Val v : it)
			this.add(v);
	}
	
	public TArray(ArrayList<Var> values) {
		values = new ArrayList<Var>();
		for(int i = 0; i < values.size(); i++) {
			this.values.set(i, new Var(values.get(i)));
		}
	}
	
	public TArray(String[] strs) {
		values = new ArrayList<Var>();
		for(int i = 0; i < strs.length; i++) {
			this.add(new TString(strs[i]));
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < values.size(); i++) {
			sb.append(values.get(i).get());
			if(i+1 < values.size())
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public Val duplicate() {
		return new TArray(values);
	}

	@Override
	public boolean booleanValue() {
		return values.size() > 0;
	}

	@Override
	public String getTypeName() {
		return "array";
	}

	@Override
	public Object getValue() {
		return this;
	}
	
	public Var get(int i) {
		return values.get(i < 0?size()+i:i);
	}
	
	public int size() {
		return values.size();
	}
	
	public void set(int index, Val v) {
		values.set(index, new Var(index+"", v));
	}
	
	public void set(int index, Var v) {
		values.set(index, v);
	}
	
	@Override
	public Val add(Val v) {
		Var var = new Var(size()+"", v);
		values.add(var);
		return var;
	}
	
	@Override
	public Val multi(Val v) {
		if(v.isString()) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < values.size(); i++) {
				sb.append(get(i).get());
				if(i+1 < values.size())
					sb.append(TString.from(v).get());
			}
			return new TString(sb.toString());
		}
		return TUndefined.getInstance();
	}
	
	@Override
	public Val accessor(ArrayList<Val> args) {
		/*if(args.size() == 0) 
			return new TNumber(values.size());
		else if(args.size() == 1) {
			if(args.get(0).isNumber()) {
				return get(TNumber.from(args.get(0)).integerValue());
			}
			else if(args.get(0).isIterable()) {
				TArray a = new TArray();
				IIterable iter = IIterable.from(args.get(0));
				for(Val v : iter)
					a.add(this.get(TNumber.from(v).integerValue()));
				return a;
			}
		}
		else {
			TArray a = new TArray();
			for(int i = 0; i < args.size(); i++) {
				if(args.get(i).isNumber()) {
					a.add(get(TNumber.from(args.get(i)).integerValue()));
				}
				else if(args.get(i).isIterable()) {
					
					IIterable iter = IIterable.from(args.get(i));
					for(Val v : iter)
						a.add(this.get(TNumber.from(v).integerValue()));
				}
			}
			return a;
		}*/
		if(args.size() == 0) 
			return TNumber.create(values.size());
		else if(args.size() == 1) {
			if(args.get(0).isNumber()) {
				return get(TNumber.from(args.get(0)).integerValue());
			}
			else if(args.get(0).isIterable()) {
				TArray a = new TArray();
				IIterable iter = IIterable.from(args.get(0));
				for(Val v : iter)
					a.add(this.get(TNumber.from(v).integerValue()));
				return a;
			}
		}
		else if(args.size() == 2) {
			if(args.get(0).isNumber() && args.get(1).isNumber()) {
				TArray a = new TArray();
				int as = TNumber.from(args.get(0)).integerValue();
				int ae = TNumber.from(args.get(1)).integerValue();
				
				if(as == ae) {
					a.add(get(as));
				}
				else if(as < ae && as < 0 && ae >= 0) {
					for(int i = size()+as; i > ae-1; i--) {
						a.add(get(i));
					}
				}
				else if(as > ae && as >= 0 && ae < 0){
					for(int i = as; i < size()+ae+1; i++) {
						a.add(get(i));
					}
				}
				else if(as < ae) {
					for(int i = as; i < ae+1; i++) {
						a.add(get(i));
					}
				}
				else if(as > ae){
					for(int i = as; i > ae-1; i--) {
						a.add(get(i));
					}
				}
				return a;
			}
		}
		return TUndefined.getInstance();
	}
	
	public class TArrayIterator implements Iterator<Val> {
		private Iterator<Var> a;
		public TArrayIterator(TArray a) {
			this.a = a.values.iterator();
		}
		
		@Override
		public boolean hasNext() {
			return a.hasNext();
		}

		@Override
		public Val next() {
			return a.next().get();
		}
	}

	@Override
	public Iterator<Val> iterator() {
		return new TArrayIterator(this);
	}
}
