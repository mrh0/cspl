package com.mrh0.qspl.io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {
	public static String readFromFile(String path) {
		String r = "";
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				r += br.readLine()+"\n";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	/*public static TObject readFile(String path) {
		String s = "";
		Exception ee = null;
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				s += br.readLine()+"\n";
		} catch (FileNotFoundException e) {
			ee = e;
			e.printStackTrace();
		} catch (IOException e) {
			ee = e;
			e.printStackTrace();
		}
		TObject r = new TObject();
		r.set("data", new TString(s));
		if(ee != null) {
			r.set("error", new TString(ee.toString()));
			r.set("successful", new TNumber(0));
		}
		else {
			r.set("error", TUndefined.getInstance());
			r.set("successful", new TNumber(1));
		}
		return r;
	}
	
	public static String getPath() {
		return (new File(".")).getAbsolutePath();
	}*/
}
