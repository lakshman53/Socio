package tds.socio;

import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    public void markAttendance(View view) {
        TextView markedTime1 = (TextView) findViewById(R.id.markedTime1);
        TextView AttStatus1 = (TextView) findViewById(R.id.AttStatus1);
        TextView markedTime2 = (TextView) findViewById(R.id.markedTime2);
        TextView AttStatus2 = (TextView) findViewById(R.id.AttStatus2);

        if (true) {

            List<Shift> st1 = Shift.listAll(Shift.class);
            List<Attendance> attendances = Attendance.find(Attendance.class, null, null, null, "Log_Date_Time DESC", "1");

            Calendar calendar = Calendar.getInstance();

            String[] dateTimeArray = new dateTimeClass().getDateTimeArray();

            try {
                String monthName = dateTimeArray[2].substring(0,dateTimeArray[2].indexOf(' '));
                Date date = new SimpleDateFormat("MMMM").parse(monthName);

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                int year, month, datenow, hour, min;

                year = Integer.parseInt(dateTimeArray[2].substring(dateTimeArray[2].lastIndexOf(' ') + 1));
                month = cal.get(Calendar.MONTH);
                int ll = dateTimeArray[2].indexOf(' ');
                datenow = Integer.parseInt(dateTimeArray[2].substring(ll + 1, ll + 3));
                hour = Integer.parseInt(dateTimeArray[1].substring(0, 2));
                min = Integer.parseInt(dateTimeArray[1].substring(3, 5));


                calendar.set(year, month, datenow, hour, min);
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), Integer.toString(dateTimeArray.length), Toast.LENGTH_LONG).show();
            }

            String flag = attendances.get(0).getFlag();

            Attendance attendance = new Attendance();
            attendance.setShift(st1.get((int) spinnerShift.getSelectedItemId()));
            attendance.setFlag(flag.equals("I") ? "O" : "I");
            attendance.setLogDateTime(calendar.getTime());
            attendance.save();

            TextView tv = flag.equals("I") ? markedTime1 : markedTime2;

            tv.setText(String.format("%02d", calendar.get(calendar.HOUR_OF_DAY)) + " : " + String.format("%02d", calendar.get(calendar.MINUTE)) + " : " + String.format("%02d", calendar.get(calendar.SECOND)));


        }


    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_markattendance);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles, navMenuIcons);

        showTime();

        spinnerShift = (Spinner) findViewById(R.id.shiftSpinner);
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
        spinnerShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                Toast.makeText(getApplicationContext(), Long.toString(id) + " - " + parentView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(getApplicationContext(), "Nothing Selected", Toast.LENGTH_LONG).show();
            }
        });

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

                //  dateTimeArray =

                Day.setText(dateTimeArray[0]);

                HH1.setText(dateTimeArray[1].substring(0, 1));
                HH2.setText(dateTimeArray[1].substring(1, 2));
                MM1.setText(dateTimeArray[1].substring(3, 4));
                MM2.setText(dateTimeArray[1].substring(4, 5));

                Date.setText(dateTimeArray[2]);
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
