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
}
