package tds.libs.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tds.libs.db.util.ManifestHelper;
import tds.libs.db.util.DBLibCursorFactory;

import static tds.libs.db.util.ManifestHelper.getDatabaseVersion;
import static tds.libs.db.util.ManifestHelper.getDebugEnabled;

public class DBLibDb extends SQLiteOpenHelper {

    private final SchemaGenerator schemaGenerator;
    private SQLiteDatabase sqLiteDatabase;

    public DBLibDb(Context context) {
        super(context, ManifestHelper.getDatabaseName(context),
                new DBLibCursorFactory(getDebugEnabled(context)), getDatabaseVersion(context));
        schemaGenerator = new SchemaGenerator(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        schemaGenerator.createDatabase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        schemaGenerator.doUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public synchronized SQLiteDatabase getDB() {
        if (this.sqLiteDatabase == null) {
            this.sqLiteDatabase = getWritableDatabase();
        }

        return this.sqLiteDatabase;
    }
}
