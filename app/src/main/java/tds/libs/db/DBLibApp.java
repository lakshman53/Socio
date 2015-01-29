package tds.libs.db;

import android.app.Application;

public class DBLibApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBLibContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBLibContext.terminate();
    }

}
