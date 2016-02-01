package tech.arauk.ark.arel.visitors;

import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.ArelUtils;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.nodes.ArelNodeInsertStatement;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;

import java.util.ArrayList;
import java.util.List;

public class ArelVisitorToSql extends ArelVisitor {
    public ArelVisitorToSql(ArelConnection arelConnection) {
        super(arelConnection);
    }

    public ArelCollector visitArelNodeInsertStatement(Object object, ArelCollector arelCollector) {
        ArelNodeInsertStatement arelNodeInsertStatement = (ArelNodeInsertStatement) object;

        arelCollector.append("INSERT INTO ");
        arelCollector = visit(arelNodeInsertStatement.relation, arelCollector);

        if (arelNodeInsertStatement.columns != null && !arelNodeInsertStatement.columns.isEmpty()) {
            List<String> quotedColumns = new ArrayList<>();
            for (String column : arelNodeInsertStatement.columns) {
                quotedColumns.add(quoteColumnName(column));
            }
            arelCollector.append(String.format(" (%s)", ArelUtils.join(quotedColumns, ", ")));
        }

        if (arelNodeInsertStatement.values != null) {
            return maybeVisit(arelNodeInsertStatement.values, arelCollector);
        } else if (arelNodeInsertStatement.select != null) {
            return maybeVisit(arelNodeInsertStatement.select, arelCollector);
        } else {
            return arelCollector;
        }
    }

    public ArelCollector visitArelNodeSqlLiteral(Object object, ArelCollector arelCollector) {
        return literal(object, arelCollector);
    }

    public ArelCollector visitArelTable(Object object, ArelCollector arelCollector) {
        ArelTable arelTable = (ArelTable) object;
        if ((arelTable.tableAlias != null) && (arelTable.tableAlias.length() > 0)) {
            arelCollector.append(String.format("%s %s", quoteTableName(arelTable.tableName), quoteTableName(arelTable.tableAlias)));
        } else {
            arelCollector.append(quoteTableName(arelTable.tableName));
        }
        return arelCollector;
    }

    private ArelCollector literal(Object object, ArelCollector arelCollector) {
        arelCollector.append(String.valueOf(object));
        return arelCollector;
    }

    private ArelCollector maybeVisit(Object thing, ArelCollector arelCollector) {
        if (thing == null) {
            return arelCollector;
        }

        arelCollector.append(" ");
        visit(thing, arelCollector);

        return arelCollector;
    }

    private String quoteColumnName(Object columnName) {
        if (columnName instanceof ArelNodeSqlLiteral) {
            return ((ArelNodeSqlLiteral) columnName).getValue();
        } else {
            return this.connection.quoteColumnName(String.valueOf(columnName));
        }
    }

    private String quoteTableName(Object tableName) {
        if (tableName instanceof ArelNodeSqlLiteral) {
            return ((ArelNodeSqlLiteral) tableName).getValue();
        } else {
            return this.connection.quoteTableName(String.valueOf(tableName));
        }
    }
}
