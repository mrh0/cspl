package com.mrh0.qspl.internal;

import com.mrh0.qspl.internal.time.ExtTime;
import com.mrh0.qspl.vm.module.Module;

public class StandardModules {
	public static Module get(String key) {
		switch(key) {
			case "util":
				return new ExtUtil();
			case "math":
				return new ExtMath();
			case "json":
				return new ExtJSON();
			case "rand":
				return new ExtRandom();
			case "nblk":
				return new ExtConcurrency();
			case "http":
				return new ExtUtil();
			case "date":
				return new ExtTime();
			case "system":
				return new ExtUtil();
			case "stream":
				return new ExtUtil();
			case "swing":
				return new ExtUtil();
			case "shell":
				return new ExtShell();
			case "draw":
				return new ExtUtil();
		}
		return null;
	}
}
