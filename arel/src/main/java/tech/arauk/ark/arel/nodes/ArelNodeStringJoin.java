package tech.arauk.ark.arel.nodes;

public class ArelNodeStringJoin extends ArelNodeJoin {
    public ArelNodeStringJoin(String left, String right, Class<? extends ArelNodeJoin> constraint) {
        super(left, right, constraint);
    }
}
