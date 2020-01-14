package com.mrh0.qspl.type;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mrh0.qspl.io.console.Console;

public class TypeRegistry {
	private static Random ran;
	private static Map<Integer, Class> types;
	static {
		ran = new Random();
		types = new HashMap<Integer, Class>();
	}
	
	public static int register(Class typeClass) {
		System.out.println("registered: " + typeClass);
		if(types.containsValue(typeClass)) {
			Console.g.err("Type '" + typeClass + "' has already been registered!");
			System.exit(0);
			return -1;
		}
		while(true) {
			int r = ran.nextInt();
			if(types.containsKey(r))
				continue;
			return r;
		}
	}
}
