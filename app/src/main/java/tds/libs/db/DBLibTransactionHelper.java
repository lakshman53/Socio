package tds.libs.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBLibTransactionHelper {

    public static void doInTransaction(DBLibTransactionHelper.Callback callback) {
        SQLiteDatabase database = DBLibContext.getDBLibContext().getDBLibDb().getDB();
        database.beginTransaction();

        try {
            Log.d(DBLibTransactionHelper.class.getSimpleName(),
                    "Callback executing within transaction");
            callback.manipulateInTransaction();
            database.setTransactionSuccessful();
            Log.d(DBLibTransactionHelper.class.getSimpleName(),
                    "Callback successfully executed within transaction");
        } catch (Throwable e) {
            Log.d(DBLibTransactionHelper.class.getSimpleName(),
                    "Could execute callback within transaction", e);
        } finally {
            database.endTransaction();
        }
    }

    public static interface Callback {
        void manipulateInTransaction();
    }
}
