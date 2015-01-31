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

        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
//try{
//
//    List<Offers> offers4 = Offers.listAll(Offers.class);
//
//    Offers.deleteAll(Offers.class);
//
//
//       Offers offers = new Offers(123L,R.drawable.ic_launcher,"Niggu","Subject N",
//                                        "THis is description of the message THis is description of the message THis is description of the message", new Date(),false,false);
//       offers.save();
//
//    Offers offers1 = new Offers(124L,R.drawable.ic_launcher,"Kittu","Subject K",
//            "THis is description of the message THis is description of the message THis is description of the message", new Date(),false,false);
//    offers1.save();
//
//    Offers offers2 = new Offers(133L,R.drawable.ic_launcher,"Surri","Subject S",
//            "THis is description of the message THis is description of the message THis is description of the message", new Date(),false,false);
//    offers2.save();
//    }
//    catch (Exception e)
//    {
//        Log.e("Splash: ", e.getMessage());
//    }


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