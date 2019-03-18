package prisonescape.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import prisonescape.game.util.Time;

public class TestSetTime {

	@Test
	public void testSetTime_ChangeDay_SameMonth() {
		Calendar cal = new GregorianCalendar(1995, 12, 24, 7, 0);
		Time time = Time.getTime(cal);
		
		time = Time.setTime(time.getCalendar(), 6, 0);
				
		assertEquals(25, time.getCalendar().get(Calendar.DATE));
		assertEquals(6, time.getHour());
		assertEquals(0, time.getMin());		
	}
	
	@Test
	public void testSetTime_ChangeDay_DifferentMonth_SameYear() {
		/**
		 * @see Calendar.MONTH
		 *  -> Months begin at 0 (Jan == 0).
		 */		
		
		// So this is 30th of November, not October.
		Calendar cal = new GregorianCalendar(1995, 10, 30, 7, 0);		
		Time time = Time.getTime(cal);		
		time = Time.setTime(time.getCalendar(), 6, 0);
				
		// Hence, changes to 1st day of month as the new month is December.
		assertEquals(1, time.getCalendar().get(Calendar.DATE));
		assertEquals(11, time.getCalendar().get(Calendar.MONTH));
		assertEquals(6, time.getHour());
		assertEquals(0, time.getMin());		
	}
	
	@Test
	public void testSetTime_ChangeDay_DifferentMonth_DifferentYear() {
		Calendar cal = new GregorianCalendar(1995, 11, 31, 7, 0);		
		Time time = Time.getTime(cal);		
		time = Time.setTime(time.getCalendar(), 6, 19);
				
		assertEquals(1, time.getCalendar().get(Calendar.DATE));
		assertEquals(0, time.getCalendar().get(Calendar.MONTH));
		assertEquals(1996, time.getCalendar().get(Calendar.YEAR));
		assertEquals(6, time.getHour());
		assertEquals(19, time.getMin());		
	}
	
	@Test
	public void testSetTime_SameDay_DifferentHour() {
		Calendar cal = new GregorianCalendar(1995, 12, 24, 7, 0);
		Time time = Time.getTime(cal);
		
		time = Time.setTime(time.getCalendar(), 18, 36);
				
		assertEquals(24, time.getCalendar().get(Calendar.DATE));
		assertEquals(18, time.getHour());
		assertEquals(36, time.getMin());		
	}
	
	@Test
	public void testSetTime_SameDay_SameHour() {
		Calendar cal = new GregorianCalendar(1995, 12, 24, 7, 0);
		Time time = Time.getTime(cal);
		
		time = Time.setTime(time.getCalendar(), 7, 42);
				
		assertEquals(24, time.getCalendar().get(Calendar.DATE));
		assertEquals(7, time.getHour());
		assertEquals(42, time.getMin());		
	}
}
