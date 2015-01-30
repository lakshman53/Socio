package tds.libs;

/**
 * Created by laks on 29-01-2015.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimestampConvertor {

    public String usingDateAndCalendar(long input){
        Date date = new Date(input);
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return(cal.get(Calendar.YEAR)
                + "/" + cal.get(Calendar.MONTH)
                + "/" + cal.get(Calendar.DATE)
                + " " + cal.get(Calendar.HOUR)
                + ":" + cal.get(Calendar.MINUTE)
                + ":" + cal.get(Calendar.SECOND)
                + (cal.get(Calendar.AM_PM)==0?"AM":"PM")
        );

    }

    public String usingDateAndCalendarWithTimeZone(long input){
        Date date = new Date(input);
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+5:30"));
        cal.setTime(date);
        return(cal.get(Calendar.DATE)
                + "/" + cal.get(Calendar.MONTH)
                + "/" + cal.get(Calendar.YEAR)
                + " " + cal.get(Calendar.HOUR)
                + ":" + cal.get(Calendar.MINUTE)
                + ":" + cal.get(Calendar.SECOND)
                + (cal.get(Calendar.AM_PM)==0?"AM":"PM")
        );

    }
    public String usingDateFormatter(long input){
        Date date = new Date(input);
        Calendar cal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd hh:mm:ss z");
        sdf.setCalendar(cal);
        cal.setTime(date);
        return sdf.format(date);
    }

    public String usingDateFormatterWithTimeZone(long input){
        Date date = new Date(input);
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd hh:mm:ss z");
        sdf.setCalendar(cal);
        cal.setTime(date);
        return sdf.format(date);
    }
}
