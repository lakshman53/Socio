package tds.socio;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by laks on 12-03-2015.
 */
public class OfferService extends Service{
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {

        callAsynchronousTask();
    }

    @Override
    public void onStart(Intent intent, int startId) {

    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                           Integer offerCount = new AsyncGetEmpDetail().execute().get();
                            if (offerCount > 0){
                                NotificationCompat.Builder mBuilder =
                                        new NotificationCompat.Builder(getApplicationContext())
                                                .setSmallIcon(R.drawable.ic_launcher)
                                                .setContentTitle("Socio")
                                                .setContentText("You've got " + Integer.valueOf(offerCount) + " messages");
                                Intent resultIntent = new Intent(getApplicationContext(),CardsActivity.class);
                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                                stackBuilder.addParentStack(Messages.class);
                                stackBuilder.addNextIntent(resultIntent);
                                PendingIntent resultPendingIntent =
                                        stackBuilder.getPendingIntent(
                                                0,
                                                PendingIntent.FLAG_UPDATE_CURRENT
                                        );
                                mBuilder.setContentIntent(resultPendingIntent);
                                NotificationManager mNotificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                mNotificationManager.notify(0, mBuilder.build());
                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 20000); //execute in every 20000 ms
    }

    @Override
    public void onDestroy() {
       // Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
    }

    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://sociowebservice.azurewebsites.net/GenMethods.asmx";
    private static String SOAP_ACTION = "http://tempuri.org/";

    public static class getEmployeeOffers {

        public static Integer getOffers(Integer EmpId, Integer LastOfferId ) {

            try {

                SoapObject request = new SoapObject(NAMESPACE, "getOffers");
                PropertyInfo sayHelloPI;

                sayHelloPI = new PropertyInfo();
                sayHelloPI.setName("EmpId");
                sayHelloPI.setValue(EmpId);
                sayHelloPI.setType(Integer.class);
                request.addProperty(sayHelloPI);

                sayHelloPI = new PropertyInfo();
                sayHelloPI.setName("LastOfferId");
                sayHelloPI.setValue(LastOfferId);
                sayHelloPI.setType(Integer.class);
                request.addProperty(sayHelloPI);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION + "getOffers", envelope);
                SoapObject response = (SoapObject) envelope.bodyIn;
                SoapObject array = (SoapObject) response.getProperty(0);

             addOffersByParseXML addOffersByParseXML = new addOffersByParseXML();

              return addOffersByParseXML.addOffers(array.toString().replace("anyType{}","").replace("anyType{offerString=","").replace("; }",""));

            }
            catch (Exception e) {
                Log.e("List:", e.getMessage());
            }
            return null;
        }

    }


    private class AsyncGetEmpDetail extends AsyncTask<Void,Void,Integer> {

            Employee emp = Employee.findById(Employee.class, 1L);

        protected AsyncGetEmpDetail( ) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {

            return getEmployeeOffers.getOffers(Integer.parseInt(emp.getInternalEmpId()), emp.getLastOrderId());

        }


        protected void onPostExecute(Integer result) {

        }

    }
}
