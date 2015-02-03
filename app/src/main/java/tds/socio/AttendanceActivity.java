package tds.socio;

/**
 * Created by laks on 29-01-2015.
 */

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tds.libs.GPSTracker;
import tds.libs.SntpClient;
import tds.libs.TimestampConvertor;

public class AttendanceActivity extends BaseActivity {
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    long lngCurrentTime = -1;

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Socio_" + timeStamp + "_";

        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File storageDir =  Environment.getExternalStorageDirectory();

        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

//        Save a file: path for use with ACTION_VIEW intents

        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;

    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
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

//          String StrdistBetCoor = Double.toString(gps.distance(latitude, longitude, 17.3986852, 78.3896163, 'C'));
//          Toast.makeText(getApplicationContext(),StrdistBetCoor,Toast.LENGTH_LONG).show();

            try
            {
                dispatchTakePictureIntent();
            }
           catch (Exception e) {
               Log.e("Capture Image Error: " , e.getMessage());
           }
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

                    TimestampConvertor tsc = new TimestampConvertor();
                    TVcurrentTime.setText(tsc.usingDateAndCalendarWithTimeZone(lngCurrentTime));
                }
            } catch (Exception e) {
                Log.e("Async Task - Exception ", e.getMessage());
            }
            return null;
        }
    }
}