package tds.socio;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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

        //Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
       // Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
    }
}
