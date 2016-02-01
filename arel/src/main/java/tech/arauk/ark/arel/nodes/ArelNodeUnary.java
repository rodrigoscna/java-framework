package tech.arauk.ark.arel.nodes;

public class ArelNodeUnary extends ArelNode {
    public Object expr;

    public ArelNodeUnary(Object expr) {
        this.expr = expr;
    }
}
