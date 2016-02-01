package tech.arauk.ark.arel.connection;

import tech.arauk.ark.arel.visitors.ArelVisitor;

public interface ArelConnection {
    ArelVisitor getVisitor();

    String quoteColumnName(String columnName);

    String quoteTableName(String tableName);
}
