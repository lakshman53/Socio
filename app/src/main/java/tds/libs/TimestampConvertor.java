package tds.libs;

/**
 * Created by laks on 29-01-2015.
 */

import java.text.DateFormatSymbols;
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
        return(addLeadingZero(cal.get(Calendar.DATE))
                + "/" + getMonthForInt(cal.get(Calendar.MONTH))
                + "/" + cal.get(Calendar.YEAR)
                + " " + addLeadingZero(cal.get(Calendar.HOUR))
                + ":" + addLeadingZero(cal.get(Calendar.MINUTE))
//                + ":" + addLeadingZero(cal.get(Calendar.SECOND))
                + " " + (cal.get(Calendar.AM_PM)==0?"AM":"PM")
        );
    }

    private String addLeadingZero(int num) {
        return (num < 10 ? "0" : "") + num;
    }

    private  String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month.substring(0,3);
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
