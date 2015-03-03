package tds.socio;

/**
 * Created by laks on 29-01-2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import tds.libs.GPSTracker;
import tds.libs.MarshalDouble;

public class AttendanceActivity extends BaseActivity {


    CalendarView calendar;

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

//    long lngCurrentTime = -1;

    String mCurrentPhotoPath;

    static final int REQUEST_TAKE_PHOTO = 1;

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
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent;
        File photoFile = null;

        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            setPic();
        }
    }
    private void setPic() {

        // Get the dimensions of the View

        int targetW = 50;
        int targetH = 100;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles, navMenuIcons);

        initializeCalendar();

        try {

            new RetrieveTimeWS().execute();
        }
        catch (Exception e)
        {
            Log.e("Current Time", e.getMessage());
        }
    }

    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar);

        calendar.setShowWeekNumber(false);

        calendar.setFirstDayOfWeek(2);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                TextView selectedDate = (TextView) findViewById(R.id.editText);
                selectedDate.setText(day + "/" + month + "/" + year);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

          super.onDestroy();
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

                List<Employee> employees;
                employees = Employee.listAll(Employee.class);

                String internalEmpId = employees.get(0).getInternalEmpId();


                Integer getval = new AttendanceWS(Double.toString(latitude), Double.toString(longitude),internalEmpId, "A", gps.distFrom(latitude,longitude, 16.7414888,81.0831915)).execute().get();

                //employees.get(0).setAttendanceRetValue();
                Employee emp = Employee.findById(Employee.class, 1L);
                emp.setAttendanceRetValue(Integer.toString(getval));
                emp.save();
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

    public void MarkOutAttendance(View view)
    {
        dispatchTakePictureIntent();

        GPSTracker gps = new GPSTracker(AttendanceActivity.this);

        if(gps.canGetLocation())
        {
            Employee emp = Employee.findById(Employee.class, 1L);



            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

          //  Toast.makeText(getApplication(), emp.getAttendanceRetValue(),Toast.LENGTH_LONG).show();

//          String StrdistBetCoor = Double.toString(gps.distance(latitude, longitude, 17.3986852, 78.3896163, 'C'));
//          Toast.makeText(getApplicationContext(),StrdistBetCoor,Toast.LENGTH_LONG).show();

            try
            {
                dispatchTakePictureIntent();

                //TODO: Get store coordinates and remove hard coding

                new AttendanceWS(Double.toString(latitude), Double.toString(longitude),emp.getAttendanceRetValue(), "P", gps.distance(latitude, longitude, 16.7414888, 81.0831915, 0.0, 0.0)).execute().get();

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

    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://sociowebservice.azurewebsites.net/GenMethods.asmx";
    private static String SOAP_ACTION = "http://tempuri.org/";

    public static class getDateTimeClass {
        public static String getDateTime() {
            String resTxt = "";
            SoapObject request = new SoapObject(NAMESPACE, "getcurrentDateTime");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAP_ACTION + "getcurrentDateTime", envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                resTxt = response.toString();

            } catch (Exception e) {
                resTxt = e.getMessage();
            }
            return  resTxt;
        }
    }

    public static class AttendanceMarking {

        public static Integer logAttendance(String latitude, String longitude, String empId, String LogFlag, Double DistanceFromStore) {

            Integer resTxt = 1;
            SoapObject request = new SoapObject(NAMESPACE, "logAttendance");
            PropertyInfo sayHelloPI;

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("Latitude");
            sayHelloPI.setValue(latitude);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("Longitude");
            sayHelloPI.setValue(longitude);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("GPID");
            sayHelloPI.setValue(empId);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("LogFlag");
            sayHelloPI.setValue(LogFlag);
            sayHelloPI.setType(String.class);
            request.addProperty(sayHelloPI);

            sayHelloPI = new PropertyInfo();
            sayHelloPI.setName("DistanceFromStore");
            sayHelloPI.setValue(DistanceFromStore);
            sayHelloPI.setType(Double.class);
            request.addProperty(sayHelloPI);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                MarshalDouble md = new MarshalDouble();
                md.register(envelope);

                androidHttpTransport.call(SOAP_ACTION + "logAttendance", envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                resTxt = Integer.parseInt(response.toString());

            } catch (Exception e) {
               Log.e("resTxt" , e.getMessage());

            }
            //Return resTxt to calling object
            return resTxt;
        }
    }

    private class AttendanceWS extends AsyncTask<String, Void, Integer> {

        String latitude;
        String longitude;
        String empId;
        String LogFlag;
        Double DistanceFromStore;
        public Integer retNum;

        public AttendanceWS(String latitude, String longitude, String empId, String logFlag, Double distanceFromStore) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.empId = empId;
            LogFlag = logFlag;
            DistanceFromStore = distanceFromStore;

        }

        protected Integer doInBackground(String... params) {
            retNum =   AttendanceMarking.logAttendance(latitude,longitude,empId,LogFlag,DistanceFromStore );
            return retNum;
        }

  }

    class RetrieveTimeWS extends AsyncTask<Void, Void, String> {

        TextView TVcurrentTime = (TextView) findViewById(R.id.DateTimeNow);
        String datetime = "";

        protected void onPreExecute() {
            super.onPreExecute();
            TVcurrentTime.setText("Loading Current Time...");
        }

        protected String doInBackground(Void... params) {
            try {
                datetime = getDateTimeClass.getDateTime();

            } catch (Exception e) {
                Log.e("Async Task - GetDateTime ", e.getMessage());
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
}

