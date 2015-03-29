package co.atlas_tech.atlasandroid.activerecord;

import java.util.ArrayList;
import java.util.List;

import co.atlas_tech.atlasandroid.activerecord.connectionadapters.AbstractAdapter;
import co.atlas_tech.atlasandroid.activesupport.annotations.Beta;

/**
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class ActiveRelation {
    private AbstractAdapter mConnectionAdapter;
    private Integer mLimit;
    private Integer mOffset;
    private List<String> mConditions;
    private List<Object> mConditionsArguments;
    private List<String> mJoins;
    private List<String> mOrder;
    private List<String> mSelect;

    /**
     * Returns the current connection adapter for this ActiveRelation.
     *
     * @return The current ConnectionAdapter of this object.
     */
    public AbstractAdapter getConnectionAdapter() {
        if (mConnectionAdapter != null) {
            return mConnectionAdapter;
        } else {
            throw new RuntimeException("Database adapter not configured for ActiveRelation.");
        }
    }

    public void setConnectionAdapter(AbstractAdapter connectionAdapter) {
        mConnectionAdapter = connectionAdapter;
    }

    public Integer getLimit() {
        return mLimit;
    }

    public Boolean hasLimit() {
        return getLimit() != null;
    }

    public Integer getOffset() {
        return mOffset;
    }

    public Boolean hasOffset() {
        return getOffset() != null;
    }

    public List<String> getConditions() {
        if (mConditions == null) {
            mConditions = new ArrayList<String>();
        }
        return mConditions;
    }

    public Boolean hasConditions() {
        return !getConditions().isEmpty();
    }

    public List<Object> getConditionsArguments() {
        if (mConditionsArguments == null) {
            mConditionsArguments = new ArrayList<Object>();
        }
        return mConditionsArguments;
    }

    public Boolean hasConditionsArguments() {
        return !getConditionsArguments().isEmpty();
    }

    public List<String> getJoins() {
        if (mJoins == null) {
            mJoins = new ArrayList<String>();
        }
        return mJoins;
    }

    public Boolean hasJoins() {
        return !getJoins().isEmpty();
    }

    public List<String> getOrder() {
        if (mOrder == null) {
            mOrder = new ArrayList<String>();
        }
        return mOrder;
    }

    public Boolean hasOrder() {
        return !getOrder().isEmpty();
    }

    public List<String> getSelect() {
        if (mSelect == null) {
            mSelect = new ArrayList<String>();
        }
        return mSelect;
    }

    public Boolean hasSelect() {
        return !getSelect().isEmpty();
    }

    public ActiveRelation joins(String joins) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ActiveRelation limit(Integer limit) {
        mLimit = limit;
        return this;
    }

    public ActiveRelation offset(Integer offset) {
        mOffset = offset;
        return this;
    }

    public ActiveRelation order(String order) {
        getOrder().add(order);
        return this;
    }

    public ActiveRelation reorder(String order) {
        getOrder().clear();
        return this.order(order);
    }

    public ActiveRelation select(String... fields) {
        for (String field : fields) {
            getSelect().add(field);
        }
        return this;
    }

    public ActiveRelation where(String conditions, Object... arguments) {
        getConditions().add(conditions);
        for (Object argument : arguments) {
            getConditionsArguments().add(argument);
        }
        return this;
    }

    public <T extends ActiveRecord> List<T> all(Class<T> type) {
        return getConnectionAdapter().all(this, type);
    }

    public <T extends ActiveRecord> T first(Class<T> type) {
        return getConnectionAdapter().first(this, type);
    }

    public <T extends ActiveRecord> List<T> first(Class<T> type, Integer limit) {
        return getConnectionAdapter().first(this, type, limit);
    }

    public <T extends ActiveRecord> T last(Class<T> type) {
        return getConnectionAdapter().last(this, type);
    }

    public <T extends ActiveRecord> List<T> last(Class<T> type, Integer limit) {
        return getConnectionAdapter().last(this, type, limit);
    }

    public <T extends ActiveRecord> String toSQL(Class<T> type) {
        return getConnectionAdapter().toSQL(this, type);
    }
}
