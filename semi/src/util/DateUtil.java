package util;

import java.util.Calendar;

public class DateUtil {
	public boolean isValidDate(String date) {

		try {
			date = date.replaceAll("(\\.|\\-|/|:|\\s)", "");
			if (date.length() != 12)
				return false;

			int year, month, day;
			int hour, minute;

			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(4, 6));
			day = Integer.parseInt(date.substring(6, 8));
			hour = Integer.parseInt(date.substring(8, 10));
			minute = Integer.parseInt(date.substring(10, 12));

			Calendar now = Calendar.getInstance();
			now.set(year, month-1, day, hour, minute);
			
			int y = now.get(Calendar.YEAR);
			int m = now.get(Calendar.MONTH)+1;
			int d = now.get(Calendar.DATE);
			int h = now.get(Calendar.HOUR_OF_DAY);
			int min = now.get(Calendar.MINUTE);
			
			if(year != y || month != m || day != d || hour != h || minute != min ) return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String standardDate(String date) {
	
		date = date.replaceAll("(\\.|\\-|/|:|\\s)", "");
		
		String ans= date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6, 8)+" "+date.substring(8, 10)+":"+date.substring(10, 12);
		
		
		return ans;
	}
}
