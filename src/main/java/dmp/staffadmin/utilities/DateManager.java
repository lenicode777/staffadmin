package dmp.staffadmin.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateManager 
{
	public static Period getPeriod(Date dateDepart, Date dateArrivee)
	{
//		System.out.println("===================DateManager getPeriodMethod=================");
//		System.out.println("Date de départ = " + dateDepart + " | Date arrivée = " + dateArrivee);
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
//		System.out.println("AVANT setDateDepart");
		calendar1.setTime(dateDepart);
		calendar2.setTime(dateArrivee);
//		System.out.println("APRES setDateDepart et Fin");
		
//		System.out.println("DAY 1 = " + calendar1.get(calendar1.DAY_OF_MONTH));
//		System.out.println("Month 1 = " + calendar1.get( calendar1.MONTH));
//		System.out.println("YEAR 1 = " + calendar1.get(calendar1.YEAR));

//		System.out.println("AVANT setLocalDate");
		LocalDate ld1 = LocalDate.of(calendar1.get(calendar1.YEAR),calendar1.get(calendar1.MONTH)+1, calendar1.get(calendar1.DAY_OF_MONTH));
		LocalDate ld2 = LocalDate.of(calendar2.get(calendar2.YEAR),calendar2.get(calendar2.MONTH)+1, calendar2.get(calendar2.DAY_OF_MONTH));
		Period period = Period.between(ld1, ld2);
//		System.out.println("APRES setLocalDate");
		return period;
	}
	
	public static String getRemainTime(Date dateDepart, Date dateArrivee)
	{
		Period p = getPeriod(dateDepart, dateArrivee);
		long nbYears = p.getYears();
		long nbMonths = p.getMonths();
		long nbDays = p.getDays();
		String remainTime = String.valueOf(nbYears);
		
		if (nbYears > 1 )
		{
			remainTime = String.valueOf(nbYears) + " ans, ";
		}
		else
		{
			remainTime = String.valueOf(nbYears) + " an, ";
		}
		remainTime += String.valueOf(nbMonths) + " mois et ";
		if(nbDays>1)
		{
			remainTime += String.valueOf(nbDays) + " jours";
		}
		else
		{
			remainTime += String.valueOf(nbDays) + " jour";
		}
		return remainTime;
	}
	public static long dateDiff(Date dateDebut, Date dateFin)
	{
		Period p = getPeriod(dateDebut, dateFin);
		return p.getYears();
	}
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
	
	public static void main(String[] args) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		Date dateNaissance = sdf.parse("01-01-2000");
		System.out.println(dateNaissance);
		System.out.println("Age = " +dateDiff(dateNaissance, new Date()));
		System.out.println(addMinutes(new Date(), 20));
		System.out.println(getRemainTime(dateNaissance, new Date()));
	}
}
