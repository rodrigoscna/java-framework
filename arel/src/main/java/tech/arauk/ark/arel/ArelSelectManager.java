package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeJoin;
import tech.arauk.ark.arel.nodes.ArelNodeSelectCore;
import tech.arauk.ark.arel.nodes.ArelNodeSelectStatement;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOffset;

public class ArelSelectManager extends ArelTreeManager {
    private ArelNodeSelectCore mCtx;

    public ArelSelectManager() {
        super(new ArelNodeSelectStatement());

        ArelNodeSelectCore[] cores = ((ArelNodeSelectStatement) this.arelStatement).cores;

        this.mCtx = cores[cores.length - 1];
    }

    public ArelSelectManager(Object table) {
        super(new ArelNodeSelectStatement());

        ArelNodeSelectCore[] cores = ((ArelNodeSelectStatement) this.arelStatement).cores;

        this.mCtx = cores[cores.length - 1];

        this.from(table);
    }

    public ArelSelectManager from(Object object) {
        Object table;

        if (object instanceof String) {
            table = new ArelNodeSqlLiteral(object);
        } else {
            table = object;
        }

        if (table instanceof ArelNodeJoin) {
            this.mCtx.source.right.add(table);
        } else {
            this.mCtx.source.left = table;
        }

        return this;
    }

    public ArelSelectManager offset(int amount) {
        return this.skip(amount);
    }

    public ArelSelectManager offset(ArelNodeOffset amount) {
        return this.skip(amount);
    }

    public ArelSelectManager skip(int amount) {
        return this.skip(new ArelNodeOffset(amount));
    }

    public ArelSelectManager skip(ArelNodeOffset amount) {
        ((ArelNodeSelectStatement) this.arelStatement).offset = amount;
        return this;
    }
}
