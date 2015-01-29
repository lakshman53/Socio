package tds.libs.db.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

public class DBLibCursorFactory implements SQLiteDatabase.CursorFactory {

    private boolean debugEnabled;

    public DBLibCursorFactory() {
        this.debugEnabled = false;
    }

    public DBLibCursorFactory(boolean debugEnabled) {

        this.debugEnabled = debugEnabled;
    }

    @SuppressWarnings("deprecation")
    public Cursor newCursor(SQLiteDatabase sqLiteDatabase,
            SQLiteCursorDriver sqLiteCursorDriver,
            String editTable,
            SQLiteQuery sqLiteQuery) {

        if (debugEnabled) {
            Log.d("SQL Log", sqLiteQuery.toString());
        }

        return new SQLiteCursor(sqLiteDatabase, sqLiteCursorDriver, editTable, sqLiteQuery);
    }

}
