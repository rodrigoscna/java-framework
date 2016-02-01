package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeJoin;
import tech.arauk.ark.arel.visitors.ArelVisitor;

public class ArelTable {
    public static ArelVisitor engine;

    public String tableName;
    public String tableAlias;

    public ArelTable(String name) {
        tableName = name;
    }

    public ArelTable(String name, String as) {
        tableName = name;

        if (as == tableName) {
            as = null;
        }

        tableAlias = as;
    }

    public ArelNodeJoin createJoin(String to) {
        return ArelNodeFactory.createJoin(to);
    }

    public ArelNodeJoin createJoin(String to, String constraint) {
        return ArelNodeFactory.createJoin(to, constraint);
    }

    public ArelNodeJoin createJoin(String to, String constraint, Class<? extends ArelNodeJoin> kclass) {
        return ArelNodeFactory.createJoin(to, constraint, kclass);
    }

    public ArelNodeJoin createStringJoin(String to) {
        return ArelNodeFactory.createStringJoin(to);
    }

    public ArelInsertManager createInsert() {
        return new ArelInsertManager();
    }

    public ArelInsertManager compileInsert(String values) {
        ArelInsertManager insertManager = createInsert();
        insertManager.insert(values);
        return insertManager;
    }

    public ArelSelectManager from() {
        return new ArelSelectManager(this);
    }

    public ArelSelectManager skip(int amount) {
        return from().skip(amount);
    }
}
