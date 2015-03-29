package co.atlas_tech.atlasandroid.activerecord;

import java.util.List;

import co.atlas_tech.atlasandroid.activemodel.ActiveModel;
import co.atlas_tech.atlasandroid.activerecord.connectionadapters.AbstractAdapter;
import co.atlas_tech.atlasandroid.activesupport.annotations.Beta;

/**
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class ActiveRecord extends ActiveModel {
    private static AbstractAdapter mConnectionAdapter;

    /**
     * Establishes the connection to the database.
     *
     * @param connectionAdapter
     */
    public static void establishConnection(AbstractAdapter connectionAdapter) {
        mConnectionAdapter = connectionAdapter;
        mConnectionAdapter.initializeConnection();
    }

    /**
     * Returns the current connection adapter for the ActiveRecord.
     *
     * @return The current ConnectionAdapter for the ActiveRecord.
     */
    public static AbstractAdapter getConnectionAdapter() {
        return mConnectionAdapter;
    }

    private static ActiveRelation getActiveRelation() {
        ActiveRelation activeRelation = new ActiveRelation();
        activeRelation.setConnectionAdapter(getConnectionAdapter());

        return activeRelation;
    }

    /**
     * Performs a joins using the specified argument.
     *
     * @param join A string specified the join clause to be used in the query.
     * @return A new relation containing the new join clause.
     */
    public static ActiveRelation joins(String join) {
        return getActiveRelation().joins(join);
    }

    /**
     * Specifies a limit for the number of records to retrieve.
     *
     * @param limit The number of records to retrieve.
     * @return A new relation containing the new limit of records to be
     * retrieved.
     */
    public static ActiveRelation limit(Integer limit) {
        return getActiveRelation().limit(limit);
    }

    /**
     * Specifies the number of rows to skip before returning rows.
     *
     * @param offset The number of records to skip from the query results.
     * @return A new relation containing the new offset of records to be
     * retrieved.
     */
    public static ActiveRelation offset(Integer offset) {
        return getActiveRelation().offset(offset);
    }

    /**
     * Allows to specify an order attribute.
     *
     * @param order The desired order in which the records will be retrieved.
     * @return A new relation containing the order of records retrieval.
     */
    public static ActiveRelation order(String order) {
        return getActiveRelation().order(order);
    }

    /**
     * Replaces any existing order defined on the relation with the specified
     * order.
     *
     * @param order The desired order in which the records will be retrieved.
     * @return A new relation containing the new order of records retrieval.
     */
    public static ActiveRelation reorder(String order) {
        return getActiveRelation().reorder(order);
    }

    /**
     * Modifies the SELECT statement for the query so that only certain fields
     * are retrieved:
     *
     * @param fields The desired fields to be retrieved for the records.
     * @return A new relation containing the fields restriction rule.
     */
    public static ActiveRelation select(String... fields) {
        return getActiveRelation().select(fields);
    }

    /**
     * Returns a new relation, which is the result of filtering the current
     * relation according to the conditions in the arguments.
     *
     * @param conditions A single string, without additional arguments, is
     *                   passed to the query constructor as an SQL fragment, and
     *                   used in the where clause of the query.
     * @param arguments  If an arbitrary number of arguments is passed, then the
     *                   first argument is treated as a template, and the
     *                   remaining elements are inserted into the template to
     *                   generate the condition.
     * @return A new relation filtered according to the new conditions.
     */
    public static ActiveRelation where(String conditions, Object... arguments) {
        return getActiveRelation().where(conditions, arguments);
    }

    /**
     * Retrieves a list of records according to the current relation's
     * conditions.
     *
     * @param type The class to be used for instantiating the results.
     * @return The list of records retrieved by the defined query.
     */
    public static <T extends ActiveRecord> List<T> all(Class<T> type) {
        return getActiveRelation().all(type);
    }

    /**
     * Find the first record. If no order is defined it will order by primary
     * key.
     *
     * @param type The class to be used for instantiating the results.
     * @return The first record retrieved by the defined query.
     */
    public static <T extends ActiveRecord> T first(Class<T> type) {
        return getActiveRelation().first(type);
    }

    /**
     * Find the first N records. If no order is defined it will order by primary
     * key.
     *
     * @param type  The class to be used for instantiating the results.
     * @param limit The number of records to retrieve.
     * @return The first N record retrieved by the defined query.
     */
    public static <T extends ActiveRecord> List<T> first(Class<T> type, Integer limit) {
        return getActiveRelation().first(type, limit);
    }

    /**
     * Find the last record. If no order is defined it will order by primary
     * key.
     *
     * @param type The class to be used for instantiating the results.
     * @return The last record retrieved by the defined query.
     */
    public static <T extends ActiveRecord> T last(Class<T> type) {
        return getActiveRelation().last(type);
    }

    /**
     * Find the last N records. If no order is defined it will order by primary
     * key.
     *
     * @param type  The class to be used for instantiating the results.
     * @param limit The number of records to retrieve.
     * @return The last N record retrieved by the defined query.
     */
    public static <T extends ActiveRecord> List<T> last(Class<T> type, Integer limit) {
        return getActiveRelation().last(type, limit);
    }
}
