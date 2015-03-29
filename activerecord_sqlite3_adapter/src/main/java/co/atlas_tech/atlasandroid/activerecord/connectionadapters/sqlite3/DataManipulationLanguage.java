package co.atlas_tech.atlasandroid.activerecord.connectionadapters.sqlite3;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import co.atlas_tech.atlasandroid.activerecord.ActiveRecord;
import co.atlas_tech.atlasandroid.activerecord.ActiveRelation;
import co.atlas_tech.atlasandroid.activesupport.core_ext.StringUtils;

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

    public static <T extends ActiveRecord> List<T> fetchFirst(SQLiteDatabase connection, ActiveRelation activeRelation, Class<T> type, Integer limit) {
        return null;
    }

    public static <T extends ActiveRecord> T fetchLast(SQLiteDatabase connection, ActiveRelation activeRelation, Class<T> type) {
        return null;
    }

    public static <T extends ActiveRecord> List<T> fetchLast(SQLiteDatabase connection, ActiveRelation activeRelation, Class<T> type, Integer limit) {
        return null;
    }

    public static <T extends ActiveRecord> String toSQL(SQLiteDatabase connection, ActiveRelation activeRelation, Class<T> type) {
        String modelName = type.getSimpleName();
        String tableName = modelName;

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT ");

        if (activeRelation.hasSelect()) {
            sqlQuery.append(StringUtils.join(", ", activeRelation.getSelect())).append(" ");
        } else {
            sqlQuery.append(tableName).append(".* ");
        }

        if (activeRelation.hasLimit()) {
            sqlQuery.append(" LIMIT ").append(activeRelation.getLimit()).append(" ");
        }

        if (activeRelation.hasOffset()) {
            sqlQuery.append(" OFFSET ").append(activeRelation.getOffset()).append(" ");
        }

        return sqlQuery.toString().trim();
    }
}
