package tech.arauk.ark.arel.visitors;

import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.ArelUtils;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.nodes.ArelNodeInsertStatement;
import tech.arauk.ark.arel.nodes.ArelNodeSelectCore;
import tech.arauk.ark.arel.nodes.ArelNodeSelectStatement;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;
import tech.arauk.ark.arel.nodes.binary.ArelNodeJoinSource;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOffset;

import java.util.ArrayList;
import java.util.List;

public class ArelVisitorToSql extends ArelVisitor {
    private static final String AND = " AND ";
    private static final String COMMA = ", ";
    private static final String GROUP_BY = " GROUP BY ";
    private static final String OFFSET = "OFFSET ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String SPACE = " ";
    private static final String WHERE = " WHERE ";
    private static final String WINDOW = " WINDOW ";

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

    public ArelCollector visitArelNodeJoinSource(Object object, ArelCollector arelCollector) {
        ArelNodeJoinSource arelNodeJoinSource = (ArelNodeJoinSource) object;

        if (arelNodeJoinSource.left != null) {
            arelCollector = visit(arelNodeJoinSource.left, arelCollector);
        }

        if (arelNodeJoinSource.right != null && arelNodeJoinSource.right.size() > 0) {
            if (arelNodeJoinSource.left != null) {
                arelCollector.append(SPACE);
            }
            arelCollector = injectJoin(arelNodeJoinSource.right, arelCollector, SPACE);
        }

        return arelCollector;
    }

    public ArelCollector visitArelNodeOffset(Object object, ArelCollector arelCollector) {
        ArelNodeOffset arelNodeOffset = (ArelNodeOffset) object;

        arelCollector.append(OFFSET);

        if (arelNodeOffset.expr != null) {
            arelCollector = visit(arelNodeOffset.expr, arelCollector);
        }

        return arelCollector;
    }

    public ArelCollector visitArelNodeSelectStatement(Object object, ArelCollector arelCollector) {
        ArelNodeSelectStatement arelNodeSelectStatement = (ArelNodeSelectStatement) object;

        if (arelNodeSelectStatement.with != null) {
            arelCollector = visit(arelNodeSelectStatement.with, arelCollector);
            arelCollector.append(SPACE);
        }

        for (ArelNodeSelectCore arelNodeSelectCore : arelNodeSelectStatement.cores) {
            arelCollector = visitArelNodeSelectCore(arelNodeSelectCore, arelCollector);
        }

        if (arelNodeSelectStatement.orders != null && arelNodeSelectStatement.orders.length > 0) {
            arelCollector.append(ORDER_BY);

            int len = arelNodeSelectStatement.orders.length - 1;

            for (int i = 0; i < arelNodeSelectStatement.orders.length; i++) {
                arelCollector = visit(arelNodeSelectStatement.orders[i], arelCollector);
                if (len != i) {
                    arelCollector.append(COMMA);
                }
            }
        }

        arelCollector = visitArelNodeSelectOptions(object, arelCollector);

        return arelCollector;
    }

    public ArelCollector visitArelNodeSelectCore(Object object, ArelCollector arelCollector) {
        ArelNodeSelectCore arelNodeSelectCore = (ArelNodeSelectCore) object;

        arelCollector.append("SELECT");

        arelCollector = maybeVisit(arelNodeSelectCore.top, arelCollector);

        arelCollector = maybeVisit(arelNodeSelectCore.setQuantifier, arelCollector);

        if (arelNodeSelectCore.projections != null && arelNodeSelectCore.projections.length > 0) {
            arelCollector.append(SPACE);

            int len = arelNodeSelectCore.projections.length - 1;

            for (int i = 0; i < arelNodeSelectCore.projections.length; i++) {
                arelCollector = visit(arelNodeSelectCore.projections[i], arelCollector);
                if (len != i) {
                    arelCollector.append(COMMA);
                }
            }
        }

        if (arelNodeSelectCore.source != null) {
            arelCollector.append(" FROM ");
            arelCollector = visit(arelNodeSelectCore.source, arelCollector);
        }

        if (arelNodeSelectCore.wheres != null && arelNodeSelectCore.wheres.length > 0) {
            arelCollector.append(WHERE);

            int len = arelNodeSelectCore.wheres.length - 1;

            for (int i = 0; i < arelNodeSelectCore.wheres.length; i++) {
                arelCollector = visit(arelNodeSelectCore.wheres[i], arelCollector);
                if (len != i) {
                    arelCollector.append(AND);
                }
            }
        }

        if (arelNodeSelectCore.groups != null && arelNodeSelectCore.groups.length > 0) {
            arelCollector.append(GROUP_BY);

            int len = arelNodeSelectCore.groups.length - 1;

            for (int i = 0; i < arelNodeSelectCore.groups.length; i++) {
                arelCollector = visit(arelNodeSelectCore.groups[i], arelCollector);
                if (len != i) {
                    arelCollector.append(COMMA);
                }
            }
        }

        if (arelNodeSelectCore.havings != null && arelNodeSelectCore.havings.length > 0) {
            arelCollector.append(" HAVING ");
            injectJoin(arelNodeSelectCore.havings, arelCollector, AND);
        }

        if (arelNodeSelectCore.windows != null && arelNodeSelectCore.windows.length > 0) {
            arelCollector.append(WINDOW);

            int len = arelNodeSelectCore.windows.length - 1;

            for (int i = 0; i < arelNodeSelectCore.windows.length; i++) {
                arelCollector = visit(arelNodeSelectCore.windows[i], arelCollector);
                if (len != i) {
                    arelCollector.append(COMMA);
                }
            }
        }

        return arelCollector;
    }

    public ArelCollector visitArelNodeSelectOptions(Object object, ArelCollector arelCollector) {
        ArelNodeSelectStatement arelNodeSelectStatement = (ArelNodeSelectStatement) object;

        maybeVisit(arelNodeSelectStatement.limit, arelCollector);
        maybeVisit(arelNodeSelectStatement.offset, arelCollector);
        maybeVisit(arelNodeSelectStatement.lock, arelCollector);

        return arelCollector;
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

    public ArelCollector visitInteger(Object object, ArelCollector arelCollector) {
        return literal(object, arelCollector);
    }

    private ArelCollector literal(Object object, ArelCollector arelCollector) {
        arelCollector.append(String.valueOf(object));
        return arelCollector;
    }

    private ArelCollector maybeVisit(Object thing, ArelCollector arelCollector) {
        if (thing == null) {
            return arelCollector;
        }

        arelCollector.append(SPACE);
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

    private ArelCollector injectJoin(List<Object> list, ArelCollector collector, String joinStr) {
        int len = list.size() - 1;

        for (int i = 0; i < list.size(); i++) {
            collector = visit(list.get(i), collector);

            if (i != len) {
                collector.append(joinStr);
            }
        }

        return collector;
    }

    private ArelCollector injectJoin(Object[] list, ArelCollector collector, String joinStr) {
        int len = list.length - 1;

        for (int i = 0; i < list.length; i++) {
            collector = visit(list[i], collector);

            if (i != len) {
                collector.append(joinStr);
            }
        }

        return collector;
    }
}
