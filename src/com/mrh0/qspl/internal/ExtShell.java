package com.mrh0.qspl.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mrh0.qspl.type.TArray;
import com.mrh0.qspl.type.TContainer;
import com.mrh0.qspl.type.TString;
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

public class ExtShell implements Module {

	@Override
	public String getName() {
		return "mrh0.cspl.shell";
	}

	@Override
	public String getAuthor() {
		return "mrh0";
	}

	@Override
	public String getDescription() {
		return "Shell";
	}

	@Override
	public void extend(ModuleScope ext) {
		IFunc f;
		
		f = (VM vm, Val _this, Arguments args) -> {
			TContainer r = execSync(TString.string(args.get(0)), null, TString.string(args.get(1)));
			return r==null?TUndefined.getInstance():r;
		};
		ext.export("execSync", new InternalFunc(f));
	}
	
	private TContainer execSync(String cmd, String[] params, String path) {
		try {
			Process process = Runtime.getRuntime().exec(
					cmd, params, new File(path));

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			
			return new TContainer(
					new Var("exit", TNumber.create(exitVal)),
					new Var("output", new TString(output))
				);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}