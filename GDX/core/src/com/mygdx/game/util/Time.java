package com.mygdx.game.util;

import java.awt.*;
import java.util.Calendar;

/**
 *	@see https://github.com/jonbonazza/akatsuki
 */

public class Time {

	private static long lastTime = System.currentTimeMillis();	
    private Calendar cal;

    private Time(Calendar cal) {
        this.cal = cal;
    }

    public int getHour() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public int getMin() {
        return cal.get(Calendar.MINUTE);
    }
    
    public Calendar getCalendar() {
    	return this.cal;
    }

    public static Time getTime(Calendar cal, int scale) {  	
    	long currentTime = System.currentTimeMillis();
    	long difference = currentTime - lastTime;
    	long timePassed = difference * scale;
    	   	
        cal.setTimeInMillis(cal.getTimeInMillis() + timePassed);        
        lastTime = currentTime;

        return new Time(cal);
    }

    public static Time getTime(Calendar cal) {
        return getTime(cal, 1);
    }

    public Color getTint() {

        // Credit to Ted Larue.
        double z = Math.cos((getHour()-14) * Math.PI / 12);
        float b = (float) (0.3f + 0.7f * (z + 1.0) / 2.0);

        return new Color(b, b, b, 1.0f);
    }
}