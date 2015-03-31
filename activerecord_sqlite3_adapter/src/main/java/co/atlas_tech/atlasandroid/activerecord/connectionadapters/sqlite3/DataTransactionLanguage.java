package co.atlas_tech.atlasandroid.activerecord.connectionadapters.sqlite3;

import android.database.sqlite.SQLiteDatabase;

import co.atlas_tech.atlasandroid.activesupport.annotations.Beta;

/**
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
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
