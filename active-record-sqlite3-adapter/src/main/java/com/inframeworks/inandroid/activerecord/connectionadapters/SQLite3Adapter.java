package com.inframeworks.inandroid.activerecord.connectionadapters;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author rodrigoscna
 */
public class SQLite3Adapter extends AbstractAdapter {
  @Override
  protected void initializeConnection() {
    SQLiteDatabase.openDatabase(path, factory, flags);
  }

  @Override
  protected void terminateConnection() {
  }

  @Override
  public void beginTransaction() {
  }

  @Override
  public void commitTransaction() {
  }

  @Override
  public void rollbackTransaction() {
  }

  @Override
  public String toSQL() {
    return null;
  }
}
