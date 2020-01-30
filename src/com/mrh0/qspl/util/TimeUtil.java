package com.mrh0.qspl.util;

public class TimeUtil {
	public static long getMilis() {
		return System.currentTimeMillis();
	}
	
	public static long getDifMilis(long start) {
		return System.currentTimeMillis() - start;
	}
}
