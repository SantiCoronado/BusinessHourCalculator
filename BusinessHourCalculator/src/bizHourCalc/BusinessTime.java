package bizHourCalc;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

class BusinessTime 
{
	BusinessTime() {  // Class constructor
	
	}
	
	private static String setDefaultOpeningTime = new String();
	private static String setDefaultClosingTime = new String();
	private static List<List<String>> datesOpeningTimes = new ArrayList<List<String>>();
	private static List<List<Object>> dowOpeningTimes = new ArrayList<List<Object>>();
	
	
	///////////// Sets the defaults opening times for any non-specific day ///////////////////
	static void setDefaultTimes(String defaultOpeningTime, String defaultClosingTime) 
	{
		if (validateTime(defaultOpeningTime))
		{
			if (validateTime(defaultClosingTime))
			{
				setDefaultOpeningTime = defaultOpeningTime;
				setDefaultClosingTime = defaultClosingTime;
				
				System.out.println("The default opening and closing times have been set: " + "\n"
						+ "Opening time: " + setDefaultOpeningTime + "\n"
						+ "Closing time: " + setDefaultClosingTime);
			}
			else
			{
				System.out.println("The default closing time is not in a 24h format: " + defaultClosingTime);
			}
		}
		else
		{
			System.out.println("The default opening time is not in a 24h format: " + defaultOpeningTime);
		}
	}
	/*------------------------------------------------------------------------------------*/
	
	
	////////////////// Methods to get the set default opening times ///////////////////////
	static String getDefaultOpeningTime()
	{
		String defaultOpeningTime = setDefaultOpeningTime;
		return defaultOpeningTime;
	}
	
	static String getDefaultClosingTime()
	{
		String defaultClosingTime = setDefaultClosingTime;
		return defaultClosingTime;
	}
	/*------------------------------------------------------------------------------------*/
	
	
	//////////////// Method to customise opening hours for specific day ///////////////////
	static void setOpeningHours(String setDay, String openingTime, String closingTime) 
	{
		
		ArrayList<String> singleDayOpeningHours = new ArrayList<String>();
		boolean dayDuplicated = false;
		
		// Validates the setDay
		if (BusinessDay.validateDate(setDay))
		{
			// Check if day is already in datesOpeningTimes
			for (List<String> singleOpeningTime : datesOpeningTimes)
			{
				if (singleOpeningTime.get(0).equals(setDay))
				{
					dayDuplicated = true;
					System.out.println("There are already opening times for day " + setDay);
					break;
				}
			}
		
			if (!dayDuplicated)
			{
				// Validates that set times are in 24h format
				if (!BusinessTime.validateTime(openingTime))
				{
					System.out.println("The opening time is not in a 24h format: " + openingTime);
				}
				else if (!BusinessTime.validateTime(closingTime))
				{
					System.out.println("The closing time is not in a 24h format: " + closingTime);
				}
				else
				{
					
					singleDayOpeningHours.add(setDay);
					singleDayOpeningHours.add(openingTime);
					singleDayOpeningHours.add(closingTime);
					datesOpeningTimes.add(singleDayOpeningHours);
				}
			}
			
		}
	}
	/*------------------------------------------------------------------------------------*/
	
	
	///////////////// Method to customise opening hours for a day of week /////////////////
	static void setOpeningHours(DayOfWeek dow, String openingTime, String closingTime) 
	{
		
		ArrayList<Object> singleDowOpeningHours = new ArrayList<Object>();
		
		boolean validDow = true;
		// Check the DayOfWeek is not already set as closed
		for (DayOfWeek aClosedDow : BusinessDay.getClosedDows())
		{
			if (aClosedDow.equals(dow))
			{
				System.out.println("The day of the week " + dow + " was already set as closed.");
				validDow = false;
			}
			break;
		}
		
		// Check if day is already in dowOpeningTimes
		for (List<Object> aDowOpeningHours : dowOpeningTimes)
		{
			if (aDowOpeningHours.get(0).equals(dow))
			{
				System.out.println("There are already opening times for the week day " + dow);
				validDow = false;
			}
			break;
		}
		
		if (validDow)
		{
			// Validates that set times are in 24h format
			if (!BusinessTime.validateTime(openingTime))
			{
				System.out.println("The opening time is not in a 24h format: " + openingTime);
			}
			else if (!BusinessTime.validateTime(closingTime))
			{
				System.out.println("The closing time is not in a 24h format: " + closingTime);
			}
			else
			{
				
				singleDowOpeningHours.add(dow);
				singleDowOpeningHours.add(openingTime);
				singleDowOpeningHours.add(closingTime);
				dowOpeningTimes.add(singleDowOpeningHours);
			}
		}
	}
	/*------------------------------------------------------------------------------------*/			
		
	
	/////////////// Method to get current list of specific dates' opening times ////////////
	static List<List<String>> getDatesOpeningTimes()
	{
		List<List<String>> outputList = datesOpeningTimes;
		return outputList;
	}
	/*------------------------------------------------------------------------------------*/
	
	
	/////////// Method to get current list day of week's opening times ////////////////////
	static List<List<Object>> getDowsOpeningTimes()
	{
		List<List<Object>> outputList = dowOpeningTimes;
		return outputList;
	}
	/*------------------------------------------------------------------------------------*/
	
	
	////////////////////// Validates that the time is in a 24h format /////////////////////
	static boolean validateTime(String defaultTime)
	{
		boolean validTime = false;
		
		// Check if the string contains single digit hour or double digit hour
		String defaultHour = defaultTime.substring(0, defaultTime.indexOf(":"));
		if (defaultHour.length() == 1)
		{
			defaultTime = "0" + defaultTime;
		}
		
		try 
		{
	        LocalTime.parse(defaultTime);
	        validTime = true;
	    }
		
		catch (DateTimeParseException | NullPointerException e) 
		{
	        validTime = false;
	    }
		return validTime;
	}
	/*------------------------------------------------------------------------------------*/
	
	
	/////// Get methods for returning the opening and closing times given a specific day //////
	static String getSpecificOpenTime(String strInputDay)
	{
		String daysOpenTime = new String();
		
		for (List<String> singleOpeningTime : datesOpeningTimes)
		{
			if (singleOpeningTime.get(0).equals(strInputDay))
			{
				// Get opening time for the day
				daysOpenTime = strInputDay;
				daysOpenTime += " " + singleOpeningTime.get(1);
				break;
			}
		}
		return daysOpenTime;
	}
	
	static String getSpecificCloseTime(String strInputDay)
	{
		String daysCloseTime = new String();
		
		for (List<String> singleOpeningTime : datesOpeningTimes)
		{
			if (singleOpeningTime.get(0).equals(strInputDay))
			{
				// Get closing time for the day
				daysCloseTime = strInputDay;
				daysCloseTime += " " + singleOpeningTime.get(2);
				break;
			}
		}
		return daysCloseTime;
	}
	/*------------------------------------------------------------------------------------*/
	
	
	///// Get methods to either populate specific open/close times or the default ones for a given day /////
	static String getOpenTime(String strInputDay)
	{
		String nextDayOpenTime = BusinessTime.getSpecificOpenTime(strInputDay);
		if (nextDayOpenTime.isEmpty())
		{
			nextDayOpenTime = strInputDay + " " + BusinessTime.getDefaultOpeningTime();
		}
		return nextDayOpenTime;
	}
	
	static String getCloseTime(String strInputDay)
	{
		String nextDayOpenTime = BusinessTime.getSpecificCloseTime(strInputDay);
		if (nextDayOpenTime.isEmpty())
		{
			nextDayOpenTime = strInputDay + " " + BusinessTime.getDefaultClosingTime();
		}
		return nextDayOpenTime;
	}
	/*------------------------------------------------------------------------------------*/
}
