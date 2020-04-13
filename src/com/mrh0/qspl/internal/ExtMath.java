package com.mrh0.qspl.internal;

import java.util.List;
import com.mrh0.qspl.type.TString;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.func.IFunc;
import com.mrh0.qspl.type.func.InternalFunc;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.vm.VM;
import com.mrh0.qspl.vm.module.Module;
import com.mrh0.qspl.vm.module.ModuleScope;

public class ExtMath implements Module {

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
		return "default math libarary for qspl4";
	}

	@Override
	public void extend(ModuleScope ext) {
		ext.export("PI", new TNumber(Math.PI));
		ext.export("INF", new TNumber(Double.POSITIVE_INFINITY));
		ext.export("INFINITY", new TNumber(Double.POSITIVE_INFINITY));
		ext.export("NEGINF", new TNumber(Double.NEGATIVE_INFINITY));
		ext.export("NEGATIVE_INFINITY", new TNumber(Double.NEGATIVE_INFINITY));
		
		IFunc f;
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return TNumber.create(0);
			Val min = args.get(0);
			for(Val v : args) {
				if(v.compare(min) < 0)
					min = v;
			}
			return min;
		};
		ext.export("min", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return new TNumber(0);
			Val max = args.get(0);
			for(Val v : args) {
				if(v.compare(max) > 0)
					max = v;
			}
			return max;
		};
		ext.export("max", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 3)
				return new TNumber(0);
			double v = TNumber.from(args.get(0)).get();
			double min = TNumber.from(args.get(1)).get();
			double max = TNumber.from(args.get(2)).get();
			v = v < min?min:v;
			v = v > max?max:v;
			return new TNumber(v);
		};
		ext.export("clamp", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return new TNumber(0);
			return new TNumber(args.get(0).booleanValue()?1:0);
		};
		ext.export("if", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 3)
				return new TNumber(0);
			return args.get(0).booleanValue()?args.get(1):args.get(2);
		};
		ext.export("condition", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 1)
				return new TString(args.get(0).booleanValue()?"true":"false");
			return TUndefined.getInstance();
		};
		ext.export("bool", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			double from = 0;
			double to = 1;
			if(args.size() == 1)
				to = TNumber.from(args.get(0)).get();
			if(args.size() == 2) {
				from = TNumber.from(args.get(0)).get();
				to = TNumber.from(args.get(1)).get();
			}
			if(from > to)
				return new TNumber(0);
			if(from == to)
				return new TNumber(from);
			return new TNumber(Math.random()*(to-from)+from);
		};
		ext.export("random", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return TUndefined.getInstance();
			return new TNumber((double)Math.round(TNumber.from(args.get(0)).get()));
		};
		ext.export("round", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return TUndefined.getInstance();
			return new TNumber((double)Math.floor(TNumber.from(args.get(0)).get()));
		};
		ext.export("floor", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return TUndefined.getInstance();
			return new TNumber((double)Math.ceil(TNumber.from(args.get(0)).get()));
		};
		ext.export("ceil", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return TUndefined.getInstance();
			return new TNumber((double)Math.abs(TNumber.from(args.get(0)).get()));
		};
		ext.export("abs", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return TUndefined.getInstance();
			return new TNumber(Math.sqrt(TNumber.from(args.get(0)).get()));
		};
		ext.export("sqrt", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() < 2)
				return TUndefined.getInstance();
			return new TNumber(Math.pow(TNumber.from(args.get(0)).get(),TNumber.from(args.get(1)).get()));
		};
		ext.export("pow", new InternalFunc(f));
	}
}
