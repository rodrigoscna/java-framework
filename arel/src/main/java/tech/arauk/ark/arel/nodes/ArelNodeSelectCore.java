package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.nodes.binary.ArelNodeJoinSource;
import tech.arauk.ark.arel.nodes.unary.ArelNodeTop;

public class ArelNodeSelectCore extends ArelNode {
    public ArelNodeJoinSource source;
    public ArelNodeTop top;
    public ArelNode setQuantifier;
    public Object[] projections;
    public Object[] wheres;
    public Object[] groups;
    public Object[] havings;
    public Object[] windows;

    public ArelNodeSelectCore() {
        super();
        this.source = new ArelNodeJoinSource(null);
    }
}
