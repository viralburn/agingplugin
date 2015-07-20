package ch.oblivion.confluence.plugins.aging.utilities;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class DateUtilities {
	
	public static String getElapsedTime(Date from) {
		return getPeriodText(from, new Date());
	}
	
	public static String getPeriodText(Date start, Date end) {
		DateTime startInstant = new DateTime(start.getTime());
		DateTime endInstant = new DateTime(end.getTime());
		Duration duration = new Duration(startInstant, endInstant);
		
		PeriodFormatterBuilder builder = new PeriodFormatterBuilder()
			.printZeroNever();
		
		if (duration.getStandardDays() >= 32) {
			builder
				.appendYears().appendSuffix(" year", " years")
				.appendSeparator(", ", " and ")
				.appendMonths().appendSuffix(" month", " months")
				.appendSeparator(", ", " and ")
				.appendDays().appendSuffix(" day", " days");
			
		} else if (duration.getStandardDays() >= 1) {
			builder
				.appendDays().appendSuffix(" day", " days")
				.appendSeparator(", ", " and ")
				.appendHours().appendSuffix(" hour", " hours");
			
		} else {
			builder
				.appendHours().appendSuffix(" hour", " hours")
				.appendSeparator(", ", " and ")
				.appendMinutes().appendSuffix(" minute", " minutes");
		}
			
		PeriodFormatter formatter = builder.toFormatter(); 
		return formatter.print(duration.toPeriodFrom(startInstant));
	}
}
