package co.atlas_tech.atlasandroid.activerecord;

import co.atlas_tech.atlasandroid.activemodel.ActiveModel;
import co.atlas_tech.atlasandroid.activerecord.connectionadapters.AbstractAdapter;
import co.atlas_tech.atlasandroid.activesupport.annotations.Beta;

/**
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class ActiveRecord extends ActiveModel {
    private static AbstractAdapter mConnectionAdapter;
    private ActiveRelation mActiveRelation;

    private ActiveRelation getActiveRelation() {
        if (mActiveRelation == null) {
            mActiveRelation = new ActiveRelation();
        }

        return mActiveRelation;
    }

    public ActiveRelation joins(String join) {
        return getActiveRelation().joins(join);
    }

    public ActiveRelation limit(int limit) {
        return getActiveRelation().limit(limit);
    }

    public ActiveRelation offset(int offset) {
        return getActiveRelation().offset(offset);
    }

    public ActiveRelation orderBy(String orderBy) {
        return getActiveRelation().orderBy(orderBy);
    }

    public ActiveRelation where(String conditions, Object... arguments) {
        return getActiveRelation().where(conditions, arguments);
    }

    public <T extends ActiveRecord> T first(Class<T> type) {
        return getActiveRelation().first(type);
    }

    public <T extends ActiveRecord> T last(Class<T> type) {
        return getActiveRelation().last(type);
    }

    /**
     * Establishes the connection to the database.
     * @param connectionAdapter
     */
    public static void establishConnection(AbstractAdapter connectionAdapter) {
        connectionAdapter.initializeConnection();
    }


}
