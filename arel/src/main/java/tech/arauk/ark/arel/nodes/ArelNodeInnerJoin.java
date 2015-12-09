package tech.arauk.ark.arel.nodes;

public class ArelNodeInnerJoin extends ArelNodeJoin {
    public ArelNodeInnerJoin(String left, String right, Class<? extends ArelNodeJoin> constraint) {
        super(left, right, constraint);
    }
}
