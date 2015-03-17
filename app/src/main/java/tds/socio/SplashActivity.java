package tds.socio;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SplashActivity extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle appBundle) {
        super.onCreate(appBundle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/

        //TODO: Check if internet connection exists.


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity.*/

                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);



    }
    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://sociowebservice.azurewebsites.net/GenMethods.asmx";
    private static String SOAP_ACTION = "http://tempuri.org/";

    public static class uploadImage {

        public static String uploadImage(String binString ) {

            try {

                SoapObject request = new SoapObject(NAMESPACE, "insertPicture");
                PropertyInfo sayHelloPI;

                sayHelloPI = new PropertyInfo();
                sayHelloPI.setName("binPicture");
                sayHelloPI.setValue(binString);
                sayHelloPI.setType(String.class);
                request.addProperty(sayHelloPI);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION + "insertPicture", envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                return response.toString();
            }
            catch (Exception e) {
                Log.e("List:", e.getMessage());
            }
            return null;
        }

    }


    private class AsyncGetEmpDetail extends AsyncTask<Void,Void,String> {

        Employee emp = Employee.findById(Employee.class, 1L);

        protected AsyncGetEmpDetail( ) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            return uploadImage.uploadImage("010");

        }


        protected void onPostExecute(Integer result) {

        }

    }

}