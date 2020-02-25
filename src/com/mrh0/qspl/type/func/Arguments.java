package com.mrh0.qspl.type.func;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;

public class Arguments implements Iterable<Val> {
	private List<Val> args;
	
	public Arguments() {
		args = new ArrayList<Val>();
	}
	
	public Arguments(List<Val> list) {
		args = list;
	}
	
	public Val get(int i) {
		if(i >= args.size())
			return TUndefined.getInstance();
		return args.get(i);
	}
	
	public Val get(int i, Val def) {
		if(i >= args.size())
			return def;
		return args.get(i);
	}
	
	public boolean is(int i, Class<? extends Val> type) {
		return type.isInstance(get(i, TUndefined.getInstance()));
	}
	
	public int size() {
		return args.size();
	}

	@Override
	public Iterator<Val> iterator() {
		return args.iterator();
	}
}
