package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.prisonescapegame.GameSettings;
import java.util.Calendar;

/**
 * A class for the manipulation of time within the game.
 * 
 * Credit for structure of class and some methods: https://github.com/jonbonazza/akatsuki/blob/master/src/akatsuki/Time.java
 * 
 * @author Sam Ward
 * 
 * @version 1.0
 * @since 1.0
 * 
 */

public class Time {

	private static long lastTime = System.currentTimeMillis(); // The last time that the getTime method was called.
    private Calendar cal; // Stores the current time and date information.

    /**
     * Creates a new Time object.
     * 
     * @param cal The current time and date information.
     */
    private Time(Calendar cal) {
        this.cal = cal;
    }

    /**
     * Provides the current hour of the day stored in the calendar.
     * 
     * @return The current hour of the day.
     */
    public int getHour() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Provides the current minute of the hour stored in the calendar.
     * 
     * @return The current minute of the hour.
     */
    public int getMin() {
        return cal.get(Calendar.MINUTE);
    }
    
    /**
     * Provides the complete current date and time information.
     * 
     * @return The calendar containing current date and time.
     */
    public Calendar getCalendar() {
    	return this.cal;
    }   
    
    /**
     * Finds whether it is currently daytime or not.
     *  
     * @return True if it is daytime, or false if it is nighttime.
     */
    public boolean isDay() {   	
    	if (getHour() >= GameSettings.HOUR_DAY_BEGINS && getHour() < GameSettings.HOUR_NIGHT_BEGINS) {
    		return true;
    	} 
    	return false;
    }
    
    /**
     * Sets the time forward to the first occurrence of the exact hour and minute provided.
     * 
     * @param cal The current time and date information.
     * @param hour The hour to advance to.
     * @param minute The minute to advance to.
     * 
     * @return A Time object with the first occurrence of the given hour and minute after the calendar provided. 
     */
    public static Time setTime(Calendar cal, int hour, int minute) {   	
    	if (hour < cal.get(Calendar.HOUR_OF_DAY) || 
    			hour == cal.get(Calendar.HOUR_OF_DAY) && minute < cal.get(Calendar.MINUTE)) {
    		cal.add(Calendar.DATE, 1);
    	}
    	
    	cal.set(Calendar.HOUR_OF_DAY, hour);
    	cal.set(Calendar.MINUTE, minute);
    	
    	return new Time(cal);
    }

    /**
     * Advances the time based on a given time scale and the last time the method was called.
     * 
     * @param cal The current time and date information.
     * @param scale The scale to advance time by (scale = gameTime/realWorldTime).
     * 
     * @return The new Time.
     */
    public static Time getTime(Calendar cal, int scale) {  	
    	long currentTime = System.currentTimeMillis();
    	long difference = currentTime - lastTime;
    	long timePassed = difference * scale;
    	   	
        cal.setTimeInMillis(cal.getTimeInMillis() + timePassed);        
        lastTime = currentTime;

        return new Time(cal);
    }

    /**
     * Provides the current time in the game.
     * 
     * @param cal The current time and date information.
     * 
     * @return The current Time object.
     */
    public static Time getTime(Calendar cal) {
        return getTime(cal, 1);
    }
    
    /**
     * Returns the current time as a string that can be used.
     * 
     * @return Time in string format hh:mm.
     */
    public String toString() {	
		String hour = String.format("%02d", getHour());
		String minutes = String.format("%02d", getMin());
		
		return hour + ":" + minutes;
    }

    /**
     * Provides a colour tint to be used over the map depending on the time of day that it is.
     * 
     * @return A colour tint.
     */
    public Color getTint() {
        double z = Math.cos((getHour()-14) * Math.PI / 12);
        float b = (float) (0.3f + 0.7f * (z + 1.0) / 2.0);

        return new Color(b, b, b, 1.0f);
    }
}