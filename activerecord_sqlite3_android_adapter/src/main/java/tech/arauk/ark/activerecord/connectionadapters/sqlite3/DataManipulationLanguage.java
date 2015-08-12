package tech.arauk.ark.activerecord.connectionadapters.sqlite3;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import tech.arauk.ark.activerecord.ActiveRecord;
import tech.arauk.ark.activerecord.ActiveRelation;
import tech.arauk.ark.activesupport.annotations.Beta;
import tech.arauk.ark.activesupport.core_ext.StringUtils;

/**
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
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
        String tableName = StringUtils.tableize(modelName);

        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT ");

        if (activeRelation.hasSelect()) {
            sqlQuery.append(StringUtils.join(activeRelation.getSelect(), ", ")).append(" ");
        } else {
            sqlQuery.append(tableName).append(".* ");
        }

        sqlQuery.append("FROM ").append(tableName).append(" ");

        if (activeRelation.hasLimit()) {
            sqlQuery.append("LIMIT ").append(activeRelation.getLimit()).append(" ");
        }

        if (activeRelation.hasOffset()) {
            sqlQuery.append("OFFSET ").append(activeRelation.getOffset()).append(" ");
        }

        return sqlQuery.toString().trim();
    }
}
