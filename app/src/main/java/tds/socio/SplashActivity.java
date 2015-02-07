package tds.socio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Date;
import java.util.List;
import java.util.Random;

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

        List<Offers> offer = Offers.listAll(Offers.class);

        Offers.deleteAll(Offers.class);

        Random randomGenerator = new Random();
        Offers offers;

        offers = new Offers(Integer.toString(randomGenerator.nextInt(1000)), R.drawable.ic_launcher,"Lakshman","This is a subject", "This is Description",new Date(),false,false);
        offers.save();
        offers = new Offers(Integer.toString(randomGenerator.nextInt(1000)), R.drawable.ic_launcher,"Lakshman","This is a subject", "This is Description",new Date(),false,false);
        offers.save();
        offers = new Offers(Integer.toString(randomGenerator.nextInt(1000)), R.drawable.ic_launcher,"Lakshman","This is a subject", "This is Description",new Date(),false,false);
        offers.save();
        offers = new Offers(Integer.toString(randomGenerator.nextInt(1000)), R.drawable.ic_launcher,"Lakshman","This is a subject", "This is Description",new Date(),false,false);
        offers.save();
        offers = new Offers(Integer.toString(randomGenerator.nextInt(1000)), R.drawable.ic_launcher,"Lakshman","This is a subject", "This is Description",new Date(),false,false);
        offers.save();


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
}