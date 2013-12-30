package com.inframeworks.inandroid.activerecord.connectionadapters.sqlite3;

import android.database.sqlite.SQLiteDatabase;

import com.inframeworks.inandroid.activerecord.ActiveRecord;
import com.inframeworks.inandroid.activerecord.ActiveRelation;

import java.util.List;

/**
 * @author rodrigoscna
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
