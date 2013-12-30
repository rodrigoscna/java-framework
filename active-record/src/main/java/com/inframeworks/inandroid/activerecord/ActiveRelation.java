package com.inframeworks.inandroid.activerecord;

import com.inframeworks.inandroid.activesupport.annotations.Beta;

import java.util.List;

/**
 * @author rodrigoscna
 */
@Beta
public class ActiveRelation {
  private int mLimit;
  private int mOffset;
  private List<String> mJoins;
  private List<String> mOrderBy;
  private List<String> mConditions;
  private List<Object> mConditionsArguments;

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

  public <T extends ActiveRecord> T first(Class<T> type) {
    return type.cast(this);
  }

  public <T extends ActiveRecord> T last(Class<T> type) {
    return type.cast(this);
  }
}
