package com.mrh0.qspl.util;

import java.util.Iterator;
import java.util.List;

import com.mrh0.qspl.type.var.Var;
import com.mrh0.qspl.type.var.VarDef;

public class StringUtil {
	public static String arrayToString(String start, Object[] arr, String end, String sep) {
		StringBuilder r = new StringBuilder();
		r.append(start);
		for(int i = 0; i < arr.length; i++) {
			r.append(arr[i].toString());
			if(i+1 < arr.length)
				r.append(sep);
		}
		r.append(end);
		return r.toString();
	}
	
	public static String arrayToString(String start, List<?> arr, String end, String sep) {
		StringBuilder r = new StringBuilder();
		r.append(start);
		for(int i = 0; i < arr.size(); i++) {
			r.append(arr.get(i).toString());
			if(i+1 < arr.size())
				r.append(sep);
		}
		r.append(end);
		return r.toString();
	}
	
	public static String arrayToString(String start, Iterable<?> iterable, String end, String sep) {
		StringBuilder r = new StringBuilder();
		r.append(start);
		Iterator<?> it = iterable.iterator();
		while(it.hasNext()) {
			r.append(it.next().toString());
			if(it.hasNext())
				r.append(sep);
		}
		r.append(end);
		return r.toString();
	}
	
	public static String arrayToString(Object[] arr, String sep) {
		return arrayToString("", arr, "", sep);
	}
	
	public static String arrayToString(String start, Object[] arr, String end) {
		return arrayToString(start, arr, end, ", ");
	}
	
	public static String arrayToString(Object[] arr) {
		return arrayToString("", arr, "", ", ");
	}
}
