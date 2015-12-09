package tech.arauk.ark.arel.nodes;

public class ArelNodeFullOuterJoin extends ArelNodeJoin {
    public ArelNodeFullOuterJoin(String left, String right, Class<? extends ArelNodeJoin> constraint) {
        super(left, right, constraint);
    }
}
