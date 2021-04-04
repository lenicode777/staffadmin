package dmp.staffadmin.utilities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DateManagerTest 
{
	private DateManager dateManager;
	
	@BeforeEach
	void inti()
	{
		this.dateManager = new DateManager();
	}
	
	@Test
	void testGetPeriod() throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = sdf.parse("01/01/2000");
		Date date2 = sdf.parse("02/01/2030");
		Period p = DateManager.getPeriod(date1, date2);
		assertAll
		(
			()->{assertEquals(30, p.getYears(), "Erreur sur l'année");},
			()->{assertEquals(0, p.getMonths(), "Erreur sur le mois");},
			()->{assertEquals(1, p.getDays(), "Erreur sur le jour");}
		);
		//System.out.println(dateManager.getPeriod(today, tomorow));
	}
}
