package com.mrh0.qspl.internal;

import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.vm.extension.Extension;
import com.mrh0.qspl.vm.extension.ExtensionScope;

public class ExtMath implements Extension{

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
		return "default math libarary for qspl4";
	}

	@Override
	public void extend(ExtensionScope ext) {
		ext.export("PI", new TNumber(Math.PI));
		ext.export("INF", new TNumber(Double.POSITIVE_INFINITY));
		ext.export("INFINITY", new TNumber(Double.POSITIVE_INFINITY));
		ext.export("NEGINF", new TNumber(Double.NEGATIVE_INFINITY));
		ext.export("NEGATIVE_INFINITY", new TNumber(Double.NEGATIVE_INFINITY));
	}
}
