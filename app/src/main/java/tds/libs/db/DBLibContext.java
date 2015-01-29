package tds.libs.db;

import android.content.Context;

public class DBLibContext {

    private static DBLibContext instance = null;
    private DBLibDb DBLibDb;
    private Context context;

    private DBLibContext(Context context) {
        this.context = context;
        this.DBLibDb = new DBLibDb(context);
    }
    
    public static DBLibContext getDBLibContext() {
        if (instance == null) {
            throw new NullPointerException("DBLibContext has not been initialized properly. Call DBLibContext.init(Context) in your Application.onCreate() method and DBLibContext.terminate() in your Application.onTerminate() method.");
        }
        return instance;
    }

    public static void init(Context context) {
        instance = new DBLibContext(context);
    }

    public static void terminate() {
        if (instance == null) {
            return;
        }
        instance.doTerminate();
    }

    /*
     * Per issue #106 on Github, this method won't be called in
     * any real Android device. This method is used purely in
     * emulated process environments such as an emulator or
     * Robolectric Android mock.
     */
    private void doTerminate() {
        if (this.DBLibDb != null) {
            this.DBLibDb.getDB().close();
        }
    }

    protected DBLibDb getDBLibDb() {
        return DBLibDb;
    }

}
