package bizHourCalc;

import java.time.DayOfWeek;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		
		// Instantiate the class BusinessHourCalculator using the constructor
		BusinessHourCalculator businessHourCalculator = new BusinessHourCalculator("9:00", "15:00");
		System.out.println("\n");
		
		// Customisation of the opening hours by specifying the day of the week or a specific date
		businessHourCalculator.setOpeningHours(DayOfWeek.FRIDAY, "10:00", "17:00");
		businessHourCalculator.setOpeningHours("2010-12-24", "8:00", "13:00");
		System.out.println("\n");
		
		// Specify that the store is closed for a specific day of week or date
		businessHourCalculator.setClosed(DayOfWeek.SUNDAY, DayOfWeek.WEDNESDAY);
		businessHourCalculator.setClosed("2010-12-25");
		System.out.println("\n");
		
		// Exercises' examples
		System.out.println("// example #1");
		Date deadlineExample1 = businessHourCalculator.calculateDeadline(2*60*60, "2010-06-07 09:10");
		System.out.println(BusinessHourCalculator.displayDateFormatter.format(deadlineExample1));
		System.out.println("\n");
		
		System.out.println("// example #2");
		Date deadlineExample2 = businessHourCalculator.calculateDeadline(15*60, "2010-06-08 14:48");
		System.out.println(BusinessHourCalculator.displayDateFormatter.format(deadlineExample2));
		System.out.println("\n");
		
		System.out.println("// example #3");
		Date deadlineExample3 = businessHourCalculator.calculateDeadline(7*60*60, "2010-12-24 6:45");
		System.out.println(BusinessHourCalculator.displayDateFormatter.format(deadlineExample3));
		System.out.println("\n");
		
	}

}
