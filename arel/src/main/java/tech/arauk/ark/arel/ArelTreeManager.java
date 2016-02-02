package tech.arauk.ark.arel;

import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.nodes.ArelNode;
import tech.arauk.ark.arel.visitors.ArelVisitor;

public class ArelTreeManager {
    public ArelNode ast;

    public ArelTreeManager(ArelNode ast) {
        this.ast = ast;
    }

    public String toSQL() {
        ArelVisitor visitor = ArelTable.engine;
        return this.toSQL(visitor);
    }

    public String toSQL(ArelVisitor visitor) {
        ArelCollector collector = new ArelCollector();
        collector = visitor.connection.getVisitor().accept(this.ast, collector);
        return collector.getValue();
    }
}
