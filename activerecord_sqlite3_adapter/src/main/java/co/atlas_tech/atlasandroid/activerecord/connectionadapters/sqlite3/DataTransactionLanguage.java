package co.atlas_tech.atlasandroid.activerecord.connectionadapters.sqlite3;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
public class DataTransactionLanguage {
    public static void beginTransaction(SQLiteDatabase connection) {
        connection.beginTransaction();
    }

    public static void commitTransaction(SQLiteDatabase connection) {
        connection.setTransactionSuccessful();
        connection.endTransaction();
    }

    public static void rollbackTransaction(SQLiteDatabase connection) {
        connection.endTransaction();
    }
}
