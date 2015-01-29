package tds.socio;

/**
 * Created by laks on 29-01-2015.
 */

import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import tds.libs.GPSTracker;
import tds.libs.SntpClient;
import tds.libs.TimestampConvertor;

public class AttendanceActivity extends BaseActivity {
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    long lngCurrentTime = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        set(navMenuTitles, navMenuIcons);

        new RetrieveTime().execute();

    }

    public void MarkInAttendance(View view)
    {

        GPSTracker gps = new GPSTracker(AttendanceActivity.this);

        if(gps.canGetLocation())
        {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
//            EditText editTextlat = (EditText)findViewById(R.id.latitude);
//            editTextlat.setText(String.valueOf(latitude));
//            EditText editTextlong = (EditText)findViewById(R.id.longitude);
//            editTextlong.setText(String.valueOf(longitude));
//            EditText editDistance = (EditText)findViewById(R.id.distance);
//            editDistance.setText(StrdistBetCoor);
            String StrdistBetCoor = Double.toString(gps.distance(latitude, longitude, 17.3986852, 78.3896163, 'C'));
            Toast.makeText(getApplicationContext(),StrdistBetCoor,Toast.LENGTH_LONG).show();
        }
        else
        {
            gps.showSettingsAlert();
        }
    }

    class RetrieveTime extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {

            try {
                SntpClient currentTime = new SntpClient();

                if (currentTime.requestTime("2.in.pool.ntp.org", 60000)) {
                    lngCurrentTime = currentTime.getNtpTime();

                    TextView TVcurrentTime = (TextView) findViewById(R.id.DateTimeNow);
                    java.util.Date d = new java.util.Date(lngCurrentTime);

                    TimestampConvertor tsc = new TimestampConvertor();
                    TVcurrentTime.setText(tsc.usingDateAndCalendarWithTimeZone(lngCurrentTime));
                }
            } catch (Exception e) {
                Log.e("Async Task - ", "Exception Raised: " + e);
            }
            return null;
        }
    }//
}