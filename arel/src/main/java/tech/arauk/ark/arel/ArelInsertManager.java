package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeInsertStatement;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;

public class ArelInsertManager extends ArelTreeManager {
    public ArelInsertManager() {
        super(new ArelNodeInsertStatement());
    }

    public ArelInsertManager into(ArelTable table) {
        ((ArelNodeInsertStatement) this.ast).relation = table;
        return this;
    }

    public void insert(String fields) {
        if ((fields == null) || (fields.length() == 0)) {
            return;
        }

        ((ArelNodeInsertStatement) this.ast).values = new ArelNodeSqlLiteral(fields);
    }
}
