package bizHourCalc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

class BusinessHourCalculator {

	//////////////// Constructor for the BusinessHourCalculator class //////////////////////
	
	BusinessHourCalculator(String defaultOpeningTime, String defaultClosingTime)
	{
		BusinessTime.setDefaultTimes(defaultOpeningTime,defaultClosingTime);
	}
	
	static SimpleDateFormat displayDateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
	
	
	
	////////////////////// Methods to customise opening hours for specific day /////////////////////////////
	
	static void setOpeningHours(String setDay, String openingTime, String closingTime)
	{
		BusinessTime.setOpeningHours(setDay, openingTime, closingTime);
		System.out.println("Specific days opening hours: " + BusinessTime.getDatesOpeningTimes());
	}
	
	// Method to customise opening hours for specific day given DayOfWeek
	static void setOpeningHours(DayOfWeek dow, String openingTime, String closingTime)
	{
		// Transform DayOfWeek to string date "CCYY-MM-DD"
		/*DayOfWeek[] listDowInput = {dow1};
		BusinessDay.convertDowtoCCYYMMDD(listDowInput);*/
		
		BusinessTime.setOpeningHours(dow, openingTime, closingTime);
		System.out.println("Week days opening hours: " + BusinessTime.getDowsOpeningTimes());
	}
	/*------------------------------------------------------------------------------------*/
	
	
	/////////// Methods to specify that the store is closed for a specific date. ////////////////
	
	// setClosed method with 3 String parameters
	static void setClosed(String closedDay1, String closedDay2, String closedDay3) 
	{
		BusinessDay.setClosed(closedDay1, closedDay2, closedDay3);
	}
	
	// setClosed overloading with 2 String parameters
	static void setClosed(String closedDay1, String closedDay2)
	{
		BusinessDay.setClosed(closedDay1, closedDay2, "");
	}
	
	// setClosed overloading with 1 String parameter
	static void setClosed(String closedDay1)
	{
		BusinessDay.setClosed(closedDay1, "", "");
	}
	/*------------------------------------------------------------------------------------*/
	
	
	/////////// Methods to specify that the store is closed for a specific day of week //////////
	
	// setClosed with 3 DayOfWeek parameters
	static void setClosed(DayOfWeek dow1, DayOfWeek dow2, DayOfWeek dow3)
	{
		BusinessDay.setClosed(dow1);
		BusinessDay.setClosed(dow2);
		BusinessDay.setClosed(dow3);
	}
	
	// setClosed overloading with 2 DayOfWeek parameters
	static void setClosed(DayOfWeek dow1, DayOfWeek dow2)
	{
		BusinessDay.setClosed(dow1);
		BusinessDay.setClosed(dow2);
	}
		
	// setClosed overloading with 1 DayOfWeek parameter
	static void setClosed(DayOfWeek dow1)
	{
		BusinessDay.setClosed(dow1);
	}
	/*------------------------------------------------------------------------------------*/
	
	
	///////////////////////////// calculateDeadline //////////////////////////////////////
	
	static Date calculateDeadline(int interval, String startDatetime)
	{
		
		String strInputDay = startDatetime.substring(0,10);
		Date dateNextDayOpenTime = new Date();
		
		// Convert startDatetime to Date type
		Date dateStartDatetime = convertStrToDate(startDatetime);
		
		
		// First check that start date is open
		boolean startDateTimeClosed = false;
		if ((BusinessDay.isClosed(startDatetime)) || (BusinessDay.isDowClosed(startDatetime)))
		{
			startDateTimeClosed = true;
		}
		
		
		// Check if opening time for the start date is later than the passed parameter
		String startOpenTime = BusinessTime.getOpenTime(strInputDay);
			
		// Convert open time to date format
		Date dateOpenTime = convertStrToDate(startOpenTime);
		
		// Compare start time and open time, start time can't be earlier than the opening time
		if (dateStartDatetime.before(dateOpenTime))
		{
			dateStartDatetime = dateOpenTime;
		}
		
		
		
		// Check if there is specific closing time for the start day.
		String startCloseTime = BusinessTime.getCloseTime(strInputDay);
		
		// Convert closing time to date format
		Date dateCloseTime = convertStrToDate(startCloseTime);
		
		
		// This adds the interval to the start datetime
		Date dateDeadline = addInterval(dateStartDatetime, interval);
		
		
		// There is enough seconds left on start day, and the start date is open,
		// return resulting deadline then
		if (dateDeadline.before(dateCloseTime) && !startDateTimeClosed)
		{
			return dateDeadline;
		}
		else
		{
			// Keep adding available seconds in following open days
			// while the interval is bigger than the available seconds
			int availSecsStartDay = Math.toIntExact(dateCloseTime.getTime() - dateStartDatetime.getTime())/1000;
			int intervalRemaining = interval - availSecsStartDay;
			int endInterval = 0;
			
			while (intervalRemaining > 0)
			{
				// Find if next day is set as closed. If so, check the following day until it's open
				String strNextDay = BusinessDay.nextDay(strInputDay);
				int i = 1;
				while ((BusinessDay.isClosed(strNextDay)) || (BusinessDay.isDowClosed(strNextDay)))
				{
					strNextDay = BusinessDay.nextDay(strNextDay);
					i++;
					
					if (i > 90)
					{
						System.out.println("No open days in the next 3 months.");
						break;
					}
				}
								
				
				// Get next day's opening time
				String nextDayOpenTime = BusinessTime.getOpenTime(strNextDay);
				
				// Get next day's closing time
				String nextDayCloseTime = BusinessTime.getCloseTime(strNextDay);
				
				
				// Transform to dates
				dateNextDayOpenTime = convertStrToDate(nextDayOpenTime);
				Date dateNextDayCloseTime = convertStrToDate(nextDayCloseTime);
				
				
				int availSecsNextDay = Math.toIntExact(dateNextDayCloseTime.getTime() - dateNextDayOpenTime.getTime())/1000;
				endInterval = intervalRemaining;
				intervalRemaining = intervalRemaining - availSecsNextDay;
			}
				
			// This adds the remaining interval to the open time
			Date dateNextDayDeadline = addInterval(dateNextDayOpenTime, endInterval);

			return dateNextDayDeadline;
		}

	}
	/*------------------------------------------------------------------------------------*/
	
	// Method to convert from string (in yyyy-MM-dd HH:mm format) to date
	static Date convertStrToDate(String strDate)
	{
		DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date outputDate = new Date();
		
		try
		{
			outputDate = timeFormat.parse(strDate);
		}
		catch (ParseException e)
		{
			System.out.println("Parameter " + strDate + " is not in a yyyy-MM-dd HH:mm format.");
		}
		
		return outputDate;
	}
	
	static Date convertStrToDay(String strDate)
	{
		DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date outputDate = new Date();
		
		try
		{
			outputDate = timeFormat.parse(strDate);
		}
		catch (ParseException e)
		{
			System.out.println("Parameter " + strDate + " is not in a yyyy-MM-dd HH:mm format.");
		}
		
		return outputDate;
	}

	// Method to add intervals to a specific datetime
	static Date addInterval(Date dateInput, int interval)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateInput);
		cal.add(Calendar.SECOND, interval);
		Date resultDate = cal.getTime();
		
		return resultDate;
	}
	
}
