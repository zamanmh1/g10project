package com.mygdx.game.util;

import java.awt.Color;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @see https://github.com/jonbonazza/akatsuki
 */

public class Time {
	
	private int hour;
	private int min;
	
	public Time(Calendar cal) {
		hour = cal.get(Calendar.HOUR_OF_DAY);
		min = cal.get(Calendar.MINUTE);
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMin() {
		return min;
	}
	
	public static Time getTime(int scale) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(System.currentTimeMillis() * scale);
		
		return new Time(cal);
	}
	
	public static Time getTime() {
		return getTime(1);
	}
	
	/**
	 * Calculates a tint to be used for rendering based on time.
	 * @return the tint to be used for rendering
	 */
    public Color getTint() {

        // Credit to Ted Larue.
        double z = Math.cos((hour-14) * Math.PI / 12);
        float b = (float) (0.3f + 0.7f * (z + 1.0) / 2.0);

        return new Color(b, b, b, 1.0f);
    }
}
