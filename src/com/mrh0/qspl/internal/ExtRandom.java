package com.mrh0.qspl.internal;

import java.util.Random;

import com.mrh0.qspl.type.TString;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.func.IFunc;
import com.mrh0.qspl.type.func.InternalFunc;
import com.mrh0.qspl.type.iterator.TIterator;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.vm.VM;
import com.mrh0.qspl.vm.module.Module;
import com.mrh0.qspl.vm.module.ModuleScope;

public class ExtRandom implements Module {
	
	@Override
	public String getName() {
		return "mrh0.qspl.math";
	}

	@Override
	public String getAuthor() {
		return "mrh0";
	}

	@Override
	public String getDescription() {
		return "default random libarary for qspl4";
	}

	@Override
	public void extend(ModuleScope ext) {
		IFunc f;
		
		f = (VM vm, Val _this, Arguments args) -> {
			return new RandomDoubleIterator();
		};
		ext.export("iter", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 1)
				return args.get(0).multi(new TNumber(Math.random()));
			if(args.size() == 2) {
				return args.get(1).sub(args.get(0)).multi(new TNumber(Math.random())).add(args.get(0));
			}
			return new TNumber(Math.random());
		};
		ext.export("random", new InternalFunc(f));
	}
	
	public class RandomDoubleIterator extends TIterator{
		
		private Random rand = new Random();
		
		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public Val next() {
			return new TNumber(rand.nextDouble());
		}
		
	}
}
