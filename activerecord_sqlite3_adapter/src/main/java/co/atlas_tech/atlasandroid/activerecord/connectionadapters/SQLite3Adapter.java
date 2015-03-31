package co.atlas_tech.atlasandroid.activerecord.connectionadapters;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import co.atlas_tech.atlasandroid.activerecord.ActiveRecord;
import co.atlas_tech.atlasandroid.activerecord.ActiveRelation;
import co.atlas_tech.atlasandroid.activerecord.connectionadapters.sqlite3.DataManipulationLanguage;
import co.atlas_tech.atlasandroid.activerecord.connectionadapters.sqlite3.DataTransactionLanguage;
import co.atlas_tech.atlasandroid.activesupport.annotations.Beta;

/**
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class SQLite3Adapter extends AbstractAdapter {
    private static SQLiteDatabase sSQLiteDatabase;
    private String mDatabaseName;
    private Integer mDatabaseFlags;

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
    public void initializeConnection() {
        if (sSQLiteDatabase == null) {
            String mDatabaseName = mConnectionSettings.getString("database_name");
            Integer mDatabaseFlags = mConnectionSettings.getInt("database_flags"); //

            if (mDatabaseName == null) {
                throw new RuntimeException("Database name not defined.");
            }

            if (mDatabaseFlags == null) {
                mDatabaseFlags = SQLiteDatabase.CREATE_IF_NECESSARY;
            }

//            sSQLiteDatabase = SQLiteDatabase.openDatabase(mDatabaseName, null, mDatabaseFlags);
        }
    }

    /**
     * Closes all database connections and terminates them.
     */
    @Override
    public void terminateConnection() {
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
     * Fetch the first N database records based on your ActiveRelation query.
     *
     * @param activeRelation The ActiveRelation instance.
     * @param type           Model class type for query and returning objects.
     * @param limit          The number of records to retrieve.
     * @return The first N records fetched from the database.
     */
    @Override
    public <T extends ActiveRecord> List<T> first(ActiveRelation activeRelation, Class<T> type, Integer limit) {
        return DataManipulationLanguage.fetchFirst(getConnection(), activeRelation, type, limit);
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
     * Fetch the last N database records based on your ActiveRelation query.
     *
     * @param activeRelation The ActiveRelation instance.
     * @param type           Model class type for query and returning objects.
     * @param limit          The number of records to retrieve.
     * @return The last N records fetched from the database.
     */
    @Override
    public <T extends ActiveRecord> List<T> last(ActiveRelation activeRelation, Class<T> type, Integer limit) {
        return DataManipulationLanguage.fetchLast(getConnection(), activeRelation, type, limit);
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
