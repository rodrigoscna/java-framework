package tech.arauk.ark.arel.support;

import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.visitors.ArelVisitor;
import tech.arauk.ark.arel.visitors.ArelVisitorToSql;

public class FakeRecord {
    public static class Column {
    }

    public static class Connection implements ArelConnection {
        private ArelVisitorToSql arelVisitor;

        @Override
        public ArelVisitor getVisitor() {
            return arelVisitor;
        }

        @Override
        public String quoteColumnName(String columnName) {
            return String.format("\"%s\"", columnName);
        }

        @Override
        public String quoteTableName(String tableName) {
            return String.format("\"%s\"", tableName);
        }
    }

    public static class ConnectionPool {
        private Connection connection;

        public ConnectionPool() {
            this.connection = new Connection();
            this.connection.arelVisitor = new ArelVisitorToSql(this.connection);
        }
    }

    public static class Base extends ArelVisitor {
        private ConnectionPool connectionPool;

        public Base() {
            super(new ConnectionPool().connection);
        }
    }
}
