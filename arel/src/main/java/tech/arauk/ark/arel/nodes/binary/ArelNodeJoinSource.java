package tech.arauk.ark.arel.nodes.binary;

import tech.arauk.ark.arel.nodes.ArelNodeBinary;

import java.util.ArrayList;
import java.util.List;

public class ArelNodeJoinSource extends ArelNodeBinary {
    public ArelNodeJoinSource(Object singleSource) {
        super(singleSource, new ArrayList<>());
    }

    public ArelNodeJoinSource(Object singleSource, List<Object> joinOp) {
        super(singleSource, joinOp);
    }
}
