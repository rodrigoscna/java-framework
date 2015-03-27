package co.atlas_tech.atlasandroid.activerecord.connectionadapters.sqlite3;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import co.atlas_tech.atlasandroid.activerecord.ActiveRecord;
import co.atlas_tech.atlasandroid.activerecord.ActiveRelation;

/**
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
public class DataManipulationLanguage {
    public static <T extends ActiveRecord> List<T> fetchAll(SQLiteDatabase connection, ActiveRelation activeRelation, Class<T> type) {
        return null;
    }

    public static <T extends ActiveRecord> T fetchFirst(SQLiteDatabase connection, ActiveRelation activeRelation, Class<T> type) {
        return null;
    }

    public static <T extends ActiveRecord> T fetchLast(SQLiteDatabase connection, ActiveRelation activeRelation, Class<T> type) {
        return null;
    }

    public static <T extends ActiveRecord> String toSQL(SQLiteDatabase connection, ActiveRelation activeRelation, Class<T> type) {
        return null;
    }
}
