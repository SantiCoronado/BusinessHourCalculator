package bizHourCalc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;

class BusinessDay
{

	public BusinessDay()	{  // Class constructor
		
	}
	
	private static SimpleDateFormat inputDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	//private static Date dateFormatted = new Date();
	private static List<String> setClosedDates = new ArrayList<String>();
	private static List<DayOfWeek> setClosedDows = new ArrayList<DayOfWeek>();
	static String strDow1 = new String();
	static String strDow2 = new String();
	static String strDow3 = new String();
	
	
////////////// Method to convert DayOfWeek into a String yyyy-MM-dd format ///////////////////
	
	// Well, after reading again the exercise's examples, it looks like this converter
	// is not necessary. DayOfWeek inputs are for every single week on that week day, and not 
	// a specific day.
	//
	// I'll leave it in case it could be handy in the future (extra points?)
	
	static void convertDowtoCCYYMMDD(DayOfWeek[] listDowInput)
	{
		
		// Get today's DayOfWeek as integer
		Calendar todaysCalendar = Calendar.getInstance();
		int intTodayDow = todaysCalendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (intTodayDow == 0)
		{
			intTodayDow = 7;
		}
		
		// Iterate through the parameters to calculate calendar date considering
		// the difference between today's date and given DayOfWeek
		int intParam = 1;
		for (DayOfWeek dowParam : listDowInput)
		{
			Calendar calendarDow = Calendar.getInstance();
			int intDayDiffer = dowParam.getValue() - intTodayDow;
			calendarDow.add(Calendar.DATE, intDayDiffer);
			
			// Convert calendar to date
	        Date currentDatePlusDow = calendarDow.getTime();
	        
	        // Convert date to string with yyyy-MM-dd format
	        String strDow = inputDateFormatter.format(currentDatePlusDow);
	        
	        switch(intParam)
	        {
	        	case 1:
	        		strDow1 = strDow;
	        	  break;
		        case 2:
		        	strDow2 = strDow;
		          break;
		        case 3:
		        	strDow3 = strDow;
		          break;
	        }
	        ++intParam;
		}
	}
	/*------------------------------------------------------------------------------------*/
	
	
	/////////// Method specify that the store is closed for a specific date. //////////////////
	static void setClosed(String closedDay1, String closedDay2, String closedDay3)
	{

		String[] strClosedDays = {closedDay1, closedDay2, closedDay3};
		
		for (String strClosedDay : strClosedDays)
		{
			if (!strClosedDay.isEmpty())		// Check strClosedDay parameter is not null
			{
				if (validateDate(strClosedDay))
				{
					
					// Check if setClosedDates already contains that day
					if (setClosedDates.contains(strClosedDay))
					{
						System.out.println("The date " + strClosedDay + " was already set as closed.");
					}
					else
					{
						setClosedDates.add(strClosedDay);
						System.out.println("Date " + strClosedDay + " has been set as closed.");
					}
				}
				else
				{
					System.out.println("Date " + strClosedDay + " is not in a CCYY-MM-DD format.");
				}
			}
		}
		System.out.println("List of closed dates: " + setClosedDates);
	}
	/*------------------------------------------------------------------------------------*/
	
	
	/////// Method to validate that setClosed specifics date's are in a valid CCYY-MM-DD format //////
	static boolean validateDate(String strDate)
	{
		boolean validDate = false;
		try 
		{
			inputDateFormatter.setLenient(false);
			inputDateFormatter.parse(strDate);
            validDate = true;		// date is a valid CCYY-MM-DD
        } 
		catch (ParseException e) 
		{
			validDate = false;
        }
		
        return validDate;
	}
	/*------------------------------------------------------------------------------------*/
	
	
	/////////////// Method to set closed a day of the week, every week. /////////////////////
	static void setClosed(DayOfWeek closedDay)
	{
		boolean validDow = true;
		// Check the DayOfWeek is not already set as closed
		for (DayOfWeek dow : setClosedDows)
		{
			if (dow.equals(closedDay))
			{
				System.out.println("The day of the week " + closedDay + " was already set as closed.");
				validDow = false;
			}
			break;
		}
		
		if (validDow)
		{
			setClosedDows.add(closedDay);
		}
		
		System.out.println("List of closed week days: " + setClosedDows);
	}
	/*------------------------------------------------------------------------------------*/
	
	
	//////////// Method to get current list of closed days (setClosedDates) //////////////
	static List<String> getClosedDates()
	{
		List<String> outputList = setClosedDates;
		return outputList;
	}
	
	// Method to get current list of closed week days
	static List<DayOfWeek> getClosedDows()
	{
		List<DayOfWeek> outputList = setClosedDows;
		return outputList;
	}
	
	// Method to check if day is closed or not
	static boolean isClosed(String strDay)
	{
		boolean closed = false;
		
		if (setClosedDates.contains(strDay))
		{
			closed = true;
		}
		return closed;
	}
	/*------------------------------------------------------------------------------------*/
	
	
	////////////////// Method to check if week day is closed or not ///////////////////////
	static boolean isClosed(DayOfWeek dow)
	{
		boolean closed = false;
		
		if (setClosedDows.contains(dow))
		{
			closed = true;
		}
		return closed;
	}
	/*------------------------------------------------------------------------------------*/
	
	
	/////////// Method to check if week day is closed or not - using CCYY-MM-DD ////////////
	static boolean isDowClosed(String strDay)
	{
		boolean closed = false;
		
		Date dateDowClosed = BusinessHourCalculator.convertStrToDay(strDay);

		// Get date's DayOfWeek as integer
		Calendar c = Calendar.getInstance();
		c.setTime(dateDowClosed);
		int intNextDayDow = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (intNextDayDow == 0)
		{
			intNextDayDow = 7;
		}
		
		
		switch (intNextDayDow)
		{
		  case 1:
			  closed = BusinessDay.isClosed(DayOfWeek.MONDAY);
			  break;
		  case 2:
			  closed = BusinessDay.isClosed(DayOfWeek.TUESDAY);
			  break;
		  case 3:
			  closed = BusinessDay.isClosed(DayOfWeek.WEDNESDAY);
			  break;
		  case 4:
			  closed = BusinessDay.isClosed(DayOfWeek.THURSDAY);
			  break;
		  case 5:
			  closed = BusinessDay.isClosed(DayOfWeek.FRIDAY);
			  break;
		  case 6:
			  closed = BusinessDay.isClosed(DayOfWeek.SATURDAY);
			  break;
		  case 7:
			  closed = BusinessDay.isClosed(DayOfWeek.SUNDAY);
			  break;
		}
		return closed;
	}
	/*------------------------------------------------------------------------------------*/
	
	
	//////////////// Method to generate next calendar day (yyyy-MM-dd format) /////////////
	static String nextDay(String currentDay)
	{
		DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateCurrentDay = new Date();
		Date dateNextDay = new Date();
		
		try
		{
			dateCurrentDay = dayFormat.parse(currentDay);
		}
		catch (ParseException e)
		{
			System.out.println("Parameter's date is not in a yyyy-MM-dd format.");
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateCurrentDay);
		cal.add(Calendar.DATE, 1);
		dateNextDay = cal.getTime();
		String strNextDay = dayFormat.format(dateNextDay);
		return strNextDay;
	}
	/*------------------------------------------------------------------------------------*/
	
	
}
