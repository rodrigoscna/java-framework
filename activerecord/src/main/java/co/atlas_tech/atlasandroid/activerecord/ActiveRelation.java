package co.atlas_tech.atlasandroid.activerecord;

import co.atlas_tech.atlasandroid.activerecord.connectionadapters.AbstractAdapter;
import co.atlas_tech.atlasandroid.activesupport.annotations.Beta;

import java.util.List;

/**
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class ActiveRelation {
    private int mLimit;
    private int mOffset;
    private List<String> mJoins;
    private List<String> mOrderBy;
    private List<String> mConditions;
    private List<Object> mConditionsArguments;

    public int getLimit() {
        return mLimit;
    }

    public int getOffset() {
        return mOffset;
    }

    public List<String> getJoins() {
        return mJoins;
    }

    public List<String> getOrderBy() {
        return mOrderBy;
    }

    public List<String> getConditions() {
        return mConditions;
    }

    public List<Object> getConditionsArguments() {
        return mConditionsArguments;
    }

    public ActiveRelation joins(String joins) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ActiveRelation limit(int limit) {
        mLimit = limit;
        return this;
    }

    public ActiveRelation offset(int offset) {
        mOffset = offset;
        return this;
    }

    public ActiveRelation orderBy(String orderBy) {
        mOrderBy.add(orderBy);
        return this;
    }

    public ActiveRelation where(String conditions, Object... arguments) {
        mConditions.add(conditions);
        for (Object argument : arguments) {
            mConditionsArguments.add(argument);
        }
        return this;
    }

    public <T extends ActiveRecord> List<T> all(Class<T> type) {
        return AbstractAdapter.getAdapter().all(this, type);
    }

    public <T extends ActiveRecord> T first(Class<T> type) {
        return AbstractAdapter.getAdapter().first(this, type);
    }

    public <T extends ActiveRecord> T last(Class<T> type) {
        return AbstractAdapter.getAdapter().last(this, type);
    }

    public <T extends ActiveRecord> String toSQL(Class<T> type) {
        return AbstractAdapter.getAdapter().toSQL(this, type);
    }
}
