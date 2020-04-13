package com.mrh0.qspl.vm.module;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import com.mrh0.qspl.io.console.Console;

public class JarLoader {
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static Module getExtension(String path, String classname) throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
		File dir = new File(path);
		URL loadPath = dir.toURI().toURL();
		URL[] classUrl = new URL[]{loadPath};

		ClassLoader cl = new URLClassLoader(classUrl);

		Class loadedClass = cl.loadClass(classname);
		
		Object obj = loadedClass.newInstance();
		if(obj instanceof Module)
			return (Module) obj;
		Console.g.err("Class is not implementing Extension.");
		return null;
	}
}
