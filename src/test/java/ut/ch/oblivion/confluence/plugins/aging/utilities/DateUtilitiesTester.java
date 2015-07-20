package ut.ch.oblivion.confluence.plugins.aging.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.oblivion.confluence.plugins.aging.utilities.DateUtilities;

public class DateUtilitiesTester {

	private Logger logger = LoggerFactory.getLogger(DateUtilitiesTester.class);
	
	@Test
	public void testPeriodFormatter() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date from = sdf.parse("2014-01-01");
		Date to = sdf.parse("2014-03-05");
		String text = DateUtilities.getPeriodText(from, to);
		System.out.println();
		logger.info("Value : {} - {} Formatted : {}", new Object[] {from, to, text});
	}
	
}
