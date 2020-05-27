package com.mrh0.qspl.internal;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mrh0.qspl.type.TArray;
import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.TString;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.func.Arguments;
import com.mrh0.qspl.type.func.IFunc;
import com.mrh0.qspl.type.func.InternalFunc;
import com.mrh0.qspl.vm.VM;
import com.mrh0.qspl.vm.module.Module;
import com.mrh0.qspl.vm.module.ModuleScope;

public class ExtJSON implements Module {

	@Override
	public String getName() {
		return "mrh0.cspl.json";
	}

	@Override
	public String getAuthor() {
		return "mrh0";
	}

	@Override
	public String getDescription() {
		return "JSON";
	}

	@Override
	public void extend(ModuleScope ext) {
		IFunc f;
		
		f = (VM vm, Val _this, Arguments args) -> {
			if(args.get(0).isContainer())
				return new TString(TContainer.from(args.get(0)).toJSON().toString());
			if(args.get(0).isArray())
				return new TString(TArray.from(args.get(0)).toJSON().toString());
			return new TString(args.get(0).toString());
		};
		ext.export("serialize", new InternalFunc(f));
		
		f = (VM vm, Val _this, Arguments args) -> {
			String s = TString.from(args.get(0)).get();
			if(s.startsWith("["))
				return TArray.fromJSON(new JSONArray(s));
			return TContainer.fromJSON(new JSONObject(s));
		};
		ext.export("parse", new InternalFunc(f));
	}
	
	
}
