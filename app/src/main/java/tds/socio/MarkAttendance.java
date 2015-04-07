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

        TextView TVcurrentTime = (TextView) findViewById(R.id.DateTimeNow);
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
                TVcurrentTime.setText(datetime);
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
            };
        };
        timer.schedule(showTime, 0, 30000); //execute in every 30000 ms
    }
}
