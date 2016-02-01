package tech.arauk.ark.arel.nodes;

import java.util.List;

public class ArelNodeBinary extends ArelNode {
    public Object left;
    public List<Object> right;

    public ArelNodeBinary(Object left, List<Object> right) {
        super();
        this.left = left;
        this.right = right;
    }
}
