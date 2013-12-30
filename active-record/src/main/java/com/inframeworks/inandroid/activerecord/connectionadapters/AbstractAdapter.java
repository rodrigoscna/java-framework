package com.inframeworks.inandroid.activerecord.connectionadapters;

import com.inframeworks.inandroid.activerecord.ActiveRecord;
import com.inframeworks.inandroid.activerecord.ActiveRelation;
import com.inframeworks.inandroid.activesupport.Application;
import com.inframeworks.inandroid.activesupport.annotations.Beta;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author rodrigoscna
 */
@Beta
public abstract class AbstractAdapter {
  public static AbstractAdapter getAdapter() {
    String databaseAdapter = Application.getApplicationConfiguration().getMetaData().getString("database_adapter");

    try {
      Class<?> aClass = Class.forName("com.inframeworks.inandroid.activerecord.connectionadapters" + databaseAdapter);
      Constructor<?> aClassConstructor = aClass.getConstructor(String.class, Integer.class);
      AbstractAdapter abstractAdapter = (AbstractAdapter) aClassConstructor.newInstance("string", 42);

      abstractAdapter.initializeConnection();

      return abstractAdapter;
    } catch (ClassNotFoundException cnfe) {
      throw new UnsupportedOperationException("Database adapter not found.", cnfe);
    } catch (Exception e) {
      throw new UnsupportedOperationException("Invalid database adapter.");
    }
  }

  /**
   * Initializes the connection with the database.
   */
  protected abstract void initializeConnection();

  /**
   * Closes all database connections and terminates them.
   */
  protected abstract void terminateConnection();

  /**
   * Starts a transaction within the database connection.
   */
  public abstract void beginTransaction();

  /**
   * Sets the transaction as successful and commits all changes to the database.
   */
  public abstract void commitTransaction();

  /**
   * Sets the transaction as unsuccessful and cancel all changes to the database.
   */
  public abstract void rollbackTransaction();

  /**
   * Fetch all records based on your ActiveRelation query.
   *
   * @param activeRelation The ActiveRelation instance.
   * @param type           Model class type for query and returning objects.
   * @return The list of records fetched from the database.
   */
  public abstract <T extends ActiveRecord> List<T> all(ActiveRelation activeRelation, Class<T> type);

  /**
   * Fetch the first database record based on your ActiveRelation query.
   *
   * @param activeRelation The ActiveRelation instance.
   * @param type           Model class type for query and returning objects.
   * @return The first record fetched from the database.
   */
  public abstract <T extends ActiveRecord> T first(ActiveRelation activeRelation, Class<T> type);

  /**
   * Fetch the last database record based on your ActiveRelation query.
   *
   * @param activeRelation The ActiveRelation instance.
   * @param type           Model class type for query and returning objects.
   * @return The last record fetched from the database.
   */
  public abstract <T extends ActiveRecord> T last(ActiveRelation activeRelation, Class<T> type);

  /**
   * Converts your ActiveRelation query to a SQL String.
   *
   * @param activeRelation The ActiveRelation instance.
   * @param type           Model class type for query.
   * @return The SQL equivalent to your ActiveRelation.
   */
  public abstract <T extends ActiveRecord> String toSQL(ActiveRelation activeRelation, Class<T> type);
}
