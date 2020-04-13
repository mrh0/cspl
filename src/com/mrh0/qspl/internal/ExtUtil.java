package com.mrh0.qspl.internal;

import java.util.List;
import java.util.Scanner;
import com.mrh0.qspl.type.TArray;
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

public class ExtUtil implements Module{

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
		return "default util libarary for qspl4";
	}

	@Override
	public void extend(ModuleScope ext) {
		IFunc f;
		
		f = (VM vm, Val _this, Arguments args) -> {
			String r = "";
			for(int i = 0; i < args.size(); i++) {
				r+=args.get(i);
				if(i+1 < args.size())
					r+=", ";
			}
			System.out.print(r);
			return new TString(r);
		};
		ext.export("print", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			String r = "";
			for(int i = 0; i < args.size(); i++) {
				r+=args.get(i);
				if(i+1 < args.size())
					r+=", ";
			}
			System.out.println(r);
			return new TString(r);
		};
		ext.export("println", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			Scanner scan = new Scanner(System.in);
			Val r = TUndefined.getInstance();
			if(args.size() >= 1) {
				if(args.get(0).isNumber()) {
					return new TNumber(scan.nextDouble());
				}
			} 
			return new TString(scan.nextLine());
		};
		ext.export("read", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			System.exit(0);
			return TUndefined.getInstance();
		};
		ext.export("stop", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return TUndefined.getInstance();
			try {
				Thread.sleep(TNumber.from(args.get(0)).integerValue());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return TUndefined.getInstance();
		};
		ext.export("sleep", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return TUndefined.getInstance();
			return vm.getVariable(TString.from(args.get(0)).get());
		};
		ext.export("valueOf", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return TUndefined.getInstance();
			String s = TString.from(args.get(0)).get();
			if(s.length() > 1) {
				TArray a = new TArray();
				for(int i = 0; i < s.length(); i++)
					a.add(new TNumber((int)s.charAt(i)));
				return a;
			}
			return new TNumber((int)s.charAt(0));
		};
		ext.export("charcodeOf", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.size() == 0)
				return TUndefined.getInstance();
			if(args.get(0).isNumber())
				return new TString((char)TNumber.from(args.get(0)).get());
			if(args.get(0).isArray()) {
				String s = "";
				for(Val v : TArray.from(args.get(0))) {
					s += ((char)TNumber.from(v).get());
				}
				return new TString(s);
			}
			return TUndefined.getInstance();
		};
		ext.export("stringOf", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			for(int i = 0; i < args.size(); i++) {
				System.out.println("#" + i + ":" + args.get(i));
			}
			return TUndefined.getInstance();
		};
		ext.export("testfunc", new InternalFunc(f, "a", "b", "c", "d"));
	}
}
