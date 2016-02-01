package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.nodes.unary.ArelNodeLimit;
import tech.arauk.ark.arel.nodes.unary.ArelNodeLock;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOffset;
import tech.arauk.ark.arel.nodes.unary.ArelNodeWith;

public class ArelNodeSelectStatement extends ArelNodeStatement {
    public ArelNodeLimit limit;
    public ArelNodeLock lock;
    public ArelNodeOffset offset;
    public ArelNodeWith with;
    public ArelNodeSelectCore[] cores;
    public Object[] orders;

    public ArelNodeSelectStatement() {
        super();
        this.cores = new ArelNodeSelectCore[]{new ArelNodeSelectCore()};
    }

    public ArelNodeSelectStatement(ArelNodeSelectCore[] cores) {
        super();
        this.cores = cores;
    }
}
