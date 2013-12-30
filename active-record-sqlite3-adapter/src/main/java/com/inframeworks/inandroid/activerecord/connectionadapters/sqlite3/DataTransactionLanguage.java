package com.inframeworks.inandroid.activerecord.connectionadapters.sqlite3;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author rodrigoscna
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
