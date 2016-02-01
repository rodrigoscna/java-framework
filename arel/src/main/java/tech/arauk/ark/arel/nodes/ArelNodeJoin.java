package tech.arauk.ark.arel.nodes;

public class ArelNodeJoin extends ArelNode {
    public String left;
    public String right;
    public Class<? extends ArelNodeJoin> constraint;

    public ArelNodeJoin(String left, String right, Class<? extends ArelNodeJoin> constraint) {
        super();
        this.left = left;
        this.right = right;
        this.constraint = constraint;
    }
}
