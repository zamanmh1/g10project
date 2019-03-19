package prisonescape.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import prisonescape.game.util.Time;

/**
 * A class which tests setting the time of a Time object.
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class TestSetTime {

	/**
	 * This test changes the time to the following day, when the next
	 * day is in the same month.
	 */
	@Test
	public void testSetTime_ChangeDay_SameMonth() {
		// Creates a date to be tested.
		Calendar cal = new GregorianCalendar(1995, 12, 24, 7, 0);
		Time time = Time.getTime(cal);
		
		// Set time to next occurrence of given time.
		time = Time.setTime(time.getCalendar(), 6, 0);
				
		// Assert that date is the next day.
		assertEquals(25, time.getCalendar().get(Calendar.DATE));
		
		// Assert that time is also correct.
		assertEquals(6, time.getHour());
		assertEquals(0, time.getMin());		
	}
	
	/**
	 * This test changes the time to the following day, where the next 
	 * day is in a different month.
	 */
	@Test
	public void testSetTime_ChangeDay_DifferentMonth_SameYear() {
		/**
		 * @see Calendar.MONTH
		 *  -> Months begin at 0 (Jan == 0).
		 */		
		
		// Creates a date to be tested.
		// So this is 30th of November, not October.
		Calendar cal = new GregorianCalendar(1995, 10, 30, 7, 0);		
		Time time = Time.getTime(cal);		
		
		// Set the time to the next occurrence of given time.
		time = Time.setTime(time.getCalendar(), 6, 0);
				
		// Assert that day of month and month are correct. 
		// Hence, changes to 1st day of month as the new month is December.
		assertEquals(1, time.getCalendar().get(Calendar.DATE));
		assertEquals(11, time.getCalendar().get(Calendar.MONTH));
		
		// Assert that time is also correct.
		assertEquals(6, time.getHour());
		assertEquals(0, time.getMin());		
	}
	
	/**
	 * This test changes the time to the following day, where the next
	 * day is in a different month and year.
	 */
	@Test
	public void testSetTime_ChangeDay_DifferentMonth_DifferentYear() {
		// Creates a date to be tested.
		Calendar cal = new GregorianCalendar(1995, 11, 31, 7, 0);		
		Time time = Time.getTime(cal);	
		
		// Set the time to the next occurrence of given time.
		time = Time.setTime(time.getCalendar(), 6, 19);
				
		// Assert that day of month, month and year are correct.
		assertEquals(1, time.getCalendar().get(Calendar.DATE));
		assertEquals(0, time.getCalendar().get(Calendar.MONTH));
		assertEquals(1996, time.getCalendar().get(Calendar.YEAR));
		
		// Assert that the time is also correct.
		assertEquals(6, time.getHour());
		assertEquals(19, time.getMin());		
	}
	
	/**
	 * This test changes the time to a different hour within the same
	 * day.
	 */
	@Test
	public void testSetTime_SameDay_DifferentHour() {
		// Creates a date to be tested.
		Calendar cal = new GregorianCalendar(1995, 12, 24, 7, 0);
		Time time = Time.getTime(cal);
		
		// Sets the time to later in the same day.
		time = Time.setTime(time.getCalendar(), 18, 36);
				
		// Asserts that the date is still the same.
		assertEquals(24, time.getCalendar().get(Calendar.DATE));
		
		// Assert that time is also correct.
		assertEquals(18, time.getHour());
		assertEquals(36, time.getMin());		
	}
	
	/**
	 * This test changes the time to a diferent minute within the same hour.
	 */
	@Test
	public void testSetTime_SameDay_SameHour() {
		// Create a date to be tested.
		Calendar cal = new GregorianCalendar(1995, 12, 24, 7, 0);
		Time time = Time.getTime(cal);
		
		// Sets the time to later in the hour.
		time = Time.setTime(time.getCalendar(), 7, 42);
				
		// Assert that date and hour are still the same.
		assertEquals(24, time.getCalendar().get(Calendar.DATE));
		assertEquals(7, time.getHour());
		
		// Assert that minute has been updated correctly.
		assertEquals(42, time.getMin());		
	}
}
