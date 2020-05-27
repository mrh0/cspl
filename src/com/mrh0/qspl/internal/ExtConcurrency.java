package com.mrh0.qspl.internal;

import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.func.IFunc;
import com.mrh0.qspl.type.func.InternalFunc;
import com.mrh0.qspl.type.func.TFunc;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.vm.VM;
import com.mrh0.qspl.vm.module.Module;
import com.mrh0.qspl.vm.module.ModuleScope;
import com.mrh0.qspl.vm.queue.TimedEntry;

public class ExtConcurrency implements Module {

	@Override
	public String getName() {
		return "mrh0.cspl.executionqueue";
	}

	@Override
	public String getAuthor() {
		return "mrh0";
	}

	@Override
	public String getDescription() {
		return "Execution Queue Functions";
	}

	@Override
	public void extend(ModuleScope ext) {
		IFunc f;
		
		f = (VM vm, Val _this, Arguments args) -> {
			TimedEntry te = new TimedEntry(
				TFunc.from(args.get(1)),
				(long)TNumber.from(args.get(0)).get() + System.currentTimeMillis()
			);
			vm.spawnExec(te);
			return TNumber.create(te.getId());
		};
		ext.export("timeout", new InternalFunc(f));
	}
}
