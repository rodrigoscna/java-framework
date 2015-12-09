package tech.arauk.ark.arel.nodes;

public class ArelNodeRightOuterJoin extends ArelNodeJoin {
    public ArelNodeRightOuterJoin(String left, String right, Class<? extends ArelNodeJoin> constraint) {
        super(left, right, constraint);
    }
}
