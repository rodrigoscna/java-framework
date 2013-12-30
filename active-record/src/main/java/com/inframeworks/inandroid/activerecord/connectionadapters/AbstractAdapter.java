package com.inframeworks.inandroid.activerecord.connectionadapters;

import com.inframeworks.inandroid.activesupport.Application;
import com.inframeworks.inandroid.activesupport.annotations.Beta;

import java.lang.reflect.Constructor;

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
   * Converts your ActiveRelation query to a SQL String.
   *
   * @return The SQL equivalent to your ActiveRelation query.
   */
  public abstract String toSQL();
}
