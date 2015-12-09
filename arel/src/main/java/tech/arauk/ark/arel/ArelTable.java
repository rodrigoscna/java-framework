package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeJoin;

public class ArelTable {
    private String pTableName;
    private String pTableAlias;

    public ArelTable(String name) {
        pTableName = name;
    }

    public ArelTable(String name, String as) {
        pTableName = name;

        if (as == pTableName) {
            as = null;
        }

        pTableAlias = as;
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
}
