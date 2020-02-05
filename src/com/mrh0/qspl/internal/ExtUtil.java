package com.mrh0.qspl.internal;

import java.util.ArrayList;
import java.util.Scanner;
import com.mrh0.qspl.type.TString;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.IFunc;
import com.mrh0.qspl.type.func.InternalFunc;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.vm.VM;
import com.mrh0.qspl.vm.extension.Extension;
import com.mrh0.qspl.vm.extension.ExtensionScope;

public class ExtUtil implements Extension{

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
	public void extend(ExtensionScope ext) {
		IFunc f;
		
		f = (VM vm, Val _this, ArrayList<Val> args) -> {
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
		
		f = (VM vm, Val _this, ArrayList<Val> args) -> {
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
		
		f = (VM vm, Val _this, ArrayList<Val> args) -> {
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
		
		f = (VM vm, Val _this, ArrayList<Val> args) -> {
			System.exit(0);
			return TUndefined.getInstance();
		};
		ext.export("stop", new InternalFunc(f));
		
		f = (VM vm, Val _this, ArrayList<Val> args) -> {
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
		
		f = (VM vm, Val _this, ArrayList<Val> args) -> {
			if(args.size() == 0)
				return TUndefined.getInstance();
			return vm.getVariable(TString.from(args.get(0)).get());
		};
		ext.export("valueOf", new InternalFunc(f));
	}
}
