package tech.arauk.ark.arel.nodes.unary;

import tech.arauk.ark.arel.nodes.ArelNodeUnary;

public class ArelNodeWith extends ArelNodeUnary {
    public ArelNodeWith(Object expr) {
        super(expr);
    }

    public Object children() {
        return expr;
    }
}
