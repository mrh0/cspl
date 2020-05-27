package com.mrh0.qspl.internal.time;

import java.util.Calendar;
import java.util.List;
import com.mrh0.qspl.io.console.Console;
import com.mrh0.qspl.type.TString;
import com.mrh0.qspl.type.TUndefined;
import com.mrh0.qspl.type.Val;
import com.mrh0.qspl.type.number.TNumber;
import com.mrh0.qspl.type.var.Var;

public class TDate implements Val {
	private Calendar date;
	
	public TDate() {
		this.date = Calendar.getInstance();
	}
	
	public TDate(Calendar date) {
		this.date = date;
	}
	
	@Override
	public String getTypeName() {
		return "date";
	}
	
	@Override
	public String toString() {
		return date.getTime().toString();
	}
	
	public boolean equals(TDate tdate) {
		return date.equals(tdate.date);
	}
	
	public boolean equals(Val tdate) {
		return compare(tdate) == 0;
	}
	
	@Override
	public int compare(Val v) {
		if(v instanceof TDate) {
			Calendar c = from(v).date;
			if(date.after(c))
				return 1;
			if(date.before(c))
				return -1;
			return 0;
		}
		return Val.super.compare(v);
	}
	
	@Override
	public Val accessor(List<Val> args) {
		String a = Val.simplifyOneArgumentAccessor(this, args);
		if(a == null)
			return TUndefined.getInstance();
		switch(a) {
			case "year":
				return TNumber.create(this.date.get(Calendar.YEAR));
			case "month":
				return TNumber.create(this.date.get(Calendar.MONTH));
			case "day":
				return TNumber.create(this.date.get(Calendar.DAY_OF_MONTH));
			case "hour":
				return TNumber.create(this.date.get(Calendar.HOUR));
			case "minute":
				return TNumber.create(this.date.get(Calendar.MINUTE));
			case "second":
				return TNumber.create(this.date.get(Calendar.SECOND));
			case "millisecond":
				return TNumber.create(this.date.get(Calendar.MILLISECOND));
			case "timeInMillis":
				return TNumber.create(this.date.getTimeInMillis());
			case "weekday":
				return TNumber.create(this.date.get(Calendar.DAY_OF_WEEK));
			case "week":
				return TNumber.create(this.date.get(Calendar.WEEK_OF_YEAR));
			case "localTimezone":
				return new TString(this.date.getTimeZone().getDisplayName());
			case "timezone":
				return new TString(this.date.getTimeZone().getID());
			case "timezoneOffset":
				return TNumber.create(this.date.getTimeZone().getRawOffset());
			case "usesDaylightTime":
				return TNumber.create(this.date.getTimeZone().useDaylightTime());
			case "daytimeSavings":
				return TNumber.create(this.date.getTimeZone().getDSTSavings());
			case "inDaylightTime":
				return TNumber.create(this.date.getTimeZone().inDaylightTime(this.date.getTime()));
		}
		return TUndefined.getInstance();
	}
	
	public static TDate from(Val v) {
		if(v instanceof TNumber)
			return (TDate)v;
		if(v instanceof Var)
			return from(((Var)v).get());
		Console.g.err("Cannot convert " + v.getTypeName() + " to number.");
		return null;
	}
}