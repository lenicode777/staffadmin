package dmp.staffadmin.utilities;

import java.util.Calendar;
import java.util.Date;

public class DateManager 
{
	public static Date addDates(Date dateDebut, int duree, int periodeField)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(dateDebut);
		c.add(periodeField, duree);
		return c.getTime();
	}
	
	public static Date addYears(Date dateDebut, int duree)
	{
		return addDates(dateDebut, duree, Calendar.YEAR);
	}
	
	public static Date addMonths(Date dateDebut, int duree)
	{
		return addDates(dateDebut, duree, Calendar.MONTH);
	}
	
	public static Date addDays(Date dateDebut, int duree)
	{
		return addDates(dateDebut, duree, Calendar.DAY_OF_YEAR); // A tester
	}
	
	public static Date addHours(Date dateDebut, int duree)
	{
		return addDates(dateDebut, duree, Calendar.HOUR); // A tester
	}
	
	public static Date addMinutes(Date dateDebut, int duree)
	{
		return addDates(dateDebut, duree, Calendar.MINUTE); // A tester
	}
	
	public static Date addSecondes(Date dateDebut, int duree)
	{
		return addDates(dateDebut, duree, Calendar.SECOND); // A tester
	}
}
