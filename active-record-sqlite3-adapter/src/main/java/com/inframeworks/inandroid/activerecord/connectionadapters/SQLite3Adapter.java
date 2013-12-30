package com.inframeworks.inandroid.activerecord.connectionadapters;

import android.database.sqlite.SQLiteDatabase;

import com.inframeworks.inandroid.activerecord.ActiveRecord;
import com.inframeworks.inandroid.activerecord.ActiveRelation;
import com.inframeworks.inandroid.activerecord.connectionadapters.sqlite3.DataManipulationLanguage;
import com.inframeworks.inandroid.activerecord.connectionadapters.sqlite3.DataTransactionLanguage;
import com.inframeworks.inandroid.activesupport.Application;

import java.util.List;

/**
 * @author rodrigoscna
 */
public class SQLite3Adapter extends AbstractAdapter {
  private static SQLiteDatabase sSQLiteDatabase;

  private SQLiteDatabase getConnection() {
    if (sSQLiteDatabase == null) {
      initializeConnection();
    }

    return sSQLiteDatabase;
  }

  /**
   * Initializes the connection with the database.
   */
  @Override
  protected void initializeConnection() {
    if (sSQLiteDatabase == null) {
      String databaseName = Application.getApplicationConfiguration().getMetaData().getString("database_name");
      sSQLiteDatabase = SQLiteDatabase.openDatabase(databaseName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
    }
  }

  /**
   * Closes all database connections and terminates them.
   */
  @Override
  protected void terminateConnection() {
    if (sSQLiteDatabase != null) {
      sSQLiteDatabase.close();
      sSQLiteDatabase = null;
    }
  }

  /**
   * Starts a transaction within the database connection.
   */
  @Override
  public void beginTransaction() {
    DataTransactionLanguage.beginTransaction(getConnection());
  }

  /**
   * Sets the transaction as successful and commits all changes to the database.
   */
  @Override
  public void commitTransaction() {
    DataTransactionLanguage.commitTransaction(getConnection());
  }

  /**
   * Sets the transaction as unsuccessful and cancel all changes to the database.
   */
  @Override
  public void rollbackTransaction() {
    DataTransactionLanguage.rollbackTransaction(getConnection());
  }

  /**
   * Fetch all records based on your ActiveRelation query.
   *
   * @param activeRelation The ActiveRelation instance.
   * @param type           Model class type for query and returning objects.
   * @return The list of records fetched from the database.
   */
  @Override
  public <T extends ActiveRecord> List<T> all(ActiveRelation activeRelation, Class<T> type) {
    return DataManipulationLanguage.fetchAll(getConnection(), activeRelation, type);
  }

  /**
   * Fetch the first database record based on your ActiveRelation query.
   *
   * @param activeRelation The ActiveRelation instance.
   * @param type           Model class type for query and returning objects.
   * @return The first record fetched from the database.
   */
  @Override
  public <T extends ActiveRecord> T first(ActiveRelation activeRelation, Class<T> type) {
    return DataManipulationLanguage.fetchFirst(getConnection(), activeRelation, type);
  }

  /**
   * Fetch the last database record based on your ActiveRelation query.
   *
   * @param activeRelation The ActiveRelation instance.
   * @param type           Model class type for query and returning objects.
   * @return The last record fetched from the database.
   */
  @Override
  public <T extends ActiveRecord> T last(ActiveRelation activeRelation, Class<T> type) {
    return DataManipulationLanguage.fetchLast(getConnection(), activeRelation, type);
  }

  /**
   * Converts your ActiveRelation query to a SQL String.
   *
   * @param activeRelation The ActiveRelation instance.
   * @param type           Model class type for query.
   * @return The SQL equivalent to your ActiveRelation.
   */
  @Override
  public <T extends ActiveRecord> String toSQL(ActiveRelation activeRelation, Class<T> type) {
    return DataManipulationLanguage.toSQL(getConnection(), activeRelation, type);
  }
}
