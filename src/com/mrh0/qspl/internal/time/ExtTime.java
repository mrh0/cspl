package com.mrh0.qspl.internal.time;

import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.TAtom;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.func.IFunc;
import com.mrh0.qspl.type.func.InternalFunc;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.type.var.Var;
import com.mrh0.qspl.vm.VM;
import com.mrh0.qspl.vm.module.Module;
import com.mrh0.qspl.vm.module.ModuleScope;

public class ExtTime implements Module {
	
	@Override
	public String getName() {
		return "mrh0.qspl.time";
	}

	@Override
	public String getAuthor() {
		return "mrh0";
	}

	@Override
	public String getDescription() {
		return "default time libarary for cspl";
	}

	@Override
	public void extend(ModuleScope ext) {
		IFunc f;
		
		f = (VM vm, Val _this, Arguments args) -> {
			return TNumber.create(System.currentTimeMillis());
		};
		ext.export("getSystemMilliseconds", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			return new TDate();
		};
		ext.export("now", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			Calendar cal = Calendar.getInstance();
			if(args.size() > 0)
				cal.set(Calendar.YEAR, TNumber.integer(args.get(0)));
			if(args.size() > 1)
				cal.set(Calendar.MONTH, TNumber.integer(args.get(1)));
			if(args.size() > 2)
				cal.set(Calendar.DAY_OF_MONTH, TNumber.integer(args.get(2)));
			if(args.size() > 3)
				cal.set(Calendar.HOUR, TNumber.integer(args.get(3)));
			if(args.size() > 4)
				cal.set(Calendar.MINUTE, TNumber.integer(args.get(4)));
			if(args.size() > 5)
				cal.set(Calendar.SECOND, TNumber.integer(args.get(5)));
			if(args.size() > 6)
				cal.set(Calendar.MILLISECOND, TNumber.integer(args.get(6)));
			
			return new TDate(cal);
		};
		ext.export("create", new InternalFunc(f));
	}
}

