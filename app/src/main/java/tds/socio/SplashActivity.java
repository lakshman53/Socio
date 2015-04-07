package tds.socio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle appBundle) {
        super.onCreate(appBundle);
        setContentView(R.layout.activity_splash);

       //TODO: Check if internet connection exists.
//
//        Shift.deleteAll(Shift.class);
//Calendar startTime, endTime;
//
//        startTime = Calendar.getInstance();
//        endTime = Calendar.getInstance();
//
//        startTime.set(Calendar.HOUR,9);
//        startTime.set(Calendar.MINUTE,30);
//
//        endTime.set(Calendar.HOUR,18);
//        endTime.set(Calendar.MINUTE,0);
//
//        Shift shift;
//
//        shift = new Shift("General Shift",startTime.getTime(),endTime.getTime());
//        shift.save();
//
//        startTime = Calendar.getInstance();
//        endTime = Calendar.getInstance();
//
//        startTime.set(Calendar.HOUR,7);
//        startTime.set(Calendar.MINUTE,0);
//
//        endTime.set(Calendar.HOUR,15);
//        endTime.set(Calendar.MINUTE,0);
//
//        shift = new Shift("A Shift",startTime.getTime(),endTime.getTime());
//        shift.save();
//
//        startTime = Calendar.getInstance();
//        endTime = Calendar.getInstance();
//
//        startTime.set(Calendar.HOUR,15);
//        startTime.set(Calendar.MINUTE,0);
//
//        endTime.set(Calendar.HOUR,23);
//        endTime.set(Calendar.MINUTE,0);
//
//        shift = new Shift("B Shift",startTime.getTime(),endTime.getTime());
//        shift.save();
//
//        startTime = Calendar.getInstance();
//        endTime = Calendar.getInstance();
//
//        startTime.set(Calendar.HOUR,23);
//        startTime.set(Calendar.MINUTE,0);
//
//        endTime.set(Calendar.HOUR,7);
//        endTime.set(Calendar.MINUTE,0);
//
//        shift = new Shift("C Shift",startTime.getTime(),endTime.getTime());
//        shift.save();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}