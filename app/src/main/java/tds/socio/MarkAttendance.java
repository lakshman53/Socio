package tds.socio;

import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by laks on 06-04-2015.
 */

public class MarkAttendance extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    Spinner spinnerShift;
    TextView markedTime1, markedTime2;

    public void markAttendance(View view) {

        try {

            List<Shift> st1 = Shift.listAll(Shift.class);

            String[] dateTimeArray = new dateTimeClass().getDateTimeArray();

            Calendar calendar = setCalendarFromArrayString(dateTimeArray);

            String setFlag, prefix;
            TextView tv;


            CharSequence charSequence = "";
            if((markedTime1.getText()+"L").equals("L") ) {

                setFlag = "I";
                tv = markedTime1;
                prefix = "Entry - ";

            } else {

                setFlag = "O";
                tv = markedTime2;
                prefix = "Exit - ";

            }

            Attendance attendance = new Attendance();
            //attendance.setShift(st1.get((int) spinnerShift.getSelectedItemId()));
            attendance.setFlag(setFlag);
            attendance.setLogDateTime(calendar.getTime());
            attendance.save();

            setTime(tv, prefix, getTimeFromCalendar(calendar));

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Calendar setCalendarFromArrayString(String[] dateTimeArray) {
        Calendar cal = Calendar.getInstance();

        try {
            String monthName = dateTimeArray[2].substring(0, dateTimeArray[2].indexOf(' '));
            Date date = new SimpleDateFormat("MMMM").parse(monthName);

            cal.setTime(date);

            int year, month, datenow, hour, min;

            year = Integer.parseInt(dateTimeArray[2].substring(dateTimeArray[2].lastIndexOf(' ') + 1));
            month = cal.get(Calendar.MONTH);
            int ll = dateTimeArray[2].indexOf(' ');
            datenow = Integer.parseInt(dateTimeArray[2].substring(ll + 1, ll + 3));
            hour = Integer.parseInt(dateTimeArray[1].substring(0, 2));
            min = Integer.parseInt(dateTimeArray[1].substring(3, 5));

            cal.set(year, month, datenow, hour, min);

        } catch (Exception ex) {
        }
        return cal;

    }

    private String getTimeFromCalendar(Calendar calendar) {
        return String.format("%02d", calendar.get(calendar.HOUR_OF_DAY)) + " : " + String.format("%02d", calendar.get(calendar.MINUTE)) + " : " + String.format("%02d", calendar.get(calendar.SECOND));
    }

    private String getDateFromCalendar(Calendar calendar) {
        return calendar.get(calendar.DAY_OF_MONTH) + "-" +  calendar.get(calendar.MONTH) + "-" + calendar.get(calendar.YEAR);
    }

    private void setTime(TextView tv, String prefix, String time) {
        tv.setText(prefix + time);

    }


    private void setupScreen() {

        String[] dateTimeString = new dateTimeClass().getDateTimeArray();
        spinnerShift = (Spinner) findViewById(R.id.shiftSpinner);

        if (dateTimeString.length == 0) {
            setupScreen();
        }

        List<Attendance> attendances = Attendance.find(Attendance.class, null, null, null, "Id DESC", "2");

        if (attendances.size() == 0) {
            return;
        }

        Calendar todayDate = setCalendarFromArrayString(dateTimeString);
        Calendar markedDate = Calendar.getInstance();

        if (attendances.size() == 2) {
            markedDate.setTime(attendances.get(0).getLogDateTime());

            if (getDateFromCalendar(markedDate).equals(getDateFromCalendar(todayDate))) { //today's attendance is marked

                String flag = attendances.get(0).getFlag();

                if (flag.equals("O")) { // In and Out are marked. Set text box values of both in and out.

                    setTime(markedTime1, "Entry - ", getTimeFromCalendar(markedDate));
                    markedDate.setTime(attendances.get(1).getLogDateTime());
                    setTime(markedTime2, "Exit -", getTimeFromCalendar(markedDate));

                    Button punch = (Button) findViewById(R.id.markAttendance);

                    punch.setVisibility(View.INVISIBLE);

                } else { // Only In is marked

                    markedDate.setTime(attendances.get(0).getLogDateTime());
                    setTime(markedTime1, "Entry - ", getTimeFromCalendar(markedDate));
                }
            }

        } else {
            if (getDateFromCalendar(markedDate).equals(getDateFromCalendar(todayDate))) { //today's attendance is marked

               markedDate.setTime(attendances.get(0).getLogDateTime());
               setTime(markedTime1, "Entry - ", getTimeFromCalendar(markedDate));
            }
        }

        List<Shift> shiftList = Shift.listAll(Shift.class);
        String[] state = new String[shiftList.size()];
        for (int i = 0; i < shiftList.size(); i = i + 1) {
            Calendar cl = Calendar.getInstance();
            cl.setTime(shiftList.get(i).getStartTime());
            cl.add(Calendar.MINUTE, 330);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

            Calendar cl1 = Calendar.getInstance();
            cl1.setTime(shiftList.get(i).getEndTime());
            cl1.add(Calendar.MINUTE, 330);

            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm", Locale.getDefault());
            sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));

            state[i] = shiftList.get(i).getShiftName() + " ( " + sdf.format(cl.getTime()) + " - " + sdf.format(cl1.getTime()) + " )";
        }

        ArrayAdapter<String> adapter_state = new ArrayAdapter<>(this, R.layout.simple_spinner_dropdown_item, state);
        spinnerShift.setAdapter(adapter_state);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_markattendance);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles, navMenuIcons);

        markedTime1 = (TextView) findViewById(R.id.markedTime1);
        markedTime2 = (TextView) findViewById(R.id.markedTime2);

        showTime();

    }


    private class RetrieveTimeWS extends AsyncTask<Void, Void, String> {

        TextView HH1 = (TextView) findViewById(R.id.HH1);
        TextView HH2 = (TextView) findViewById(R.id.HH2);
        TextView Day = (TextView) findViewById(R.id.Day);
        TextView MM1 = (TextView) findViewById(R.id.MM1);
        TextView MM2 = (TextView) findViewById(R.id.MM2);
        TextView Date = (TextView) findViewById(R.id.Date);

        dateTimeClass dtc = new dateTimeClass();

        String datetime = "";

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(Void... params) {
            try {
                datetime = AttendanceActivity.getDateTimeClass.getDateTime();

            } catch (Exception e) {
                Log.e("Async Task", e.getMessage());
            }
            return datetime;
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);

            if (s != null) {

                dtc.setDateTimeArray(datetime.split("\\-"));

                String[] dateTimeArray = dtc.getDateTimeArray();

                Day.setText(dateTimeArray[0]);

                HH1.setText(dateTimeArray[1].substring(0, 1));
                HH2.setText(dateTimeArray[1].substring(1, 2));
                MM1.setText(dateTimeArray[1].substring(3, 4));
                MM2.setText(dateTimeArray[1].substring(4, 5));

                Date.setText(dateTimeArray[2]);

                setupScreen();
            }
        }
    }

    public void showTime() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask showTime = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            new RetrieveTimeWS().execute();
                        } catch (Exception e) {
                        }
                    }
                });
            }

            ;
        };
        timer.schedule(showTime, 0, 30000); //execute in every 30000 ms
    }
}
