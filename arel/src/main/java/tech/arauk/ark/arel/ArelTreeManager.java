package tech.arauk.ark.arel;

import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.nodes.ArelNodeStatement;
import tech.arauk.ark.arel.visitors.ArelVisitor;

public class ArelTreeManager {
    public ArelNodeStatement arelStatement;

    public ArelTreeManager(ArelNodeStatement arelStatement) {
        this.arelStatement = arelStatement;
    }

    public String toSQL() {
        ArelVisitor arelVisitor = ArelTable.engine;
        return this.toSQL(arelVisitor);
    }

    public String toSQL(ArelVisitor arelVisitor) {
        ArelCollector collector = new ArelCollector();
        collector = arelVisitor.connection.getVisitor().accept(this.arelStatement, collector);
        return collector.getValue();
    }
}
