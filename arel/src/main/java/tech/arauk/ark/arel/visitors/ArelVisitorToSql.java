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

    public ArelCollector visitArelNodeInsertStatement(Object object, ArelCollector collector) {
        ArelNodeInsertStatement insertStatement = (ArelNodeInsertStatement) object;

        collector.append("INSERT INTO ");
        collector = visit(insertStatement.relation, collector);

        if (insertStatement.columns != null && !insertStatement.columns.isEmpty()) {
            List<String> quotedColumns = new ArrayList<>();
            for (String column : insertStatement.columns) {
                quotedColumns.add(quoteColumnName(column));
            }
            collector.append(String.format(" (%s)", ArelUtils.join(quotedColumns, ", ")));
        }

        if (insertStatement.values != null) {
            return maybeVisit(insertStatement.values, collector);
        } else if (insertStatement.select != null) {
            return maybeVisit(insertStatement.select, collector);
        } else {
            return collector;
        }
    }

    public ArelCollector visitArelNodeJoinSource(Object object, ArelCollector collector) {
        ArelNodeJoinSource joinSource = (ArelNodeJoinSource) object;

        if (joinSource.left != null) {
            collector = visit(joinSource.left, collector);
        }

        if (joinSource.right != null && joinSource.right.size() > 0) {
            if (joinSource.left != null) {
                collector.append(SPACE);
            }
            collector = injectJoin(joinSource.right, collector, SPACE);
        }

        return collector;
    }

    public ArelCollector visitArelNodeOffset(Object object, ArelCollector collector) {
        ArelNodeOffset offset = (ArelNodeOffset) object;

        collector.append(OFFSET);

        if (offset.expr != null) {
            collector = visit(offset.expr, collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeSelectStatement(Object object, ArelCollector collector) {
        ArelNodeSelectStatement selectStatement = (ArelNodeSelectStatement) object;

        if (selectStatement.with != null) {
            collector = visit(selectStatement.with, collector);
            collector.append(SPACE);
        }

        for (ArelNodeSelectCore selectCore : selectStatement.cores) {
            collector = visitArelNodeSelectCore(selectCore, collector);
        }

        if (selectStatement.orders != null && selectStatement.orders.length > 0) {
            collector.append(ORDER_BY);

            int len = selectStatement.orders.length - 1;

            for (int i = 0; i < selectStatement.orders.length; i++) {
                collector = visit(selectStatement.orders[i], collector);
                if (len != i) {
                    collector.append(COMMA);
                }
            }
        }

        collector = visitArelNodeSelectOptions(object, collector);

        return collector;
    }

    public ArelCollector visitArelNodeSelectCore(Object object, ArelCollector collector) {
        ArelNodeSelectCore selectCore = (ArelNodeSelectCore) object;

        collector.append("SELECT");

        collector = maybeVisit(selectCore.top, collector);

        collector = maybeVisit(selectCore.setQuantifier, collector);

        if (selectCore.projections != null && selectCore.projections.length > 0) {
            collector.append(SPACE);

            int len = selectCore.projections.length - 1;

            for (int i = 0; i < selectCore.projections.length; i++) {
                collector = visit(selectCore.projections[i], collector);
                if (len != i) {
                    collector.append(COMMA);
                }
            }
        }

        if (selectCore.source != null) {
            collector.append(" FROM ");
            collector = visit(selectCore.source, collector);
        }

        if (selectCore.wheres != null && selectCore.wheres.length > 0) {
            collector.append(WHERE);

            int len = selectCore.wheres.length - 1;

            for (int i = 0; i < selectCore.wheres.length; i++) {
                collector = visit(selectCore.wheres[i], collector);
                if (len != i) {
                    collector.append(AND);
                }
            }
        }

        if (selectCore.groups != null && selectCore.groups.length > 0) {
            collector.append(GROUP_BY);

            int len = selectCore.groups.length - 1;

            for (int i = 0; i < selectCore.groups.length; i++) {
                collector = visit(selectCore.groups[i], collector);
                if (len != i) {
                    collector.append(COMMA);
                }
            }
        }

        if (selectCore.havings != null && selectCore.havings.length > 0) {
            collector.append(" HAVING ");
            injectJoin(selectCore.havings, collector, AND);
        }

        if (selectCore.windows != null && selectCore.windows.length > 0) {
            collector.append(WINDOW);

            int len = selectCore.windows.length - 1;

            for (int i = 0; i < selectCore.windows.length; i++) {
                collector = visit(selectCore.windows[i], collector);
                if (len != i) {
                    collector.append(COMMA);
                }
            }
        }

        return collector;
    }

    public ArelCollector visitArelNodeSelectOptions(Object object, ArelCollector collector) {
        ArelNodeSelectStatement selectStatement = (ArelNodeSelectStatement) object;

        maybeVisit(selectStatement.limit, collector);
        maybeVisit(selectStatement.offset, collector);
        maybeVisit(selectStatement.lock, collector);

        return collector;
    }

    public ArelCollector visitArelNodeSqlLiteral(Object object, ArelCollector collector) {
        return literal(object, collector);
    }

    public ArelCollector visitArelTable(Object object, ArelCollector collector) {
        ArelTable table = (ArelTable) object;

        if ((table.tableAlias != null) && (table.tableAlias.length() > 0)) {
            collector.append(String.format("%s %s", quoteTableName(table.tableName), quoteTableName(table.tableAlias)));
        } else {
            collector.append(quoteTableName(table.tableName));
        }
        return collector;
    }

    public ArelCollector visitInteger(Object object, ArelCollector collector) {
        return literal(object, collector);
    }

    private ArelCollector literal(Object object, ArelCollector collector) {
        collector.append(String.valueOf(object));
        return collector;
    }

    private ArelCollector maybeVisit(Object thing, ArelCollector collector) {
        if (thing == null) {
            return collector;
        }

        collector.append(SPACE);
        visit(thing, collector);

        return collector;
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
